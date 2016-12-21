package com.yingjun.ssm.entity;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public class User implements Serializable {
    private Long id; //编号
    @NotEmpty
    private String username; //用户名
    @NotEmpty
    private String password; //密码
    private String salt; //加密密码的盐
    @NotEmpty
    private List<Long> roleIds; //拥有的角色列表
    private Boolean locked = Boolean.FALSE;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String salt) {
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

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

    public String getCredentialsSalt() {
        return username + salt;
    }

    public List<Long> getRoleIds() {
        if(roleIds == null) {
            roleIds = new ArrayList<Long>();
        }
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    // this.roleIds --> roleIdsStr
    public String getRoleIdsStr() {
        if(CollectionUtils.isEmpty(roleIds)) {
            return "";
        }
        StringBuilder s = new StringBuilder();
        for(int i=0; i<roleIds.size(); i++) {
            if(i>0){
                s.append(",");
            }
            s.append(roleIds.get(i));
        }
        return s.toString();
    }

    // 获得sql中返回的roleIdsStr，并解析成roleIds
    public void setRoleIdsStr(String roleIdsStr) {
        if(StringUtils.isNotEmpty(roleIdsStr)) {
            String[] roleIdStrs = roleIdsStr.split(",");
            for(String roleIdStr : roleIdStrs) {
                getRoleIds().add(Long.valueOf(roleIdStr));
            }
        }
    }
    
    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", roleIds=" + roleIds +
                ", locked=" + locked +
                '}';
    }
}
