package metro;

public class Transfer {
    private final String lineName;
    private final String stationName;

    public Transfer(String line, String station) {
        this.lineName = line;
        this.stationName = station;
    }

    public String getLineName() {
        return lineName;
    }

    public String getStationName() {
        return stationName;
    }

}
