package com.yuan.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.yuan.mall.common.CommonResult;
import com.yuan.mall.entity.ums.UmsResource;
import com.yuan.mall.entity.ums.UmsResourceCategory;
import com.yuan.mall.mapper.UmsResourceMapper;
import com.yuan.mall.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yuan Diao
 * @date 2021/12/28
 */
@Service
public class UmsResourceServiceImpl implements UmsResourceService {

    @Autowired
    private UmsResourceMapper umsResourceMapper;

    @Override
    public CommonResult create(UmsResource umsResource) {
        return null;
    }

    @Override
    public CommonResult update(Integer id, UmsResource umsResource) {
        return null;
    }

    @Override
    public CommonResult delete(Integer id) {
        return null;
    }

    @Override
    public List<UmsResource> list(Integer categoryId, String nameKeyword,
                                  String urlKeyword, Integer pageSize, Integer pageNum) {
        System.out.println(pageNum);
        System.out.println(pageSize);
        PageHelper.startPage(pageNum,pageSize);
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
