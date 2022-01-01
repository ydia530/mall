package com.yuan.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.yuan.mall.common.utils.RedisUtil;
import com.yuan.mall.entity.ums.UmsAdmin;
import com.yuan.mall.entity.ums.UmsAdminRoleRelation;
import com.yuan.mall.entity.ums.UmsResource;
import com.yuan.mall.mapper.UmsAdminRoleRelationMapper;
import com.yuan.mall.service.UmsAdminCacheService;
import com.yuan.mall.service.UmsAdminRoleRelationService;
import com.yuan.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台用户缓存操作Service实现类
 * @author diaoyuan
 */
@Service
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {
    @Autowired
    private UmsAdminService umsAdminService;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.admin}")
    private String REDIS_KEY_ADMIN;
    @Value("${redis.key.resourceList}")
    private String REDIS_KEY_RESOURCE_LIST;

    @Override
    public void delAdmin(Integer adminId) {
//        UmsAdmin admin = umsAdminService.getItem(adminId);
//        if (admin != null) {
//            String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
//            redisUtil.del(key);
//        }
    }

    @Override
    public void delResourceList(Integer adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisUtil.del(key);
    }

    @Override
    public void delResourceListByRole(Integer roleId) {
//        UmsAdminRoleRelationExample example = new UmsAdminRoleRelationExample();
//        example.createCriteria().andRoleIdEqualTo(roleId);
//        List<UmsAdminRoleRelation> relationList = adminRoleRelationMapper.selectByExample(example);
//        if (CollUtil.isNotEmpty(relationList)) {
//            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
//            List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
//            redisUtil.del(keys);
//        }
    }

    @Override
    public void delResourceListByRoleIds(List<Integer> roleIds) {
//        UmsAdminRoleRelationExample example = new UmsAdminRoleRelationExample();
//        example.createCriteria().andRoleIdIn(roleIds);
//        List<UmsAdminRoleRelation> relationList = adminRoleRelationMapper.selectByExample(example);
//        if (CollUtil.isNotEmpty(relationList)) {
//            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
//            List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
//            redisService.del(keys);
//        }
    }

    @Override
    public void delResourceListByResource(Integer resourceId) {
//        List<Integer> adminIdList = adminRoleRelationService.getAdminIdList(resourceId);
//        if (CollUtil.isNotEmpty(adminIdList)) {
//            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
//            List<String> keys = adminIdList.stream().map(adminId -> keyPrefix + adminId).collect(Collectors.toList());
//            redisUtil.del(keys);
//        }
    }

    @Override
    public UmsAdmin getAdmin(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + username;
        return (UmsAdmin) redisUtil.get(key);
    }

    @Override
    public void setAdmin(UmsAdmin admin) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
        redisUtil.set(key, admin, REDIS_EXPIRE);
    }

    @Override
    public List<UmsResource> getResourceList(Integer adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        return (List<UmsResource>) redisUtil.get(key);
    }

    @Override
    public void setResourceList(Integer adminId, List<UmsResource> resourceList) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisUtil.set(key, resourceList, REDIS_EXPIRE);
    }
}
