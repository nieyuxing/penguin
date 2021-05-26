package com.qexz.dao;

import com.qexz.model.ExaminationPaper;
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
public interface ExaminationPaperMapper {

    int insertExaminationPaper(@Param("ExaminationPaper") ExaminationPaper paper);

    int updateExaminationPaperById(@Param("ExaminationPaper") ExaminationPaper paper);

    int getCount();

    List<ExaminationPaper> getExaminationPapers();

    int deleteExaminationPaper(@Param("id") int id);

    int updateState(@Param("id") int id, @Param("state") int state);

    List<ExaminationPaper> getExaminationPapersByIdSets(@Param("ids") Set<Integer> ids);

    ExaminationPaper getExaminationPaperById(@Param("id") int id);


}
