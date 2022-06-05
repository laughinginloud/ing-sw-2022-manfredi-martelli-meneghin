package it.polimi.ingsw.common.json.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.viewRecord.UsernameAndMagicAge;

import java.io.IOException;

/**
 * Adapter for a username and magic age
 * @author Mattia Martelli
 */
public class UsernameAndMagicAgeJSONAdapter extends TypeAdapter<UsernameAndMagicAge> {
    @Override
    public void write(JsonWriter jsonWriter, UsernameAndMagicAge usernameAndMagicAge) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("username");
        jsonWriter.value(usernameAndMagicAge.username());

        jsonWriter.name("magicAge");
        jsonWriter.value(usernameAndMagicAge.magicAge());

        jsonWriter.endObject();
    }

    @Override
    public UsernameAndMagicAge read(JsonReader jsonReader) throws IOException {
        String username = null;
        int magicAge = 0;

        String fieldName = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken jsonToken = jsonReader.peek();

            if (jsonToken == JsonToken.NAME)
                fieldName = jsonReader.nextName();

            if (fieldName != null)
                switch (fieldName) {
                    case "username" -> username = jsonReader.nextString();
                    case "magicAge" -> magicAge = jsonReader.nextInt();
                }

            jsonReader.peek();
        }

        jsonReader.endObject();

        return new UsernameAndMagicAge(username, magicAge);
    }
}
