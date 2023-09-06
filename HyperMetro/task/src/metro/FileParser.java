package metro;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileParser {

    public static Map<String, MetroLine> parseFile(String filePath) {
        Map<String, MetroLine> metroLines = new HashMap<>();

        try (FileReader reader = new FileReader(filePath)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            if (jsonElement == null || jsonElement.isJsonNull()) {
                throw new Exception("JSON data is null.");
            }

            JsonObject jsonObject = jsonElement.getAsJsonObject();

            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                MetroLine metroLine = new MetroLine();
                JsonObject stations = entry.getValue().getAsJsonObject();

                for (Map.Entry<String, JsonElement> station : stations.entrySet()) {
                    JsonObject stationData = station.getValue().getAsJsonObject();
                    JsonElement nameElement = stationData.get("name");
                    JsonElement timeElement = stationData.get("time");

                    if (nameElement == null || nameElement.isJsonNull()) {
                        throw new Exception("Missing name data.");
                    }

                    String stationName = nameElement.getAsString();
                    int time = (timeElement == null || timeElement.isJsonNull()) ? 0 : timeElement.getAsInt();  // default to 0 if time is null

                    metroLine.addStation(stationName, time);

                    // Handling transfers
                    JsonArray transfers = stationData.get("transfer").getAsJsonArray();
                    List<Transfer> transferList = new ArrayList<>();
                    for (JsonElement transferElement : transfers) {
                        JsonObject transferObject = transferElement.getAsJsonObject();
                        String transferLine = transferObject.get("line").getAsString();
                        String transferStation = transferObject.get("station").getAsString();
                        transferList.add(new Transfer(transferLine, transferStation));
                    }
                    metroLine.setTransfersForStation(stationName, transferList);
                }
                metroLines.put(entry.getKey(), metroLine);
            }

        } catch (IOException e) {
            System.out.println("Error! Such a file doesn't exist!");
            return null;
        } catch (Exception e) {
            System.out.println("Incorrect file: " + e.getMessage());
            return null;
        }

        return metroLines;
    }
}