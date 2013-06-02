package net.amoc.track;

import javax.servlet.http.HttpServletRequest;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Atiqur Rahman
 * @since Jun 1, 2013
 */
public class RequestInfo implements Externalizable {
    private long beginTime;
    private String uri;
    private String method;
    private String queryString;
    private String[] postParamNames;
    private String threadName;
    private long duration = -1L;

    private static final long serialVersionUID = 2L;

    public void begin(HttpServletRequest request) {
        this.beginTime = System.currentTimeMillis();
        this.uri = request.getRequestURI();
        this.method = request.getMethod().substring(0, 1);
        this.queryString = request.getQueryString();

        if ("P".equalsIgnoreCase(method)) {
            Enumeration e = request.getParameterNames();
            List<String> paramNames = new ArrayList<String>(64);
            while (e.hasMoreElements()) {
                paramNames.add((String) e.nextElement());
            }
            this.postParamNames = paramNames.toArray(new String[paramNames.size()]);
        }

        this.threadName = Thread.currentThread().getName();
    }

    public void end() {
        long endTime = System.currentTimeMillis();
        this.duration = endTime - this.beginTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

        sb.append(sdf.format(new Date(beginTime)))
                .append(' ')
                .append(method)
                .append(' ')
                .append(threadName)
                .append(' ')
                .append('[').append(uri);
        if (queryString != null) {
            sb.append('?').append(queryString);
        }
        sb.append(']');
        sb.append(' ');
        if (duration >= 0) {
            sb.append(duration).append("ms");
        } else {
            sb.append("?");
        }

        if (postParamNames != null) {
            sb.append(' ').append('(');
            for (int i = 0; i < postParamNames.length; i++) {
                if (i > 0) {
                    sb.append(',');
                }
                sb.append(postParamNames[i]);
            }
            sb.append(')');
        }

        return sb.toString();
    }


    /**
     * NOTE: Except <code>queryString</code>, no other String fields should be null when the object is serialized.
     */
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(beginTime);
        out.writeUTF(uri);
        out.writeUTF(method);

        if (queryString != null) {
            out.writeBoolean(true);
            out.writeUTF(queryString);
        } else {
            out.writeBoolean(false);
        }

        out.writeObject(postParamNames);
        out.writeUTF(threadName);
        out.writeLong(duration);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        beginTime = in.readLong();
        uri = in.readUTF();
        method = in.readUTF();

        boolean hasQueryString = in.readBoolean();
        if (hasQueryString) {
            queryString = in.readUTF();
        }

        postParamNames = (String[]) in.readObject();
        threadName = in.readUTF();
        duration = in.readLong();
    }
}