package com.qexz.service;

import com.qexz.model.Position;

import java.util.List;
import java.util.Map;

public interface PositionService {

    int addPosition(Position Position);

    boolean updatePosition(Position Position);

    Position getPositionById(int id);

    Map<String, Object> getPositions(int pageNum, int pageSize);

    Map<String, Object> getPositions(int pageNum,String name, int pageSize);

    List<Position> getPositions();

    boolean deletePositionById(int id);

    Map<String, Object> getPositionsByType(int pageNum, int pageSize,int type);

    Map<String, Object> getPositionsBySourceType(int pageNum, int pageSize,String sourCeType);

}
