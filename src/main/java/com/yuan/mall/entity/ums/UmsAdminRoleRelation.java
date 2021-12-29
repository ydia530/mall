package com.yuan.mall.entity.ums;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author diaoyuan
 */
@Data
public class UmsAdminRoleRelation implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer adminId;

    private Integer roleId;

    private static final long serialVersionUID = 1L;

}
