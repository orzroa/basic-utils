package com.seceh.basic.utils.dag;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.seceh.basic.utils.dag.DagDirectionEnum.DOWN;
import static com.seceh.basic.utils.dag.DagDirectionEnum.UP;


public class DagNode implements Serializable {

    private static final long serialVersionUID = 2975795282283596488L;

    protected String id;

    protected Map<String, Serializable> properties = new HashMap<>();

    protected transient Map<DagDirectionEnum, Set<DagLink>> streamSet = Maps.newHashMap();

    protected transient int streamUsage = 0;

    public DagNode(String id) {
        this.id = id;
        this.streamSet.put(UP, Sets.newHashSet());
        this.streamSet.put(DOWN, Sets.newHashSet());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Serializable> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Serializable> properties) {
        this.properties = properties;
    }

    public void addStream(DagDirectionEnum dagDirectionEnum, DagLink dagLink) {
        this.streamSet.get(dagDirectionEnum).add(dagLink);
    }

    /**
     * @param dagDirectionEnum 方向
     * @return 是否所有支流都已经被检索
     */
    public boolean exhausted(DagDirectionEnum dagDirectionEnum) {
        streamUsage++;
        return this.streamSet.get(dagDirectionEnum.reverse()).size() == this.streamUsage;
    }

    public Set<DagLink> getStream(DagDirectionEnum dagDirectionEnum) {
        return this.streamSet.get(dagDirectionEnum);
    }
}
