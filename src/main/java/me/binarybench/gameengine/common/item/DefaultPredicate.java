package me.binarybench.gameengine.common.item;

import java.util.function.Predicate;

/**
 * Created by BinaryBench on 3/24/2016.
 */
public class DefaultPredicate<T> implements Predicate<T> {
    @Override
    public boolean test(T t)
    {
        return true;
    }
}
