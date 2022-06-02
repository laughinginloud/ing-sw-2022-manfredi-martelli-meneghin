package it.polimi.ingsw.common.json.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.GameActions;

import java.io.IOException;

/**
 * Adapter for a game action
 * @author Mattia Martelli
 */
public class GameActionsJSONAdapter extends TypeAdapter<GameActions> {
    @Override
    public void write(JsonWriter jsonWriter, GameActions gameActions) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("action");
        jsonWriter.value(gameActions.name());

        jsonWriter.endObject();
    }

    @Override
    public GameActions read(JsonReader jsonReader) throws IOException {
        GameActions actions = null;

        String fieldName = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken jsonToken = jsonReader.peek();

            if (jsonToken == JsonToken.NAME)
                fieldName = jsonReader.nextName();

            if (fieldName != null)
                actions = GameActions.valueOf(jsonReader.nextString());

            jsonReader.peek();
        }

        jsonReader.endObject();

        return actions;
    }
}
