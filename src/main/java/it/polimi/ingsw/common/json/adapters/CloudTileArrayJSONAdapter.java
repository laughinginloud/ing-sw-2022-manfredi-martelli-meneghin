package it.polimi.ingsw.common.json.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.CloudTile;
import it.polimi.ingsw.common.model.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for an array of cloud tiles
 * @author Mattia Martelli
 */
public class CloudTileArrayJSONAdapter extends TypeAdapter<CloudTile[]> {
    private static final Gson json = new GsonBuilder().registerTypeAdapter(Color[].class, new ColorArrayJSONAdapter()).setPrettyPrinting().create();

    @Override
    public void write(JsonWriter jsonWriter, CloudTile[] cloudTiles) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("cloudTiles");
        jsonWriter.beginArray();
        for (CloudTile cloudTile : cloudTiles)
            jsonWriter.value(json.toJson(cloudTile.getStudents()));
        jsonWriter.endArray();

        jsonWriter.endObject();
    }

    @Override
    public CloudTile[] read(JsonReader jsonReader) throws IOException {
        List<Color[]> cloudTileList = new ArrayList<>();

        jsonReader.beginObject();
        jsonReader.nextName();
        jsonReader.beginArray();
        while (jsonReader.hasNext())
            cloudTileList.add(json.fromJson(jsonReader.nextString(), Color[].class));
        jsonReader.endArray();
        jsonReader.endObject();

        return cloudTileList.stream().map(s -> {
            CloudTile cloudTile = new CloudTile();
            cloudTile.setStudents(s);
            return cloudTile;
        }).toArray(CloudTile[]::new);
    }
}
