package com.yuan.mall.service;

import com.yuan.mall.model.PmsBrand;

import java.util.List;

/**
 * @author Yuan Diao
 * @date 2021/12/25
 */
public interface PmsBrandService {
    List<PmsBrand> listAllBrand();

    void createNewBrand(PmsBrand pmsBrand);

    PmsBrand getById(Integer id);

    void deleteById(Integer id);

    void updateById(PmsBrand brand);

    List<PmsBrand> listBrand(Integer pageNum, Integer pageSize);
}
