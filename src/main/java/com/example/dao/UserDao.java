package com.example.dao;

import com.example.domain.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();

    Long save(User user);

    void saveUserRoleRelation(Long id, Long[] roleIds);

    void delUserRoleRelation(Long userId);

    void delUser(Long userId);

    User findByUsernameAndPassword(String username, String password);
}
