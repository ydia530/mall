package com.yuan.mall.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yuan.mall.common.utils.RedisUtil;
import com.yuan.mall.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * 会员管理实现类
 * @author Yuan Diao
 * @date 2021/12/26
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    @Autowired
    private RedisUtil redisUtil;

    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;

    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;



    @Override
    public String generateAuthCode(String telephone) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        //生成code
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(random.nextInt(10));
        }

        //储存至redis 并设置过期时间
        String key = REDIS_KEY_PREFIX_AUTH_CODE + telephone;
        redisUtil.set(key, stringBuilder.toString());
        redisUtil.expire(key, AUTH_CODE_EXPIRE_SECONDS);

        return stringBuilder.toString();
    }

    @Override
    public Boolean verify(String telephone, String code) {
        String correctCode = (String) redisUtil.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        if (StringUtils.isNotEmpty(correctCode) && correctCode.equals(code)){
            return true;
        } else {
            return false;
        }
    }
}
