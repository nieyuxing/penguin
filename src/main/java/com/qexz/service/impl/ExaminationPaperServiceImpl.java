package com.qexz.service.impl;

import com.github.pagehelper.PageHelper;
import com.qexz.dao.ContestMapper;
import com.qexz.dao.ExaminationPaperDetailMapper;
import com.qexz.dao.ExaminationPaperMapper;
import com.qexz.dao.QuestionMapper;
import com.qexz.model.Contest;
import com.qexz.model.ExaminationPaper;
import com.qexz.model.ExaminationPaperDetail;
import com.qexz.model.Question;
import com.qexz.service.ExaminationPaperService;
import com.qexz.service.QuestionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("examinationPaperService")
public class ExaminationPaperServiceImpl implements ExaminationPaperService {

    private static Log LOG = LogFactory.getLog(ExaminationPaperServiceImpl.class);

    @Autowired
    private ExaminationPaperMapper examinationPaperMapper ;



    @Override
    public int addExaminationPaper(ExaminationPaper paper) {
        return examinationPaperMapper.insertExaminationPaper(paper);
    }

    @Override
    public boolean updateExaminationPaper(ExaminationPaper paper) {
        int flag = examinationPaperMapper.updateExaminationPaperById(paper);
        return flag>0;
    }

    @Override
    public boolean deleteExaminationPaper(int id) {

        return examinationPaperMapper.deleteExaminationPaper(id)>0;
    }

    @Override
    public ExaminationPaper getExaminationPaperById(int id) {
        return examinationPaperMapper.getExaminationPaperById(id);
    }

    @Override
    public Map<String, Object> getPagesExaminationPapers(int pageNum, int pageSize) {
        Map<String, Object> data = new HashMap<>();
        int count = examinationPaperMapper.getCount();
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("papers", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("papers", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<ExaminationPaper> papers = examinationPaperMapper.getExaminationPapers();
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("papers", papers);
        return data;
    }

    @Override
    public boolean abledExaminationPaper(int id) {
        return examinationPaperMapper.updateState(id, 1) > 0;
    }

    @Override
    public boolean disabledExaminationPaper(int id) {
        return examinationPaperMapper.updateState(id, 2) > 0;
    }

    @Override
    public List<ExaminationPaper> getExaminationPapers() {
        return examinationPaperMapper.getExaminationPapers();
    }
}
