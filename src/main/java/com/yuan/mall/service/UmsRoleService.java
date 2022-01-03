package com.yuan.mall.service;

import com.yuan.mall.common.CommonResult;
import com.yuan.mall.entity.ums.UmsMenu;
import com.yuan.mall.entity.ums.UmsResource;
import com.yuan.mall.entity.ums.UmsRole;

import java.util.List;

/**
 * @author Yuan Diao
 * @date 2021/12/29
 */

public interface UmsRoleService {
    List<UmsResource> listResource(Integer roleId);

    CommonResult allocMenu(Integer roleId, List<Integer> menuIds);

    CommonResult allocResource(Integer roleId, List<Integer> resourceIds);

    List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum);

    List<UmsRole> list();

    CommonResult create(UmsRole role);

    CommonResult delete(Integer ids);

    List<UmsMenu> getMenuList(Integer id);

    List<UmsResource> loadAdminResource(Integer id);

    List<UmsRole> listAll();

    CommonResult updateStatus(Integer id, Integer status);

    CommonResult update(Integer id, UmsRole role);

    List<UmsMenu> listMenuByRoleId(Integer roleId);
}
