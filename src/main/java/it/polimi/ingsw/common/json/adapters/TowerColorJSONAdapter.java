package it.polimi.ingsw.common.json.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.TowerColor;

import java.io.IOException;

/**
 * Adapter for a tower color
 * @author Mattia Martelli
 */
public class TowerColorJSONAdapter extends TypeAdapter<TowerColor> {
    @Override
    public void write(JsonWriter jsonWriter, TowerColor towerColor) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("towerColor");
        jsonWriter.value(towerColor.name());

        jsonWriter.endObject();
    }

    @Override
    public TowerColor read(JsonReader jsonReader) throws IOException {
        TowerColor towerColor;

        jsonReader.beginObject();
        jsonReader.nextName();
        towerColor = TowerColor.valueOf(jsonReader.nextString());
        jsonReader.peek();
        jsonReader.endObject();

        return towerColor;
    }
}
