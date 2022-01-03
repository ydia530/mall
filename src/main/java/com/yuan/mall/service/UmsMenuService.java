package com.yuan.mall.service;

import com.yuan.mall.common.CommonResult;
import com.yuan.mall.entity.ums.UmsMenu;

import java.util.List;

/**
 * @author Yuan Diao
 * @date 2022/1/3
 */
public interface UmsMenuService {
    CommonResult create(UmsMenu umsMenu);

    CommonResult update(Integer id, UmsMenu umsMenu);

    UmsMenu getMenu(Integer id);

    CommonResult delete(Integer id);

    List<UmsMenu> list(Integer parentId, Integer pageSize, Integer pageNum);

    CommonResult updateHidden(Integer id, Integer hidden);

    List<UmsMenu> menuList();
}
