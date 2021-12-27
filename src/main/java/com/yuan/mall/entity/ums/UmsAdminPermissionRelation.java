package com.yuan.mall.entity.ums;

import lombok.Data;

import java.io.Serializable;

/**
 * @author diaoyuan
 */
@Data
public class UmsAdminPermissionRelation implements Serializable {
    private Long id;

    private Long adminId;

    private Long permissionId;

    private Integer type;

    private static final long serialVersionUID = 1L;
}
