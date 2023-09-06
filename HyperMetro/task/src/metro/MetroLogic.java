package metro;

import java.util.*;

public class MetroLogic {

    private final Map<String, MetroLine> metroLines;

    public MetroLogic(String filePath) {
        this.metroLines = FileParser.parseFile(filePath);
    }

    public void run() {
        if (metroLines == null) {
            return;
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String commandLine = scanner.nextLine();
            if (commandLine.equals("/exit")) {
                break;
            }
            List<String> splitCommand = splitInput(commandLine);

            if (splitCommand.size() < 2) {
                System.out.println("Invalid command");
                continue;
            }

            String operation = splitCommand.get(0);
            String lineName = splitCommand.get(1).replaceAll("\"", "");

            MetroLine metroLine = metroLines.get(lineName);
            if (metroLine == null) {
                System.out.println("Invalid command");
                continue;
            }

            switch (operation) {
                case "/output" -> printMetroLine(metroLine);
                case "/append" -> {
                    if (splitCommand.size() != 3) {
                        System.out.println("Invalid command");
                        continue;
                    }
                    String appendStationName = splitCommand.get(2).replaceAll("\"", "");
                    metroLine.addStation(appendStationName, 0);
                }
                case "/add-head" -> {
                    if (splitCommand.size() != 3) {
                        System.out.println("Invalid command");
                        continue;
                    }
                    String headStationName = splitCommand.get(2).replaceAll("\"", "");
                    metroLine.addHead(headStationName, 0);
                }
                case "/connect" -> {
                    if (splitCommand.size() != 5) {
                        System.out.println("Invalid command");
                        continue;
                    }
                    String line1 = splitCommand.get(1).replaceAll("\"", "");
                    String station1 = splitCommand.get(2).replaceAll("\"", "");
                    String line2 = splitCommand.get(3).replaceAll("\"", "");
                    String station2 = splitCommand.get(4).replaceAll("\"", "");

                    MetroLine metroLine1 = metroLines.get(line1);
                    MetroLine metroLine2 = metroLines.get(line2);

                    if (metroLine1 == null || metroLine2 == null) {
                        System.out.println("Invalid line names");
                        continue;
                    }

                    // Search for station nodes and connect them
                    StationNode stationNode1 = metroLine1.searchStation(station1);
                    StationNode stationNode2 = metroLine2.searchStation(station2);

                    if (stationNode1 == null || stationNode2 == null) {
                        System.out.println("Invalid station names");
                        continue;
                    }

                    stationNode1.addTransfer(line2, station2);
                    stationNode2.addTransfer(line1, station1);

                    // Then print the connected station name
                    System.out.println("Connected " + station1 + " with " + station2);
                }

                case "/route" -> {
                    if (splitCommand.size() != 5) {
                        System.out.println("Invalid command");
                        continue;
                    }
                    String startLine = splitCommand.get(1).replaceAll("\"", "");
                    String startStation = splitCommand.get(2).replaceAll("\"", "");
                    String endLine = splitCommand.get(3).replaceAll("\"", "");
                    String endStation = splitCommand.get(4).replaceAll("\"", "");

                    Route route = findShortestRoute(startLine, startStation, endLine, endStation);
                    printRoute(route);
                }

                case "/fastest-route" -> {
                    if (splitCommand.size() != 5) {
                        System.out.println("Invalid command");
                        continue;
                    }
                    String startLine = splitCommand.get(1).replaceAll("\"", "");
                    String startStation = splitCommand.get(2).replaceAll("\"", "");
                    String endLine = splitCommand.get(3).replaceAll("\"", "");
                    String endStation = splitCommand.get(4).replaceAll("\"", "");

                    Route route = findFastestRoute(startLine, startStation, endLine, endStation);
                    printFastestRoute(route);
                }

                case "/remove" -> {
                    if (splitCommand.size() != 3) {
                        System.out.println("Invalid command");
                        continue;
                    }
                    String removeStationName = splitCommand.get(2).replaceAll("\"", "");
                    metroLine.removeStation(removeStationName);
                }
                default -> System.out.println("Invalid command");
            }
        }
    }

