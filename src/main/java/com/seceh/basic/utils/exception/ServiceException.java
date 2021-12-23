package com.seceh.basic.utils.exception;

public class ServiceException extends RuntimeException {

    protected final String type;

    protected final String code;

    public ServiceException(String type, String code, String template, String... messages) {
        super(String.format(template, messages));
        this.type = type;
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }
}
