package com.yuan.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.yuan.mall.common.CommonResult;
import com.yuan.mall.entity.ums.UmsResource;
import com.yuan.mall.entity.ums.UmsResourceCategory;
import com.yuan.mall.mapper.UmsResourceMapper;
import com.yuan.mall.service.UmsResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yuan Diao
 * @date 2021/12/28
 */
@Service
@Slf4j
public class UmsResourceServiceImpl implements UmsResourceService {

    @Autowired
    private UmsResourceMapper umsResourceMapper;

    @Override
    public CommonResult create(UmsResource umsResource) {
        try{
            umsResourceMapper.insert(umsResource);
            return CommonResult.success(200,"添加成功");
        }catch (Exception e){
            log.error("添加出错" + e.getMessage());
            return CommonResult.failed("添加失败");
        }
    }

    @Override
    public CommonResult update(Integer id, UmsResource umsResource) {
        try{
            umsResourceMapper.updateById(umsResource);
            return CommonResult.success(200,"更新成功");
        }catch (Exception e){
            log.error("更新出错" + e.getMessage());
            return CommonResult.failed("更新失败");
        }
    }

    @Override
    public CommonResult delete(Integer id) {
        try {
            umsResourceMapper.deleteById(id);
            return CommonResult.success(200,"删除成功");
        }catch (Exception e){
            log.error("删除失败"+e.getMessage());
            return CommonResult.failed("删除失败");
        }
    }

    @Override
    public List<UmsResource> list(Integer categoryId, String nameKeyword,
                                  String urlKeyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<UmsResource> resourceList = umsResourceMapper.selectByKeywords(categoryId, nameKeyword, urlKeyword);
        return resourceList;
    }

    @Override
    public List<UmsResource> listAll() {
        return umsResourceMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public List<UmsResource> getListByIds(List<Integer> ids){
        return umsResourceMapper.selectBatchIds(ids);
    }

    @Override
    public CommonResult getCategory() {
        return CommonResult.success(umsResourceMapper.getResourceCategory(), "查询成功");
    }

    @Override
    public UmsResource getResource(Integer id) {
        return null;
    }
}
