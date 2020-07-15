package com.zm.redis.demo.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zm.redis.demo.constants.ScoreConstant;
import com.zm.redis.demo.dto.ScoreDto;
import com.zm.redis.demo.mapper.UserMapper;
import com.zm.redis.demo.model.User;
import com.zm.redis.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description: 一句话描述此类
 *
 * @Date 2020/7/9 15:32
 */
@Service
public class ScoreManagerImpl implements ScoreManager {


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean addScore(ScoreDto dto) {
        boolean flag = false;
        User user = userMapper.findById(dto.getUser().getId()).get();
        if (null == user) {
            user = dto.getUser();
            userMapper.save(user);
        }
        //获取年榜 key
        String allKey = getKey(null);
        Set<String> allKeys = redisUtil.keys(allKey);
        if (CollectionUtils.isEmpty(allKeys)) {
            flag = redisUtil.addZSet(allKey, user, dto.getScore());
        } else {
            Double score = redisUtil.zScore(allKey, user);
            double newSc = score + dto.getScore() + getYearScore();
            flag = redisUtil.addZSet(allKey, user, newSc);
        }
        //获取周榜 key
        String weekKey = getKey(getFirstDayOfWeek());
        Set<String> weekKeys = redisUtil.keys(weekKey);
        if (CollectionUtils.isEmpty(weekKeys)) {
            flag = redisUtil.addZSet(weekKey, user, dto.getScore());
        } else {
            Double score = redisUtil.zScore(weekKey, user);
            double newSc = score + dto.getScore() + getYearScore();
            flag = redisUtil.addZSet(weekKey, user, newSc);
        }

        return flag;
    }

    @Override
    public List<ScoreDto> list(Integer type, Integer page, Integer size) {
        String key = "";
        if (1 == type) {
            key = getKey(getFirstDayOfWeek());
        } else {
            key = getKey(null);
        }
        Set<ZSetOperations.TypedTuple<Object>> set = redisUtil.zRange(key, page * size, (page + 1) * size);
        List<ScoreDto> scoreDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(set)) {
            for (ZSetOperations.TypedTuple<Object> tuple : set) {
                ScoreDto dto = new ScoreDto();
                Object o = tuple.getValue();
                JSONObject json = JSON.parseObject(JSONObject.toJSONString(o));
                User u = new User();
                u.setId(json.getInteger("id"));
                u.setName(json.getString("name"));
                dto.setUser(u);
                dto.setScore((int) (tuple.getScore() / 1));
                scoreDtos.add(dto);
            }
        }
        return scoreDtos;
    }

    @Override
    public ScoreDto detail(Integer type, Integer userId) {
        User u = userMapper.findById(userId).get();
        String key = "";
        if (1 == type) {
            key = getKey(getFirstDayOfWeek());
        } else {
            key = getKey(null);
        }
        ScoreDto dto = new ScoreDto();
        Optional<Double> optional = Optional.of(redisUtil.zScore(key, u));
        double score = optional.orElse(0.0);
        dto.setUser(u);
        dto.setScore((int) score);
        return dto;
    }

    @Override
    public boolean delAll() {
        return false;
    }

    public long getNowTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public long getDerTime(long start) {
        long now = getNowTime();
        return now - start;
    }

    public Date getFirstDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        try {
            cal.set(Calendar.DAY_OF_WEEK, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cal.getTime();
    }

    public Date simpleNowDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = sdf.format(date);
        Date date2 = null;
        try {
            date2 = sdf.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date2;
    }

    public int get(Date date, Integer type) {
        Calendar cal = Calendar.getInstance();
        try {
            if (null != date) {
                cal.setTime(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cal.get(type);
    }

    public String getKey(Date date) {
        Calendar cal = Calendar.getInstance();
        if (null != date) {
            cal.setTime(date);
            return ScoreConstant.WEEK_SCORE_KEY + "_" + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH + 1) + cal.get(Calendar.DATE);
        } else {
            return ScoreConstant.ALL_SCORE_KEY + "_" + cal.get(Calendar.YEAR);
        }
    }

    public double getYearScore() {
        Calendar cal = Calendar.getInstance();
        String d = String.valueOf(cal.get(Calendar.DAY_OF_YEAR));
        String h = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        String m = String.valueOf(cal.get(Calendar.MINUTE));
        String s = String.valueOf(cal.get(Calendar.SECOND));
        String ms = String.valueOf(cal.get(Calendar.MILLISECOND));
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 3 - d.length(); i++) {
            builder.append("0");
        }
        builder.append(d);
        for (int i = 0; i < 2 - h.length(); i++) {
            builder.append("0");
        }
        builder.append(h);
        for (int i = 0; i < 2 - m.length(); i++) {
            builder.append("0");
        }
        builder.append(m);
        for (int i = 0; i < 2 - s.length(); i++) {
            builder.append("0");
        }
        builder.append(s);
        for (int i = 0; i < 3 - ms.length(); i++) {
            builder.append("0");
        }
        builder.append(ms);
        BigDecimal b1 = new BigDecimal("1000000000000");
        BigDecimal b2 = new BigDecimal(builder.toString());
        BigDecimal b3 = b2.divide(b1);
        BigDecimal b4 = new BigDecimal("1").subtract(b3);
        return b4.doubleValue();
    }
}
