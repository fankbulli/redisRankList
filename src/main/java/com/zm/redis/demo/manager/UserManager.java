package com.zm.redis.demo.manager;

import com.zm.redis.demo.model.User;

import java.util.List;

/**
 * Description: 一句话描述此类
 *
 * @Date 2020/7/14 9:57
 */
public interface UserManager {
    void save(User user);

    List<User> list();

    User detail(Integer id);

    void del(Integer id);
}
