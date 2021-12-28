package com.yuan.mall.service;

import com.yuan.mall.entity.ums.UmsAdmin;
import com.yuan.mall.entity.ums.UmsResource;

import java.util.List;

/**
 * 后台用户缓存操作Service
 * @author diaoyuan
 */
public interface UmsAdminCacheService {

    /**
     * @param adminId
     * 删除后台用户缓存
     */
    void delAdmin(Long adminId);

    /**
     * @param adminId
     * 删除后台用户资源列表缓存
     */
    void delResourceList(Long adminId);

    /**
     * @param roleId
     * 当角色相关资源信息改变时删除相关后台用户缓存
     */
    void delResourceListByRole(Long roleId);

    /**
     * @param roleIds
     * 当角色相关资源信息改变时删除相关后台用户缓存
     */
    void delResourceListByRoleIds(List<Long> roleIds);

    /**
     * @param resourceId
     * 当资源信息改变时，删除资源项目后台用户缓存
     */
    void delResourceListByResource(Long resourceId);

    /**
     * @param username
     * 获取缓存后台用户信息
     */
    UmsAdmin getAdmin(String username);

    /**
     * @param admin
     * 设置缓存后台用户信息
     */
    void setAdmin(UmsAdmin admin);

    /**
     * @param adminId
     * 获取缓存后台用户资源列表
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * @param resourceList
     * 设置缓存后台用户资源列表
     */
    void setResourceList(Long adminId, List<UmsResource> resourceList);
}