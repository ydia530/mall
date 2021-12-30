package com.yuan.mall.entity.ums;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author diaoyuan
 */
@Data
public class UmsRoleResourceRelation implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer Id;

    private Integer roleId;

    private Integer relationId;
}
