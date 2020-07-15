package com.zm.redis.demo.controller;

import com.zm.redis.demo.dto.ScoreDto;
import com.zm.redis.demo.manager.UserManager;
import com.zm.redis.demo.model.User;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description: 一句话描述此类
 *
 * @Date 2020/7/14 9:56
 */
@Api
@RequestMapping
@RestController
public class UserController {
    @Autowired
    private UserManager userManager;

    @PostMapping("/add")
    public void addScore(@RequestBody User dto) {
        userManager.save(dto);
    }

    @GetMapping("/list")
    public List<User> list() {
        return userManager.list();
    }

    @GetMapping("/get/{id}")
    public User detail(@PathVariable Integer id) {
        return userManager.detail(id);
    }

    @DeleteMapping("/del/{id}")
    public void del(@PathVariable Integer id) {
        userManager.del(id);
    }

}
