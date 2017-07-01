package com.douglasgiovanella.queuer;

/**
 * Created by Douglas Giovanella on 01/07/2017.
 */

public class QueueItem {

    private Object value;
    private boolean isHead, isTail;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isHead() {
        return isHead;
    }

    public void setHead(boolean head) {
        isHead = head;
    }

    public boolean isTail() {
        return isTail;
    }

    public void setTail(boolean tail) {
        isTail = tail;
    }
}
