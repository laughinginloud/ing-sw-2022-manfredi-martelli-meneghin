package it.polimi.ingsw.common.json.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.model.DiningRoom;
import it.polimi.ingsw.common.model.Entrance;
import it.polimi.ingsw.common.utils.Tuple;

import java.io.IOException;

/**
 * Adapter for a generic tuple
 * @author Mattia Martelli
 */
public class TupleJSONAdapter extends TypeAdapter<Tuple> {
    private static final Gson json = new GsonBuilder()
        .registerTypeAdapter(GameActions.class, new GameActionsJSONAdapter())
        .registerTypeAdapter(DiningRoom.class, new DiningRoomJSONAdapter())
        .registerTypeAdapter(Entrance.class, new EntranceJSONAdapter())
        .setPrettyPrinting().create();

    @Override
    public void write(JsonWriter jsonWriter, Tuple tuple) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("left");
        jsonWriter.beginObject();
        jsonWriter.name("type");
        jsonWriter.value(tuple.left().getClass().getName());
        jsonWriter.name("value");
        jsonWriter.value(json.toJson(tuple.left()));
        jsonWriter.endObject();

        jsonWriter.name("right");
        jsonWriter.beginObject();
        jsonWriter.name("type");
        jsonWriter.value(tuple.right().getClass().getName());
        jsonWriter.name("value");
        jsonWriter.value(json.toJson(tuple.right()));
        jsonWriter.endObject();

        jsonWriter.endObject();
    }

    @Override
    public Tuple read(JsonReader jsonReader) throws IOException {
        Object left  = null;
        Object right = null;

        String fieldName = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken jsonToken = jsonReader.peek();

            if (jsonToken.equals(JsonToken.NAME))
                fieldName = jsonReader.nextName();

            if (fieldName != null) {
                String sideField = null;
                Class  type      = null;
                Object value     = null;

                jsonReader.beginObject();

                while (jsonReader.hasNext()) {
                    JsonToken sideToken = jsonReader.peek();

                    if (jsonToken.equals(JsonToken.NAME))
                        sideField = jsonReader.nextName();

                    if (sideField != null)
                        switch (sideField) {
                            case "type" -> {
                                try {
                                    type = Class.forName(jsonReader.nextString());
                                }

                                // Should never happen, since the class is saved correctly
                                catch (ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            case "value" -> value = json.fromJson(jsonReader.nextString(), type);
                        }

                    jsonReader.peek();
                }

                jsonReader.endObject();

                switch (fieldName) {
                    case "left"  -> left  = value;
                    case "right" -> right = value;
                }
            }
        }

        jsonReader.endObject();

        return new Tuple<>(left, right);
    }
}
