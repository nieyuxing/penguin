package com.qexz.controller;

import com.qexz.common.QexzConst;
import com.qexz.dto.AjaxResult;
import com.qexz.model.Position;
import com.qexz.service.AccountService;
import com.qexz.service.PositionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/position")
public class PositionController {

    private static Log LOG = LogFactory.getLog(PositionController.class);

    @Autowired
    private PositionService positionService;

    //添加职位
    @RequestMapping(value="/api/addPosition", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult addPosition(@RequestBody Position position) {
        AjaxResult ajaxResult = new AjaxResult();
        position.setImgUrl(QexzConst.DEFAULT_POSITION_IMG_URL);
        position.setQuestionNum(0);
        int positionId = positionService.addPosition(position);
        return new AjaxResult().setData(positionId);
    }

    //更新职位
    @RequestMapping(value="/api/updatePosition", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult updatePosition(@RequestBody Position position) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = positionService.updatePosition(position);
        return new AjaxResult().setData(result);
    }

    //删除职位
    @DeleteMapping("/api/deletePosition/{id}")
    public AjaxResult deletePosition(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = positionService.deletePositionById(id);
        return new AjaxResult().setData(result);
    }

    /**
     * 分页获取所有职位列表
     */
    @RequestMapping(value = "/api/getPositions", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getPositions(HttpServletRequest request, HttpServletResponse response) {
        AjaxResult ajaxResult = new AjaxResult();
//        try {
//            String username = request.getParameter("username");
//            String password = request.getParameter("password");
//            Account current_account = accountService.getAccountByUsername(username);
//            if(current_account != null) {
//                String pwd = MD5.md5(QexzConst.MD5_SALT+password);
//                if(pwd.equals(current_account.getPassword())) {
//                    request.getSession().setAttribute(QexzConst.CURRENT_ACCOUNT,current_account);
//                    ajaxResult.setData(current_account);
//                } else {
//                    return AjaxResult.fixedError(QexzWebError.WRONG_PASSWORD);
//                }
//            } else {
//                return AjaxResult.fixedError(QexzWebError.WRONG_USERNAME);
//            }
//        } catch (Exception e) {
//            LOG.error(e.getMessage(), e);
//            return AjaxResult.fixedError(QexzWebError.COMMON);
//        }
        return ajaxResult;
    }
}
