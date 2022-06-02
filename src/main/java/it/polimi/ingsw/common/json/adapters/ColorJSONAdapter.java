package it.polimi.ingsw.common.json.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.Color;

import java.io.IOException;

/**
 * Adapter for a single color
 * @author Mattia Martelli
 */
public class ColorJSONAdapter extends TypeAdapter<Color> {
    @Override
    public void write(JsonWriter jsonWriter, Color color) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("color");
        jsonWriter.value(color.name());

        jsonWriter.endObject();
    }

    @Override
    public Color read(JsonReader jsonReader) throws IOException {
        Color color = null;

        String fieldName = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken jsonToken = jsonReader.peek();

            if (jsonToken == JsonToken.NAME)
                fieldName = jsonReader.nextName();

            if (fieldName != null)
                color = Color.valueOf(jsonReader.nextString());

            jsonReader.peek();
        }

        jsonReader.endObject();

        return color;
    }
}
