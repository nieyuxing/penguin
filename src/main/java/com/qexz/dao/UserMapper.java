package com.qexz.dao;

import com.qexz.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Mapper
public interface UserMapper {

    int insertUser(@Param("User") User user);

    int updateUserById(@Param("user") User user);

    int updatePassword(@Param("user") User user);

    int updateAvatarImgUrlById(@Param("avatarImgUrl") String avatarImgUrl, @Param("id") int id);

    User getUserByUsername(@Param("username") String username);

    User getUserByPhone(@Param("phone") String phone);

    List<User> getUsersByIds(@Param("studentIds") List<Integer> studentIds);

    int getCount();

    List<User> getUsers();

    int deleteUser(@Param("id") int id);

    int updateState(@Param("id") int id, @Param("state") int state);

    List<User> getUsersByIdSets(@Param("ids") Set<Integer> ids);

    User getUserById(@Param("id") int id);

    int updateApproveStatus(@Param("id") int id, @Param("approvestatus") int approvestatus);

    List<User> getApprovedUsers();
}
