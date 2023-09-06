package data;

import java.util.HashMap;
import java.util.Map;

public class MetroStationList {
    private Map<String, MetroStation> stationMap = new HashMap<>();

    public MetroStationList() {
    }

    public void addStation(String name, MetroStation station) {
        stationMap.put(name, station);
    }

    public Map<String, MetroStation> getStationMap() {
        return stationMap;
    }

    @Override
    public String toString() {
        return "MetroStationList{" +
                "stationMap=" + stationMap +
                '}';
    }
}
