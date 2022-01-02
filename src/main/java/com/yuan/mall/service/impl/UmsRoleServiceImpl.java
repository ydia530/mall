package com.yuan.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.yuan.mall.entity.ums.UmsMenu;
import com.yuan.mall.entity.ums.UmsResource;
import com.yuan.mall.entity.ums.UmsRole;
import com.yuan.mall.mapper.UmsMenuMapper;
import com.yuan.mall.mapper.UmsRoleMapper;
import com.yuan.mall.service.UmsAdminCacheService;
import com.yuan.mall.service.UmsRoleService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @author diaoyuan
 */
@Service
@Slf4j
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsAdminCacheService umsAdminCacheService;

    @Autowired
    private UmsRoleMapper umsRoleMapper;

    @Autowired
    UmsMenuMapper umsMenuMapper;

    @Override
    public List<UmsMenu> listMenu(Integer roleId) {
        return null;
    }

    @Override
    public List<UmsResource> listResource(Integer roleId) {
        return umsRoleMapper.getResourcesByRoleId(roleId);
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
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StrUtil.isNotEmpty(keyword)){
            queryWrapper.like("name", keyword);
        }
        queryWrapper.eq("status", 1);

        return umsRoleMapper.selectList(queryWrapper);
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

    @Override
    public List<UmsMenu> getMenuList(Integer userId) {
        List<UmsMenu> umsMenus =  umsRoleMapper.getMenuList(userId);

        if (umsMenus.isEmpty()){
            return  new ArrayList<>();
        }
        return umsMenus;
    }

    @Override
    public List<UmsResource> loadAdminResource(Integer adminId) {
        List<UmsResource> resourceList = umsAdminCacheService.getResourceList(adminId);
        if(CollUtil.isNotEmpty(resourceList)){
            log.info("缓存命中 admin: "+ adminId);
            return  resourceList;
        }
        resourceList = umsRoleMapper.getResourcesByAdminId(adminId);
        if(CollUtil.isNotEmpty(resourceList)){
            umsAdminCacheService.setResourceList(adminId,resourceList);
        }
        return resourceList;
    }

    @Override
    public List<UmsRole> listAll() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("status", 1);
        return umsRoleMapper.selectList(queryWrapper);
    }

}
