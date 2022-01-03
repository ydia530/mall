package com.yuan.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.yuan.mall.common.CommonResult;
import com.yuan.mall.entity.ums.UmsMenu;
import com.yuan.mall.mapper.UmsMenuMapper;
import com.yuan.mall.service.UmsMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yuan Diao
 * @date 2022/1/3
 */
@Service
public class UmsMenuServiceImpl implements UmsMenuService {

    @Autowired
    private UmsMenuMapper umsMenuMapper;
    @Override
    public CommonResult create(UmsMenu umsMenu) {
        return null;
    }

    @Override
    public CommonResult update(Integer id, UmsMenu umsMenu) {
        return null;
    }

    @Override
    public UmsMenu getMenu(Integer id) {
        return null;
    }

    @Override
    public CommonResult delete(Integer id) {
        return null;
    }

    @Override
    public List<UmsMenu> list(Integer parentId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        return umsMenuMapper.selectList(queryWrapper);
    }

    @Override
    public CommonResult updateHidden(Integer id, Integer hidden) {
        return null;
    }

    @Override
    public List<UmsMenu> menuList() {
        List<UmsMenu> allMenus = umsMenuMapper.selectList(null);
        List<UmsMenu> tree = allMenus.stream()
                        .filter(umsMenu -> umsMenu.getLevel().equals(0))
                        .map(umsMenu -> getSubMenu(umsMenu, allMenus))
                    .collect(Collectors.toList());
        return tree;
    }

    private UmsMenu getSubMenu(UmsMenu umsMenu, List<UmsMenu> menuList) {
        List<UmsMenu> subMenus = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(umsMenu.getId()))
                .map(subMenu -> getSubMenu(subMenu, menuList))
                .collect(Collectors.toList());
        umsMenu.setChildren(subMenus);
        return umsMenu;
    }

}
