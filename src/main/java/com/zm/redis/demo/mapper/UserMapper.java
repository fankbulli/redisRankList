package com.zm.redis.demo.mapper;

import com.zm.redis.demo.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Description: 一句话描述此类
 *
 * @Date 2020/7/10 13:54
 */
public interface UserMapper extends CrudRepository<User, Integer> {

}
