package com.yuan.mall.controller;

import com.yuan.mall.common.CommonResult;
import com.yuan.mall.entity.pms.PmsBrand;
import com.yuan.mall.service.PmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yuan Diao
 * @date 2021/12/25
 */

@Api(tags = "PmsBrandController", description = "商品品牌管理")
@RestController
@RequestMapping("/api/brand")
public class PmsBrandController {

    @Autowired
    private PmsBrandService pmsBrandService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandController.class);

    @ApiOperation("获取所有品牌列表")
    @GetMapping("/listAll")
    public CommonResult<List<PmsBrand>> getAllPmsBrand(){
        return CommonResult.success(pmsBrandService.listAllBrand());
    }

    @ApiOperation("添加品牌")
    @PostMapping("/")
    public CommonResult createBrand(@RequestBody PmsBrand pmsBrand){
        try{
            pmsBrandService.createNewBrand(pmsBrand);
        }catch (Exception e){
            LOGGER.error("创建品牌失败: " + e.getMessage());
            return CommonResult.failed("创建失败");
        }
        return CommonResult.success("创建成功");
    }

    @ApiOperation("获取指定id品牌信息")
    @GetMapping("{id}")
    public CommonResult getBrandById(@PathVariable("id") Integer id){
        try{
            PmsBrand pmsBrand = pmsBrandService.getById(id);
            return CommonResult.success(pmsBrand);
        }catch (Exception e){
            LOGGER.error("查询失败: " + e.getMessage());
            return CommonResult.failed();
        }
    }

    @ApiOperation("删除指定id的品牌")
    @DeleteMapping("{id}")
    public CommonResult deleteBrandById(@PathVariable("id") Integer id){
        try{
            pmsBrandService.deleteById(id);
            return CommonResult.success("删除成功");
        }catch (Exception e){
            LOGGER.error("删除失败: " + e.getMessage());
            return CommonResult.failed("删除失败");
        }
    }

    @ApiOperation("更新指定id品牌信息")
    @PutMapping()
    public CommonResult updateBrandById(@RequestBody PmsBrand pmsBrand){
        try{
            pmsBrandService.updateById(pmsBrand);
            return CommonResult.success("更新成功");
        }catch (Exception e){
            LOGGER.error("更新失败: " + e.getMessage());
            return CommonResult.failed("更新失败");
        }
    }

    @ApiOperation("分页查询")
    @GetMapping( "/list")
    public CommonResult<List<PmsBrand>> listBrand(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {
        List<PmsBrand> brandList = pmsBrandService.listBrand(pageNum, pageSize);
        return CommonResult.success(brandList);
    }

}
