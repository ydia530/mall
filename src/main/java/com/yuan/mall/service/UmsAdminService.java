package com.yuan.mall.service;

import com.yuan.mall.common.CommonResult;
import com.yuan.mall.entity.ums.UmsAdmin;
import com.yuan.mall.entity.ums.UmsPermission;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 后台管理员Service
 * @author diaoyuan
 */
@Service
public interface UmsAdminService {
    /**
     * 根据用户名获取后台管理员
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 注册功能
     */
    CommonResult register(UmsAdmin umsAdminParam);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     */
    List<UmsPermission> getPermissionList(Integer adminId);
}