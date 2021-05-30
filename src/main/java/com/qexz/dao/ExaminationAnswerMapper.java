package com.qexz.dao;

import com.qexz.model.ExaminationAnswer;
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
public interface ExaminationAnswerMapper {
    int insertExaminationAnswer(@Param("ExaminationAnswer") ExaminationAnswer user);

    int updateExaminationAnswerById(@Param("ExaminationAnswer") ExaminationAnswer user);

    int getCount();

    List<ExaminationAnswer> getExaminationAnswers();

    int deleteExaminationAnswer(@Param("id") int id);

    int updateState(@Param("id") int id, @Param("state") int state);

    List<ExaminationAnswer> getExaminationAnswersByIdSets(@Param("ids") Set<Integer> ids);

    ExaminationAnswer getExaminationAnswerById(@Param("id") int id);

    ExaminationAnswer getExaminationAnswerByUserIdAndPaperId(@Param("userId")int userId,@Param("paperId")int paperId);

    int finishAnswer(@Param("id") int id, @Param("score") int score ,@Param("state") int state);
}
