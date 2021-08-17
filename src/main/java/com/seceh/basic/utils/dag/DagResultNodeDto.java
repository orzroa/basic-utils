package com.seceh.basic.utils.dag;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import static com.seceh.basic.utils.dag.DagDirectionEnum.DOWN;
import static com.seceh.basic.utils.dag.DagDirectionEnum.UP;


public class DagResultNodeDto implements Serializable {

    private static final long serialVersionUID = 2975795282283596488L;

    protected String id;

    protected String name;

    protected transient Map<DagDirectionEnum, Set<DagResultLinkDto>> streamSet = Maps.newHashMap();

    protected transient int streamUsage = 0;

    public DagResultNodeDto(String id, String name) {
        this.id = id;
        this.name = name;
        this.streamSet.put(UP, Sets.newHashSet());
        this.streamSet.put(DOWN, Sets.newHashSet());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addStream(DagDirectionEnum dagDirectionEnum, DagResultLinkDto dagResultLinkDto) {
        this.streamSet.get(dagDirectionEnum).add(dagResultLinkDto);
    }

    public boolean exhausted(DagDirectionEnum dagDirectionEnum) {
        streamUsage++;
        return this.streamSet.get(dagDirectionEnum.reverse()).size() == this.streamUsage;
    }

    public Set<DagResultLinkDto> getStream(DagDirectionEnum dagDirectionEnum) {
        return this.streamSet.get(dagDirectionEnum);
    }
}
