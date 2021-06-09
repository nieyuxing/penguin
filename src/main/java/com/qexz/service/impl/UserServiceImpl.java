package com.qexz.service.impl;

import com.github.pagehelper.PageHelper;
import com.qexz.common.QexzConst;
import com.qexz.dao.UserMapper;
import com.qexz.model.User;
import com.qexz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int addUser(User user) {
        user.setAvatarImgUrl(QexzConst.DEFAULT_AVATAR_IMG_URL);
        return userMapper.insertUser(user);
    }

    @Override
    public boolean updateUser(User user) {
        return userMapper.updateUserById(user) > 0;
    }
    @Override
    public boolean updatePassword(User user) {
        return userMapper.updatePassword(user) > 0;
    }

    @Override
    public boolean updateAvatarImgUrlById(String avatarImgUrl, int id) {
        return userMapper.updateAvatarImgUrlById(avatarImgUrl, id) > 0;
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public User getUserByPhone(String phone) {
         return userMapper.getUserByPhone(phone);
    }

    @Override
    public List<User> getUsersByIds(List<Integer> studentIds) {
        return userMapper.getUsersByIds(studentIds);
    }

    @Override
    public Map<String, Object> getUsers(int pageNum, int pageSize) {
        Map<String, Object> data = new HashMap<>();
        int count = userMapper.getCount();
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("users", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("users", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.getUsers();
        for(User user:users){
            if(user.getResume_file()!=null && user.getResume_file() !=""){
                user.setResume_file(QexzConst.IPADDRESS + user.getResume_file());
            }
        }
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("users", users);
        return data;
    }

    @Override
    public boolean deleteUser(int id) {
        return userMapper.deleteUser(id) > 0;
    }

    @Override
    public boolean disabledUser(int id) {
        return userMapper.updateState(id, 1) > 0;
    }

    @Override
    public boolean abledUser(int id) {
        return userMapper.updateState(id, 0) > 0;
    }

    @Override
    public List<User> getUsersByIds(Set<Integer> ids) {
        return userMapper.getUsersByIdSets(ids);
    }

    @Override
    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }

    @Override
    public boolean approvedUser(int id) {
        return userMapper.updateApproveStatus(id, 1) > 0;
    }

    @Override
    public boolean rejectedUser(int id) {
        return userMapper.updateApproveStatus(id, 2) > 0;
    }

    @Override
    public List<User> getUsers() {
        return userMapper.getUsers();
    }

    @Override
    public List<User> getApprovedUsers() {
        return userMapper.getApprovedUsers();
    }

}
