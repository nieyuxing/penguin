package com.qexz.model;

import java.util.Date;

public class Position {

    private int id;
    private String name;
    private Date createTime;
    private Date updateTime;
    private int positionNum;
    private String imgUrl;
    private int state;
    private String descr;
    private int department_id;
    private String posi_code ;
    private String place;
    private String posi_type ;
    private String degree;
    private String deepth ;
    private String attr1 ;
    private String attr2 ;
    private String attr3 ;

    private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getPositionNum() {
        return positionNum;
    }

    public void setPositionNum(int positionNum) {
        this.positionNum = positionNum;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getPosi_code() {
        return posi_code;
    }

    public void setPosi_code(String posi_code) {
        this.posi_code = posi_code;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPosi_type() {
        return posi_type;
    }

    public void setPosi_type(String posi_type) {
        this.posi_type = posi_type;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDeepth() {
        return deepth;
    }

    public void setDeepth(String deepth) {
        this.deepth = deepth;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
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
                ", descr=" + descr +
                ", department_id=" + department_id +
                ", posi_code='" + posi_code + '\'' +
                ", place='" + place + '\'' +
                ", posi_type='" + posi_type + '\'' +
                ", degree='" + degree + '\'' +
                ", deepth='" + deepth + '\'' +
                ", attr1='" + attr1 + '\'' +
                ", attr2='" + attr2 + '\'' +
                ", attr3='" + attr3 + '\'' +
                '}';
    }
}
