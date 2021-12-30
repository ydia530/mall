package com.yuan.mall.service;

import org.springframework.stereotype.Service;

/**
 * @author Yuan Diao
 * @date 2021/12/26
 */

public interface UmsMemberService {

    /**
     * 生成验证码
     * @param telephone 用户手机号
     * @return 验证码
     */
    String generateAuthCode(String telephone);

    Boolean verify(String telephone, String code);
}
