package com.zm.redis.demo.dto;

import com.zm.redis.demo.model.User;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: 一句话描述此类
 *
 * @Date 2020/7/9 15:57
 */
@Data
public class ScoreDto implements Serializable {

    private User user;

    private Integer score;
}
