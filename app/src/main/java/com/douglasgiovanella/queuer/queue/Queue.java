package com.douglasgiovanella.queuer.queue;

/**
 * Created by Douglas Giovanella on 27/06/2017.
 */

public interface Queue<T> {

    void enqueue(T e);

    T dequeue();

    T front();

    boolean isEmpty();

    int getSize();

}
