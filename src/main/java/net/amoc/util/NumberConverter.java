package net.amoc.util;

/**
 * User: atiq2
 * Date: Mar 26, 2008
 */
public abstract class NumberConverter {

    //    private static final String TAKA = "Taka";
    private static final String TAKA = "";
    private static final String PAISA = "Paisa";

    private static double getPlace(String number) {
        switch (number.length()) {
            case 1:
                return DefinePlace.UNITS;
            case 2:
                return DefinePlace.TENS;
            case 3:
                return DefinePlace.HUNDREDS;
            case 4:
                return DefinePlace.THOUSANDS;
            case 5:
                return DefinePlace.TENTHOUSANDS;
            case 6:
                return DefinePlace.LAKHS;
            case 7:
                return DefinePlace.TENLAKHS;
            case 8:
                return DefinePlace.CRORES;
            case 9:
                return DefinePlace.TENCRORES;
            case 10:
                return DefinePlace.HUNDREDCRORES;
            case 11:
                return DefinePlace.THOUSANDCRORES;
        }//switch
        return DefinePlace.LIMITEXCEED;
    }// getPlace

    private static String getWord(int number) {
        switch (number) {
            case 1:
                return "One";
            case 2:
                return "Two";
            case 3:
                return "Three";
            case 4:
                return "Four";
            case 5:
                return "Five";
            case 6:
                return "Six";
            case 7:
                return "Seven";
            case 8:
                return "Eight";
            case 9:
                return "Nine";
            case 0:
                return "Zero";
            case 10:
                return "Ten";
            case 11:
                return "Eleven";
            case 12:
                return "Tweleve";
            case 13:
                return "Thirteen";
            case 14:
                return "Forteen";
            case 15:
                return "Fifteen";
            case 16:
                return "Sixteen";
            case 17:
                return "Seventeen";
            case 18:
                return "Eighteen";
            case 19:
                return "Ninteen";
            case 20:
                return "Twenty";
            case 30:
                return "Thirty";
            case 40:
                return "Forty";
            case 50:
                return "Fifty";
            case 60:
                return "Sixty";
            case 70:
                return "Seventy";
            case 80:
                return "Eighty";
            case 90:
                return "Ninty";
            case 100:
                return "Hundred";
        } //switch
        return "";
    } //getWord

    private static String cleanNumber(String number) {
        number = number.replaceAll(" ", "");
        number = number.replace(',', ' ').replaceAll(" ", "");
        while (number.startsWith("0")) {
            number = number.replaceFirst("0", "");
        }
        return number;
    }

    public static String convertNumberWithDecimal(String number) {
        String returnValue = "";
        number = cleanNumber(number);
        String[] a = number.split("\\.");
        returnValue += convertNumber(a[0]) + " " + TAKA;
        if (a.length > 1 && cleanNumber(a[1]).length() > 0) {
            returnValue += " " + convertNumber(a[1]) + " " + PAISA;
        }
        return returnValue;
    }

    public static String convertNumber(String number) {

        double num;
        try {
            num = Double.parseDouble(number);
        } catch (Exception e) {
            return "Invalid Number Sent to Convert";
        }

        String returnValue = "";
        while (num > 0) {
            number = "" + (int) num;
            double place = getPlace(number);
            if (place == DefinePlace.LIMITEXCEED) {
                return "In Words Conversion Error ";
            } else if (place == DefinePlace.TENS || place == DefinePlace.TENTHOUSANDS ||
                    place == DefinePlace.TENLAKHS || place == DefinePlace.TENCRORES ||
                    place == DefinePlace.HUNDREDCRORES || place == DefinePlace.THOUSANDCRORES) {
                int subNum = Integer.parseInt(number.charAt(0) + "" + number.charAt(1));

                if (place > DefinePlace.TENCRORES) {
                    subNum = Integer.parseInt(number.charAt(0) + "" + number.charAt(1) + "" + number.charAt(2));
                }

                if (subNum > 99) {
                    returnValue += convertNumber(Integer.toString(subNum));
                } else if (subNum >= 21 && (subNum % 10) != 0) {
                    returnValue += getWord(Integer.parseInt("" + number.charAt(0)) * 10) + " " + getWord(subNum % 10);
                } else {
                    returnValue += getWord(subNum);
                }

                if (place == DefinePlace.TENS) {
                    num = 0;
                } else if (place == DefinePlace.TENTHOUSANDS) {
                    num -= subNum * DefinePlace.THOUSANDS;
                    returnValue += " Thousand ";
                } else if (place == DefinePlace.TENLAKHS) {
                    num -= subNum * DefinePlace.LAKHS;
                    returnValue += " Lac ";
                } else if (place == DefinePlace.TENCRORES) {
                    num -= subNum * DefinePlace.CRORES;
                    returnValue += " Crore ";
                } else if (place == DefinePlace.HUNDREDCRORES) {
                    num -= subNum * DefinePlace.CRORES;
                    returnValue += " Crore ";
                } else if (place == DefinePlace.THOUSANDCRORES) {
                    num -= subNum * DefinePlace.CRORES;
                    returnValue += " Crore ";
                }
            } else {
                int subNum = Integer.parseInt("" + number.charAt(0));

                returnValue += getWord(subNum);
                if (place == DefinePlace.UNITS) {
                    num = 0;
                } else if (place == DefinePlace.HUNDREDS) {
                    num -= subNum * DefinePlace.HUNDREDS;
                    returnValue += " Hundred ";
                } else if (place == DefinePlace.THOUSANDS) {
                    num -= subNum * DefinePlace.THOUSANDS;
                    returnValue += " Thousand ";
                } else if (place == DefinePlace.LAKHS) {
                    num -= subNum * DefinePlace.LAKHS;
                    returnValue += " Lac ";
                } else if (place == DefinePlace.CRORES) {
                    num -= subNum * DefinePlace.CRORES;
                    returnValue += " Crore ";
                }
            }
        }//while
        return returnValue;
    }//convert number
} //class

class DefinePlace {
    public static final double UNITS = 1;
    public static final double TENS = 10 * UNITS;
    public static final double HUNDREDS = 10 * TENS;
    public static final double THOUSANDS = 10 * HUNDREDS;
    public static final double TENTHOUSANDS = 10 * THOUSANDS;
    public static final double LAKHS = 10 * TENTHOUSANDS;
    public static final double TENLAKHS = 10 * LAKHS;
    public static final double CRORES = 10 * TENLAKHS;
    public static final double TENCRORES = 10 * CRORES;
    public static final double HUNDREDCRORES = 10 * TENCRORES;
    public static final double THOUSANDCRORES = 10 * HUNDREDCRORES;
    public static final double LIMITEXCEED = -1;
}
