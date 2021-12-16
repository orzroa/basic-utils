package com.seceh.basic.utils.dag;

import com.google.common.collect.Maps;
import com.seceh.basic.utils.exception.NamedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Collectors;

import static com.seceh.basic.utils.dag.DagDirectionEnum.DOWN;
import static com.seceh.basic.utils.dag.DagDirectionEnum.UP;


class DagSearchUtilsTest {

    @Test
    void testUp() {
        Map<String, DagNode> nodeMap = Maps.newHashMap();
        add(nodeMap, "11#a", "12#b");
        add(nodeMap, "12#b", "13#c");
        add(nodeMap, "13#c", "14#d");
        add(nodeMap, "14#d", "15#e");
        add(nodeMap, "15#e", "16#f");

        Assertions.assertEquals("13#c,12#b,11#a",
                String.join(",", DagSearchUtils.search(nodeMap, "13#c", UP).getNodes().stream()
                        .map(DagNode::getId).collect(Collectors.toSet())));
    }

    @Test
    void testDown() {
        Map<String, DagNode> nodeMap = Maps.newHashMap();
        add(nodeMap, "11#a", "12#b");
        add(nodeMap, "12#b", "13#c");
        add(nodeMap, "13#c", "14#d");
        add(nodeMap, "14#d", "15#e");
        add(nodeMap, "15#e", "16#f");

        Assertions.assertEquals("15#e,14#d,13#c,12#b,16#f",
                String.join(",", DagSearchUtils.search(nodeMap, "12#b", DOWN).getNodes().stream()
                        .map(DagNode::getId).collect(Collectors.toSet())));
    }

    @Test
    void testFork() {
        Map<String, DagNode> nodeMap = Maps.newHashMap();
        add(nodeMap, "11#a", "12#b");
        add(nodeMap, "12#b", "13#c");
        add(nodeMap, "13#c", "14#d");
        add(nodeMap, "12#b", "15#e");
        add(nodeMap, "15#e", "16#f");

        Assertions.assertEquals("15#e,14#d,13#c,12#b,16#f",
                String.join(",", DagSearchUtils.search(nodeMap, "12#b", DOWN).getNodes().stream()
                        .map(DagNode::getId).collect(Collectors.toSet())));
    }

    @Test
    void testSelfLoop() {
        Map<String, DagNode> nodeMap = Maps.newHashMap();
        add(nodeMap, "11#a", "12#b");
        add(nodeMap, "12#b", "11#a");

        Assertions.assertThrows(NamedException.class,
                () -> DagSearchUtils.search(nodeMap, "11#a", DOWN),
                "以下数据节点处于回环中：12#b,11#a");
    }

    @Test
    void testDownStreamLoop() {
        Map<String, DagNode> nodeMap = Maps.newHashMap();
        add(nodeMap, "11#a", "12#b");
        add(nodeMap, "12#b", "13#c");
        add(nodeMap, "13#c", "14#d");
        add(nodeMap, "14#d", "15#e");
        add(nodeMap, "15#e", "14#d");

        Assertions.assertThrows(NamedException.class,
                () -> DagSearchUtils.search(nodeMap, "11#a", DOWN),
                "以下数据节点处于回环中：15#e,14#d");
    }

    private void add(Map<String, DagNode> nodeMap, String from, String to) {
        DagNode fromNode = nodeMap.get(from);
        if (fromNode == null) {
            fromNode = new DagNode(from);
            nodeMap.put(from, fromNode);
        }

        DagNode toNode = nodeMap.get(to);
        if (toNode == null) {
            toNode = new DagNode(to);
            nodeMap.put(to, toNode);
        }

        DagLink link = new DagLink(from, to);

        fromNode.addStream(DOWN, link);
        toNode.addStream(UP, link);
    }
}
