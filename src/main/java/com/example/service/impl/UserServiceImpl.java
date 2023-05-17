package com.example.service.impl;

import com.example.dao.RoleDao;
import com.example.dao.UserDao;
import com.example.domain.Role;
import com.example.domain.User;
import com.example.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    private RoleDao roleDao;

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> showList() {
        List<User> userList = userDao.findAll();
        // 封装userList中每一个User的roles数据
        for (User user : userList) {
            // 获得每个user的id
            Long id = user.getId();
            // 将id作为参数查询当前userId对应的role的集合数据
            List<Role> roles = roleDao.findRoleByUserId(id);

            user.setRoles(roles);
        }
        return userList;
    }

    public void save(User user, Long[] roleIds) {
        // 第一步 向sys_user表中存数据
        Long userId = userDao.save(user);

        // 第二步 向sys_user_role中存储多条数据
        userDao.saveUserRoleRelation(userId, roleIds);
    }

    public void del(Long userId) {
        // 1、删除sys_user_role关系表
        userDao.delUserRoleRelation(userId);

        // 2、sys_user表
        userDao.delUser(userId);
    }

    public User login(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, password);
        return user;
    }
}
