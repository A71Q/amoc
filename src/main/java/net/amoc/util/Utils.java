package net.amoc.util;

import net.amoc.web.SessionKeys;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: atiq2
 * Date: Dec 10, 2007
 */


public abstract class Utils {
    private static final Logger log = Logger.getLogger(Utils.class);

    public static String FORMAT_DATE_ONLY = "dd/MM/yyyy";
    public static String FORMAT_DATE_TIME = "dd/MM/yy hh:mm a";
    public static DateFormat DATE_FORMAT;

    public static final String SELECT_ALL = "All";
    public static final String NUMBER = "Num";

    public static final String HOST_NAME;

    static {
        InetAddress temp;
        try {
            temp = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
        HOST_NAME = temp.getHostName().replaceAll("\\..+$", "").toLowerCase();

        DATE_FORMAT = new SimpleDateFormat(FORMAT_DATE_ONLY);
    }

    public static boolean isNullOrEmpty(String str) {
        return ((str == null) || (str.trim().length() == 0));
    }

    public static boolean isNotEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    public static boolean isTrue(Boolean bool) {
        return ((bool != null) && bool);
    }

    public static boolean isExistsInRequest(HttpServletRequest request, String str) {
        return isNotEmpty(ServletRequestUtils.getStringParameter(request, str, null));
    }

    public static List getNullRemovedList(List contentList) {
        List rList = new ArrayList();

        if (isEmpty(contentList)) {
            for (Object o : contentList) {
                if (o != null) {
                    rList.add(o);
                }
            }
        }
        return rList;
    }

    public static String getLanguage(Locale locale) {
        String loc = locale.getLanguage();

        return loc.substring(0, 2);
    }

    /**
     * Convenient equality checker, works even if first object is
     * null.
     */
    public static boolean nullEquals(Object o1, Object o2) {
        if (o1 == null)
            return (o2 == null);

        return o1.equals(o2);
    }

    public static String convJS(String s) {
        // Convert problem characters to JavaScript Escaped values
        if (s == null) {
            return "";
        }

        String t = s;
        t = replace(t, "\\", "\\\\"); // replace backslash with \\
        t = replace(t, "'", "\\\'"); // replace an single quote with \'
        t = replace(t, "\"", "\\\""); // replace a double quote with \"
        t = replace(t, "\r", "\\r"); // replace CR with \r;
        t = replace(t, "\n", "\\n"); // replace LF with \n;

        return t;
    }

    private static String replace(String s, String one, String another) {
        // In a string replace one substring with another
        if (s.equals("")) {
            return "";
        }

        String res = "";
        int i = s.indexOf(one, 0);
        int lastpos = 0;

        while (i != -1) {
            res += (s.substring(lastpos, i) + another);
            lastpos = i + one.length();
            i = s.indexOf(one, lastpos);
        }

        res += s.substring(lastpos); // the rest

        return res;
    }


    /**
     * Merges two arrays. Primitive types wouldn't work. Dont' need any type casting.
     * Just pass to arrays of any type and it returns an array of the same type.
     * Magic of generics....and they say it's bad ... :(
     *
     * @param a1
     * @param a2
     * @return an array of the same type as a1&a2
     */
    public static <T> T[] mergeArrays(T[] a1, T[] a2) {
        Class cType = a1.getClass().getComponentType();
        T[] arr = (T[]) Array.newInstance(cType, a1.length + a2.length);

        System.arraycopy(a1, 0, arr, 0, a1.length);

        System.arraycopy(a2, 0, arr, a1.length, a2.length);

        return arr;
    }

    /**
     * Returns a copy of the given object
     *
     * @param t
     * @return
     * @throws Exception
     */
    public static <T extends Serializable> T cloneObject(T t) throws Exception {
        byte[] objectStream = serialize(t);

        Object o = deserialize(objectStream);
        System.out.println(t.getClass());
        return (T) o;
    }

    /**
     * <p>Serializes an <code>Object</code> to a byte array for
     * storage/serialization.</p>
     *
     * @param obj the object to serialize to bytes
     * @return a byte[] with the converted Serializable
     * @throws RuntimeException if the serialization fails
     */
    public static byte[] serialize(Serializable obj) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        serialize(obj, baos);
        return baos.toByteArray();
    }


