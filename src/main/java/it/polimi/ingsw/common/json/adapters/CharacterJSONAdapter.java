package it.polimi.ingsw.common.json.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.Character;

import java.io.IOException;

/**
 * Adapter for a character (enum)
 * @author Mattia Martelli
 */
public class CharacterJSONAdapter extends TypeAdapter<Character> {
    @Override
    public void write(JsonWriter jsonWriter, Character character) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("character");
        jsonWriter.value(character.name());

        jsonWriter.endObject();
    }

    @Override
    public Character read(JsonReader jsonReader) throws IOException {
        Character character;

        jsonReader.beginObject();

        jsonReader.nextName();
        character = Character.valueOf(jsonReader.nextString());
        jsonReader.peek();

        jsonReader.endObject();

        return character;
    }
}
