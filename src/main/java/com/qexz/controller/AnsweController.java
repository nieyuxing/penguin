package com.qexz.controller;

import com.qexz.dto.AjaxResult;
import com.qexz.model.ExaminationAnswer;
import com.qexz.service.ExaminationAnswerService;
import com.qexz.service.QuestionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/answer")
public class AnsweController {

    private static Log LOG = LogFactory.getLog(AnsweController.class);

    @Autowired
    private ExaminationAnswerService examinationAnswerService;
    @Autowired
    private QuestionService questionService;

    //添加考试
    @RequestMapping(value="/api/addAnswer", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult addExaminationAnswer(@RequestBody ExaminationAnswer examinationAnswer) {
        AjaxResult ajaxResult = new AjaxResult();
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
        examinationAnswer.setState(3);
        questionService.updateQuestionsStateByContestId(id, 1);
        boolean result = examinationAnswerService.updateExaminationAnswer(examinationAnswer);
        return new AjaxResult().setData(result);
    }




}
