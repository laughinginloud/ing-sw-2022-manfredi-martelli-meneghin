package it.polimi.ingsw.common.json.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.DiningRoom;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for a dining room
 * @author Mattia Martelli
 */
public class DiningRoomJSONAdapter extends TypeAdapter<DiningRoom> {
    @Override
    public void write(JsonWriter jsonWriter, DiningRoom diningRoom) throws IOException {
        try {
            Field studentCountersField = diningRoom.getClass().getDeclaredField("studentCounters");
            studentCountersField.setAccessible(true);

            int[] studentCounters = (int[]) studentCountersField.get(diningRoom);

            jsonWriter.beginObject();

            jsonWriter.name("studentCounters");
            jsonWriter.beginArray();
            for (int studentCounter : studentCounters)
                jsonWriter.value(studentCounter);
            jsonWriter.endArray();

            jsonWriter.endObject();

            studentCountersField.setAccessible(false);
        }

        // The field exists so it should never happen
        catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DiningRoom read(JsonReader jsonReader) throws IOException {
        try {
            DiningRoom diningRoom = new DiningRoom();
            Field studentCountersField = diningRoom.getClass().getDeclaredField("studentCounters");
            studentCountersField.setAccessible(true);

            List<Integer> studentCounters = new ArrayList<>();

            String fieldName = null;

            jsonReader.beginObject();

            while (jsonReader.hasNext()) {
                JsonToken jsonToken = jsonReader.peek();

                if (jsonToken == JsonToken.NAME)
                    fieldName = jsonReader.nextName();

                if (fieldName != null) {
                    jsonReader.beginArray();

                    while (jsonReader.hasNext())
                        studentCounters.add(jsonReader.nextInt());

                    jsonReader.endArray();
                }

                jsonReader.peek();
            }

            jsonReader.endObject();

            studentCountersField.set(diningRoom, studentCounters.stream().mapToInt(Integer::intValue).toArray());
            studentCountersField.setAccessible(false);

            return diningRoom;
        }

        // The field exists so it should never happen
        catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
