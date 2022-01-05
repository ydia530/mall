package com.yuan.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.yuan.mall.common.CommonResult;
import com.yuan.mall.entity.ums.UmsMenu;
import com.yuan.mall.mapper.UmsMenuMapper;
import com.yuan.mall.service.UmsMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yuan Diao
 * @date 2022/1/3
 */
@Service
@Slf4j
public class UmsMenuServiceImpl implements UmsMenuService {

    @Autowired
    private UmsMenuMapper umsMenuMapper;
    @Override
    public CommonResult create(UmsMenu umsMenu) {
        try{
            umsMenuMapper.insert(umsMenu);
            return CommonResult.success(200,"添加成功");
        }catch (Exception e){
            log.error("添加出错" + e.getMessage());
            return CommonResult.failed("添加失败");
        }
    }

    @Override
    public CommonResult update(Integer id, UmsMenu umsMenu) {
        try{
            umsMenuMapper.updateById(umsMenu);
            return CommonResult.success(200,"更新成功");
        }catch (Exception e){
            log.error("更新出错" + e.getMessage());
            return CommonResult.failed("更新失败");
        }
    }

    @Override
    public UmsMenu getMenu(Integer id) {
        return umsMenuMapper.selectById(id);
    }

    @Override
    public CommonResult delete(Integer id) {
        try {
            umsMenuMapper.deleteById(id);
            return CommonResult.success(200,"删除成功");
        }catch (Exception e){
            log.error("删除失败"+e.getMessage());
            return CommonResult.failed("删除失败");
        }
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
        UpdateWrapper<UmsMenu> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", id);
        UmsMenu umsMenu = new UmsMenu();
        umsMenu.setHidden(hidden);
        try{
            umsMenuMapper.update(umsMenu, updateWrapper);
            return CommonResult.success(200,"更新成功");
        }catch (Exception e){
            log.error("更新失败" + e.getMessage());
            return CommonResult.failed("更新失败");
        }
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
