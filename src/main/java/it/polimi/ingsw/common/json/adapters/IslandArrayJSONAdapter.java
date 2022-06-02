package it.polimi.ingsw.common.json.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.Color;
import it.polimi.ingsw.common.model.Island;
import it.polimi.ingsw.common.model.TowerColor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for an array of islands
 * @author Mattia Martelli
 */
public class IslandArrayJSONAdapter extends TypeAdapter<Island[]> {
    private static final Gson json = new GsonBuilder().registerTypeAdapter(TowerColor.class, new TowerColorJSONAdapter()).setPrettyPrinting().create();

    @Override
    public void write(JsonWriter jsonWriter, Island[] islands) throws IOException {
        try {
            jsonWriter.beginObject();

            jsonWriter.name("islands");
            jsonWriter.beginArray();

            for (Island island : islands) {
                jsonWriter.beginObject();

                Field studentCountersField = island.getClass().getDeclaredField("studentCounters");
                studentCountersField.setAccessible(true);
                int[] studentCounters = (int[]) studentCountersField.get(island);
                studentCountersField.setAccessible(false);
                jsonWriter.name("studentCounters");
                jsonWriter.value(json.toJson(studentCounters));

                jsonWriter.name("multiplicity");
                jsonWriter.value(island.getMultiplicity());

                jsonWriter.name("towerColor");
                jsonWriter.value(island.getTowerColor() == null ? "none" : json.toJson(island.getTowerColor()));

                jsonWriter.name("noEntryTileCount");
                jsonWriter.value(island.getNoEntryTileCount() == null ? "none" : island.getNoEntryTileCount().toString());

                jsonWriter.name("backgroundID");
                jsonWriter.value(island.getBackgroundID());

                jsonWriter.endObject();
            }

            jsonWriter.endArray();
            jsonWriter.endObject();
        }

        catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Island[] read(JsonReader jsonReader) throws IOException {
        List<Island> islandList = new ArrayList<>();

        jsonReader.beginObject();
        jsonReader.nextName();
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            Island island = new Island();

            String fieldName = null;

            jsonReader.beginObject();

            while (jsonReader.hasNext()) {
                JsonToken jsonToken = jsonReader.peek();

                if (jsonToken == JsonToken.NAME)
                    fieldName = jsonReader.nextName();

                if (fieldName != null)
                    switch (fieldName) {
                        case "studentCounters" -> {
                            int[] studentCounters = json.fromJson(jsonReader.nextString(), int[].class);
                            for (Color color : Color.values())
                                island.setStudentCounters(color, studentCounters[color.ordinal()]);
                        }
                        case "multiplicity" -> island.setMultiplicity(jsonReader.nextInt());
                        case "towerColor" -> {
                            String tc = jsonReader.nextString();
                            if (!tc.equals("none"))
                                island.setTowerColor(json.fromJson(jsonReader.nextString(), TowerColor.class));
                        }
                        case "noEntryTileCount" -> {
                            String count = jsonReader.nextString();
                            if (!count.equals("none"))
                                island.setNoEntryTileCount(Integer.parseInt(count));
                        }
                        case "backgroundID" -> island.setBackgroundID(jsonReader.nextInt());
                    }

                jsonReader.peek();
            }

            jsonReader.endObject();

            islandList.add(island);
        }
        jsonReader.endArray();
        jsonReader.endObject();

        return islandList.toArray(Island[]::new);
    }
}
