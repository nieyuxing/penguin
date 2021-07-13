package com.qexz.service;

import com.qexz.model.ExaminationPaper;
import com.qexz.model.ExaminationPaperDetail;

import java.util.List;
import java.util.Map;

public interface ExaminationPaperDetailService {

    int addExaminationPaperDetail(ExaminationPaperDetail paperDetail);

    boolean updateExaminationPaperDetail(ExaminationPaperDetail paperDetail);

    boolean deleteExaminationPaperDetail(int id);

    ExaminationPaperDetail getExaminationPaperDetailById(int id);

    List<ExaminationPaperDetail> getExaminationPaperDetailsByPaperId(int paper);

    Map<String, Object> getExaminationPaperDetails(int pageNum, int pageSize, int paper);

    List<ExaminationPaperDetail> getExaminationPaperDetailsByQuestionId(int question_id);

    ExaminationPaperDetail getByPaperQuestionId(int paperId,int questionId);
}
