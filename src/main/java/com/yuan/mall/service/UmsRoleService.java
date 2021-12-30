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
    List<UmsMenu> listMenu(Long roleId);

    List<UmsResource> listResource(Long roleId);

    int allocMenu(Long roleId, List<Long> menuIds);

    int allocResource(Long roleId, List<Long> resourceIds);

    int update(Long id, UmsRole umsRole);

    List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum);

    List<UmsRole> list();

    int create(UmsRole role);

    int delete(List<Long> ids);
}
