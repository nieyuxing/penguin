package com.qexz.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by YANGFU18 on 2021/5/22.
 */
public class ExaminationPaper implements Serializable {

    private static final long serialVersionUID = -5833060742923499664L;
    private int id; //'主键ID',
    private String exam_name;//'试卷名称',
    private int examination_type;// '试卷类型,0表示单项选择题,1表示多项选择题,2表示问答题,3表示编程题',
    private int department_id;// '所属部门',
    private int state;//'试卷状态',
    private Date create_time;// '创建时间',
    private String create_by;// '创建人',
    private Date update_time;// '更新时间',
    private String update_by;// '更新人',
    private int score;//'题目分值',
    private int difficulty;//'试题难度',
    private int version;//'数据版本',
    private Date startDate;
    private Date endDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public int getExamination_type() {
        return examination_type;
    }

    public void setExamination_type(int examination_type) {
        this.examination_type = examination_type;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
