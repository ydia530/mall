package com.yuan.mall.entity.pms;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Yuan Diao
 * @date 2021/12/25
 */

@Data
public class PmsBrand implements Serializable {


    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 首字母
     */
    private String firstLetter;

    private Integer sort;

    /**
     * 是否为品牌制造商：0->不是；1->是
     */
    private Integer factoryStatus;

    private Integer showStatus;

    /**
     * 产品数量
     */
    private Integer productCount;

    /**
     * 产品评论数量
     */
    private Integer productCommentCount;

    /**
     * 品牌logo
     */
    private String logo;

    /**
     * 专区大图
     */
    private String bigPic;

    /**
     * 品牌故事
     */
    private String brandStory;

    /**
     * @author diaoyuan
     */
    @Data
    public static class UmsAdminRoleRelation implements Serializable {
        private Long id;

        private Long adminId;

        private Long roleId;

        private static final long serialVersionUID = 1L;
    }
}
