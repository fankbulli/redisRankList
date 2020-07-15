package com.zm.redis.demo.manager;

import com.zm.redis.demo.dto.ScoreDto;

import java.util.List;

/**
 * Description: 一句话描述此类
 *
 * @Date 2020/7/9 15:32
 */
public interface ScoreManager {

    boolean addScore(ScoreDto dto);

    List<ScoreDto> list(Integer type, Integer page, Integer size);

    ScoreDto detail(Integer type, Integer userId);

    boolean delAll();
}
