package com.qexz.service;

import com.qexz.model.ExaminationAnswer;

import java.util.Map;

public interface ExaminationAnswerService {

    int addExaminationAnswer(ExaminationAnswer paper);

    boolean updateExaminationAnswer(ExaminationAnswer paper);

    boolean deleteExaminationAnswer(int id);

    ExaminationAnswer getExaminationAnswerById(int id);

    ExaminationAnswer getExaminationAnswerByUserIdAndPaperId(int userId,int paperId);

    Map<String, Object> getExaminationAnswers(int pageNum, int pageSize);

    int updateState(int id,int state);

    boolean finishAnswer(ExaminationAnswer answer);

    Map<String, Object> getActiveExaminationAnswers(int pageNum, int pageSize);
}
