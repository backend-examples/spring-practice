package com.example.dao.impl;

import com.example.dao.UserDao;
import com.example.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findAll() {
        List<User> userList = jdbcTemplate.query("select * from sys_user", new BeanPropertyRowMapper<User>(User.class));

        return userList;
    }

    public Long save(final User user) {
        // 创建PreparedStatementCreator
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                // 使用原始的jdbc完成一个PreparedStatement的组建

                // 指定当前生成主键key
                PreparedStatement preparedStatement = connection.prepareStatement("insert into sys_user values (?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

                preparedStatement.setObject(1, null);
                preparedStatement.setString(2, user.getUsername());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getPassword());
                preparedStatement.setString(5, user.getPhoneNum());

                return preparedStatement;
            }
        };

        // 创建KeyHolder
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        // 获得生成的主键
        Long userId = generatedKeyHolder.getKey().longValue();

        // 使用update插入数据，无法自动获取插入记录的id
//        jdbcTemplate.update("insert into sys_user values (?, ?, ?, ?, ?)", null, user.getUsername(), user.getEmail(), user.getPassword(), user.getPhoneNum());

        // 返回该用户保存后的id，该id是数据库自动生成的
        return userId;
    }

    public void saveUserRoleRelation(Long userId, Long[] roleIds) {
        for (Long roleId : roleIds ) {
            jdbcTemplate.update("insert into sys_user_role values (?, ?)", userId, roleId);
        }
    }

    public void delUserRoleRelation(Long userId) {
        jdbcTemplate.update("delete from sys_user_role where userId = ?", userId);
    }

    public void delUser(Long userId) {
        jdbcTemplate.update("delete from sys_user where id = ?", userId);
    }

    public User findByUsernameAndPassword(String username, String password) {
        User user = jdbcTemplate.queryForObject("select * from sys_user where username=? and password = ?", new BeanPropertyRowMapper<User>(User.class), username, password);
        return user;
    }
}
