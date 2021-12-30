package com.yuan.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuan.mall.entity.ums.UmsResource;
import com.yuan.mall.entity.ums.UmsRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author diaoyuan
 */
@Mapper
public interface UmsRoleMapper extends BaseMapper<UmsRole> {
    List<UmsResource> getResourcesById(@Param("roleId") Integer roleId);
}
