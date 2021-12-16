package com.seceh.basic.utils.dag;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DagLink implements Serializable {

    private static final long serialVersionUID = -6939613985367364636L;

    private String source;

    private String target;

    private Map<String, Serializable> properties = new HashMap<>();

    public DagLink() {
    }

    public DagLink(String source, String target) {
        this.source = source;
        this.target = target;
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

    public Map<String, Serializable> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Serializable> properties) {
        this.properties = properties;
    }
}
