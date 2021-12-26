package com.yuan.mall.common;

/**
 * 封装API的错误码
 * @author Yuan
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}