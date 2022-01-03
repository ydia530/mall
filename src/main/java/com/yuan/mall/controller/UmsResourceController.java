package com.yuan.mall.controller;


import com.github.pagehelper.PageHelper;
import com.yuan.mall.common.CommonResult;
import com.yuan.mall.common.utils.CommonPage;
import com.yuan.mall.component.DynamicSecurityMetadataSource;
import com.yuan.mall.entity.ums.UmsResource;
import com.yuan.mall.entity.ums.UmsResourceCategory;
import com.yuan.mall.service.UmsResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 后台资源管理Controller
 * @author diaoyuan
 */
@CrossOrigin
@RestController
@Api(tags = "UmsResourceController", description = "后台资源管理")
@RequestMapping("/api/resource")
public class UmsResourceController {

    @Autowired
    private UmsResourceService umsResourceService;

    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @ApiOperation("添加后台资源")
    @PostMapping("/")
    public CommonResult create(@RequestBody UmsResource umsResource) {
        CommonResult commonResult = umsResourceService.create(umsResource);
        dynamicSecurityMetadataSource.clearDataSource();
        return commonResult;
    }

    @ApiOperation("修改后台资源")
    @PutMapping(value = "/{id}")
    public CommonResult update(@PathVariable Integer id,
                               @RequestBody UmsResource umsResource) {
        CommonResult commonResult = umsResourceService.update(id, umsResource);
        dynamicSecurityMetadataSource.clearDataSource();
        return commonResult;
    }

    @ApiOperation("根据ID获取资源详情")
    @GetMapping(value = "/{id}")
    public CommonResult<UmsResource> getResource(@PathVariable Integer id) {
        UmsResource umsResource = umsResourceService.getResource(id);
        return CommonResult.success(umsResource);
    }

    @ApiOperation("根据ID删除后台资源")
    @DeleteMapping(value = "/{id}")
    public CommonResult delete(@PathVariable Integer id) {
        CommonResult commonResult =  umsResourceService.delete(id);
        dynamicSecurityMetadataSource.clearDataSource();
        return commonResult;
    }

    @ApiOperation("分页模糊查询后台资源")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<UmsResource>> list(@RequestParam(required = false) Integer categoryId,
                                                @RequestParam(required = false) String nameKeyword,
                                                @RequestParam(required = false) String urlKeyword,
                                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<UmsResource> resourceList = umsResourceService.list(categoryId,nameKeyword, urlKeyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(resourceList));
    }

    @ApiOperation("查询所有后台资源")
    @GetMapping(value = "/listAll")
    public CommonResult<List<UmsResource>> listAll() {
        List<UmsResource> resourceList = umsResourceService.listAll();
        return CommonResult.success(resourceList);
    }

    @ApiOperation("查询所有后台资源分类")
    @GetMapping(value = "/category")
    public CommonResult<List<UmsResource>> getCategory() {
        return umsResourceService.getCategory();
    }

}