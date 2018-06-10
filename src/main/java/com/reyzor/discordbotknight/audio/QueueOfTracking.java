package com.reyzor.discordbotknight.audio;

import java.util.List;

public interface QueueOfTracking<T extends Queueable>
{
    int add(T item);
    void clear();
    T next();
    boolean isEmpty();
    List<T> getAll();
}
