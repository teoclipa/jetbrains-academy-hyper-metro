package metro;

import java.util.ArrayList;
import java.util.List;

public class StationNode {
    public String stationName;
    public StationNode next;
    public StationNode prev;
    private final int time;
    private List<Transfer> transfers;

    public StationNode(String name, int time) {
        this.stationName = name;
        this.next = null;
        this.prev = null;
        this.time = time;
    }

    public int getTime() {
        return time;
    }
    public void setTransfers(List<Transfer> transfers) {
        this.transfers = transfers;
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public boolean hasTransfers() {
        return transfers != null && !transfers.isEmpty();
    }

    public void addTransfer(String lineName, String stationName) {
        if (transfers == null) {
            transfers = new ArrayList<>();
        }
        transfers.add(new Transfer(lineName, stationName));
    }
}
