package com.yuan.mall.controller;

/**
 * @author Yuan Diao
 * @date 2022/1/3
 * 后台菜单管理Controller
 */

import com.github.pagehelper.PageHelper;
import com.yuan.mall.common.CommonResult;
import com.yuan.mall.common.utils.CommonPage;
import com.yuan.mall.entity.ums.UmsMenu;
import com.yuan.mall.service.UmsMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@Api(tags = "UmsMenuController", description = "后台菜单管理")
@RequestMapping("/api/menu")
public class UmsMenuController {

    @Autowired
    private UmsMenuService menuService;

    @ApiOperation("添加后台菜单")
    @PostMapping(value = "/")
    public CommonResult create(@RequestBody UmsMenu umsMenu) {
        return menuService.create(umsMenu);
    }

    @ApiOperation("修改后台菜单")
    @PutMapping(value = "/{id}")
    @ResponseBody
    public CommonResult update(@PathVariable Integer id,
                               @RequestBody UmsMenu umsMenu) {
        return menuService.update(id, umsMenu);
    }

    @ApiOperation("根据ID获取菜单详情")
    @GetMapping(value = "/{id}")
    @ResponseBody
    public CommonResult<UmsMenu> getMenu(@PathVariable Integer id) {
        UmsMenu umsMenu = menuService.getMenu(id);
        return CommonResult.success(umsMenu);
    }

    @ApiOperation("根据ID删除后台菜单")
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public CommonResult delete(@PathVariable Integer id) {
        return menuService.delete(id);
    }

    @ApiOperation("分页查询后台菜单")
    @GetMapping(value = "/list/{parentId}")
    @ResponseBody
    public CommonResult<CommonPage<UmsMenu>> list(@PathVariable Integer parentId,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<UmsMenu> menuList = menuService.list(parentId, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(menuList));
    }

    @ApiOperation("树形结构返回所有菜单列表")
    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<UmsMenu>> menuList() {
        List<UmsMenu> list = menuService.menuList();
        return CommonResult.success(list);
    }

    @ApiOperation("修改菜单显示状态")
    @RequestMapping(value = "/updateHidden/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateHidden(@PathVariable Integer id, @RequestParam("hidden") Integer hidden) {
        return menuService.updateHidden(id, hidden);
    }
}
