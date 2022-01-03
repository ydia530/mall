package com.yuan.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.yuan.mall.common.CommonResult;
import com.yuan.mall.entity.ums.UmsAdmin;
import com.yuan.mall.entity.ums.UmsMenu;
import com.yuan.mall.entity.ums.UmsResource;
import com.yuan.mall.entity.ums.UmsRole;
import com.yuan.mall.mapper.UmsMenuMapper;
import com.yuan.mall.mapper.UmsRoleMapper;
import com.yuan.mall.service.UmsAdminCacheService;
import com.yuan.mall.service.UmsRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;



/**
 * 角色服务实现类
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
    private UmsAdminCacheService adminCacheService;


    @Override
    public List<UmsMenu> listMenuByRoleId(Integer roleId) {
        return umsRoleMapper.getMenuListByRole(roleId);
    }

    @Override
    public List<UmsResource> listResource(Integer roleId) {
        return umsRoleMapper.getResourcesByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult allocMenu(Integer roleId, List<Integer> menuIds) {
        try{
            umsRoleMapper.allocMenu(roleId, menuIds);

            return CommonResult.success(200, "分配成功");
        }catch (Exception e){
            log.error("分配出错"+ e.getMessage());
            return CommonResult.failed("分配失败");
        }
    }

    public List<UmsAdmin> getAdminListByRole(int roleId){
        return umsRoleMapper.getAdminsByRole(roleId);
    }

    @Override
    public CommonResult allocResource(Integer roleId, List<Integer> resourceIds) {
        try{
            umsRoleMapper.allocResource(roleId, resourceIds);
            //需删除所有与该角色可访问的资源 缓存的用户
            List<UmsAdmin> admins = getAdminListByRole(roleId);
            admins.forEach(admin -> adminCacheService.delResourceList(admin.getId()));
            return CommonResult.success(200, "分配成功");
        }catch (Exception e){
            log.error("分配出错" + e.getMessage());
            return CommonResult.failed("分配失败");
        }
    }

    @Override
    public List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StrUtil.isNotEmpty(keyword)){
            queryWrapper.like("name", keyword);
        }

        return umsRoleMapper.selectList(queryWrapper);
    }

    @Override
    public List<UmsRole> list() {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult create(UmsRole role) {
        try{
            umsRoleMapper.insert(role);
            return CommonResult.success(200, "添加成功");
        } catch (Exception e){
            log.error("新增失败："+ e.getMessage());
        }

        return CommonResult.failed("新增失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult delete(Integer id) {
        try{
            umsRoleMapper.deleteById(id);
            return CommonResult.success(id, "删除成功");
        }catch (Exception e) {
            log.error("删除失败" + e.getMessage());
            return CommonResult.failed("删除失败");
        }
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
        return umsRoleMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult updateStatus(Integer id, Integer status) {
        try {
            UmsRole role = umsRoleMapper.selectById(id);
            role.setStatus(status);
            umsRoleMapper.updateById(role);
            return CommonResult.success(200, "操作成功");
        } catch (Exception e) {
            log.error("操作失败：" + e.getMessage());
            return CommonResult.failed("操作失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(Integer id, UmsRole role) {
        try{
            umsRoleMapper.updateById(role);
            return CommonResult.success(200, "更新成功");
        }catch (Exception e){
            log.error("更新失败" + e.getMessage());
            return CommonResult.failed("更新失败");
        }
    }
}
