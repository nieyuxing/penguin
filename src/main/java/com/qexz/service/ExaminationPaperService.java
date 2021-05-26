package com.qexz.service;

import com.qexz.model.ExaminationPaper;

import java.util.List;
import java.util.Map;

public interface ExaminationPaperService {

    int addExaminationPaper(ExaminationPaper paper);

    boolean updateExaminationPaper(ExaminationPaper paper);

    boolean deleteExaminationPaper(int id);

    ExaminationPaper getExaminationPaperById(int id);

    Map<String, Object> getPagesExaminationPapers(int pageNum, int pageSize);

    boolean abledExaminationPaper(int id);

    boolean disabledExaminationPaper(int id);

    List<ExaminationPaper> getExaminationPapers();
}
