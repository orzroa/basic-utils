package com.seceh.basic.utils.dag;

import java.io.Serializable;

public class DagResultLinkDto implements Serializable {

    private static final long serialVersionUID = -6939613985367364636L;

    private String source;

    private String target;

    private String linkName;

    private String linkStatus;

    public DagResultLinkDto() {
    }

    public DagResultLinkDto(String source, String target, String linkName, String linkStatus) {
        this.source = source;
        this.target = target;
        this.linkName = linkName;
        this.linkStatus = linkStatus;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkStatus() {
        return linkStatus;
    }

    public void setLinkStatus(String linkStatus) {
        this.linkStatus = linkStatus;
    }
}
