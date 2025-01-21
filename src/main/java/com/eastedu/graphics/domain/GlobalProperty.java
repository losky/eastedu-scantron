package com.eastedu.graphics.domain;

/**
 * The type Global property.
 *
 * @author ZhenZhong
 */
public class GlobalProperty {
    private static boolean debug = false;

    private GlobalProperty() {
    }

    /**
     * Is debug boolean.
     *
     * @return the boolean
     */
    public static boolean isDebug() {
        String property = System.getProperty("graphics.debug", "false");
        return debug || Boolean.parseBoolean(property);
    }

    /**
     * Sets debug.
     *
     * @param debug the debug
     */
    public static void setDebug(boolean debug) {
        GlobalProperty.debug = debug;
    }
}
