package com.yuan.mall.service;

import com.yuan.mall.entity.ums.UmsMenu;
import com.yuan.mall.entity.ums.UmsResource;
import com.yuan.mall.entity.ums.UmsRole;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yuan Diao
 * @date 2021/12/29
 */

public interface UmsRoleService {
    List<UmsMenu> listMenu(Integer roleId);

    List<UmsResource> listResource(Integer roleId);

    int allocMenu(Integer roleId, List<Integer> menuIds);

    int allocResource(Integer roleId, List<Integer> resourceIds);

    int update(Integer id, UmsRole umsRole);

    List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum);

    List<UmsRole> list();

    int create(UmsRole role);

    int delete(List<Integer> ids);

    List<String> getMenuList(Integer id);
}
