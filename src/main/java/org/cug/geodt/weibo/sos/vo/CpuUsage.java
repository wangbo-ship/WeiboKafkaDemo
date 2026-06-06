package org.cug.geodt.weibo.sos.vo;

import java.util.ArrayList;

/**
 * @FileName CpuUsage
 * @Author WJW
 * @Date 2023/10/6 9:48
 * @Description
 */
public class CpuUsage {
    private String name;
    private String description;
    private String baseUnit;
    private ArrayList<Measurements> measurements;
    private AvailableTags availableTags;

    public CpuUsage() {
    }

    public CpuUsage(String name, String description, String baseUnit, ArrayList<Measurements> measurements, AvailableTags availableTags) {
        this.name = name;
        this.description = description;
        this.baseUnit = baseUnit;
        this.measurements = measurements;
        this.availableTags = availableTags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public ArrayList<Measurements> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(ArrayList<Measurements> measurements) {
        this.measurements = measurements;
    }

    public AvailableTags getAvailableTags() {
        return availableTags;
    }

    public void setAvailableTags(AvailableTags availableTags) {
        this.availableTags = availableTags;
    }
}
class Measurements{
    private String statistic;

    private Double value;

    public Measurements() {
    }

    public Measurements(String statistic, Double value) {
        this.statistic = statistic;
        this.value = value;
    }

    public String getStatistic() {
        return statistic;
    }

    public void setStatistic(String statistic) {
        this.statistic = statistic;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}

class Measurement{
    private String statistic;
    private float value;

    public Measurement() {
    }

    public Measurement(String statistic, float value) {
        this.statistic = statistic;
        this.value = value;
    }

    public String getStatistic() {
        return statistic;
    }

    public void setStatistic(String statistic) {
        this.statistic = statistic;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}

class AvailableTags{

}
