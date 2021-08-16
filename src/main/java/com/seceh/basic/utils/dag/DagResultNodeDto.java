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

    protected String uuid;

    protected String name;

    protected transient Map<DagDirectionEnum, Set<DagResultLinkDto>> streamSet = Maps.newHashMap();

    protected transient int streamUsage = 0;

    public DagResultNodeDto(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.streamSet.put(UP, Sets.newHashSet());
        this.streamSet.put(DOWN, Sets.newHashSet());
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
