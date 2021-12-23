package com.seceh.basic.utils.vo;

import com.google.common.collect.Maps;
import com.seceh.basic.utils.exception.ServiceException;

import java.io.Serializable;

public class BaseResponse<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = -1725931467565416953L;

    private String code = "0000";
    private String message = "SUCCESS";
    private T data;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    private void setData(T data) {
        if (data == null) {
            this.data = (T) Maps.newHashMap();
        } else {
            this.data = data;
        }
    }

    public T getData() {
        return data;
    }

    public static <T extends Serializable> BaseResponse<T> buildSuccessResponse(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setData(data);
        if (data instanceof String) {
            response.setMessage((String) data);
        }
        return response;
    }

    public static <T extends Serializable> BaseResponse<T> buildFailureResponse(String code, String message, T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(code);
        response.setMessage(message);
        if (null != data) {
            response.setData(data);
        }
        return response;
    }

    public static <T extends Serializable> BaseResponse<T> buildFailureResponse(String code, String message) {
        return buildFailureResponse(code, message, null);
    }

    public static <T extends Serializable> BaseResponse<T> buildFailureResponse(int code, String message) {
        return buildFailureResponse(Integer.toString(code), message);
    }

    public static <T extends Serializable> BaseResponse<T> buildFailureResponse(Throwable throwable) {
        if (throwable instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) throwable;
            return buildFailureResponse(serviceException.getCode(), serviceException.getMessage(), null);
        } else {
            return buildFailureResponse(throwable.getClass().getName(), throwable.getMessage(), null);
        }
    }
}