package com.yuan.mall.service;

import com.yuan.mall.common.CommonResult;
import com.yuan.mall.entity.ums.UmsAdmin;
import com.yuan.mall.entity.ums.UmsRole;
import com.yuan.mall.pojo.dto.UmsAdminParam;
import com.yuan.mall.pojo.dto.UpdateAdminPasswordParam;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台管理员Service
 * @author diaoyuan
 */
public interface UmsAdminService {
    /**
     * 根据用户名获取后台管理员
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 注册功能
     */
    CommonResult register(UmsAdminParam umsAdminParam);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username,String password);

    /**
     * 刷新token的功能
     * @param oldToken 旧的token
     */
    String refreshToken(String oldToken);

    /**
     * 根据用户id获取用户
     */
    UmsAdmin getAdminById(Integer id);

    /**
     * 根据用户名或昵称分页查询用户
     */
    List<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改指定用户信息
     * @return
     */
    CommonResult update(Integer id, UmsAdmin admin);

    /**
     * 删除指定用户
     * @return
     */
    CommonResult delete(Integer id);

    /**
     * 修改用户角色关系
     */
    @Transactional
    int updateRole(Integer adminId, List<Long> roleIds);

    /**
     * 获取用户对应角色
     */
    List<UmsRole> getRoleList(Integer adminId);

    /**
     * 修改密码
     */
    int updatePassword(UpdateAdminPasswordParam updatePasswordParam);

    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);

    List<UmsAdmin> listAll(Integer pageNum, Integer pageSize);

    CommonResult allocRoles(Integer adminId, List<Integer> roleIds);

    CommonResult updateStatus(Integer adminId, Integer status);
}