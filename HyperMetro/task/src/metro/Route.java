package metro;

import java.util.List;

public class Route {
    private final String line;
    private final String station;
    private final List<String> path;
    private final int totalTime;

    public Route(String line, String station, List<String> path, int totalTime) {
        this.line = line;
        this.station = station;
        this.path = path;
        this.totalTime = totalTime;
    }

    public String getLine() {
        return line;
    }

    public String getStation() {
        return station;
    }

    public List<String> getPath() {
        return path;
    }

    public int getTotalTime() {
        return totalTime;
    }
}
