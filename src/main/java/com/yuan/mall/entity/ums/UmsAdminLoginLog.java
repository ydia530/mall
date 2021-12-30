package com.yuan.mall.entity.ums;

import cn.hutool.db.meta.Table;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Yuan Diao
 * @date 2021/12/30
 */
@Data
public class UmsAdminLoginLog implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer adminId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String ip;

    private String address;

    private String userAgent;
}
