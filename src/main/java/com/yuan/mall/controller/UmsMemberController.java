package com.yuan.mall.controller;

import com.yuan.mall.common.CommonResult;
import com.yuan.mall.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yuan Diao
 * @date 2021/12/26
 */
@Api(tags = "UmsMemberController", description = "会员登录注册管理")
@RestController
@RequestMapping("/api/sso")
public class UmsMemberController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsMemberController.class);

    @Autowired
    private UmsMemberService umsMemberService;

    @ApiOperation("获取验证码")
    @GetMapping("/getAuthCode")
    @ResponseBody
    public CommonResult getAuthCode(@RequestParam String telephone){
        try{
            String code = umsMemberService.generateAuthCode(telephone);
            return CommonResult.success(code,"获取验证码成功");
        }catch (Exception e){
            LOGGER.error("获取验证码出错：" + e.getMessage());
            return CommonResult.failed("获取验证码失败");
        }
    }

    @ApiOperation("判断验证码是否正确")
    @PostMapping("/verify")
    @ResponseBody
    public CommonResult verifyAuthCode(@RequestParam String telephone, @RequestParam String code){
        try{
            Boolean isCorrect = umsMemberService.verify(telephone, code);
            if (isCorrect){
                return CommonResult.success("验证成功");
            } else {
                return CommonResult.failed("验证码错误");
            }
        }catch (Exception e){
            LOGGER.error("验证出错：" + e.getMessage());
            return CommonResult.failed("验证失败，请稍后重试");
        }
    }

}
