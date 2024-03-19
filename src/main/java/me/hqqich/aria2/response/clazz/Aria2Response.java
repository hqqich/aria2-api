package me.hqqich.aria2.response.clazz;

import lombok.Getter;

/**
 * 请求参数
 *
 * @author : hqqich
 */
@Getter
public class Aria2Response<T> {

    String id;
    String jsonrpc;
    T result;
}
