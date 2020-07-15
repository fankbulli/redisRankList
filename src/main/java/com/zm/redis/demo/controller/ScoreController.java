package com.zm.redis.demo.controller;

import com.zm.redis.demo.dto.ScoreDto;
import com.zm.redis.demo.manager.ScoreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description: 一句话描述此类
 *
 * @Date 2020/7/9 15:33
 */
@RestController
@RequestMapping("/score")
public class ScoreController {
    @Autowired
    private ScoreManager scoreManager;

    @PostMapping("/add")
    public boolean addScore(@RequestBody ScoreDto dto) {
        return scoreManager.addScore(dto);
    }

    @GetMapping("/list/{type}/{page}/{size}")
    public List<ScoreDto> list(@PathVariable Integer type, @PathVariable Integer page, @PathVariable Integer size) {
        return scoreManager.list(type, page, size);
    }

    @GetMapping("/get/{type}/{userId}")
    public ScoreDto detail(@PathVariable Integer type, @PathVariable Integer userId) {
        return scoreManager.detail(type, userId);
    }
}
