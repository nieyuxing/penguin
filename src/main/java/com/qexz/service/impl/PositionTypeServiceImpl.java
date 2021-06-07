package com.qexz.service.impl;

import com.github.pagehelper.PageHelper;
import com.qexz.common.QexzConst;
import com.qexz.dao.PositionTypeMapper;
import com.qexz.model.PositionType;
import com.qexz.service.PositionTypeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("positionTypeService")
public class PositionTypeServiceImpl implements PositionTypeService {

    private static Log LOG = LogFactory.getLog(PositionTypeServiceImpl.class);

    @Autowired
    private PositionTypeMapper positionTypeMapper;

    @Override
    public int addPositionType(PositionType position) {
        return positionTypeMapper.insertPositionType(position);
    }

    @Override
    public boolean updatePositionType(PositionType position) {
        return positionTypeMapper.updatePositionTypeById(position) > 0;
    }

    @Override
    public PositionType getPositionTypeById(int id) {
        return positionTypeMapper.getPositionTypeById(id);
    }

    @Override
    public Map<String, Object> getPositionTypes(int pageNum, int pageSize) {
        Map<String, Object> data = new HashMap<>();
        int count = positionTypeMapper.getCount();
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("positionTypes", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("positionTypes", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<PositionType> positions = positionTypeMapper.getPositionTypes();
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("positionTypes", positions);
        return data;
    }

    @Override
    public List<PositionType> getPositionTypes() {
        return positionTypeMapper.getPositionTypes();
    }

    @Override
    public boolean deletePositionTypeById(int id) {
        return positionTypeMapper.deletePositionTypeById(id) > 0;
    }
}
