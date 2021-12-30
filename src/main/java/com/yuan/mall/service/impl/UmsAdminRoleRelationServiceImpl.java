package com.yuan.mall.service.impl;

import com.yuan.mall.entity.ums.UmsAdminRoleRelation;
import com.yuan.mall.entity.ums.UmsResource;
import com.yuan.mall.entity.ums.UmsRole;
import com.yuan.mall.service.UmsAdminRoleRelationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author diaoyuan
 */
@Service
public class UmsAdminRoleRelationServiceImpl implements UmsAdminRoleRelationService {
    @Override
    public int insertList(List<UmsAdminRoleRelation> adminRoleRelationList) {
        return 0;
    }

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        return null;
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        return null;
    }

    @Override
    public List<Long> getAdminIdList(Long resourceId) {
        return null;
    }
}
