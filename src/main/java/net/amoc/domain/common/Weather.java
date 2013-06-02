package net.amoc.domain.common;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Atiqur Rahman
 * @since 01/06/2013 12:53 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Weather extends Persistent {

    private int id;
    private String stationId;
    private String year;
    private String month;
    private String day;
    private int dateInt;
    private double maxTemperature;
    private double minTemperature;
    private double rainFall;


    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(nullable = false, length = 20)
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Column(nullable = false, length = 20)
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Column(nullable = false, length = 20)
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Column(nullable = false, length = 20)
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getDateInt() {
        return dateInt;
    }

    public void setDateInt(int dateInt) {
        this.dateInt = dateInt;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getRainFall() {
        return rainFall;
    }

    public void setRainFall(double rainFall) {
        this.rainFall = rainFall;
    }
}
