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
public class Station extends Persistent {

    private int id;
    private String stationId;
    private String name;
    private String lat;
    private String lng;
    private boolean historicalDataAvailable;
    private double maxTmp;
    private String maxTmpDate;
    private double minTmp;
    private String minTmpDate;
    private double maxRainFall;
    private String maxRainFallDate;
    private double minRainFall;
    private String minRainFallDate;


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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false, length = 20)
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public boolean isHistoricalDataAvailable() {
        return historicalDataAvailable;
    }

    public void setHistoricalDataAvailable(boolean historicalDataAvailable) {
        this.historicalDataAvailable = historicalDataAvailable;
    }

    public double getMaxTmp() {
        return maxTmp;
    }

    public void setMaxTmp(double maxTmp) {
        this.maxTmp = maxTmp;
    }

    public String getMaxTmpDate() {
        return maxTmpDate;
    }

    public void setMaxTmpDate(String maxTmpDate) {
        this.maxTmpDate = maxTmpDate;
    }

    public double getMinTmp() {
        return minTmp;
    }

    public void setMinTmp(double minTmp) {
        this.minTmp = minTmp;
    }

    public String getMinTmpDate() {
        return minTmpDate;
    }

    public void setMinTmpDate(String minTmpDate) {
        this.minTmpDate = minTmpDate;
    }

    public double getMaxRainFall() {
        return maxRainFall;
    }

    public void setMaxRainFall(double maxRainFall) {
        this.maxRainFall = maxRainFall;
    }

    public String getMaxRainFallDate() {
        return maxRainFallDate;
    }

    public void setMaxRainFallDate(String maxRainFallDate) {
        this.maxRainFallDate = maxRainFallDate;
    }

    public double getMinRainFall() {
        return minRainFall;
    }

    public void setMinRainFall(double minRainFall) {
        this.minRainFall = minRainFall;
    }

    public String getMinRainFallDate() {
        return minRainFallDate;
    }

    public void setMinRainFallDate(String minRainFallDate) {
        this.minRainFallDate = minRainFallDate;
    }
}
