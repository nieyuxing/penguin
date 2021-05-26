package com.qexz.model;

import java.util.Date;

public class Contest {

    private int id;
    private String title;
    private int totalScore;
    private int positionId;
    private Date createTime;
    private Date updateTime;
    private Date startTime;
    private Date endTime;
    private int state;

    private String positionName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getpositionId() {
        return positionId;
    }

    public void setpositionId(int positionId) {
        this.positionId = positionId;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getpositionName() {
        return positionName;
    }

    public void setpositionName(String positionName) {
        this.positionName = positionName;
    }

    @Override
    public String toString() {
        return "Contest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", totalScore=" + totalScore +
                ", positionId=" + positionId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", state=" + state +
                ", positionName='" + positionName + '\'' +
                '}';
    }
}
