package com.qexz.controller;

import com.qexz.common.QexzConst;
import com.qexz.dto.AjaxResult;
import com.qexz.model.*;
import com.qexz.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/answer")
public class AnsweController {

    private static Log LOG = LogFactory.getLog(AnsweController.class);

    @Autowired
    private ExaminationAnswerService examinationAnswerService;
    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExaminationPaperService examinationPaperService;
    @Autowired
    private ExaminationAnswerDetailService examinationAnswerDetailService;
    @Autowired
    private ExaminationPaperDetailService examinationPaperDetailService;


    //添加考试
    @RequestMapping(value="/api/addAnswer", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult addExaminationAnswer(@RequestBody ExaminationAnswer examinationAnswer) {
        AjaxResult ajaxResult = new AjaxResult();
        if(examinationAnswer.getLimit_time()==null){
            return new AjaxResult().setMessage("分配考试必须设置截止时间");
        }
        int examinationAnswerId = examinationAnswerService.addExaminationAnswer(examinationAnswer);
        return new AjaxResult().setData(examinationAnswerId);
    }

    //更新考试信息
    @RequestMapping(value="/api/updateContest", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateExaminationAnswer(@RequestBody ExaminationAnswer examinationAnswer) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = examinationAnswerService.updateExaminationAnswer(examinationAnswer);
        return new AjaxResult().setData(result);
    }

    //删除考试信息
    @DeleteMapping("/api/deleteExaminationAnswer/{id}")
    @ResponseBody
    public AjaxResult deleteExaminationAnswer(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = examinationAnswerService.deleteExaminationAnswer(id);
        return new AjaxResult().setData(result);
    }

    //完成考试批改
    @RequestMapping(value="/api/finishExaminationAnswer/{id}", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult finishExaminationAnswer(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        ExaminationAnswer examinationAnswer = examinationAnswerService.getExaminationAnswerById(id);
        List<ExaminationAnswerDetail> examinationAnswerDetails = examinationAnswerDetailService.getExaminationAnswerDetailsByAnswerId(id);
        int total = 0;
        for(ExaminationAnswerDetail examinationAnswerDetail : examinationAnswerDetails){
            total += examinationAnswerDetail.getScore();
        }
        examinationAnswer.setScore(total);
        examinationAnswer.setState(3);
        boolean result = examinationAnswerService.finishAnswer(examinationAnswer);
        return new AjaxResult().setData(result);
    }

    /**
     * 在线考试列表页
     */
    @RequestMapping(value="/index", method= RequestMethod.GET)
    public String contestIndex(HttpServletRequest request,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               Model model) {
        User currentAccount = (User) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null) {
            return "home";
        }
        Map<String, Object> data = examinationPaperService.getExaminationPaperByUserId(page, QexzConst.contestPageSize,currentAccount.getId());
        model.addAttribute(QexzConst.DATA, data);
        return "contest/index";
    }


    @RequestMapping(value="/paper/detail", method= RequestMethod.GET)
    public String paperIndex(HttpServletRequest request,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               Model model) {
        User currentAccount = (User) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        Map<String, Object> data = examinationPaperService.getExaminationPaperByUserId(page, QexzConst.contestPageSize,currentAccount.getId());
        model.addAttribute(QexzConst.DATA, data);
        return "contest/index";
    }

    /**
     * 在线考试页
     */
    @RequestMapping(value="/{answerId}", method= RequestMethod.GET)
    public String contestDetail(HttpServletRequest request,
                                @PathVariable("answerId") int answerId,
                                Model model) {
        User currentAccount = (User) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
      //  ExaminationPaper  contest = examinationPaperService.getExaminationPaperById(contestId);
      //  ExaminationAnswer  answer= examinationAnswerService.getExaminationAnswerByUserIdAndPaperId(currentAccount.getId(),contest.getId());
        ExaminationPaper  contest = examinationPaperService.getExaminationPaperByAnswerId(answerId);
        ExaminationAnswer  answer = examinationAnswerService.getExaminationAnswerById(answerId);
        answer.setPaper(contest);
        if (currentAccount == null ) {
            return "home";
        }
        List<Question> questions = questionService.getQuestionsByContestId(contest.getId());
        for (Question question : questions) {
            ExaminationPaperDetail e = examinationPaperDetailService.getByPaperQuestionId(contest.getId(),question.getId());
            if(question.getImgUrl() != null && question.getImgUrl()!=""){
                question.setImgUrl(QexzConst.IMGIPADDRESS + question.getImgUrl());
            }
            question.setScore(e.getScore());
            question.setAnswer("");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("answer", answer);
        data.put("contest", contest);
        data.put("questions", questions);
        model.addAttribute(QexzConst.DATA, data);
        return "contest/detail";
    }


}
