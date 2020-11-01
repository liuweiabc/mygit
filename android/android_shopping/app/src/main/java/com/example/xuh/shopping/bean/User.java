package com.example.xuh.shopping.bean;

import java.io.Serializable;
import java.util.UUID;

/**
 * 用户实体类
 * @author : autumn_leaf
 */
public class User implements Serializable {

    private Integer id;
    private String uuid;
    private String nicheng;
    private String username;
    private String password;

    public User() {
        uuid = UUID.randomUUID().toString();
    }

    public String getNicheng(){return nicheng;}

    public void setNicheng(String nicheng){this.nicheng = nicheng;}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
