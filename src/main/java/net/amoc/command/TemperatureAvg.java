package net.amoc.command;

/**
 * @author Atiqur Rahman
 * @since 02/06/2013 1:38 AM
 */
public class TemperatureAvg {
    private String year;
    private double avgMax;
    private double avgMin;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public double getAvgMax() {
        return avgMax;
    }

    public void setAvgMax(double avgMax) {
        this.avgMax = avgMax;
    }

    public double getAvgMin() {
        return avgMin;
    }

    public void setAvgMin(double avgMin) {
        this.avgMin = avgMin;
    }
}
