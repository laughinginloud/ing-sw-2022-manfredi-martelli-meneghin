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
 * Adapter for a single base character card
 * @author Mattia Martelli
 */
public class CharacterCardJSONAdapter extends TypeAdapter<CharacterCard> {
    @Override
    public void write(JsonWriter jsonWriter, CharacterCard characterCard) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("cost");
        jsonWriter.value(characterCard.getCost());

        jsonWriter.name("character");
        jsonWriter.value(characterCard.getCharacter().name());

        jsonWriter.name("hasCoin");
        jsonWriter.value(characterCard.getHasCoin());

        jsonWriter.endObject();
    }

    @Override
    public CharacterCard read(JsonReader jsonReader) throws IOException {
        int       cost      = 0;
        Character character = null;
        boolean   hasCoin   = false;

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
                }

            jsonReader.peek();
        }

        jsonReader.endObject();

        CharacterCard card = CharacterCard.build(character);
        card.setCost(cost);
        card.setHasCoin(hasCoin);

        return card;
    }
}
