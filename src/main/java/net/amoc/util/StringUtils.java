package net.amoc.util;

/**
 * @author Atiqur Rahman
 * @since 27/11/2012 11:39 PM
 */
public class StringUtils {
    public static boolean isEmpty(String str) {
        return ((str == null) || (str.trim().length() == 0));
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String... values) {
        for (String value : values)
            if (isEmpty(value)) return true;
        return false;
    }

    public static boolean isNotEmpty(String... values) {
        return !isEmpty(values);
    }
}
