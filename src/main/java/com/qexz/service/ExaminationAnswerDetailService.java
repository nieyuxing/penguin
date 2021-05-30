package com.qexz.service;

import com.qexz.model.ExaminationAnswerDetail;

import java.util.List;
import java.util.Map;

public interface ExaminationAnswerDetailService {

    int addExaminationAnswerDetail(ExaminationAnswerDetail answerDetail);

    boolean updateExaminationAnswerDetail(ExaminationAnswerDetail answerDetail);

    boolean deleteExaminationAnswerDetail(int id);

    ExaminationAnswerDetail getExaminationAnswerDetailById(int id);

    List<ExaminationAnswerDetail> getExaminationAnswerDetailsByAnswerId(int answer);

    Map<String, Object> getExaminationAnswerDetails(int pageNum, int pageSize, int answer);

    List<ExaminationAnswerDetail> getExaminationAnswerDetailByAnswerId(int answerId);
}
