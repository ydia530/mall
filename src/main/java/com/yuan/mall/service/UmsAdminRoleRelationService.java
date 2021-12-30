package com.yuan.mall.service;



import com.yuan.mall.entity.ums.UmsAdminRoleRelation;
import com.yuan.mall.entity.ums.UmsResource;
import com.yuan.mall.entity.ums.UmsRole;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author diaoyuan
 */

public interface UmsAdminRoleRelationService {
    /**
     * 批量插入用户角色关系
     */
    int insertList(@Param("list") List<UmsAdminRoleRelation> adminRoleRelationList);

    /**
     * 获取用于所有角色
     */
    List<UmsRole> getRoleList(@Param("adminId") Integer adminId);

    /**
     * 获取用户所有可访问资源
     */
    List<UmsResource> getResourceList(@Param("adminId") Integer adminId);

    /**
     * 获取资源相关用户ID列表
     */
    List<Integer> getAdminIdList(@Param("resourceId") Integer resourceId);
}
