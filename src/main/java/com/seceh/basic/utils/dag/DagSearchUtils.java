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

    public static DagSearchResult search(Map<String, ? extends DagNode> fullMap, String entryId, DagDirectionEnum dir) {
        scanAndCrop(fullMap, entryId, dir);

        //初始化，只扫描入口数量为0的节点
        List<String> idList = fullMap.values().stream().filter(p -> p.getStream(dir.reverse()).isEmpty())
                .map(DagNode::getId).collect(Collectors.toList());
        int pos = 0;
        DagSearchResult dagSearchResult = new DagSearchResult();
        while (pos < idList.size()) {
            String curId = idList.get(pos);
            dagSearchResult.getNodes().add(fullMap.get(curId));
            Set<DagLink> linkSet = fullMap.get(curId).getStream(dir);
            dagSearchResult.getLinks().addAll(linkSet);
            for (DagLink link : linkSet) {
                String newId = getNextId(dir, link);
                //新出现的入口数量为0的节点，放到待处理队列中
                if (fullMap.get(newId).exhausted(dir)) {
                    idList.add(newId);
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

        return dagSearchResult;
    }

    /**
     * 快速遍历整个图，把不可及的node和link都剪掉
     *
     * @param nodeMap 节点地图
     * @param entryId 入口点
     * @param dir     检索方向
     */
    private static void scanAndCrop(Map<String, ? extends DagNode> nodeMap, String entryId, DagDirectionEnum dir) {
        int pos = 0;
        List<String> idList = Lists.newArrayList(entryId);
        Set<String> idSet = Sets.newHashSet(entryId);
        while (pos < idList.size()) {
            String curId = idList.get(pos);
            Set<DagLink> linkSet = nodeMap.get(curId).getStream(dir);
            for (DagLink link : linkSet) {
                String newId = getNextId(dir, link);
                if (idSet.add(newId)) {
                    idList.add(newId);
                }
            }
            pos++;
        }

        Iterator<? extends Map.Entry<String, ? extends DagNode>> iter = nodeMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, ? extends DagNode> entry = iter.next();
            if (!idSet.contains(entry.getKey())) {
                iter.remove();
            }
            for (DagDirectionEnum eachDir : DagDirectionEnum.values()) {
                entry.getValue().getStream(eachDir).removeIf(link -> !idSet.contains(link.getSource()) || !idSet.contains(link.getTarget()));
            }
        }
    }

    /**
     * 根据方向，获取下一个节点
     *
     * @param dir              检索方向
     * @param dagLink 连接
     * @return 另一端的节点id
     */
    private static String getNextId(DagDirectionEnum dir, DagLink dagLink) {
        String nextId;
        if (dir == UP) {
            nextId = dagLink.getSource();
        } else {
            nextId = dagLink.getTarget();
        }
        return nextId;
    }
}
