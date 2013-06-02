package net.amoc.util;

import org.springframework.beans.BeanWrapperImpl;

import java.util.List;

/**
 * @author Atiqur Rahman
 * @since Jun 1, 2013
 */
public class DaoUtils {

    public static String getCSVFromList(List list, String path, boolean withInDoubleCote) {
        if (list.size() == 0) {
            return withInDoubleCote ? "''" : "0";
        }
        StringBuilder sb = new StringBuilder(100);
        boolean first = true;
        for (Object obj : list) {
            if (!first) {
                sb.append(",");
            }
            first = false;
            if (withInDoubleCote) {
                sb.append("'");
            }
            if (path == null) {
                if (obj.getClass() == String.class) {
                    String str = (String) obj;
                    str = str.replaceAll("'", "''");
                    obj = str;
                }
                sb.append(obj);
            } else {
                sb.append(new BeanWrapperImpl(obj).getPropertyValue(path));
            }
            if (withInDoubleCote) {
                sb.append("'");
            }
        }
        return sb.toString();
    }

    public static String getCSVFromList(List list, String path) {
        return getCSVFromList(list, path, false);
    }

    public static String getCSVFromIntArray(int[] ints) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        boolean first = true;
        for (int i : ints) {
            if (!first) {
                sb.append(",");
            } else {
                first = false;
            }
            sb.append(i);
        }
        sb.append(")");
        return sb.toString();
    }
}
