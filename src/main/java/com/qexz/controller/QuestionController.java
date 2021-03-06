package com.qexz.controller;

import com.qexz.dto.AjaxResult;
import com.qexz.model.Contest;
import com.qexz.model.ExaminationPaperDetail;
import com.qexz.model.Question;
import com.qexz.service.ExaminationPaperDetailService;
import com.qexz.service.QuestionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/question")
public class QuestionController {

    private static Log LOG = LogFactory.getLog(QuestionController.class);

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExaminationPaperDetailService examinationPaperDetailService;

    //添加题目
    @RequestMapping(value="/api/addQuestion", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult addQuestion(@RequestBody Question question) {
        AjaxResult ajaxResult = new AjaxResult();
        int questionId = questionService.addQuestion(question);
        return new AjaxResult().setData(questionId);
    }

    //更新题目信息
    @RequestMapping(value="/api/updateQuestion", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateQuestion(@RequestBody Question question) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = questionService.updateQuestion(question);
        return new AjaxResult().setData(result);
    }

    //删除题目信息
    @DeleteMapping("/api/deleteQuestion/{id}")
    public AjaxResult deleteContest(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        List<ExaminationPaperDetail> list = examinationPaperDetailService.getExaminationPaperDetailsByQuestionId(id);
        if(list.size()<1){
            boolean result = questionService.deleteQuestion(id);
            return new AjaxResult().setData(result);
        }else{
            ajaxResult.setMessage("该问题已被引用，不能删除");
            return ajaxResult;
        }
    }
}