    /**
     * <p>Serializes an <code>Object</code> to the specified stream.</p>
     * <p/>
     * <p>The stream will be closed once the object is written.
     * This avoids the need for a finally clause, and maybe also exception
     * handling, in the application code.</p>
     * <p/>
     * <p>The stream passed in is not buffered internally within this method.
     * This is the responsibility of your application if desired.</p>
     *
     * @param obj          the object to serialize to bytes, may be null
     * @param outputStream the stream to write to, must not be null
     * @throws IllegalArgumentException if <code>outputStream</code> is <code>null</code>
     * @throws RuntimeException         if the serialization fails
     */
    private static void serialize(Serializable obj, OutputStream outputStream) {
        if (outputStream == null) {
            throw new IllegalArgumentException("The OutputStream must not be null");
        }
        ObjectOutputStream out = null;
        try {
            // stream closed in the finally
            out = new ObjectOutputStream(outputStream);
            out.writeObject(obj);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }

    /**
     * <p>Deserializes a single <code>Object</code> from an array of bytes.</p>
     *
     * @param objectData the serialized object, must not be null
     * @return the deserialized object
     * @throws IllegalArgumentException if <code>objectData</code> is <code>null</code>
     * @throws RuntimeException         (runtime) if the serialization fails
     */
    public static Object deserialize(byte[] objectData) {
        if (objectData == null) {
            throw new IllegalArgumentException("The byte[] must not be null");
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(objectData);
        return deserialize(bais);
    }

    /**
     * <p>Deserializes an <code>Object</code> from the specified stream.</p>
     * <p/>
     * <p>The stream will be closed once the object is written. This
     * avoids the need for a finally clause, and maybe also exception
     * handling, in the application code.</p>
     * <p/>
     * <p>The stream passed in is not buffered internally within this method.
     * This is the responsibility of your application if desired.</p>
     *
     * @param inputStream the serialized object input stream, must not be null
     * @return the deserialized object
     * @throws IllegalArgumentException if <code>inputStream</code> is <code>null</code>
     * @throws RuntimeException         if the serialization fails
     */
    private static Object deserialize(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("The InputStream must not be null");
        }
        ObjectInputStream in = null;
        try {
            // stream closed in the finally
            in = new ObjectInputStream(inputStream);
            return in.readObject();

        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }


    public static boolean requestContainsAnyParameter(HttpServletRequest request,
                                                      String... parameters) {
        String pressedBtn = ServletRequestUtils.getStringParameter(request, "pressedButton", null);
//        log.debug("pressedButton value = " + pressedBtn);

        for (String param : parameters) {
            if (param.equals(pressedBtn)
                    || isNotEmpty(ServletRequestUtils.getStringParameter(request, param, null))) {
                log.debug("requestContainsAnyParameter: param = " + param);
                return true;
            }
        }
        return false;
    }

    /**
     * Sort a Map based on its value object.
     *
     * @param passedMap The Map neeed to be sort.
     * @param ascending Pass 'true' for ascending order, false for descending.
     * @return sorted LinkedHashMap.
     */
    public static LinkedHashMap sortMapByValues(Map passedMap, boolean ascending) {

        List mapKeys = new ArrayList(passedMap.keySet());
        List mapValues = new ArrayList(passedMap.values());

        if (ascending) {
            Collections.sort(mapValues);
        } else {
            Collections.reverse(mapValues);
        }

        LinkedHashMap someMap = new LinkedHashMap();
        for (Object val : mapValues) {
            for (Object key : mapKeys) {
                if (passedMap.get(key).toString().equals(val.toString())) {
                    someMap.put(key, val);
                    break;
                }
            }
        }
        return someMap;
    }

    /**
     * Generate a unique ID based on time stamp and a random part.  It's not guaranteed to be unique though, as the
     * random part be repeat.
     */
    public static String getUniqueTsBasedId() {
        Random rnd = new Random();
        int rval = rnd.nextInt(1000000);

        StringBuilder sb = new StringBuilder(String.valueOf(System.currentTimeMillis()));
        sb.append("-");
        sb.append(rval);

        return sb.toString();
    }

    public static String makeIdSet(List<Integer> iList) {
        StringBuffer buf = new StringBuffer();
        int index = 0;

        buf.append("(");
        for (Integer i : iList) {
            if (index > 0)
                buf.append(",");
            buf.append(i.intValue());
            index++;
        }
        buf.append(")");

        return buf.toString();
    }

    /**
     * Make an SQL set out of the Integer Array.
     */
    public static String makeIdSet(Integer[] list) {
        if (list.length == 0) {
            return "(-1)";
        }
        StringBuffer buff = new StringBuffer(100);

        buff.append("(");
        for (int i = 0; i < list.length; i++) {
            if (i > 0) {
                buff.append(",");
            }
            buff.append(String.valueOf(list[i]));
        }
        buff.append(")");

        return buff.toString();
    }

    public static String makeIdSet(Integer[] list, List argList) {
        if (list.length == 0) {
            argList.add(-1);
            return "(?)";
        }
        StringBuffer buff = new StringBuffer(100);

        buff.append("(");
        for (int i = 0; i < list.length; i++) {
            if (i > 0) {
                buff.append(",");
            }
            argList.add(list[i]);
            buff.append("?");
        }
        buff.append(")");

        return buff.toString();
    }


    public static String makeIntSet(String[] list) {
        StringBuffer buff = new StringBuffer(100);

        if (list == null) {
            list = new String[]{"0"};

        }


        buff.append("(");
        for (int i = 0; i < list.length; i++) {
            if (i > 0)
                buff.append(",");
            buff.append(String.valueOf(list[i]));
        }
        buff.append(")");

        return buff.toString();
    }


    public static List<Integer> makeIntListFromString(List<String> intStrings) {
        List<Integer> newList = new ArrayList<Integer>(intStrings.size());
        for (String intStr : intStrings) {
            newList.add(Integer.parseInt(intStr));
        }
        return newList;
    }

    public static List<String> getStringIntList(int low, int high, int interval, boolean noPadding) {

        // one extra for the empty string at beginning
        List<String> intList = new ArrayList<String>();

        for (int i = low; i <= high; i += interval) {
            if (noPadding)
                intList.add(String.valueOf(i));
            else
                intList.add(String.format("%02d", i));
        }
        return intList;
    }

    public static java.io.InputStream parseStringToIS(String str) {
        if (str == null) return null;
        str = str.trim();
        java.io.InputStream in = null;
        try {
            in = new java.io.ByteArrayInputStream(str.getBytes());
        } catch (Exception ex) {
            //
        }
        return in;
    }

    public static String getHostName() {
        return HOST_NAME;
    }

    /**
     * Return a unique ID based on given timestamp, current thread id and a random number.
     *
     * @param tstamp Current time in millis
     * @return The unique ID.
     */
    public static String getUniqueId(long tstamp) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        return sb.append(tstamp)
                .append("-")
                .append(Thread.currentThread().getId())
                .append("-")
                .append(rnd.nextInt(1000000)).toString();

    }

    public static boolean isTestMode(HttpServletRequest request) {
        return WebUtils.getSessionAttribute(request, SessionKeys.TEST_MODE) != null
                && (Boolean) WebUtils.getSessionAttribute(request, SessionKeys.TEST_MODE);
    }

    public static boolean isInteger(String num) {
        try {
            Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isEmpty(Collection collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isEmpty(String[] strings) {
        return strings == null || strings.length == 0;
    }


    public static boolean containSymbols(String str, char[] filterCharArray) {
        if (str == null || filterCharArray == null)
            throw new IllegalArgumentException("Arguments cannot be Null");

        if (filterCharArray.length == 0)
            throw new IllegalArgumentException("filterCharArray length cannot be zero");

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            for (char aFilterCharArray : filterCharArray) {
                if (c == aFilterCharArray) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String formatDate(Date date) {
        try {
            return DATE_FORMAT.format(date);
        } catch (Exception ex) {
            return "";
        }
    }

    public static String formatDate(Date date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format);
            return df.format(date);
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * Given a date and a timezone converts the date into a representative integer
     *
     * @param date The input date to covert
     * @param tz   The timezone of the user/site
     * @return an integer value of the format yyyyMMdd representing the input date in tz timezone
     */
    public static int getDateInt(Date date, String tz) {

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        if (tz != null)
            df.setTimeZone(TimeZone.getTimeZone(tz));
        return Integer.parseInt(df.format(date));
    }

    public static Date strToDate(String sDate) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy");
        // TODO: check if the reason for this can be found
        // return new Date(fmt.parse(sDate).getTime());
        return fmt.parse(sDate);
    }

    public static String stringMonth(String month) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("MM");
        SimpleDateFormat fmta = new SimpleDateFormat("MMM");
        return fmta.format(fmt.parse(month));

    }

    public static Map convertXmlToHashMap(String xml) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(new StringReader(xml));
        } catch (DocumentException e) {
            log.error("Error During Read of XML", e);
        }

        if (document != null) {
            Element root = document.getRootElement();
            for (Attribute attribute : (List<Attribute>) root.attributes()) {
                map.put(attribute.getQName().getName(), attribute.getData().toString());

            }
            for (Element element : (List<Element>) root.elements()) {
                map.put(element.getQName().getName(), element.getData().toString());
            }
        }

        return map;
    }

    public static List makeNullSafe(List list) {
        return list == null ? new ArrayList() : list;
    }

    public static String makeCsvForInClause(int[] ids) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        boolean first = true;
        for (int id : ids) {
            if (!first) {
                sb.append(",");
            } else {
                first = false;
            }
            sb.append(id);
        }
        sb.append(")");
        return sb.toString();
    }

    public static Properties loadProperties(String path) throws Exception {
        Properties prop = new Properties();
        InputStream istr = null;

        try {
            istr = WebUtils.class.getResourceAsStream(path);
            if (istr == null) {
                log.error("Could not find properties file: " + path);
                throw new Exception("Could not find properties file: " + path);
            }
            prop.load(istr);
        } catch (IOException ex) {
            throw new Exception("Could not read properties file " + path
                    + ": ", ex);
        } finally {
            if (istr != null) {
                try {
                    istr.close();
                    istr = null;
                } catch (IOException ex) {
                    log.error("Error in closing InputStream for file " + path
                            + ": " + ex);
                    /* eat up */
                }
            }
        }

        return prop;
    }

    public static <T> List<T> convertToSortedList(Collection<T> collection, Comparator<? super T> comp) {
        List<T> list;
        if (isEmpty(collection)) {
            list = new ArrayList<T>();
        } else {
            list = new ArrayList<T>(collection);
        }
        Collections.sort(list, comp);
        return list;
    }

    public static Map sortMapByValue(Map map, final boolean sortOrderAsc) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                if (sortOrderAsc) {
                    return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
                } else {
                    return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
                }
            }
        });
        Map result = new LinkedHashMap();
        for (Object aList : list) {
            Map.Entry entry = (Map.Entry) aList;
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static String getNaturalName(String name) {
        StringBuilder sb = new StringBuilder(100);

        for (Character c : name.toCharArray()) {
            if (sb.length() <= 0) {
                sb.append(Character.toUpperCase(c));
                continue;
            }
            if (Character.isUpperCase(c)) {
                sb.append(" ").append(c);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String getBloodGroupInShort(String bloodGroup) {
        if (Utils.isNotEmpty(bloodGroup)) {
            return bloodGroup.replace(" Positive", "+").replace(" Negative", "-");
        }
        return "";
    }

    public static boolean hasAccess(Set<String> userRoleSet, String reportRoles) {
        for (String role : reportRoles.split(",")) {
            if (userRoleSet.contains(role.trim())) {
                return true;
            }
        }
        return false;
    }

    public static String getNickName(String name) {
        if (name != null) {
            String[] prefixes = new String[]{"Ms.", "Mr.", "Mrs.", "dr.", "sri", "engr.", "prof.", "master", "engineer", "d.", "alhaz", "al-haz", "hazi", "haji", "mst.", "late"};
            String nameWithoutPrefix = name;
            for (String prefix : prefixes) {
                nameWithoutPrefix = nameWithoutPrefix.replace(prefix, "");
            }
            nameWithoutPrefix = nameWithoutPrefix.replace("  ", " ").trim();
            String[] splited = nameWithoutPrefix.split(" ");
            if (splited.length > 0) {
                return splited[0];
            }
        }
        return name;
    }

    public static int getPageStart(int pageNo, int pageSize, int bookingStart, int bookingEnd, String sortOrder) {
        int start;
        if ("ASC".equals(sortOrder)) {
            start = (pageNo - 1) * pageSize + bookingStart;
            if (start < bookingStart) {
                return bookingStart;
            }
        } else {
            start = bookingEnd - (pageNo - 1) * pageSize;
            if (start > bookingEnd) {
                return bookingEnd;
            }
        }
        return start;
    }

    public static int getPageEnd(int pageStart, int pageSize, String sortOrder, int bookingStart, int bookingEnd) {
        int end;
        if ("ASC".equals(sortOrder)) {
            end = (pageStart + pageSize) - 1;
            if (end > bookingEnd) {
                return bookingEnd;
            }
        } else {
            end = (pageStart - pageSize) + 1;
            if (end < bookingStart) {
                return bookingStart;
            }
        }
        return end;
    }
}

