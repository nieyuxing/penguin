package com.qexz.controller;

import com.qexz.common.QexzConst;
import com.qexz.dto.AjaxResult;
import com.qexz.model.ExaminationAnswerDetail;
import com.qexz.service.ExaminationAnswerDetailService;
import com.qexz.service.QuestionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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

    //批改试卷
    @RequestMapping(value="/api/updateAnswerDetail", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateExaminationAnswerDetail(@RequestBody ExaminationAnswerDetail examinationAnswerDetail) {
        boolean flag = false ;
        AjaxResult ajaxResult = new AjaxResult();


        List<String> answerStrs = Arrays.asList(examinationAnswerDetail.getUpJson().split(QexzConst.SPLIT_AP_CHAR));

        int autoResult = 0;
        for(String answerStr :answerStrs){
            if(answerStr!=null && !answerStr.equals("")){
                String[] detail = answerStr.split("@");
                Integer detailId = Integer.parseInt(detail[0] );
                Integer score = Integer.parseInt(detail[1] );
                ExaminationAnswerDetail answerDetail = examinationAnswerDetailService.getExaminationAnswerDetailById(detailId );
                answerDetail.setScore(score);
                boolean result = examinationAnswerDetailService.updateExaminationAnswerDetail(answerDetail);
                if(result){
                    autoResult++ ;
                }

            }

        }
        if(autoResult >0){
            flag = true;
        }

        return new AjaxResult().setData(flag);
    }

    //删除考试信息
    @DeleteMapping("/api/deleteExaminationAnswerDetail/{id}")
    public AjaxResult deleteExaminationAnswerDetail(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = examinationAnswerDetailService.deleteExaminationAnswerDetail(id);
        return new AjaxResult().setData(result);
    }






}
