package data;

import parsing.MetroNameComparator;

import java.util.*;

public class MetroStation {
    private Map<String, MetroInputPoint> values = new TreeMap<>();
    private String name;

    public MetroStation(String name) {
        this.name = name;
    }

    public void addStationToEnd(MetroInputPoint point) {
        int newInt = 1;
        for (Map.Entry<String, MetroInputPoint> entry : values.entrySet()) {
            newInt++;
        }

        values.put(Integer.toString(newInt), point);

        initNext();
    }

    public void addStationToStart(MetroInputPoint point) {
        Map<String, MetroInputPoint> map = new HashMap<>();

        for (Map.Entry<String, MetroInputPoint> entry : values.entrySet()) {
            map.put(Integer.toString(Integer.parseInt(
                    entry.getKey(), 10) + 1), entry.getValue());
        }

        map.put("1", point);
        values = map;

        initNext();
    }

    public void connect(String name, String interStation, String interName) {
        String key = null;
        MetroInputPoint point = null;

        for (Map.Entry<String, MetroInputPoint> entry : values.entrySet()) {
            if (entry.getValue().getName().equals(name)) {
                point = entry.getValue();
                key = entry.getKey();
            }
        }

       // values.put(key, new MetroInputPoint(point.getName(), interStation, interName, 5));

        List<MetroInputPoint> valueList = new LinkedList<MetroInputPoint>(values.values());

        initNext();
    }

    public void removeStation(String lineName, String name) {
        Map<String, MetroInputPoint> newValues = new TreeMap<>(new MetroNameComparator());
        int counter = 1;
        for (Map.Entry<String, MetroInputPoint> entry : values.entrySet()) {
            if (!(entry.getValue().getName().equals(name)
                    && entry.getValue().stationName.equals(lineName))) {
                newValues.put(Integer.toString(counter), entry.getValue());
                counter++;
            }

        }

        this.values = newValues;
        initNext();
    }

    public void addStation(String value, MetroInputPoint point) {

        values.put(value, point);
    }

    public void initNext() {
        ArrayList<MetroInputPoint> valueList = new ArrayList<>(values.values());

        int id = 0;
        for (Map.Entry<String, MetroInputPoint> entry : values.entrySet()) {

            if (id + 1 == valueList.size()) {
           //     entry.getValue().setNextStation(valueList.get(0));
            } else {
             //   entry.getValue().setNextStation(valueList.get(id + 1));
            }

            entry.getValue().setStationName(name);
            id++;
        }
    }

    public Map<String, MetroInputPoint> getValues() {
        return values;
    }
}
