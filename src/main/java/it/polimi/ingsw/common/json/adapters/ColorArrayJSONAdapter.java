package it.polimi.ingsw.common.json.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for an array of colors
 * @author Mattia Martelli
 */
public class ColorArrayJSONAdapter extends TypeAdapter<Color[]> {
    private static final Gson json = new GsonBuilder().registerTypeAdapter(Color.class, new ColorJSONAdapter()).setPrettyPrinting().create();

    @Override
    public void write(JsonWriter jsonWriter, Color[] colors) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("length");
        jsonWriter.value(colors.length);

        jsonWriter.name("colors");
        jsonWriter.beginArray();
        for (Color color : colors) {
            if (color == null)
                break;

            jsonWriter.value(json.toJson(color));
        }
        jsonWriter.endArray();

        jsonWriter.endObject();
    }

    @Override
    public Color[] read(JsonReader jsonReader) throws IOException {
        int length = 0;
        List<Color> colorList = new ArrayList<>();

        String fieldName = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken jsonToken = jsonReader.peek();

            if (jsonToken == JsonToken.NAME)
                fieldName = jsonReader.nextName();

            if (fieldName != null)
                switch (fieldName) {
                    case "length" -> length = jsonReader.nextInt();
                    case "colors" -> {
                        jsonReader.beginArray();
                        while (jsonReader.hasNext())
                            colorList.add(json.fromJson(jsonReader.nextString(), Color.class));
                        jsonReader.endArray();
                    }
                }

            jsonReader.peek();
        }

        jsonReader.endObject();

        Color[] colors = new Color[length];

        for (int i = 0; i < colorList.size(); ++i)
            colors[i] = colorList.get(i);

        for (int i = colorList.size(); i < length; ++i)
            colors[i] = null;

        return colors;
    }
}
