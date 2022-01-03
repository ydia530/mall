package com.yuan.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuan.mall.entity.ums.UmsAdmin;
import com.yuan.mall.entity.ums.UmsMenu;
import com.yuan.mall.entity.ums.UmsResource;
import com.yuan.mall.entity.ums.UmsRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * @author diaoyuan
 */
@Mapper
public interface UmsRoleMapper extends BaseMapper<UmsRole> {
    List<UmsResource> getResourcesByRoleId(@Param("roleId") Integer roleId);

    List<UmsResource> getResourcesByAdminId(@Param("adminId") Integer adminId);

    List<UmsMenu> getMenuList(@Param("adminId") Integer adminId);

    List<UmsMenu> getMenuListByRole(@Param("roleId")Integer roleId);

    void allocMenu(@Param("roleId")Integer roleId, @Param("menuIds")List<Integer> menuIds);

    List<UmsAdmin> getAdminsByRole(@Param("roleId") int roleId);

    void allocResource(@Param("roleId") Integer roleId, @Param("resourceIds")List<Integer> resourceIds);
}
