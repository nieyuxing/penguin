package com.qexz.controller;

import com.qexz.dto.AjaxResult;
import com.qexz.model.ExaminationAnswerDetail;
import com.qexz.service.ExaminationAnswerDetailService;
import com.qexz.service.QuestionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/answerDetail")
public class AnsweDetailController {

    private static Log LOG = LogFactory.getLog(AnsweDetailController.class);

    @Autowired
    private ExaminationAnswerDetailService examinationAnswerDetailService;

    //添加考试
    @RequestMapping(value="/api/addExaminationAnswerDetail", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult addExaminationAnswerDetail(@RequestBody ExaminationAnswerDetail examinationAnswerDetail) {
        AjaxResult ajaxResult = new AjaxResult();
        int examinationAnswerDetailId = examinationAnswerDetailService.addExaminationAnswerDetail(examinationAnswerDetail);
        return new AjaxResult().setData(examinationAnswerDetailId);
    }

    //更新考试信息
    @RequestMapping(value="/api/updateAnswerDetail", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateExaminationAnswerDetail(@RequestBody ExaminationAnswerDetail examinationAnswerDetail) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = examinationAnswerDetailService.updateExaminationAnswerDetail(examinationAnswerDetail);
        return new AjaxResult().setData(result);
    }

    //删除考试信息
    @DeleteMapping("/api/deleteExaminationAnswerDetail/{id}")
    public AjaxResult deleteExaminationAnswerDetail(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = examinationAnswerDetailService.deleteExaminationAnswerDetail(id);
        return new AjaxResult().setData(result);
    }






}
