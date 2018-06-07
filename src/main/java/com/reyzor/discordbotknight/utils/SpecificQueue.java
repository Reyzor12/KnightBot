package com.reyzor.discordbotknight.utils;

import com.reyzor.discordbotknight.audio.QueueOfTracking;
import com.reyzor.discordbotknight.audio.Queueable;

import java.util.*;

/**
 * @author reyzor
 * @version 1.0
 * @since 07.06.2018
 */
public class SpecificQueue<T extends Queueable> implements QueueOfTracking<T> {

    private final List<T> list;
    private final Set<Long> set;

    public SpecificQueue()
        {
        list = new LinkedList<>();
        set = new HashSet<>();
        }


    @Override
    public int add(T item)
    {
        list.add(item);
        return list.size() - 1 ;
        /*int lastIndex;
        for (lastIndex = list.size() - 1; lastIndex > -1; lastIndex--)
        {
            if (list.get(lastIndex).getIdentifier() == item.getIdentifier()) break;
        }
        lastIndex++;
        set.clear();
        for (; lastIndex < list.size(); lastIndex ++)
        {
            if (set.contains(list.get(lastIndex).getIdentifier())) break;
            set.add(list.get(lastIndex).getIdentifier());
        }
        list.add(lastIndex, item);
        return lastIndex;*/
    }

    @Override
    public void clear() {
        list.clear();
    }
}
