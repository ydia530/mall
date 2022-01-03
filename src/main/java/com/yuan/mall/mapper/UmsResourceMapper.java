package com.yuan.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuan.mall.entity.pms.PmsBrand;
import com.yuan.mall.entity.ums.UmsResource;
import com.yuan.mall.entity.ums.UmsResourceCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Yuan Diao
 * @date 2021/12/29
 */

@Mapper
public interface UmsResourceMapper extends BaseMapper<UmsResource> {
    List<UmsResource> listAll();

    List<UmsResourceCategory> getResourceCategory();


    List<UmsResource> selectByKeywords(@Param("categoryId") Integer categoryId,
                                       @Param("name") String nameKeyword,
                                       @Param("url") String urlKeyword);
}
