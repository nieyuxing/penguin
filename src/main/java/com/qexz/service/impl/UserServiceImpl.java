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
        user.setAvatar_img_url(QexzConst.DEFAULT_AVATAR_IMG_URL);
        return userMapper.insertAccount(user);
    }

    @Override
    public boolean updateUser(User account) {
        return userMapper.updateUserById(account) > 0;
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
            data.put("accounts", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("accounts", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<User> accounts = userMapper.getUsers();
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("accounts", accounts);
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
}
