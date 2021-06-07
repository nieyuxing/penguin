package com.qexz.service;

import com.qexz.model.PositionType;

import java.util.List;
import java.util.Map;

public interface PositionTypeService {

    int addPositionType(PositionType PositionType);

    boolean updatePositionType(PositionType PositionType);

    PositionType getPositionTypeById(int id);

    Map<String, Object> getPositionTypes(int pageNum, int pageSize);

    List<PositionType> getPositionTypes();

    boolean deletePositionTypeById(int id);
}
