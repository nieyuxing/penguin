package com.qexz.dao;

import com.qexz.model.PositionType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface PositionTypeMapper {

    int insertPositionType(@Param("positionType") PositionType positionType);

    int updatePositionTypeById(@Param("positionType") PositionType positionType);

    PositionType getPositionTypeById(@Param("id") int id);

    int getCount();

    List<PositionType> getPositionTypes();

    int deletePositionTypeById(@Param("id") int id);
}
