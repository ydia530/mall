package com.yuan.mall.service.impl;

import com.yuan.mall.entity.ums.UmsResource;
import com.yuan.mall.service.UmsResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yuan Diao
 * @date 2021/12/28
 */
@Service
public class UmsResourceServiceImpl implements UmsResourceService {
    @Override
    public int create(UmsResource umsResource) {
        return 0;
    }

    @Override
    public int update(Long id, UmsResource umsResource) {
        return 0;
    }

    @Override
    public UmsResource getItem(Long id) {
        return null;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public List<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
        return null;
    }

    @Override
    public List<UmsResource> listAll() {
        return null;
    }
}
