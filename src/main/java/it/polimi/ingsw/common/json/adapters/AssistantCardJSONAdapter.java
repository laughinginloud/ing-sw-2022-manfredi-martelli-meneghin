package it.polimi.ingsw.common.json.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.AssistantCard;

import java.io.IOException;

/**
 * Adapter for a single assistant card
 * @author Mattia Martelli
 */
public class AssistantCardJSONAdapter extends TypeAdapter<AssistantCard> {
    @Override
    public void write(JsonWriter jsonWriter, AssistantCard assistantCard) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("cardValue");
        jsonWriter.value(assistantCard.cardValue());

        jsonWriter.name("movementPoints");
        jsonWriter.value(assistantCard.movementPoints());

        jsonWriter.endObject();
    }

    @Override
    public AssistantCard read(JsonReader jsonReader) throws IOException {
        int cardValue      = 0,
            movementPoints = 0;

        String fieldName = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken jsonToken = jsonReader.peek();

            if (jsonToken.equals(JsonToken.NAME))
                fieldName = jsonReader.nextName();

            if (fieldName != null)
                switch (fieldName) {
                    case "cardValue"      -> cardValue      = jsonReader.nextInt();
                    case "movementPoints" -> movementPoints = jsonReader.nextInt();
                }

            jsonReader.peek();
        }

        jsonReader.endObject();

        return new AssistantCard(cardValue, movementPoints);
    }
}
