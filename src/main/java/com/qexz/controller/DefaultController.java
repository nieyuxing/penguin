package com.qexz.controller;

import com.qexz.common.QexzConst;
import com.qexz.dto.AjaxResult;
import com.qexz.model.*;
import com.qexz.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/")
public class DefaultController {

    private static Log LOG = LogFactory.getLog(DefaultController.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private ContestService contestService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ReplyService replyService;

    @Autowired
    private PositionService positionService;

    /**
     * 首页
     */
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String home(HttpServletRequest request, Model model) {
        User currentAccount = (User) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        return "home";
    }

    /**
     * 在线考试列表页
     */
    @RequestMapping(value="/contest/index", method= RequestMethod.GET)
    public String contestIndex(HttpServletRequest request,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               Model model) {
        contestService.updateStateToStart();
        contestService.updateStateToEnd();
        User currentAccount = (User) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        Map<String, Object> data = contestService.getContests(page, QexzConst.contestPageSize);
        model.addAttribute(QexzConst.DATA, data);
        return "contest/index";
    }

    /**
     * 在线考试页
     */
    @RequestMapping(value="/contest/{contestId}", method= RequestMethod.GET)
    public String contestDetail(HttpServletRequest request,
                               @PathVariable("contestId") int contestId,
                               Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        Contest contest = contestService.getContestById(contestId);
        if (currentAccount == null || contest.getStartTime().getTime() > System.currentTimeMillis()
                || contest.getEndTime().getTime() < System.currentTimeMillis()) {
            return "redirect:/contest/index";
        }
        List<Question> questions = questionService.getQuestionsByContestId(contestId);
        for (Question question : questions) {
            question.setAnswer("");
            if(question.getImgUrl() != null && question.getImgUrl()!=""){
                question.setImgUrl(QexzConst.UPLOAD_FILE_IMAGE_PATH + question.getImgUrl());
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("contest", contest);
        data.put("questions", questions);
        model.addAttribute(QexzConst.DATA, data);
        return "contest/detail";
    }

    /**
     * 职位中心页
     */
    @RequestMapping(value="/problemset/list", method= RequestMethod.GET)
    public String problemSet(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        User currentAccount = (User) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        Map<String, Object> data = positionService.getPositions(page, QexzConst.positionPageSize);

        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(QexzConst.DATA, data);
        return "problem/problemset";
    }

    @RequestMapping(value="/position/listByName{name}", method= RequestMethod.GET)
    public String positionByName(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page,@RequestParam(value = "name") String name, Model model) {
        User currentAccount = (User) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        String tmp = request.getParameter("name");
        if("".equals(tmp)){
            tmp = null;
        }
        Map<String, Object> data = positionService.getPositions(page,tmp, QexzConst.positionPageSize);

        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(QexzConst.DATA, data);
        return "problem/problemset";
    }

    @RequestMapping(value="/position/listByType{type}", method= RequestMethod.GET)
    public String positionByType(@PathVariable("type") String type,HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        User currentAccount = (User) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        String tmp = request.getParameter("type");
        Map<String, Object> data = positionService.getPositionsByType(page, QexzConst.positionPageSize,Integer.valueOf(tmp));

        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(QexzConst.DATA, data);
        return "problem/problemset";
    }

    @RequestMapping(value="/position/listBySourceType", method= RequestMethod.GET)
    public String positionBySourceType(@PathVariable("sourceType") String sourceType,HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        User currentAccount = (User) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        Map<String, Object> data = positionService.getPositionsBySourceType(page, QexzConst.positionPageSize,sourceType);

        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(QexzConst.DATA, data);
        return "problem/problemset";
    }

    /**
     * 题目详情页
     */
    @RequestMapping(value="/problemset/{problemsetId}/problems", method= RequestMethod.GET)
    public String problemList(HttpServletRequest request,
                              @PathVariable("problemsetId") Integer problemsetId,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "content", defaultValue = "") String content,
                              @RequestParam(value = "difficulty", defaultValue = "0") int difficulty,
                              Model model) {
        User currentAccount = (User) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        Map<String, Object> data = questionService.getQuestionsByProblemsetIdAndContentAndDiffculty(page, QexzConst.questionPageSize,
                problemsetId, content, difficulty);
        Position position = positionService.getPositionById(problemsetId);
        data.put("position", position);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(QexzConst.DATA, data);
        return "problem/problemlist";
    }

//    /**
//     * 题目详情页
//     */
//    @RequestMapping(value="/problemset/{problemsetId}/problem/{problemId}", method= RequestMethod.GET)
//    public String problemDetail(HttpServletRequest request,
//                                @PathVariable("problemsetId") Integer problemsetId,
//                                @PathVariable("problemId") Integer problemId,
//                                Model model) {
//        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
//        Map<String, Object> data = new HashMap<>();
//        Question question = questionService.getQuestionById(problemId);
//        Position position = positionService.getPositionById(problemsetId);
//        data.put("question", question);
//        data.put("position", position);
//        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
//        model.addAttribute(QexzConst.DATA, data);
//        return "problem/problemdetail";
//    }



    /**
     * 获取服务器端时间,防止用户篡改客户端时间提前参与考试
     *
     * @return 时间的json数据
     */
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult time() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return new AjaxResult().setData(localDateTime);
    }

    /**
     * 测试分布式一致性session
     * @param session
     * @return
     */
    @RequestMapping(value = "/uid", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return new AjaxResult().setData(session.getId());
    }
}
