package metro;

import java.util.List;

public class MetroLine {
    private StationNode head;
    private StationNode tail;

    public void addStation(String name, int time) {
        StationNode newNode = new StationNode(name, time);

        if (head == null) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
    }

    public void setTransfersForStation(String name, List<Transfer> transfers) {
        StationNode current = head;

        while (current != null) {
            if (current.stationName.equals(name)) {
                current.setTransfers(transfers);
                return;
            }
            current = current.next;
        }
    }

    public void addHead(String name, int time) {
        StationNode newNode = new StationNode(name, time);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
    }

    public void removeStation(String name) {
        StationNode current = head;

        while (current != null) {
            if (current.stationName.equals(name)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next;
                }

                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev;
                }

                return;
            }
            current = current.next;
        }
    }

    public StationNode getHead() {
        return head;
    }

    public StationNode searchStation(String station1) {
        StationNode current = head;
        while (current != null) {
            if (current.stationName.equals(station1)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }
}
