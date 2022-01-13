package com.seceh.basic.utils.collection;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueElement<E> implements Delayed {

    private final E e;
    private final long initTime;
    private final long delayTime;

    public DelayQueueElement(long delayTime, E e) {
        this.e = e;
        this.initTime = System.currentTimeMillis();
        this.delayTime = delayTime;
    }

    public DelayQueueElement(long initTime, long delayTime, E e) {
        this.e = e;
        this.initTime = initTime;
        this.delayTime = delayTime;
    }

    public E getElement() {
        return e;
    }

    /**
     * 需要实现的接口，获得延迟时间   用过期时间-当前时间
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.initTime + this.delayTime -
                System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * 用于延迟队列内部比较排序   当前时间的延迟时间 - 比较对象的延迟时间
     */
    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }
}
