package com.qexz.service.impl;

import com.github.pagehelper.PageHelper;
import com.qexz.dao.ExaminationAnswerMapper;
import com.qexz.model.ExaminationAnswer;
import com.qexz.service.ExaminationAnswerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("examinationAnswerService")
public class ExaminationAnswerServiceImpl implements ExaminationAnswerService {

    private static Log LOG = LogFactory.getLog(ExaminationAnswerServiceImpl.class);

    @Autowired
    private ExaminationAnswerMapper examinationAnswerMapper ;


    @Override
    public int addExaminationAnswer(ExaminationAnswer answer) {
        return examinationAnswerMapper.insertExaminationAnswer(answer);
    }

    @Override
    public boolean updateExaminationAnswer(ExaminationAnswer answer) {
        return examinationAnswerMapper.updateExaminationAnswerById(answer)>0;
    }

    @Override
    public boolean deleteExaminationAnswer(int id) {

        return examinationAnswerMapper.deleteExaminationAnswer(id)>0;
    }

    @Override
    public ExaminationAnswer getExaminationAnswerById(int id) {
        return examinationAnswerMapper.getExaminationAnswerById(id);
    }

    @Override
    public Map<String, Object> getExaminationAnswers(int pageNum, int pageSize) {
        Map<String, Object> data = new HashMap<>();
        int count = examinationAnswerMapper.getCount();
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("examinationAnswers", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("examinationAnswers", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<ExaminationAnswer> examinationAnswers = examinationAnswerMapper.getExaminationAnswers();
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("examinationAnswers", examinationAnswers);
        return data;
    }
}
