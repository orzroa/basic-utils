package com.seceh.basic.utils.dag.exception;

public enum DagErrorCodeEnum {

    DAG_LOOP("0001", "以下数据节点处于回环中：%s"),
    ;

    private final String code;
    private final String template;

    DagErrorCodeEnum(String code, String template) {
        this.code = code;
        this.template = template;
    }

    public String getCode() {
        return code;
    }

    public String getTemplate() {
        return this.template;
    }
}
