package com.qexz.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private int id; //'主键ID',
    private String name; //'姓名',
    private int sex; //'性别,0-男,1-女',
    private int ismarry; //'婚姻状态,0-未婚 1-已婚 2-离异',
    private int nation;//'国籍ID,0-中国',
    private int degree; //'最高学历',
    private int college; //'毕业学校',
    private int cert_type;//'证件类型,0-身份证',
    private String certno; //'证件号',
    private String password; //'密码',
    private String qq; //'QQ',
    private String vchat; //'微信号',
    private String phone; //'手机号',
    private String email; //'邮箱地址',
    private String description; //'个人描述',
    private String avatar_img_url; //'头像',
    private int approvestatus;//'审核状态,0-未审核,1-审核通过,2-不通过',
    private int state; //'当前账号状态,0-正常,1-禁用',
    private Date create_time;//'创建时间',
    private Date update_time; //'更新时间',
    private String addr; //'住址',
    private String resume_file; //'简历文件路径',
    private String attribute1; //'预留字段1',
    private String attribute2;//'预留字段2',
    private String attribute3; //'预留字段3',
    private String attribute4; //'预留字段4',
    private String attribute5; //'预留字段5',

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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getIsmarry() {
        return ismarry;
    }

    public void setIsmarry(int ismarry) {
        this.ismarry = ismarry;
    }

    public int getNation() {
        return nation;
    }

    public void setNation(int nation) {
        this.nation = nation;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getCollege() {
        return college;
    }

    public void setCollege(int college) {
        this.college = college;
    }

    public int getCert_type() {
        return cert_type;
    }

    public void setCert_type(int cert_type) {
        this.cert_type = cert_type;
    }

    public String getCertno() {
        return certno;
    }

    public void setCertno(String certno) {
        this.certno = certno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getVchat() {
        return vchat;
    }

    public void setVchat(String vchat) {
        this.vchat = vchat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar_img_url() {
        return avatar_img_url;
    }

    public void setAvatar_img_url(String avatar_img_url) {
        this.avatar_img_url = avatar_img_url;
    }

    public int getApprovestatus() {
        return approvestatus;
    }

    public void setApprovestatus(int approvestatus) {
        this.approvestatus = approvestatus;
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

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getResume_file() {
        return resume_file;
    }

    public void setResume_file(String resume_file) {
        this.resume_file = resume_file;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    public String getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }
}
