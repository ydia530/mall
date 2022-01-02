package com.yuan.mall.controller;

import com.yuan.mall.common.CommonResult;
import com.yuan.mall.common.utils.CommonPage;
import com.yuan.mall.entity.ums.UmsMenu;
import com.yuan.mall.entity.ums.UmsResource;
import com.yuan.mall.entity.ums.UmsRole;
import com.yuan.mall.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台用户角色管理Controller
 * @author diaoyuan
 */
@RestController
@CrossOrigin
@Api(tags = "UmsRoleController", description = "后台用户角色管理")
@RequestMapping("/api/role")
public class UmsRoleController {
    @Autowired
    private UmsRoleService umsRoleService;

    @ApiOperation("添加角色")
    @PostMapping(value = "/")
    @ResponseBody
    public CommonResult create(@RequestBody UmsRole role) {
        int count = umsRoleService.create(role);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改角色")
    @PutMapping(value = "/{id}")
    @ResponseBody
    public CommonResult update(@PathVariable Integer id, @RequestBody UmsRole role) {
        int count = umsRoleService.update(id, role);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("批量删除角色")
    @DeleteMapping(value = "/")
    @ResponseBody
    public CommonResult delete(@RequestParam("ids") List<Integer> ids) {
        int count = umsRoleService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取所有角色")
    @GetMapping(value = "/listAll")
    @ResponseBody
    public CommonResult<List<UmsRole>> listAll() {
        List<UmsRole> roleList = umsRoleService.listAll();
        return CommonResult.success(roleList);
    }

    @ApiOperation("根据角色名称分页获取角色列表")
    @GetMapping(value = "/list")
    @ResponseBody
    public CommonResult<CommonPage<UmsRole>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<UmsRole> roleList = umsRoleService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(roleList));
    }

    @ApiOperation("修改角色状态")
    @PostMapping(value = "/updateStatus/{id}")
    @ResponseBody
    public CommonResult updateStatus(@PathVariable Integer id, @RequestParam(value = "status") Integer status) {
        UmsRole umsRole = new UmsRole();
        umsRole.setStatus(status);
        int count = umsRoleService.update(id, umsRole);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取角色相关菜单")
    @GetMapping(value = "/listMenu/{roleId}")
    @ResponseBody
    public CommonResult<List<UmsMenu>> listMenu(@PathVariable Integer roleId) {
        List<UmsMenu> roleList = umsRoleService.listMenu(roleId);
        return CommonResult.success(roleList);
    }

    @ApiOperation("获取角色相关资源")
    @GetMapping(value = "/listResource/{roleId}")
    @ResponseBody
    public CommonResult<List<UmsResource>> listResource(@PathVariable Integer roleId) {
        List<UmsResource> roleList = umsRoleService.listResource(roleId);
        return CommonResult.success(roleList);
    }

    @ApiOperation("给角色分配菜单")
    @PostMapping(value = "/allocMenu")
    @ResponseBody
    public CommonResult allocMenu(@RequestParam Integer roleId, @RequestParam List<Integer> menuIds) {
        int count = umsRoleService.allocMenu(roleId, menuIds);
        return CommonResult.success(count);
    }

    @ApiOperation("给角色分配资源")
    @PostMapping(value = "/allocResource")
    @ResponseBody
    public CommonResult allocResource(@RequestParam Integer roleId, @RequestParam List<Integer> resourceIds) {
        int count = umsRoleService.allocResource(roleId, resourceIds);
        return CommonResult.success(count);
    }

}