package com.yuan.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuan.mall.entity.ums.UmsAdminRoleRelation;
import com.yuan.mall.entity.ums.UmsResource;
import com.yuan.mall.entity.ums.UmsRole;
import com.yuan.mall.mapper.UmsAdminRoleRelationMapper;
import com.yuan.mall.mapper.UmsResourceMapper;
import com.yuan.mall.service.UmsAdminRoleRelationService;
import com.yuan.mall.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author diaoyuan
 */
@Service
public class UmsAdminRoleRelationServiceImpl implements UmsAdminRoleRelationService {

    @Autowired
    private UmsAdminRoleRelationMapper adminRoleRelationMapper;

    @Autowired
    private UmsResourceService umsResourceService;

    @Autowired
    private UmsRoleServiceImpl umsRoleService;

    @Override
    public int insertList(List<UmsAdminRoleRelation> adminRoleRelationList) {
        return 0;
    }

    @Override
    public List<UmsRole> getRoleList(Integer adminId) {
        return null;
    }

    @Override
    public List<UmsResource> getResourceList(Integer adminId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("admin_id", adminId);
        UmsAdminRoleRelation adminRoleRelation = adminRoleRelationMapper.selectOne(queryWrapper);
        return umsRoleService.listResource(adminRoleRelation.getRoleId());
    }

    @Override
    public List<Integer> getAdminIdList(Integer resourceId) {
        return null;
    }
}
