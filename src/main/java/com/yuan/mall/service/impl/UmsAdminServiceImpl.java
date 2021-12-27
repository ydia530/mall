package com.yuan.mall.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuan.mall.common.CommonResult;
import com.yuan.mall.common.utils.JwtTokenUtil;
import com.yuan.mall.entity.ums.UmsAdmin;
import com.yuan.mall.entity.ums.UmsPermission;
import com.yuan.mall.mapper.UmsAdminMapper;
import com.yuan.mall.service.UmsAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 后台管理员Service实现类
 * @author diaoyuan
 */
@Service
@Slf4j
public class UmsAdminServiceImpl implements UmsAdminService {

    @Autowired
    private UmsAdminMapper umsAdminMapper;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Lazy
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        queryWrapper.eq("status", 1);
        return umsAdminMapper.selectOne(queryWrapper);
    }

    @Override
    public CommonResult register(UmsAdmin umsAdminParam) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", umsAdminParam.getUsername());
        queryWrapper.eq("status", 1);
        UmsAdmin umsAdmin = umsAdminMapper.selectOne(queryWrapper);
        if (umsAdmin != null){
            return CommonResult.failed("用户名重复");
        }
        umsAdminParam.setCreateTime(new Date());
        umsAdminParam.setPassword(passwordEncoder.encode(umsAdminParam.getPassword()));
        umsAdminMapper.insert(umsAdminParam);
        return CommonResult.success("注册成功");
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public List<UmsPermission> getPermissionList(Integer adminId) {
        List<UmsPermission> permissions = umsAdminMapper.getPermissionList(adminId);
        return permissions;
    }
}
