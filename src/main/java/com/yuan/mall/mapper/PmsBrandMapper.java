package com.yuan.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuan.mall.model.PmsBrand;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * @author Yuan Diao
 * @date 2021/12/25
 */
@Mapper
public interface PmsBrandMapper extends BaseMapper<PmsBrand> {

    /**
     * 获取所有品牌信息
     * @return 品牌信息
     */
    List<PmsBrand> listAll();
}
