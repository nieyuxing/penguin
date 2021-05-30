package com.qexz.model;

import java.util.Date;

public class Position {

    private int id;
    private String name;
    private Date createTime;
    private Date updateTime;
    private int positionNum;
    private String imgUrl;
    private String position;
    private int state;
    private int need_count;
    private int desc;
    private int department_id;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getQuestionNum() {
        return positionNum;
    }

    public void setQuestionNum(int positionNum) {
        this.positionNum = positionNum;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getNeed_count() {
        return need_count;
    }

    public void setNeed_count(int need_count) {
        this.need_count = need_count;
    }

    public int getDesc() {
        return desc;
    }

    public void setDesc(int desc) {
        this.desc = desc;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", positionNum=" + positionNum +
                ", imgUrl='" + imgUrl + '\'' +
                ", state=" + state +
                ", position=" + position +

                '}';
    }
}
