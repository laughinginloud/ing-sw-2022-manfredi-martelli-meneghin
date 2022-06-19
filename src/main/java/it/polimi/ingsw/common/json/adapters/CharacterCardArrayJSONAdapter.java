package it.polimi.ingsw.common.json.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.Character;
import it.polimi.ingsw.common.model.CharacterCard;
import it.polimi.ingsw.common.model.CharacterCardNoEntry;
import it.polimi.ingsw.common.model.CharacterCardStudent;
import it.polimi.ingsw.common.model.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for an array of character cards
 * @author Mattia Martelli
 */
public class CharacterCardArrayJSONAdapter extends TypeAdapter<CharacterCard[]> {
    private static final Gson json = new GsonBuilder()
        .registerTypeAdapter(CharacterCard.class,        new CharacterCardJSONAdapter())
        .registerTypeAdapter(CharacterCardNoEntry.class, new CharacterCardNoEntryJSONAdapter())
        .registerTypeAdapter(CharacterCardStudent.class, new CharacterCardStudentJSONAdapter())
        .setPrettyPrinting()
        .create();

    @Override
    public void write(JsonWriter jsonWriter, CharacterCard[] characterCards) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("characterCards");
        jsonWriter.beginArray();

        for (CharacterCard characterCard : characterCards) {
            jsonWriter.beginObject();

            jsonWriter.name("type");
            jsonWriter.value(characterCardType(characterCard));
            jsonWriter.name("card");
            jsonWriter.value(json.toJson(characterCard));

            jsonWriter.endObject();
        }

        jsonWriter.endArray();
        jsonWriter.endObject();
    }

    @Override
    public CharacterCard[] read(JsonReader jsonReader) throws IOException {
        List<CharacterCard> characterCardList = new ArrayList<>();

        jsonReader.beginObject();
        jsonReader.nextName();
        jsonReader.beginArray();

        while (jsonReader.hasNext()) {
            String type  = null;
            String field = null;

            jsonReader.beginObject();

            while (jsonReader.hasNext()) {
                JsonToken jsonToken = jsonReader.peek();

                if (jsonToken == JsonToken.NAME)
                    field = jsonReader.nextName();

                if (field != null)
                    switch (field) {
                        case "type" -> type = jsonReader.nextString();
                        case "card" -> characterCardList.add((CharacterCard) json.fromJson(jsonReader.nextString(), characterCardType(type)));
                    }

                jsonReader.peek();
            }

            jsonReader.endObject();
        }

        jsonReader.endArray();
        jsonReader.endObject();

        return characterCardList.toArray(CharacterCard[]::new);
    }

    private static String characterCardType(CharacterCard characterCard) {
        if (characterCard instanceof CharacterCardNoEntry)
            return "noEntry";

        if (characterCard instanceof CharacterCardStudent)
            return "student";

        return "base";
    }

    private static Class characterCardType(String type) {
        return switch (type) {
            case "base"    -> CharacterCard.class;
            case "noEntry" -> CharacterCardNoEntry.class;
            case "student" -> CharacterCardStudent.class;
            default        -> throw new AssertionError();
        };
    }
}
