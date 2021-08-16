package com.seceh.basic.utils.dag;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.seceh.basic.utils.exception.NamedException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.seceh.basic.utils.dag.DagDirectionEnum.UP;
import static com.seceh.basic.utils.exception.ErrorCodeEnum.DAG_LOOP;

public class DagSearchUtils {

    private DagSearchUtils() {
    }

    public static DagResultDto search(Map<String, ? extends DagResultNodeDto> fullMap, String entryId, DagDirectionEnum dir) {
        scanAndCrop(fullMap, entryId, dir);

        //初始化，只扫描入口数量为0的节点
        List<String> idList = fullMap.values().stream().filter(p -> p.getStream(dir.reverse()).isEmpty())
                .map(DagResultNodeDto::getUuid).collect(Collectors.toList());
        int pos = 0;
        DagResultDto dagResultDto = new DagResultDto();
        while (pos < idList.size()) {
            String curUuid = idList.get(pos);
            dagResultDto.getNodes().add(fullMap.get(curUuid));
            Set<DagResultLinkDto> linkSet = fullMap.get(curUuid).getStream(dir);
            dagResultDto.getLinks().addAll(linkSet);
            for (DagResultLinkDto link : linkSet) {
                String newUuid = getNewUuid(dir, link);
                //新出现的入口数量为0的节点，放到待处理队列中
                if (fullMap.get(newUuid).exhausted(dir)) {
                    idList.add(newUuid);
                }
            }
            pos++;
        }

        //如果还有节点没有处理，输出异常信息
        if (idList.size() < fullMap.size()) {
            Set<String> idSet = fullMap.keySet();
            idList.forEach(idSet::remove);
            throw new NamedException(DAG_LOOP, String.join(",", idSet));
        }

        return dagResultDto;
    }

    /**
     * 快速遍历整个图，把不可及的node和link都剪掉
     *
     * @param nodeMap 节点地图
     * @param entryId 入口点
     * @param dir     检索方向
     */
    private static void scanAndCrop(Map<String, ? extends DagResultNodeDto> nodeMap, String entryId, DagDirectionEnum dir) {
        int pos = 0;
        List<String> idList = Lists.newArrayList(entryId);
        Set<String> idSet = Sets.newHashSet(entryId);
        while (pos < idList.size()) {
            String curUuid = idList.get(pos);
            Set<DagResultLinkDto> linkSet = nodeMap.get(curUuid).getStream(dir);
            for (DagResultLinkDto link : linkSet) {
                String newUuid = getNewUuid(dir, link);
                if (idSet.add(newUuid)) {
                    idList.add(newUuid);
                }
            }
            pos++;
        }

        Iterator<? extends Map.Entry<String, ? extends DagResultNodeDto>> iter = nodeMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, ? extends DagResultNodeDto> entry = iter.next();
            if (!idSet.contains(entry.getKey())) {
                iter.remove();
            }
            for (DagDirectionEnum eachDir : DagDirectionEnum.values()) {
                entry.getValue().getStream(eachDir).removeIf(link -> !idSet.contains(link.getFrom()) || !idSet.contains(link.getTo()));
            }
        }
    }

    /**
     * 根据方向，获取下一个节点
     *
     * @param dir              检索方向
     * @param dagResultLinkDto 连接
     * @return 另一端的节点uuid
     */
    private static String getNewUuid(DagDirectionEnum dir, DagResultLinkDto dagResultLinkDto) {
        String newUuid;
        if (dir == UP) {
            newUuid = dagResultLinkDto.getFrom();
        } else {
            newUuid = dagResultLinkDto.getTo();
        }
        return newUuid;
    }
}
