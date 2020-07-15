package com.zm.redis.demo.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Description: 一句话描述此类
 *
 * @Date 2020/7/9 15:31
 */
@Data
@Entity(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 159714803901985366L;
    @Id
    private Integer id;

    @Column
    private String name;
}
