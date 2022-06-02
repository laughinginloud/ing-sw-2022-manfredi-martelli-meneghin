package it.polimi.ingsw.common.json.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.Color;
import it.polimi.ingsw.common.model.Entrance;

import java.io.IOException;

/**
 * Adapter for an entrance
 * @author Mattia Martelli
 */
public class EntranceJSONAdapter extends TypeAdapter<Entrance> {
    private static final Gson json = new GsonBuilder().registerTypeAdapter(Color[].class, new ColorArrayJSONAdapter()).setPrettyPrinting().create();

    @Override
    public void write(JsonWriter jsonWriter, Entrance entrance) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("students");
        jsonWriter.value(json.toJson(entrance.getStudents()));

        jsonWriter.endObject();
    }

    @Override
    public Entrance read(JsonReader jsonReader) throws IOException {
        Color[] students = null;

        String fieldName = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken jsonToken = jsonReader.peek();

            if (jsonToken == JsonToken.NAME)
                fieldName = jsonReader.nextName();

            if (fieldName != null)
                students = json.fromJson(jsonReader.nextString(), Color[].class);

            jsonReader.peek();
        }

        jsonReader.endObject();

        Entrance entrance = new Entrance(students.length);
        entrance.setStudents(students);

        return entrance;
    }
}
