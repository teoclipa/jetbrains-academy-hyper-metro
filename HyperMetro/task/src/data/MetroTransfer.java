package data;

public class MetroTransfer {
    private String line;
    private String station;

    public String getLine() {
        return line;
    }

    public String getStation() {
        return station;
    }

    @Override
    public String toString() {
        return "MetroTransfer{" +
                "line='" + line + '\'' +
                ", station='" + station + '\'' +
                '}';
    }

    public MetroTransfer(String line, String station) {
        this.line = line;
        this.station = station;
    }
}
