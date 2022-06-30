package it.polimi.ingsw.common.json.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.model.Character;

import java.io.IOException;

/**
 * Adapter for a single no entry character card
 * @author Mattia Martelli
 */
public class CharacterCardNoEntryJSONAdapter extends TypeAdapter<CharacterCardNoEntry> {
    @Override
    @SuppressWarnings("DuplicatedCode")
    public void write(JsonWriter jsonWriter, CharacterCardNoEntry characterCardNoEntry) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("cost");
        jsonWriter.value(characterCardNoEntry.getCost());

        jsonWriter.name("character");
        jsonWriter.value(characterCardNoEntry.getCharacter().name());

        jsonWriter.name("hasCoin");
        jsonWriter.value(characterCardNoEntry.getHasCoin());

        jsonWriter.name("noEntryCount");
        jsonWriter.value(characterCardNoEntry.getNoEntryCount());

        jsonWriter.endObject();
    }

    @Override
    public CharacterCardNoEntry read(JsonReader jsonReader) throws IOException {
        int       cost         = 0;
        Character character    = null;
        boolean   hasCoin      = false;
        int       noEntryCount = 0;

        String fieldName = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken jsonToken = jsonReader.peek();

            if (jsonToken == JsonToken.NAME)
                fieldName = jsonReader.nextName();

            if (fieldName != null)
                switch (fieldName) {
                    case "cost"         -> cost         = jsonReader.nextInt();
                    case "character"    -> character    = Character.valueOf(jsonReader.nextString());
                    case "hasCoin"      -> hasCoin      = jsonReader.nextBoolean();
                    case "noEntryCount" -> noEntryCount = jsonReader.nextInt();
                }

            jsonReader.peek();
        }

        jsonReader.endObject();

        CharacterCardNoEntry card = (CharacterCardNoEntry) CharacterCard.build(character);
        card.setCost(cost);
        card.setHasCoin(hasCoin);
        card.setNoEntryCount(noEntryCount);

        return card;
    }
}
