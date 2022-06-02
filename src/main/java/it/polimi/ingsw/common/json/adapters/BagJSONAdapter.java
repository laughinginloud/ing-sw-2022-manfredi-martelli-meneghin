package it.polimi.ingsw.common.json.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.Bag;
import it.polimi.ingsw.common.model.Color;

import java.io.IOException;

/**
 * Adapter for a bag
 * @author Mattia Martelli
 */
public class BagJSONAdapter extends TypeAdapter<Bag> {
    @Override
    public void write(JsonWriter jsonWriter, Bag bag) throws IOException {
            int[] studentCounters = new int[Color.values().length];

            for (Color color : Color.values())
                studentCounters[color.ordinal()] = bag.getStudentCounters(color);

            jsonWriter.beginObject();

            jsonWriter.name("studentCounters");
            jsonWriter.beginArray();
            for (int e : studentCounters)
                jsonWriter.value(e);
            jsonWriter.endArray();

            jsonWriter.endObject();
    }

    @Override
    public Bag read(JsonReader jsonReader) throws IOException {
        int[] studentCounters = new int[Color.values().length];

        jsonReader.beginObject();
        jsonReader.nextName();
        jsonReader.beginArray();
        for (int i = 0; i < studentCounters.length; ++i)
            studentCounters[i] = jsonReader.nextInt();
        jsonReader.endArray();
        jsonReader.endObject();

        Bag bag = new Bag();

        for (Color color : Color.values())
            bag.setStudentCounters(color, studentCounters[color.ordinal()]);

        return bag;
    }
}
