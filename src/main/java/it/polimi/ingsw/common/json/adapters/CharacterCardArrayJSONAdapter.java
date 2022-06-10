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
    private static final Gson json = new GsonBuilder().registerTypeAdapter(CharacterCard.class, new CharacterCardJSONAdapter()).setPrettyPrinting().create();

    @Override
    public void write(JsonWriter jsonWriter, CharacterCard[] characterCards) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("characterCards");
        jsonWriter.beginArray();

        for (CharacterCard characterCard : characterCards)
            jsonWriter.value(json.toJson(characterCard));

        jsonWriter.endArray();
        jsonWriter.endObject();
    }

    @Override
    public CharacterCard[] read(JsonReader jsonReader) throws IOException {
        List<CharacterCard> characterCardList = new ArrayList<>();

        jsonReader.beginObject();
        jsonReader.nextName();
        jsonReader.beginArray();

        while (jsonReader.hasNext())
            characterCardList.add(json.fromJson(jsonReader.nextString(), CharacterCard.class));

        jsonReader.endArray();
        jsonReader.endObject();

        return characterCardList.toArray(CharacterCard[]::new);
    }
}
