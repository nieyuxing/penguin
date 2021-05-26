package com.qexz.controller;

import com.qexz.dto.AjaxResult;
import com.qexz.model.ExaminationPaperDetail;
import com.qexz.service.ExaminationPaperDetailService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/paperDetail")
public class PaperDetailController {

    private static Log LOG = LogFactory.getLog(PaperDetailController.class);

    @Autowired
    private ExaminationPaperDetailService examinationPaperDetailService;

    //添加考题
    @RequestMapping(value="/api/addExaminationPaperDetail", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult addExaminationPaperDetail(@RequestBody ExaminationPaperDetail examinationAnswerDetail) {
        AjaxResult ajaxResult = new AjaxResult();
        int examinationExaminationPaperDetailId = examinationPaperDetailService.addExaminationPaperDetail(examinationAnswerDetail);
        return new AjaxResult().setData(examinationExaminationPaperDetailId);
    }

    //更新考题
    @RequestMapping(value="/api/updateExaminationPaperDetail", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateExaminationPaperDetail(@RequestBody ExaminationPaperDetail examinationAnswerDetail) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = examinationPaperDetailService.updateExaminationPaperDetail(examinationAnswerDetail);
        return new AjaxResult().setData(result);
    }

    //删除考题
    @DeleteMapping("/api/deleteExaminationPaperDetail/{id}")
    public AjaxResult deleteExaminationPaperDetail(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = examinationPaperDetailService.deleteExaminationPaperDetail(id);
        return new AjaxResult().setData(result);
    }






}
