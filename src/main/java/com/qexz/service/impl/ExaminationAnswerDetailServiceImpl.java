package com.qexz.service.impl;

import com.github.pagehelper.PageHelper;
import com.qexz.dao.ExaminationAnswerDetailMapper;
import com.qexz.model.ExaminationAnswerDetail;
import com.qexz.service.ExaminationAnswerDetailService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("examinationAnswerDetailService")
public class ExaminationAnswerDetailServiceImpl implements ExaminationAnswerDetailService {

    private static Log LOG = LogFactory.getLog(ExaminationAnswerDetailServiceImpl.class);

    @Autowired
    private ExaminationAnswerDetailMapper examinationAnswerDetailMapper ;


    @Override
    public int addExaminationAnswerDetail(ExaminationAnswerDetail answer) {
        return examinationAnswerDetailMapper.insertExaminationAnswerDetail(answer);
    }

    @Override
    public boolean updateExaminationAnswerDetail(ExaminationAnswerDetail answer) {
        return examinationAnswerDetailMapper.updateExaminationAnswerDetailById(answer)>0;
    }

    @Override
    public boolean deleteExaminationAnswerDetail(int id) {

        return examinationAnswerDetailMapper.deleteExaminationAnswerDetail(id)>0;
    }

    @Override
    public ExaminationAnswerDetail getExaminationAnswerDetailById(int id) {
        return examinationAnswerDetailMapper.getExaminationAnswerDetailById(id);
    }

    @Override
    public List<ExaminationAnswerDetail> getExaminationAnswerDetailsByAnswerId(int answer) {
        return examinationAnswerDetailMapper.getExaminationAnswerDetailsByAnswerId(answer);
    }

    @Override
    public Map<String, Object> getExaminationAnswerDetails(int pageNum, int pageSize, int paper) {
        Map<String, Object> data = new HashMap<>();
        int count = examinationAnswerDetailMapper.getCount();
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
        List<ExaminationAnswerDetail> positions = examinationAnswerDetailMapper.getExaminationAnswerDetails();
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("positions", positions);
        return data;
    }
}
