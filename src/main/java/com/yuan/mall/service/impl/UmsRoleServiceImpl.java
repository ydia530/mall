package com.yuan.mall.service.impl;

import com.yuan.mall.entity.ums.UmsMenu;
import com.yuan.mall.entity.ums.UmsResource;
import com.yuan.mall.entity.ums.UmsRole;
import com.yuan.mall.mapper.UmsRoleMapper;
import com.yuan.mall.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author diaoyuan
 */
@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsRoleMapper umsRoleMapper;

    @Override
    public List<UmsMenu> listMenu(Integer roleId) {
        return null;
    }

    @Override
    public List<UmsResource> listResource(Integer roleId) {
        return umsRoleMapper.getResourcesById(roleId);
    }

    @Override
    public int allocMenu(Integer roleId, List<Integer> menuIds) {
        return 0;
    }

    @Override
    public int allocResource(Integer roleId, List<Integer> resourceIds) {
        return 0;
    }

    @Override
    public int update(Integer id, UmsRole umsRole) {
        return 0;
    }

    @Override
    public List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
        return null;
    }

    @Override
    public List<UmsRole> list() {
        return null;
    }

    @Override
    public int create(UmsRole role) {
        return 0;
    }

    @Override
    public int delete(List<Integer> ids) {
        return 0;
    }
}
