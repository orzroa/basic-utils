package com.seceh.basic.utils.dag;

public enum DagDirectionEnum {

    UP, DOWN;

    public DagDirectionEnum reverse() {
        if (this.equals(UP)) {
            return DOWN;
        } else {
            return UP;
        }
    }
}
