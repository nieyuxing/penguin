package com.qexz.controller;

import com.qexz.common.QexzConst;
import com.qexz.dto.AjaxResult;
import com.qexz.model.*;
import com.qexz.service.*;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/grade")
public class GradeController {

    private static Log LOG = LogFactory.getLog(GradeController.class);

    @Autowired
    private GradeService gradeService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ExaminationAnswerDetailService examinationAnswerDetailService;
    @Autowired
    private ExaminationAnswerService examinationAnswerService;
    @Autowired
    private ExaminationPaperDetailService examinationPaperDetailService;

    //提交试卷
    @RequestMapping(value="/api/submitContest", method= RequestMethod.POST)
    public String submitContest(HttpServletRequest request, @RequestBody Grade grade) {
        AjaxResult ajaxResult = new AjaxResult();
        User currentAccount = (User) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        List<String> answerStrs = Arrays.asList(grade.getAnswerJson().split(QexzConst.SPLIT_CHAR));
        int autoResult = 0;
        List<Question> questions = questionService.getQuestionsByContestId(grade.getContestId());
        //List<ExaminationAnswerDetail> examinationAnswersDetail = new ArrayList<>();
        ExaminationAnswer examinationAnswer = examinationAnswerService.getExaminationAnswerById(grade.getAnswerId());
        examinationAnswer.setState(2);
        examinationAnswerService.updateState(grade.getAnswerId(),2);
        ExaminationAnswerDetail examinationAnswersDetail;
        for (int i = 0; i < questions.size(); i++) {


            Question question = questions.get(i);
            examinationAnswersDetail = new ExaminationAnswerDetail();
            examinationAnswersDetail.setAnswer_id(grade.getAnswerId());
            examinationAnswersDetail.setPaper_id(grade.getContestId());
            examinationAnswersDetail.setQuestion_id(question.getId());
            if(answerStrs.get(i) != null){
                if(!answerStrs.get(i).equals("-")){
                    examinationAnswersDetail.setAnswer(answerStrs.get(i));
                }
            }
            examinationAnswersDetail.setCreate_time(new Date());
            examinationAnswersDetail.setUpdate_time(new Date());
            if (question.getQuestionType() <= 1 && question.getAnswer()
                    .equals(answerStrs.get(i))) {
                ExaminationPaperDetail e = examinationPaperDetailService.getByPaperQuestionId(grade.getContestId(),question.getId());
                question.setScore(e.getScore());
                examinationAnswersDetail.setScore(question.getScore());
                autoResult += question.getScore();
            }
            examinationAnswerDetailService.addExaminationAnswerDetail(examinationAnswersDetail);

        }



        grade.setStudentId(currentAccount.getId());
        grade.setResult(autoResult);
        grade.setAutoResult(autoResult);
        grade.setManulResult(0);
        int gradeId = gradeService.addGrade(grade);
        return "redirect:"+"/";
    }

    //完成批改试卷
    @RequestMapping(value="/api/finishGrade", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult finishGrade(@RequestBody Grade grade) {
        AjaxResult ajaxResult = new AjaxResult();
        grade.setResult(grade.getAutoResult()+grade.getManulResult());
        grade.setFinishTime(new Date());
        grade.setState(1);
        boolean result = gradeService.updateGrade(grade);
        return new AjaxResult().setData(result);
    }
}
