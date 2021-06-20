package com.qexz.controller;

import com.qexz.dao.ExaminationPaperDetailMapper;
import com.qexz.dto.AjaxResult;
import com.qexz.model.ExaminationAnswer;
import com.qexz.model.ExaminationPaper;
import com.qexz.model.ExaminationPaperDetail;
import com.qexz.service.ExaminationAnswerService;
import com.qexz.service.ExaminationPaperDetailService;
import com.qexz.service.ExaminationPaperService;
import com.qexz.service.QuestionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/paper")
public class PaperController {

    private static Log LOG = LogFactory.getLog(PaperController.class);

    @Autowired
    private ExaminationPaperService examinationPaperService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ExaminationAnswerService examinationAnswerService;

    @Autowired
    private ExaminationPaperDetailService examinationPaperDetailService ;

    //添加考试
    @RequestMapping(value="/api/addPaper", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult addExaminationPaper(@RequestBody ExaminationPaper examinationPaper) {
        AjaxResult ajaxResult = new AjaxResult();
        int examinationPaperId = examinationPaperService.addExaminationPaper(examinationPaper);
        return new AjaxResult().setData(examinationPaperId);
    }

    //更新考试信息
    @RequestMapping(value="/api/updatePaper", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateExaminationPaper(@RequestBody ExaminationPaper ExaminationPaper) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = examinationPaperService.updateExaminationPaper(ExaminationPaper);
        return new AjaxResult().setData(result);
    }

    //删除考试信息
    @DeleteMapping("/api/deletePaper/{id}")
    public AjaxResult deleteExaminationPaper(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        ExaminationAnswer answer = examinationAnswerService.getExaminationAnswerByPaperId(id);
        if(answer!=null ){
            ajaxResult.setMessage("该问卷已经被引用，不能删除");
            return ajaxResult;
        }
        boolean result = examinationPaperService.deleteExaminationPaper(id);
        return new AjaxResult().setData(result);
    }

    //完成考试批改
    @RequestMapping(value="/api/finishPaper/{id}", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult finishExaminationPaper(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        ExaminationPaper examinationPaper = examinationPaperService.getExaminationPaperById(id);
        examinationPaper.setState(3);
        questionService.updateQuestionsStateByContestId(id, 1);
        boolean result = examinationPaperService.updateExaminationPaper(examinationPaper);
        return new AjaxResult().setData(result);
    }

    /**
     * API:禁用账号
     */
    @RequestMapping(value="/api/disabledPaper/{id}", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult disabledPaper(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = examinationPaperService.disabledExaminationPaper(id);
        return new AjaxResult().setData(result);
    }

    /**
     * API:解禁账号
     */
    @RequestMapping(value="/api/abledPaper/{id}", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult abledPaper(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result= false;
        ExaminationPaper examinationPaper = examinationPaperService.getExaminationPaperById(id);
        List<ExaminationPaperDetail> examinationPaperDetails =   examinationPaperDetailService.getExaminationPaperDetailsByPaperId(id);
        int total = 0;
        for(ExaminationPaperDetail examinationPaperDetail : examinationPaperDetails){
            total += examinationPaperDetail.getScore();
        }

        if(total == examinationPaper.getScore()){
            result = examinationPaperService.abledExaminationPaper(id);
        }

        return new AjaxResult().setData(result);
    }




}
