package com.qexz.dao;

import com.qexz.model.ExaminationAnswerDetail;
import com.qexz.model.ExaminationPaperDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Created by YANGFU18 on 2021/5/23.
 */
@Component
@Mapper
public interface ExaminationPaperDetailMapper {
    int insertExaminationPaperDetailDetail(@Param("ExaminationPaperDetail") ExaminationPaperDetail paperDetail);

    int updateExaminationPaperDetailDetailById(@Param("ExaminationPaperDetail") ExaminationPaperDetail paperDetail);

    int getCount();

    List<ExaminationPaperDetail> getPaperDetails();

    int deleteExaminationPaperDetailDetail(@Param("id") int id);

    int updateState(@Param("id") int id, @Param("state") int state);

    List<ExaminationPaperDetail> getExaminationPaperDetailDetailsByIdSets(@Param("ids") Set<Integer> ids);

    ExaminationPaperDetail getExaminationPaperDetailDetailById(@Param("id") int id);

    List<ExaminationPaperDetail> getExaminationPaperDetailDetailByPaperId(int parperId);

    List<ExaminationPaperDetail> getExaminationPaperDetailsByQuestionId(@Param("quesion_id") int quesion_id);

}
