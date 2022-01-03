package com.yuan.mall.service;

import com.yuan.mall.common.CommonResult;
import com.yuan.mall.entity.ums.UmsResource;
import com.yuan.mall.mapper.UmsResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Yuan Diao
 * @date 2021/12/28
 */

public interface UmsResourceService {
    CommonResult create(UmsResource umsResource);

    CommonResult update(Integer id, UmsResource umsResource);

    CommonResult delete(Integer id);

    List<UmsResource> list(Integer categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);

    List<UmsResource> listAll();

    List<UmsResource> getListByIds(List<Integer> roleId);

    CommonResult getCategory();

    UmsResource getResource(Integer id);
}