package com.seceh.basic.utils.dag;

import java.io.Serializable;

public class DagResultLinkDto implements Serializable {

    private static final long serialVersionUID = -6939613985367364636L;

    private String from;

    private String to;

    private String linkName;

    private String linkStatus;

    public DagResultLinkDto() {
    }

    public DagResultLinkDto(String from, String to, String linkName, String linkStatus) {
        this.from = from;
        this.to = to;
        this.linkName = linkName;
        this.linkStatus = linkStatus;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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
