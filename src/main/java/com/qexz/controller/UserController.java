package com.qexz.controller;

import com.qexz.common.QexzConst;
import com.qexz.dto.AjaxResult;
import com.qexz.exception.QexzWebError;
import com.qexz.model.*;
import com.qexz.service.*;
import com.qexz.util.MD5;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static Log LOG = LogFactory.getLog(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private ContestService contestService;
    @Autowired
    private PositionService positionService;

    /**
     * 个人信息页面
     */
    @RequestMapping(value="/profile", method= RequestMethod.GET)
    public String profile(HttpServletRequest request, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::拦截器过滤处理
        if (currentAccount == null) {
            //用户未登录直接返回首页面
            return "redirect:/";
        }
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        return "/user/profile";
    }

    /**
     * 更改密码页面
     */
    @RequestMapping(value="/password", method= RequestMethod.GET)
    public String password(HttpServletRequest request, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::拦截器过滤处理
        if (currentAccount == null) {
            //用户未登录直接返回首页面
            return "redirect:/";
        }
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        return "/user/password";
    }

    /**
     * 考试记录页面
     */
    @RequestMapping(value="/myExam", method= RequestMethod.GET)
    public String myExam(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::拦截器过滤处理
        if (currentAccount == null) {
            //用户未登录直接返回首页面
            return "redirect:/";
        }
        Map<String, Object> data = gradeService.getGradesByStudentId(page, QexzConst.gradePageSize, currentAccount.getId());
        List<Grade> grades = (List<Grade>) data.get("grades");
        Set<Integer> contestIds = grades.stream().map(Grade::getContestId).collect(Collectors.toCollection(HashSet::new));
        List<Contest> contests = contestService.getContestsByContestIds(contestIds);
        List<Position> positions = positionService.getPositions();
        Map<Integer, String> positionId2name = positions.stream().
                collect(Collectors.toMap(Position::getId, Position::getName));
        for (Contest contest : contests) {
            contest.setpositionName(positionId2name.
                    getOrDefault(contest.getpositionId(), "未知科目"));
        }
        Map<Integer, Contest> id2contest = contests.stream().
                collect(Collectors.toMap(Contest::getId, contest -> contest));
        for (Grade grade : grades) {
            grade.setContest(id2contest.get(grade.getContestId()));
        }
        model.addAttribute(QexzConst.DATA, data);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        return "/user/myExam";
    }

    /**
     * 我的发帖页面
     */
    @RequestMapping(value="/myDiscussPost", method= RequestMethod.GET)
    public String myDiscussPost(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::拦截器过滤处理
        if (currentAccount == null) {
            //用户未登录直接返回首页面
            return "redirect:/";
        }
        Map<String, Object> data = postService.getPostsByAuthorId(page, QexzConst.postPageSize, currentAccount.getId());
        model.addAttribute(QexzConst.DATA, data);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        return "/user/myDiscussPost";
    }

    /**
     * 更新密码
     */
    @RequestMapping(value = "/api/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updatePassword(HttpServletRequest request, HttpServletResponse response) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmNewPassword = request.getParameter("confirmNewPassword");
            String md5OldPassword = MD5.md5(QexzConst.MD5_SALT+oldPassword);
            String md5NewPassword = MD5.md5(QexzConst.MD5_SALT+newPassword);
            if (StringUtils.isNotEmpty(newPassword) && StringUtils.isNotEmpty(confirmNewPassword)
                    && !newPassword.equals(confirmNewPassword)) {
                return AjaxResult.fixedError(QexzWebError.NOT_EQUALS_CONFIRM_PASSWORD);
            }
            User currentAccount = (User) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
            if (!currentAccount.getPassword().equals(md5OldPassword)) {
                return AjaxResult.fixedError(QexzWebError.WRONG_PASSWORD);
            }
            currentAccount.setPassword(md5NewPassword);
            boolean result = userService.updateUser(currentAccount);
            ajaxResult.setSuccess(result);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return AjaxResult.fixedError(QexzWebError.COMMON);
        }
        return ajaxResult;
    }

    /**
     * 更新个人信息
     */
    @RequestMapping(value = "/api/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateUser(HttpServletRequest request, HttpServletResponse response) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            String phone = request.getParameter("phone");
            String qq = request.getParameter("qq");
            String email = request.getParameter("email");
            String description = request.getParameter("description");
            String avatarImgUrl = request.getParameter("avatarImgUrl");

            User currentAccount = (User) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
            currentAccount.setPhone(phone);
            currentAccount.setQq(qq);
            currentAccount.setEmail(email);
            currentAccount.setDescription(description);
            currentAccount.setAvatar_img_url(avatarImgUrl);
            boolean result = userService.updateUser(currentAccount);
            ajaxResult.setSuccess(result);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return AjaxResult.fixedError(QexzWebError.COMMON);
        }
        return ajaxResult;
    }

    /**
     * 验证登录
     */
    @RequestMapping(value = "/api/userlogin", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult login(HttpServletRequest request, HttpServletResponse response) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User current_account = userService.getUserByUsername(username);
            if(current_account != null) {
                String pwd = MD5.md5(QexzConst.MD5_SALT+password);
                if(pwd.equals(current_account.getPassword())) {
                    //设置单位为秒，设置为-1永不过期
                    //request.getSession().setMaxInactiveInterval(180*60);    //3小时
                    request.getSession().setAttribute(QexzConst.CURRENT_ACCOUNT,current_account);
                    ajaxResult.setData(current_account);
                } else {
                    return AjaxResult.fixedError(QexzWebError.WRONG_PASSWORD);
                }
            } else {
                return AjaxResult.fixedError(QexzWebError.WRONG_USERNAME);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return AjaxResult.fixedError(QexzWebError.COMMON);
        }
        return ajaxResult;
    }

    /**
     * 用户退出
     * @param request
     * @return
     */
    @RequestMapping(value = "/userlogout", method= RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().setAttribute(QexzConst.CURRENT_ACCOUNT,null);
        String url=request.getHeader("Referer");
        LOG.info("url = " + url);
        return "redirect:"+url;
    }

    /**
     * 上传头像
     */
    @RequestMapping(value = "/api/uploadAvatar", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> uploadAvatar(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException{
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //原始名称
            String oldFileName = file.getOriginalFilename(); //获取上传文件的原名
            //存储图片的物理路径
            String file_path = QexzConst.UPLOAD_FILE_IMAGE_PATH;
            LOG.info("file_path = " + file_path);
            //上传图片
            if(file!=null && oldFileName!=null && oldFileName.length()>0){
                //新的图片名称
                String newFileName = UUID.randomUUID() + oldFileName.substring(oldFileName.lastIndexOf("."));
                //新图片
                File newFile = new File(file_path+newFileName);
                //将内存中的数据写入磁盘
                file.transferTo(newFile);
                //将新图片名称返回到前端
                ajaxResult.setData(newFileName);
            }else{
                return AjaxResult.fixedError(QexzWebError.UPLOAD_FILE_IMAGE_NOT_QUALIFIED);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return AjaxResult.fixedError(QexzWebError.UPLOAD_FILE_IMAGE_ANALYZE_ERROR);
        }
        return ajaxResult;
    }

    /**
     * API:添加用户
     */
    @RequestMapping(value="/api/addUser", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult addUser(@RequestBody User user) {
        AjaxResult ajaxResult = new AjaxResult();
        User existUser = userService.getUserByUsername(user.getName());
        if(existUser == null) {//检测该用户是否已经注册
            user.setPassword(MD5.md5(QexzConst.MD5_SALT+user.getPassword()));
            user.setAvatar_img_url(QexzConst.DEFAULT_AVATAR_IMG_URL);
            user.setDescription("");
            int accountId = userService.addUser(user);
            return new AjaxResult().setData(accountId);
        }
        return AjaxResult.fixedError(QexzWebError.AREADY_EXIST_USERNAME);
    }

    /**
     * API:更新用户
     */
    @RequestMapping(value="/api/updateManegeUser", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateUser(@RequestBody User user) {
        AjaxResult ajaxResult = new AjaxResult();
        user.setPassword(MD5.md5(QexzConst.MD5_SALT+user.getPassword()));
        boolean result = userService.updateUser(user);
        return new AjaxResult().setData(result);
    }

    /**
     * API:删除用户
     */
    @DeleteMapping("/api/deleteUser/{id}")
    @ResponseBody
    public AjaxResult deleteAccount(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = userService.deleteUser(id);
        return new AjaxResult().setData(result);
    }

    /**
     * API:禁用账号
     */
    @RequestMapping(value="/api/disabledUser/{id}", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult disabledUser(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = userService.disabledUser(id);
        return new AjaxResult().setData(result);
    }

    /**
     * API:解禁账号
     */
    @RequestMapping(value="/api/abledUser/{id}", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult abledAccount(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = userService.abledUser(id);
        return new AjaxResult().setData(result);
    }

    /**
     * API:简历审核通过
     */
    @RequestMapping(value="/api/approvedUser/{id}", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult approvedUser(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = userService.approvedUser(id);
        return new AjaxResult().setData(result);
    }

    /**
     * API:简历审核拒绝
     */
    @RequestMapping(value="/api/rejectedUser/{id}", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult rejectedUser(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = userService.rejectedUser(id);
        return new AjaxResult().setData(result);
    }


}