    public Route findFastestRoute(String startLine, String startStation, String endLine, String endStation) {
        // A min-priority queue
        PriorityQueue<Route> queue = new PriorityQueue<>(Comparator.comparingInt(Route::getTotalTime));
        Set<String> visited = new HashSet<>();

        List<String> initialPath = new ArrayList<>();
        initialPath.add(startStation);
        Route initialRoute = new Route(startLine, startStation, initialPath, 0);
        queue.add(initialRoute);


        while (!queue.isEmpty()) {
            Route currentRoute = queue.poll();
            String currentLine = currentRoute.getLine();
            String currentStation = currentRoute.getStation();
            List<String> currentPath = currentRoute.getPath();
            int currentTime = currentRoute.getTotalTime();

            if (visited.contains(currentLine + "-" + currentStation)) continue;
            visited.add(currentLine + "-" + currentStation);

            if (currentLine.equals(endLine) && currentStation.equals(endStation)) {
                return new Route(endLine, endStation, currentPath, currentTime);
            }

            StationNode currentNode = metroLines.get(currentLine).searchStation(currentStation);
            if (currentNode.hasTransfers()) {
                for (Transfer transfer : currentNode.getTransfers()) {
                    List<String> newPath = new ArrayList<>(currentPath);
                    newPath.add("Transition to line " + transfer.getLineName());
                    int newTime = currentTime + 5; // Adding 5 mins for transfer
                    newPath.add(transfer.getStationName());
                    queue.add(new Route(transfer.getLineName(), transfer.getStationName(), newPath, newTime));
                }
            }

            if (currentNode.prev != null) {
                List<String> newPath = new ArrayList<>(currentPath);
                // Use the time from the previous node for moving to the current node
                int newTime = currentTime + currentNode.prev.getTime();
                newPath.add(currentNode.prev.stationName);
                queue.add(new Route(currentLine, currentNode.prev.stationName, newPath, newTime));
            }

            if (currentNode.next != null) {
                List<String> newPath = new ArrayList<>(currentPath);
                // Use the time from the current node for moving to the next node
                int newTime = currentTime + currentNode.getTime();
                newPath.add(currentNode.next.stationName);
                queue.add(new Route(currentLine, currentNode.next.stationName, newPath, newTime));
            }
        }
        return null;
    }


    public Route findShortestRoute(String startLine, String startStation, String endLine, String endStation) {
        Queue<Route> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        List<String> initialPath = new ArrayList<>();
        initialPath.add(startStation);
        Route initialRoute = new Route(startLine, startStation, initialPath, 0);
        queue.add(initialRoute);

        while (!queue.isEmpty()) {
            Route currentRoute = queue.poll();
            String currentLine = currentRoute.getLine();
            String currentStation = currentRoute.getStation();
            List<String> currentPath = currentRoute.getPath();

            if (visited.contains(currentLine + "-" + currentStation)) continue;
            visited.add(currentLine + "-" + currentStation);

            if (currentLine.equals(endLine) && currentStation.equals(endStation)) {
                return new Route(endLine, endStation, currentPath, 0);
            }

            StationNode currentNode = metroLines.get(currentLine).searchStation(currentStation);

            if (currentNode.hasTransfers()) {
                for (Transfer transfer : currentNode.getTransfers()) {
                    List<String> newPath = new ArrayList<>(currentPath);
                    newPath.add("Transition to line " + transfer.getLineName());
                    newPath.add(transfer.getStationName());
                    queue.add(new Route(transfer.getLineName(), transfer.getStationName(), newPath, 0));
                }
            }

            if (currentNode.prev != null) {
                List<String> newPath = new ArrayList<>(currentPath);
                newPath.add(currentNode.prev.stationName);
                queue.add(new Route(currentLine, currentNode.prev.stationName, newPath, 0));
            }

            if (currentNode.next != null) {
                List<String> newPath = new ArrayList<>(currentPath);
                newPath.add(currentNode.next.stationName);
                queue.add(new Route(currentLine, currentNode.next.stationName, newPath, 0));
            }
        }

        return null;
    }

    public void printRoute(Route route) {
        if (route == null) {
            System.out.println("No route found.");
            return;
        }

        for (String station : route.getPath()) {
            System.out.println(station);
        }
    }

    private void printFastestRoute(Route route) {
        if (route == null) {
            System.out.println("No route found.");
            return;
        }

        for (String station : route.getPath()) {
            System.out.println(station);
        }
        System.out.println("Total: " + route.getTotalTime() + " minutes in the way");
    }


    public static List<String> splitInput(String input) {
        List<String> output = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean insideQuotes = false;

        for (char c : input.toCharArray()) {
            if (c == '"') {
                insideQuotes = !insideQuotes;
            }

            if (c == ' ' && !insideQuotes) {
                output.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }

        if (!current.isEmpty()) {
            output.add(current.toString());
        }

        return output;
    }

    private void printMetroLine(MetroLine metroLine) {
        if (metroLine == null || metroLine.getHead() == null) {
            return;
        }

        StationNode current = metroLine.getHead();
        System.out.println("depot");

        // Print the lines starting from the first station
        while (current != null) {
            System.out.print(current.stationName);
            if (current.hasTransfers()) {
                for (Transfer transfer : current.getTransfers()) {
                    System.out.print(" - " + transfer.getStationName() + " (" + transfer.getLineName() + " line)");
                }
            }
            System.out.println();
            current = current.next;
        }
        System.out.println("depot");
    }


}
