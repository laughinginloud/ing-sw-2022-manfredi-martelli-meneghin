package it.polimi.ingsw.common.json.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.message.Message;
import it.polimi.ingsw.common.message.MessageType;
import it.polimi.ingsw.common.model.AssistantCard;
import it.polimi.ingsw.common.model.Player;
import it.polimi.ingsw.common.utils.Tuple;

import java.io.IOException;

/**
 * Adapter for a message
 * @author Mattia Martelli
 */
public class MessageJSONAdapter extends TypeAdapter<Message> {
    private static final Gson json = new GsonBuilder()
        .registerTypeAdapter(Tuple.class, new TupleJSONAdapter())
        .registerTypeAdapter(AssistantCard[].class, new AssistantDeckJSONAdapter())
        .registerTypeAdapter(Player.class, new PlayerJSONAdapter())
        .setPrettyPrinting()
        .create();

    @Override
    public void write(JsonWriter jsonWriter, Message message) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("type");
        jsonWriter.value(message.type().toString());

        if (message.value() != null) {
            jsonWriter.name("value");
            jsonWriter.beginObject();

            jsonWriter.name("type");
            jsonWriter.value(message.value().getClass().getName());

            jsonWriter.name("value");
            jsonWriter.value(json.toJson(message.value()));

            jsonWriter.endObject();
        }

        else
            jsonWriter.nullValue();

        jsonWriter.endObject();
    }

    @Override
    public Message read(JsonReader jsonReader) throws IOException {
        MessageType type = null;
        Object value = null;
        String fieldName = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken jsonToken = jsonReader.peek();

            if (jsonToken.equals(JsonToken.NAME))
                fieldName = jsonReader.nextName();

            if (fieldName != null)
                switch (fieldName) {
                    case "type"  -> type  = MessageType.valueOf(jsonReader.nextString());
                    case "value" -> {
                        Class valueType = null;

                        String valueField = null;

                        jsonReader.beginObject();

                        while (jsonReader.hasNext()) {
                            JsonToken valueToken = jsonReader.peek();

                            if (valueToken == JsonToken.NAME)
                                valueField = jsonReader.nextName();

                            if (valueField != null)
                                switch (valueField) {
                                    case "type" -> {
                                        try {
                                            valueType = Class.forName(jsonReader.nextString());
                                        }

                                        // Should never happen, since the class is saved correctly
                                        catch (ClassNotFoundException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }

                                    case "value" -> value = json.fromJson(jsonReader.nextString(), valueType);
                                }

                            jsonReader.peek();
                        }

                        jsonReader.endObject();
                    }
                }

            jsonReader.peek();
        }

        jsonReader.endObject();

        return new Message(type, value);
    }
}
