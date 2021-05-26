package com.qexz.service;

import com.qexz.model.ExaminationAnswer;

import java.util.Map;

public interface ExaminationAnswerService {

    int addExaminationAnswer(ExaminationAnswer paper);

    boolean updateExaminationAnswer(ExaminationAnswer paper);

    boolean deleteExaminationAnswer(int id);

    ExaminationAnswer getExaminationAnswerById(int id);

    Map<String, Object> getExaminationAnswers(int pageNum, int pageSize);
}
