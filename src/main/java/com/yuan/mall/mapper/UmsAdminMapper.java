package com.yuan.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuan.mall.entity.ums.UmsAdmin;
import com.yuan.mall.entity.ums.UmsAdminLoginLog;
import com.yuan.mall.entity.ums.UmsRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author diaoyuan
 */
@Mapper
public interface UmsAdminMapper extends BaseMapper<UmsAdmin> {

    void addLoginLog(UmsAdminLoginLog loginLog);

    List<UmsRole> selectRolesById(@Param("adminId") Integer adminId);

    void allocRoles(@Param("adminId")Integer adminId, @Param("roleIds")List<Integer> roleIds);

}
