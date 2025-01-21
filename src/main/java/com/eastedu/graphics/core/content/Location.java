package com.eastedu.graphics.core.content;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type Location.
 *
 * @author ZhenZhong
 */
public class Location {
    private static final ThreadLocal<Location> THREAD_LOCAL = ThreadLocal.withInitial(Location::new);
    private final Map<Object, AtomicInteger> list = new HashMap<>();
    /**
     * The Y offset.
     */
    public float yOffset = 0;
    /**
     * The Row.
     */
    public int row = 0;

    /**
     * Get location.
     *
     * @return the location
     */
    public static Location get() {
        return THREAD_LOCAL.get();
    }

    /**
     * Clear.
     */
    public static void clear() {
        THREAD_LOCAL.remove();
    }

    /**
     * Gets list symbol.
     *
     * @param key the key
     * @return the list symbol
     */
    public final int getListSymbol(Object key) {
        AtomicInteger value;
        if (list.containsKey(key)) {
            value = list.get(key);
        } else {
            value = new AtomicInteger(0);
            list.put(key, value);
        }
        return value.getAndIncrement();
    }

}
