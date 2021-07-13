package com.qexz.service.impl;

import com.github.pagehelper.PageHelper;
import com.qexz.dao.ExaminationPaperDetailMapper;
import com.qexz.model.ExaminationPaperDetail;
import com.qexz.service.ExaminationPaperDetailService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("examinationPaperDetailService")
public class ExaminationPaperDetailServiceImpl implements ExaminationPaperDetailService {

    private static Log LOG = LogFactory.getLog(ExaminationPaperDetailServiceImpl.class);

    @Autowired
    private ExaminationPaperDetailMapper examinationPaperDetailMapper ;


    @Override
    public int addExaminationPaperDetail(ExaminationPaperDetail paper) {
        int num = examinationPaperDetailMapper.insertExaminationPaperDetailDetail(paper);
        return num;
    }

    @Override
    public boolean updateExaminationPaperDetail(ExaminationPaperDetail ExaminationPaperDetail) {
        int num = examinationPaperDetailMapper.updateExaminationPaperDetailDetailById(ExaminationPaperDetail);
        return num>0;
    }

    @Override
    public boolean deleteExaminationPaperDetail(int id) {

        return examinationPaperDetailMapper.deleteExaminationPaperDetailDetail(id)>0;
    }

    @Override
    public ExaminationPaperDetail getExaminationPaperDetailById(int id) {
        return examinationPaperDetailMapper.getExaminationPaperDetailDetailById(id);
    }

    @Override
    public List<ExaminationPaperDetail> getExaminationPaperDetailsByPaperId(int paper) {
        return examinationPaperDetailMapper.getExaminationPaperDetailDetailByPaperId(paper);
    }

    @Override
    public Map<String, Object> getExaminationPaperDetails(int pageNum, int pageSize, int paper) {
        Map<String, Object> data = new HashMap<>();
        int count = examinationPaperDetailMapper.getCount();
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
        List<ExaminationPaperDetail> positions = examinationPaperDetailMapper.getPaperDetails();
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("positions", positions);
        return data;
    }

    public List<ExaminationPaperDetail> getExaminationPaperDetailsByQuestionId(int question_id){
        return examinationPaperDetailMapper.getExaminationPaperDetailsByQuestionId(question_id);
    }

    public ExaminationPaperDetail getByPaperQuestionId(int paperId,int questionId){
        return examinationPaperDetailMapper.getByPaperQuestionId(paperId,questionId);
    }
}
