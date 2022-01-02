package com.yuan.mall.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Yuan Diao
 * @date 2022/1/2
 */
@Data
public class AllocRoleDto {

    private Integer adminId;

    private List<Integer> roleIds;
}
