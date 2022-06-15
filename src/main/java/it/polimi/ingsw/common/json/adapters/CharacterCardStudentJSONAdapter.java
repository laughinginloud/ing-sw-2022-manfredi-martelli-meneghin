package it.polimi.ingsw.common.json.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.model.Character;

import java.io.IOException;

/**
 * Adapter for a single student character card
 * @author Mattia Martelli
 */
public class CharacterCardStudentJSONAdapter extends TypeAdapter<CharacterCardStudent> {
    private static final Gson json = new GsonBuilder().registerTypeAdapter(Color[].class, new ColorArrayJSONAdapter()).setPrettyPrinting().create();

    @Override
    public void write(JsonWriter jsonWriter, CharacterCardStudent characterCardStudent) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("cost");
        jsonWriter.value(characterCardStudent.getCost());

        jsonWriter.name("character");
        jsonWriter.value(characterCardStudent.getCharacter().name());

        jsonWriter.name("hasCoin");
        jsonWriter.value(characterCardStudent.getHasCoin());

        jsonWriter.name("students");
        jsonWriter.value(json.toJson(characterCardStudent.getStudents()));

        jsonWriter.endObject();
    }

    @Override
    public CharacterCardStudent read(JsonReader jsonReader) throws IOException {
        int       cost      = 0;
        Character character = null;
        boolean   hasCoin   = false;
        Color[]   students  = null;

        String fieldName = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken jsonToken = jsonReader.peek();

            if (jsonToken == JsonToken.NAME)
                fieldName = jsonReader.nextName();

            if (fieldName != null)
                switch (fieldName) {
                    case "cost"      -> cost      = jsonReader.nextInt();
                    case "character" -> character = Character.valueOf(jsonReader.nextString());
                    case "hasCoin"   -> hasCoin   = jsonReader.nextBoolean();
                    case "students"  -> students  = json.fromJson(jsonReader.nextString(), Color[].class);
                }

            jsonReader.peek();
        }

        jsonReader.endObject();

        CharacterCardStudent card = (CharacterCardStudent) CharacterCard.build(character);
        card.setCost(cost);
        card.setHasCoin(hasCoin);

        for (int i = 0; i < students.length; ++i)
            card.setStudents(students[i], i);

        return card;
    }
}
