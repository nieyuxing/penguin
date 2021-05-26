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
public interface ExaminationAnswerDetailMapper {
    int insertExaminationAnswerDetail(@Param("ExaminationAnswerDetail") ExaminationAnswerDetail anserDetail);

    int updateExaminationAnswerDetailById(@Param("ExaminationAnswerDetail") ExaminationAnswerDetail anserDetail);

    int getCount();

    List<ExaminationAnswerDetail> getExaminationAnswerDetails();

    int deleteExaminationAnswerDetail(@Param("id") int id);

    int updateState(@Param("id") int id, @Param("state") int state);

    List<ExaminationAnswerDetail> getExaminationAnswerDetailsByIdSets(@Param("ids") Set<Integer> ids);

    ExaminationAnswerDetail getExaminationAnswerDetailById(@Param("id") int id);

    List<ExaminationAnswerDetail> getExaminationAnswerDetailsByAnswerId(int answerId);

}
