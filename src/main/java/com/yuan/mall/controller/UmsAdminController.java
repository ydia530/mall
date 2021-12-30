package com.yuan.mall.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yuan.mall.common.CommonResult;
import com.yuan.mall.entity.ums.UmsAdmin;
import com.yuan.mall.entity.ums.UmsRole;
import com.yuan.mall.pojo.dto.AdminLoginDto;
import com.yuan.mall.pojo.dto.UmsAdminParam;
import com.yuan.mall.service.UmsAdminService;
import com.yuan.mall.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 后台用户管理
 * @author diaoyuan
 */
@RestController
@Slf4j
@Validated
@Api(tags = "UmsAdminController", description = "后台用户管理")
@RequestMapping("/api/admin")
public class UmsAdminController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UmsAdminService umsAdminService;

    @Autowired
    private UmsRoleService umsRoleService;


    @ApiOperation(value = "管理员注册")
    @PostMapping("/register")
    @ResponseBody
    public CommonResult register(@Validated @RequestBody UmsAdminParam umsAdminParam, BindingResult result) {
        if (result.hasErrors()) {
            return CommonResult.failed(result.getFieldError().getDefaultMessage());
        }
        try{
            return umsAdminService.register(umsAdminParam);
        }catch (Exception e){
            log.error("注册失败：" + e.getMessage());
            return CommonResult.failed("注册出错");
        }
    }

    @ApiOperation(value = "管理员登录")
    @PostMapping("/login")
    public CommonResult login(@Validated @RequestBody AdminLoginDto adminLoginDto, BindingResult result){
        if (result.hasErrors()) {
            return CommonResult.failed(result.getFieldError().getDefaultMessage());
        }
        String token = umsAdminService.login(adminLoginDto.getUsername(), adminLoginDto.getPassword());
        System.out.println(token);
        if (StringUtils.isNotEmpty(token)){
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", tokenHead);
            return CommonResult.success(tokenMap);
        }
        return CommonResult.unauthorized("用户名或密码错误");
    }


    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getAdminInfo(Principal principal) {
        if(principal==null){
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        UmsAdmin umsAdmin = umsAdminService.getAdminByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", umsAdmin.getUsername());
        data.put("menus", umsRoleService.getMenuList(umsAdmin.getId()));
        data.put("icon", umsAdmin.getIcon());
        List<UmsRole> roleList = umsAdminService.getRoleList(umsAdmin.getId());
        if(CollUtil.isNotEmpty(roleList)){
            List<String> roles = roleList.stream().map(UmsRole::getName).collect(Collectors.toList());
            data.put("roles",roles);
        }
        return CommonResult.success(data);
    }


}
