package parsing;

import data.MetroInputPoint;
import data.MetroStation;
import data.MetroStationList;
import data.MetroTransfer;

import java.util.*;

public class MapToClassParser {
    private MetroInputPoint createPoint(Object entry, String stationName) {
        HashMap<String, Object> baseData = (HashMap<String, Object>) entry;
        HashMap<String, List<Object>> pointData = (HashMap<String, List<Object>>) entry;
        MetroInputPoint point;

        List<String> next = (List<String>) (List<?>) pointData.get("next");
        List<String> prev = (List<String>) (List<?>) pointData.get("prev");

        List<LinkedHashMap<Object, Object>> transferRaw = (List<LinkedHashMap<Object, Object>>) (List<?>) pointData.get("transfer");
        List<MetroTransfer> transfers = new ArrayList<>();
        for (LinkedHashMap<Object, Object> transfer : transferRaw) {
            transfers.add(new MetroTransfer((String) transfer.get("line"), (String) transfer.get("station")));
        }
        int time = 0;
        if (baseData.get("time") != null) {
            time = (Integer) baseData.get("time");
        }

        point = new MetroInputPoint((String) baseData.get("name"), next, prev,
                transfers, time, stationName);

        return point;
    }

    private MetroStation createStation(Object entry, String name) {
        MetroStation station = new MetroStation(name);

        List<Object> entries = (ArrayList<Object>) entry;
        for (Object point : entries) {
            MetroInputPoint inputPoint = createPoint(point, name);
            station.addStation(inputPoint.getName(), inputPoint);
        }
    /*    for (Map.Entry<String, Object> point : entries.entrySet()) {
           station.addStation(point.getKey(), createPoint(point.getValue(), point.getKey()));
        }

        station.initNext();
*/
        return station;
    }

    public MetroStationList parse(Map<String, Object> map) {
        MetroStationList list = new MetroStationList();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            list.addStation(entry.getKey(), createStation(entry.getValue(), entry.getKey()));
        }

        return list;
    }
}
