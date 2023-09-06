package data;

import data.MetroTransfer;

import java.util.List;

public class MetroInputPoint {
    public String name;
    public List<MetroTransfer> transfer;
    public List<String> nextPoints;
    public List<String> previousPoints;
    public String stationName;
    public int time;

    public MetroInputPoint() {}

    public MetroInputPoint(String name, int time) {
        this.name = name;
        this.time = time;
    }

    public MetroInputPoint(String name, List<String> nextPoints,
                           List<String> previousPoints, List<MetroTransfer> transfer, int time, String stationName) {
        this.name = name;
        this.nextPoints = nextPoints;
        this.previousPoints = previousPoints;
        this.transfer = transfer;
        this.time = time;
        this.stationName = stationName;
    }

    public String getName() {
        return name;
    }

    public void setStationName(String name) {
        this.stationName = name;
    }

    public List<MetroTransfer> getTransfer() {
        return transfer;
    }

    public List<String> getNextPoints() {
        return nextPoints;
    }

    public List<String> getPreviousPoints() {
        return nextPoints;
    }

    @Override
    public String toString() {
        return "MetroInputPoint{" +
                "name='" + name + '\'' +
                ", transfer=" + transfer +
                ", nextPoints=" + nextPoints +
                ", previousPoints=" + previousPoints +
                ", stationName='" + stationName + '\'' +
                ", time=" + time +
                '}';
    }
}
