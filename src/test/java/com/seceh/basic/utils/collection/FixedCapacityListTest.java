package com.seceh.basic.utils.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FixedCapacityListTest {

    @Test
    void removeTest() {
        FixedCapacityList<String> fixedCapacityList = new FixedCapacityList<>(3);
        fixedCapacityList.add("cba");
        fixedCapacityList.add("aaa");
        fixedCapacityList.add("ccc");
        fixedCapacityList.add("bba");
        Assertions.assertEquals(3, fixedCapacityList.size());

        fixedCapacityList.remove(e -> e.endsWith("b"));
        Assertions.assertEquals(3, fixedCapacityList.size());

        fixedCapacityList.remove(e -> e.endsWith("a"));
        Assertions.assertEquals(1, fixedCapacityList.size());
    }
}
