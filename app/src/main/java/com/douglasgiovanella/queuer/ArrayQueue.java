package com.douglasgiovanella.queuer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Douglas Giovanella on 27/06/2017.
 */

@SuppressWarnings("unchecked")
public class ArrayQueue<T> implements Queue<T> {

    private T[] array;

    private int head = 0;

    private int tail = 0;

    private int size = 0;

    public ArrayQueue() {
        array = (T[]) new Object[20];
    }

    public ArrayQueue(int size) {
        array = (T[]) new Object[size];
    }

    @Override
    public void enqueue(T e) {
        if (isFull()) {
            T[] temp = (T[]) new Object[array.length * 2];

            int n = head;
            for (int i = 0; i < array.length; i++) {
                n = n % array.length;
                temp[i] = array[n];
                n++;
            }
            head = 0;
            tail = array.length;
            array = temp;
        }
        array[tail] = e;
        tail = (tail + 1) % array.length;
        size++;
    }

    private boolean isFull() {
        return getSize() == array.length;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            return null;
        } else {
            T aux = array[head];
            array[head] = null;
            head = (head + 1) % array.length;
            size--;
            return aux;
        }
    }

    @Override
    public T front() {
        return array[head];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    public HashMap<String, Object> getValues() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("head", head);
        map.put("tail", tail);
        map.put("array", array);
        return map;
    }

    T[] getArray() {
        return array;
    }


    private boolean isHead(int position) {
        return position == head;
    }

    private boolean isTail(int position) {
        return position == tail;
    }

    List<QueueItem> getQueueAsQueueItens() {

        List<QueueItem> tmp = new ArrayList<>();
        QueueItem qItem;

        for (int i = 0; i < array.length; i++) {
            qItem = new QueueItem();
            qItem.setValue(array[i]);
            qItem.setHead(isHead(i));
            qItem.setTail(isTail(i));
            tmp.add(qItem);
        }

        return tmp;
    }

}
