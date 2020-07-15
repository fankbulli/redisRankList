package com.zm.redis.demo.manager;

import com.google.common.collect.Lists;
import com.zm.redis.demo.mapper.UserMapper;
import com.zm.redis.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: 一句话描述此类
 *
 * @Date 2020/7/15 8:28
 */
@Service
public class UserManagerImpl implements UserManager {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void save(User user) {
        userMapper.save(user);
    }

    @Override
    public List<User> list() {
        Iterable<User> it = userMapper.findAll();
        List<User> list = Lists.newArrayList();
        if (null != it) {
            it.forEach(list::add);
        }
        return list;
    }

    @Override
    public User detail(Integer id) {
        return userMapper.findById(id).get();
    }

    @Override
    public void del(Integer id) {
        userMapper.deleteById(id);
    }
}
