package com.seceh.basic.utils.dag;

import com.google.common.collect.Sets;

import java.io.Serializable;
import java.util.Set;

public class DagResultDto implements Serializable {

    private static final long serialVersionUID = -604667194249161470L;

    private Set<DagResultNodeDto> nodes = Sets.newHashSet();
    private Set<DagResultLinkDto> links = Sets.newHashSet();

    public Set<DagResultNodeDto> getNodes() {
        return nodes;
    }

    public void setNodes(Set<DagResultNodeDto> nodes) {
        this.nodes = nodes;
    }

    public Set<DagResultLinkDto> getLinks() {
        return links;
    }

    public void setLinks(Set<DagResultLinkDto> links) {
        this.links = links;
    }
}
