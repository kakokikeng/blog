package com.yk.blog.core.dto;

/**
 * 带返回数据的结果类
 *
 * @Author yikang
 * @Date 2018/9/13
 */
public class GenericResult<T> extends Result {
    private T data;

    public GenericResult() {
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
