package com.qexz.model;

import com.qexz.service.UserService;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by YANGFU18 on 2021/5/23.
 */
public class ExaminationAnswer implements Serializable {

    private static final long serialVersionUID = -5833060742923499664L;

    private int id;
    private int paper_id;// '试卷ID',
    private int user_id;// '用户ID',
    private Date create_time;// '创建时间',
    private Date start_time;// '开考时间',
    private Date end_time;// '结束时间',
    private Date update_time;// '更新时间',
    private int score;// '得分',
    private int state;//'答卷状态',
    private int version;//'答卷状态',

    private ExaminationPaper paper;
    private List<ExaminationAnswerDetail> examinationAnswerDetails ;

    private User user ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(int paper_id) {
        this.paper_id = paper_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public ExaminationPaper getPaper() {
        return paper;
    }

    public void setPaper(ExaminationPaper paper) {
        this.paper = paper;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ExaminationAnswerDetail> getExaminationAnswerDetails() {
        return examinationAnswerDetails;
    }

    public void setExaminationAnswerDetails(List<ExaminationAnswerDetail> examinationAnswerDetails) {
        this.examinationAnswerDetails = examinationAnswerDetails;
    }
}
