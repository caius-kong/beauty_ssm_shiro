package com.yingjun.ssm.entity;

import java.io.Serializable;

/**
 * URL动态权限控制 --> url拦截器实体类
 *
 * 1、由于是在Spring容器启动时或者调用UrlFilterService方法时initFilterChains，因此数据库修改_urlFilter需要重启服务
 *
 * 2、roles是角色划分，permissions是权限划分。
 *    如果url只有admin能访问，那就只需要填写role=admin （注意：roles="admin.guest"，表示同时拥有）
 *    如果url可以被任何拥有user:view的权限的用户访问，那就只需要填写permissions=user:view
 *    如果url没有访问限制，那就别设urlFilter。
 */
public class UrlFilter implements Serializable {
    private Long id;
    private String name; //url名称/描述
    private String url; //地址
    private String roles; //所需要的角色，可省略
    private String permissions; //所需要的权限，可省略

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UrlFilter urlFilter = (UrlFilter) o;

        if (id != null ? !id.equals(urlFilter.id) : urlFilter.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UrlFilter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", roles='" + roles + '\'' +
                ", permissions='" + permissions + '\'' +
                '}';
    }
}
