package net.amoc.util;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author Atiqur Rahman
 * @since Jun 1, 2013
 */
public class DebugUtils {

    private static final Logger log = Logger.getLogger(DebugUtils.class);
    private static int maxLineLengthForLog = 80;

    public static void dumpAllHeaders(HttpServletRequest request, StringBuilder buff) {
        Enumeration headerNames = request.getHeaderNames();
        if (headerNames == null) {
            buff.append("\tnull");
            return;
        }

        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            buff.append("\t")
                    .append(headerName)
                    .append(": ");

            boolean first = true;
            Enumeration values = request.getHeaders(headerName);
            while (values.hasMoreElements()) {
                String value = (String) values.nextElement();
                if (first) {
                    first = false;
                } else {
                    buff.append(" | ");
                }
                buff.append(value);
            }

            buff.append("\n");
        }
    }


    public static void dumpPostParameters(HttpServletRequest request, StringBuilder buffer) {
        boolean first = true;
        //noinspection unchecked
        Map<String, ?> ppMap = request.getParameterMap();
        for (String pn : ppMap.keySet()) {
            if (!first) {
                buffer.append("\n");
            }
            buffer.append("\t").append(pn).append("=");
            String[] values = (String[]) ppMap.get(pn);
            boolean fst = true;
            for (String value : values) {
                if (!fst) {
                    buffer.append(",");
                }
                int len = value.length();
                if (len > maxLineLengthForLog) {
                    buffer.append(value.substring(0, maxLineLengthForLog)).append("...");
                    buffer.append("[length=").append(len).append("]");
                } else {
                    buffer.append(value);
                }
                fst = false;
            }
            first = false;
        }
        buffer.append("\n");
    }
}
