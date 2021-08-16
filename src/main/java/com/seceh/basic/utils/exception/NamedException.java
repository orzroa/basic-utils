package com.seceh.basic.utils.exception;

public class NamedException extends RuntimeException {

    private final int code;

    public NamedException(ErrorCodeEnum errorCodeEnum, String... messages) {
        super(String.format(errorCodeEnum.getMessage(), messages));
        this.code = errorCodeEnum.getCode();
    }

    public int getCode() {
        return code;
    }
}
