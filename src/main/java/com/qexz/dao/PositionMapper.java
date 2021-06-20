package com.qexz.dao;

import com.qexz.model.Position;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface PositionMapper {

    int insertPosition(@Param("position") Position position);

    int updatePositionById(@Param("position") Position position);

    Position getPositionById(@Param("id") int id);

    int getCount();

    List<Position> getPositions();

    int deletePositionById(@Param("id") int id);

    int getCountByType(@Param("type") int type);

    List<Position> getPositionsByType(@Param("type") int type);

    int getCountByName(String name);

    List<Position> getPositionsByName(String name);
}
