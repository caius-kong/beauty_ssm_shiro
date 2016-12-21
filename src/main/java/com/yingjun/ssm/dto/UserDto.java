package com.yingjun.ssm.dto;

import com.yingjun.ssm.entity.User;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kongyunhui on 2016/11/24.
 */
public class UserDto implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String salt;
    private String role_ids;
    private Boolean locked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRole_ids() {
        return role_ids;
    }

    public void setRole_ids(String role_ids) {
        this.role_ids = role_ids;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", role_ids='" + role_ids + '\'' +
                ", locked=" + locked +
                '}';
    }
}
