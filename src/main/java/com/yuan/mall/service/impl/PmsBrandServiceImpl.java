package com.yuan.mall.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.mall.mapper.PmsBrandMapper;
import com.yuan.mall.model.PmsBrand;
import com.yuan.mall.service.PmsBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yuan Diao
 * @date 2021/12/25
 */
@Service
public class PmsBrandServiceImpl implements PmsBrandService {

    @Autowired
    private PmsBrandMapper pmsBrandMapper;

    @Override
    public List<PmsBrand> listAllBrand() {
        return pmsBrandMapper.listAll();
    }

    @Override
    public void createNewBrand(PmsBrand pmsBrand) {
        pmsBrandMapper.insert(pmsBrand);
    }

    @Override
    public PmsBrand getById(Integer id) {
        return pmsBrandMapper.selectById(id);
    }

    @Override
    public void deleteById(Integer id) {
        pmsBrandMapper.deleteById(id);
    }

    @Override
    public void updateById(PmsBrand brand) {
        pmsBrandMapper.updateById(brand);
    }

    @Override
    public List<PmsBrand> listBrand(Integer pageNum, Integer pageSize) {
        Page<PmsBrand> page = new Page<>(pageNum, pageSize);
        pmsBrandMapper.selectPage(page, null);
        return page.getRecords();
    }
}
