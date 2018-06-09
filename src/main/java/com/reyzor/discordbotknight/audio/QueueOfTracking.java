package com.reyzor.discordbotknight.audio;

public interface QueueOfTracking<T extends Queueable>
{
    int add(T item);
    void clear();
    T next();
    boolean isEmpty();
}
