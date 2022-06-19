package it.polimi.ingsw.common.json.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.GameValues;

import java.io.IOException;

/**
 * Adapter for a game value
 * @author Mattia Martelli
 */
public class GameValuesJSONAdapter extends TypeAdapter<GameValues> {
    @Override
    public void write(JsonWriter jsonWriter, GameValues gameValues) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("value");
        jsonWriter.value(gameValues.name());
        jsonWriter.endObject();
    }

    @Override
    public GameValues read(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        GameValues gameValues = GameValues.valueOf(jsonReader.nextString());
        jsonReader.endObject();

        return gameValues;
    }
}
