package com.seceh.basic.utils.dag.exception;

import com.seceh.basic.utils.exception.ServiceException;

public class DagServiceException extends ServiceException {

    public DagServiceException(DagErrorCodeEnum dagErrorCodeEnum, String... messages) {
        super("DAG", dagErrorCodeEnum.getCode(), String.format(dagErrorCodeEnum.getTemplate(), messages));
    }
}
