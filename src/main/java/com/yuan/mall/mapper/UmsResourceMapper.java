package com.yuan.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuan.mall.entity.pms.PmsBrand;
import com.yuan.mall.entity.ums.UmsResource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yuan Diao
 * @date 2021/12/29
 */

@Mapper
public interface UmsResourceMapper extends BaseMapper<UmsResource> {
    List<UmsResource> listAll();
}
