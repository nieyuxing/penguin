package com.qexz.service.impl;

import com.github.pagehelper.PageHelper;
import com.qexz.common.QexzConst;
import com.qexz.dao.ContestMapper;
import com.qexz.dao.PositionMapper;
import com.qexz.model.Contest;
import com.qexz.model.Position;
import com.qexz.service.ContestService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("contestService")
public class ContestServiceImpl implements ContestService{

    private static Log LOG = LogFactory.getLog(ContestServiceImpl.class);

    @Autowired
    private ContestMapper contestMapper;
    @Autowired
    private PositionMapper positionMapper;

    @Override
    public int addContest(Contest contest) {
        contest.setTotalScore(0);
        contest.setState(0);
        return contestMapper.insertContest(contest);
    }

    @Override
    public boolean updateContest(Contest contest) {
        return contestMapper.updateContestById(contest) > 0;
    }

    @Override
    public Contest getContestById(int id) {
        return contestMapper.getContestById(id);
    }

    @Override
    public Map<String, Object> getContests(int pageNum, int pageSize) {
        Map<String, Object> data = new HashMap<>();
        int count = contestMapper.getCount();
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("contests", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("contests", new ArrayList<>());
            return data;
        }
        List<Position> positions = positionMapper.getPositions();
        PageHelper.startPage(pageNum, pageSize);
        List<Contest> contests = contestMapper.getContests();
        Map<Integer, String> positionId2name = positions.stream().
                collect(Collectors.toMap(Position::getId, Position::getName));
        for (Contest contest : contests) {
            contest.setPositionName(positionId2name.
                    getOrDefault(contest.getpositionId(), "????????????"));
        }
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("contests", contests);
        return data;
    }

    @Override
    public boolean deleteContest(int id) {
        return contestMapper.deleteContest(id) > 0;
    }

    @Override
    public boolean updateStateToStart() {
        return contestMapper.updateStateToStart(new Date()) > 0;
    }

    @Override
    public boolean updateStateToEnd() {
        return contestMapper.updateStateToEnd(new Date()) > 0;
    }

    @Override
    public List<Contest> getContestsByContestIds(Set<Integer> contestIds) {
        return contestMapper.getContestsByContestIds(contestIds);
    }
}
