package com.zm.redis.demo.constants;

/**
 * Description: 一句话描述此类
 *
 * @Date 2020/7/9 16:03
 */
public interface ScoreConstant {
    /**
     * 周榜
     */
    String WEEK_SCORE_KEY = "WEEK_SCORE_KEY";
    /**
     * 总榜
     */
    String ALL_SCORE_KEY = "ALL_SCORE_KEY";

    long WEEK_MS = 6_048 * 100_000;

    long YEAR_MS = 31_536 * 1_000_000;
}
