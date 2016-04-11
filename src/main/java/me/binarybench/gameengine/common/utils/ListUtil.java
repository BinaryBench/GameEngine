package me.binarybench.gameengine.common.utils;

import java.util.*;

/**
 * Created by BinaryBench on 3/24/2016.
 */
public class ListUtil {
    private ListUtil()
    {
    }

    public boolean isIterable(Object obj)
    {
        return obj instanceof Iterable || obj.getClass().isArray();
    }

    @SafeVarargs
    public static <T> List<T> joinLists(Collection<T> collection, Collection<T>... collections)
    {
        List<T> list = new ArrayList<>(collection);

        for (Collection<T> collection1 : collections)
        {
            list.addAll(collection1);
        }

        return list;
    }

    public static <T> List<T> append(T single, T[] array)
    {
        List<T> list = new ArrayList<T>(Arrays.asList(array));
        list.add(single);

        return list;
    }


}
