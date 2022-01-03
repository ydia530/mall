package com.yuan.mall.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
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
    public List<UmsAdmin> listAll(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum,pageSize);
        return umsAdminMapper.listAll(keyword);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult allocRoles(Integer adminId, List<Integer> roleIds) {
        if (roleIds.isEmpty()){
            return CommonResult.failed("角色不能为空");
        }
        try{
            //1.先删除已经有的role 2.再插入更新
            umsAdminMapper.allocRoles(adminId, roleIds);
            umsAdminCacheService.delResourceList(adminId);
        } catch (Exception e){
            log.error("更新失败" +e.getMessage());
            return CommonResult.failed("更新失败");
        }
        return CommonResult.success("更新成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult updateStatus(Integer adminId, Integer status) {
        try{
            UmsAdmin updateAdmin = new UmsAdmin();
            updateAdmin.setStatus(status);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("id", adminId);
            umsAdminMapper.update(updateAdmin, queryWrapper);

            //TODO 更新后需删除缓存
//            umsAdminCacheService.delAdmin();
        }catch (Exception e){
            log.error("更新状态失败"+ e.getMessage());
        }
        return CommonResult.success("更新成功");
    }

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
    public UmsAdmin getAdminById(Integer id) {
        return umsAdminMapper.selectById(id);
    }

    @Override
    public List<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(Integer id, UmsAdmin admin) {
        try {
            //检查是否有重复用户名
            UmsAdmin umsAdmin = getAdminByUsername(admin.getUsername());
            if (!id.equals(umsAdmin.getId())){
                return CommonResult.failed("用户名存在重复");
            }
            admin.setId(id);
            if (umsAdmin.getPassword().equals(passwordEncoder.encode(admin.getPassword()))){
                //与原密码相同
                admin.setPassword(umsAdmin.getPassword());
            } else{
                //加密新密码，并更新
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            }
            umsAdminMapper.updateById(admin);

            //清除缓存
            umsAdminCacheService.delAdmin(admin.getUsername());
            return CommonResult.success("更新成功");
        }catch (Exception e){
            log.error("更新失败："+ e.getMessage());
            return CommonResult.failed("更新失败");
        }
    }

    @Override
    public CommonResult delete(Integer id) {
        if (umsAdminMapper.selectById(id) == null){
            return CommonResult.failed("删除失败");
        }

        umsAdminMapper.deleteById(id);
        return CommonResult.success("删除成功");
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
