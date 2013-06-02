package net.amoc.util;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: atiq2
 * Date: Dec 29, 2008
 * Time: 8:56:02 PM
 */
public class DateUtils {
    public static final Map<DateTimeZone, Set<String>> problematicTimeZones = createProblematicTimeZones();

    private static Map<DateTimeZone, Set<String>> createProblematicTimeZones() {
        Map<DateTimeZone, Set<String>> map = new HashMap<DateTimeZone, Set<String>>();

        addToMap(map, "Indian/Kerguelen", "01/01/50");
        addToMap(map, "Pacific/Apia", "01/01/50");
        addToMap(map, "Pacific/Pago_Pago", "01/01/50");
        addToMap(map, "Pacific/Samoa", "01/01/50");
        addToMap(map, "US/Samoa", "01/01/50");

        return Collections.unmodifiableMap(map);
    }

    private static void addToMap(Map<DateTimeZone, Set<String>> map, String tz, String dstr) {
        DateTimeZone zone = DateTimeZone.forID(tz);
        if (map.containsKey(zone)) {
            map.get(zone).add(dstr);
        } else {
            Set<String> dates = new HashSet<String>();
            dates.add(dstr);
            map.put(zone, dates);
        }
    }

    public static String dateToStr(Date date) {
        return dateToStr(date, "MM/dd/yyyy");
    }

    public static String dateToStr(Date date, String dateFormat) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat fmt = new SimpleDateFormat(dateFormat);
        return fmt.format(date);
    }

    /**
     * Checks whether the date is valid.  A valid date can be in
     * either MM/dd/yyyy or MM/dd/yy format.
     *
     * @param dstr A date String
     * @return A boolean that represents the check's  result.
     *         'true' if the date is valid, 'false' if the date is not valid.
     */
    public static boolean isValidDate(String dstr) {
        String pattern = findDatePattern(dstr);
        if (pattern == null) {
            return false;
        }
        try {
            DateTimeFormat.forPattern(pattern).parseDateTime(dstr);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    private static String findDatePattern(String dstr) {
        if (dstr == null) {
            return null;
        } else if (dstr.matches("\\d{1,2}/\\d{1,2}/\\d\\d")) {
            return "dd/MM/yy";
        } else if (dstr.matches("\\d{1,2}/\\d{1,2}/\\d\\d\\d\\d")) {
            return "dd/MM/yyyy";
        }
        return null;
    }

    public static Date parseDateIgnoringProblemZones(String dstr, String tz)
            throws IllegalArgumentException {
        return isProblematicTimeZone(dstr, tz)
                ? parseDate(dstr, null)
                : parseDate(dstr, tz);
    }

    private static boolean isProblematicTimeZone(String dstr, String tz) {
        DateTimeZone zone = DateTimeZone.forID(tz);
        if (!problematicTimeZones.containsKey(zone)) return false;
        if (!problematicTimeZones.get(zone).contains(dstr)) return false;
        return true;
    }

    public static Date parseDate(String dstr, String tz)
            throws IllegalArgumentException, IllegalFieldValueException {
        String pattern = findDatePattern(dstr);
        if (tz != null) {
            DateTimeZone zone = DateTimeZone.forID(tz);
            DateTime parsedDateTime = DateTimeFormat.forPattern(pattern).withZone(zone).parseDateTime(dstr);
            return parsedDateTime.toDate();
        }
        DateTime parsedDateTime = DateTimeFormat.forPattern(pattern).parseDateTime(dstr);
        return parsedDateTime.toDate();
    }

    public static boolean isEqual(Date d1, Date d2) {
        DateTime midNight = new DateMidnight(d1).toDateTime();
        long dateLong = new DateMidnight(d2).getMillis();
        return midNight.isEqual(dateLong);
    }

    public static List<String> getMonthList(boolean isFull) {
        if (isFull) {
            return Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        } else {
            return Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

        }
    }
}
