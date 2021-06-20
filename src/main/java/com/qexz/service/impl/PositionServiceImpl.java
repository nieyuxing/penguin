package com.qexz.service.impl;

import com.github.pagehelper.PageHelper;
import com.qexz.common.QexzConst;
import com.qexz.dao.PositionMapper;
import com.qexz.model.Position;
import com.qexz.service.PositionService;
import com.qexz.service.PositionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("positionService")
public class PositionServiceImpl implements PositionService {

    private static Log LOG = LogFactory.getLog(PositionServiceImpl.class);

    @Autowired
    private PositionMapper positionMapper;

    @Override
    public int addPosition(Position position) {
        if (position != null && StringUtils.isEmpty(position.getImgUrl())) {
            position.setImgUrl(QexzConst.DEFAULT_POSITION_IMG_URL);
        }
        return positionMapper.insertPosition(position);
    }

    @Override
    public boolean updatePosition(Position position) {
        return positionMapper.updatePositionById(position) > 0;
    }

    @Override
    public Position getPositionById(int id) {
        return positionMapper.getPositionById(id);
    }

    @Override
    public Map<String, Object> getPositionsByType(int pageNum, int pageSize,int type) {
        Map<String, Object> data = new HashMap<>();
        int count = positionMapper.getCountByType(type);
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("positions", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("positions", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Position> positions = positionMapper.getPositionsByType(type);
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("positions", positions);
        return data;
    }

    @Override
    public Map<String, Object> getPositions(int pageNum, int pageSize) {
        Map<String, Object> data = new HashMap<>();
        int count = positionMapper.getCount();
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("positions", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("positions", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Position> positions = positionMapper.getPositions();
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("positions", positions);
        return data;
    }

    @Override
    public List<Position> getPositions() {
        return positionMapper.getPositions();
    }

    @Override
    public boolean deletePositionById(int id) {
        return positionMapper.deletePositionById(id) > 0;
    }
}
