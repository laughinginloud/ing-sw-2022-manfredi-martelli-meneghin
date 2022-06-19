package it.polimi.ingsw.common.json.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.AssistantCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for an array of assistant cards
 * @author Mattia Martelli
 */
public class AssistantCardArrayJSONAdapter extends TypeAdapter<AssistantCard[]> {
    private static final Gson json = new GsonBuilder().registerTypeAdapter(AssistantCard.class, new AssistantCardJSONAdapter()).setPrettyPrinting().create();

    @Override
    public void write(JsonWriter jsonWriter, AssistantCard[] assistantCards) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("assistantDeck");

        jsonWriter.beginArray();
        for (AssistantCard assistantCard : assistantCards)
            jsonWriter.value(json.toJson(assistantCard));
        jsonWriter.endArray();

        jsonWriter.endObject();
    }

    @Override
    public AssistantCard[] read(JsonReader jsonReader) throws IOException {
        List<AssistantCard> assistantCardList = new ArrayList<>();

        String fieldName = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken jsonToken = jsonReader.peek();

            if (jsonToken.equals(JsonToken.NAME))
                fieldName = jsonReader.nextName();

            if (fieldName != null) {
                jsonReader.beginArray();
                while (jsonReader.hasNext())
                    assistantCardList.add(json.fromJson(jsonReader.nextString(), AssistantCard.class));
                jsonReader.endArray();
            }
        }

        jsonReader.endObject();

        return assistantCardList.toArray(AssistantCard[]::new);
    }
}
