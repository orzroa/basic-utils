package com.seceh.basic.utils.dag;

import com.google.common.collect.Sets;

import java.io.Serializable;
import java.util.Set;

public class DagSearchResult implements Serializable {

    private static final long serialVersionUID = -604667194249161470L;

    private Set<DagNode> nodes = Sets.newHashSet();
    private Set<DagLink> links = Sets.newHashSet();

    public Set<DagNode> getNodes() {
        return nodes;
    }

    public void setNodes(Set<DagNode> nodes) {
        this.nodes = nodes;
    }

    public Set<DagLink> getLinks() {
        return links;
    }

    public void setLinks(Set<DagLink> links) {
        this.links = links;
    }
}
