package com.yuan.mall.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuan.mall.common.CommonResult;
import com.yuan.mall.common.utils.JwtTokenUtil;
import com.yuan.mall.common.utils.RequestUtil;
import com.yuan.mall.entity.ums.UmsAdmin;
import com.yuan.mall.entity.ums.UmsAdminLoginLog;
import com.yuan.mall.entity.ums.UmsResource;
import com.yuan.mall.entity.ums.UmsRole;
import com.yuan.mall.mapper.UmsAdminMapper;
import com.yuan.mall.pojo.dto.AdminUserDetails;
import com.yuan.mall.pojo.dto.UmsAdminParam;
import com.yuan.mall.pojo.dto.UpdateAdminPasswordParam;
import com.yuan.mall.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UmsAdminCacheService umsAdminCacheService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UmsRoleService umsRoleService;

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdmin admin = umsAdminCacheService.getAdmin(username);
        if (admin != null) {
            log.info("缓存命中：" +username);
            return admin;
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        queryWrapper.eq("status", 1);

        admin = umsAdminMapper.selectOne(queryWrapper);
        if (admin != null){
            log.info("redis新增admin：" + username);
            umsAdminCacheService.setAdmin(admin);
        }
        return admin;
    }

    @Override
    public CommonResult register(UmsAdminParam umsAdminParam) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", umsAdminParam.getUsername());
        queryWrapper.eq("status", 1);
        UmsAdmin umsAdmin = umsAdminMapper.selectOne(queryWrapper);
        if (umsAdmin != null){
            return CommonResult.failed("用户名重复");
        }
        UmsAdmin newAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, newAdmin);
        newAdmin.setCreateTime(new Date());
        newAdmin.setPassword(passwordEncoder.encode(umsAdminParam.getPassword()));
        umsAdminMapper.insert(newAdmin);
        return CommonResult.success("注册成功");
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            addLoginLog(username);
        } catch (Exception e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    private void addLoginLog(String username) throws ParseException {
        UmsAdmin admin = getAdminByUsername(username);
        if(admin==null) {
            return;
        }
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(admin.getId());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(RequestUtil.getRequestIp(request));
        loginLog.setAddress("1");
        loginLog.setUserAgent("1");
        umsAdminMapper.addLoginLog(loginLog);
    }

    @Override
    public String refreshToken(String oldToken) {
        return null;
    }

    @Override
    public UmsAdmin getItem(Integer id) {
        return null;
    }

    @Override
    public List<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum) {
        return null;
    }

    @Override
    public int update(Integer id, UmsAdmin admin) {
        return 0;
    }

    @Override
    public int delete(Integer id) {
        return 0;
    }

    @Override
    public int updateRole(Integer adminId, List<Long> roleIds) {
        return 0;
    }

    @Override
    public List<UmsRole> getRoleList(Integer adminId) {
        return umsAdminMapper.selectRolesById(adminId);
    }

    @Override
    public int updatePassword(UpdateAdminPasswordParam updatePasswordParam) {
        return 0;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        //获取用户信息
        UmsAdmin admin = getAdminByUsername(username);
        if (admin != null) {
            List<UmsResource> resourceList = umsRoleService.loadAdminResource(admin.getId());
            return new AdminUserDetails(admin,resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

}
