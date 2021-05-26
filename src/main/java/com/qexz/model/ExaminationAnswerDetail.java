package com.qexz.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by YANGFU18 on 2021/5/23.
 */
public class ExaminationAnswerDetail implements Serializable {


    private static final long serialVersionUID = -5833060742923499664L;
    private int id;
    private int paper_id;// '试卷ID',
    private int answer_id;// '答卷ID',
    private int question_id;// '问题ID',
    private String answer;// '作答',
    private Date create_time ;// '创建时间',
    private Date update_time;// '更新时间',
    private int score;// '得分',

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

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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
}
