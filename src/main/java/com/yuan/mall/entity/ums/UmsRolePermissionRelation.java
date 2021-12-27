package com.yuan.mall.entity.ums;

import lombok.Data;

import java.io.Serializable;

/**
 * @author diaoyuan
 */
@Data
public class UmsRolePermissionRelation implements Serializable {
    private Long id;

    private Long roleId;

    private Long permissionId;

    private static final long serialVersionUID = 1L;
}