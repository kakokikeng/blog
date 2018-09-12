package com.yk.blog.core.dto;

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
