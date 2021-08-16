package com.seceh.basic.utils.exception;

public enum ErrorCodeEnum {

    DAG_LOOP(27001, "以下数据节点处于回环中：%s"),
    ;

    private int code;
    private String message;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return this.message;
    }
}
