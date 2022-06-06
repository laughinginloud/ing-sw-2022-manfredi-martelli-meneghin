package it.polimi.ingsw.common.json.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.viewRecord.GameRules;

import java.io.IOException;

/**
 * Adapter for an instance of game rules
 * @author Mattia Martelli
 */
public class GameRulesJSONAdapter extends TypeAdapter<GameRules> {
    @Override
    public void write(JsonWriter jsonWriter, GameRules gameRules) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("numOfPlayers");
        jsonWriter.value(gameRules.numOfPlayers());

        jsonWriter.name("expertMode");
        jsonWriter.value(gameRules.expertMode());

        jsonWriter.endObject();
    }

    @Override
    public GameRules read(JsonReader jsonReader) throws IOException {
        int numOfPlayers = 0;
        boolean expertMode = false;

        String fieldName = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken jsonToken = jsonReader.peek();

            if (jsonToken == JsonToken.NAME)
                fieldName = jsonReader.nextName();

            if (fieldName != null)
                switch (fieldName) {
                    case "numOfPlayers" -> numOfPlayers = jsonReader.nextInt();
                    case "expertMode"   -> expertMode   = jsonReader.nextBoolean();
                }

            jsonReader.peek();
        }

        jsonReader.endObject();

        return new GameRules(numOfPlayers, expertMode);
    }
}
