package com.qexz.service;

import com.qexz.model.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {

    int addUser(User user);

    boolean updateUser(User user);

    boolean updateAvatarImgUrlById(String avatarImgUrl, int id);

    User getUserByUsername(String username);

    User getUserByPhone(String phone);


    List<User> getUsersByIds(List<Integer> studentIds);

    Map<String, Object> getUsers(int pageNum, int pageSize);

    boolean deleteUser(int id);

    boolean disabledUser(int id);

    boolean abledUser(int id);

    boolean approvedUser(int id);

    boolean rejectedUser(int id);

    List<User> getUsersByIds(Set<Integer> ids);

    User getUserById(int id);

    List<User> getUsers();
}
