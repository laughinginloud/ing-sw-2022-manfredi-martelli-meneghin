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
    private static final Gson json = new GsonBuilder().registerTypeAdapter(Color[].class, new ColorArrayJSONAdapter()).setPrettyPrinting().create();

    @Override
    public void write(JsonWriter jsonWriter, CharacterCard[] characterCards) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("characterCards");
        jsonWriter.beginArray();

        for (CharacterCard characterCard : characterCards) {
            jsonWriter.beginObject();

            jsonWriter.name("type");
            jsonWriter.value(cardType(characterCard));

            jsonWriter.name("cost");
            jsonWriter.value(characterCard.getCost());

            jsonWriter.name("character");
            jsonWriter.value(characterCard.getCharacter().name());

            jsonWriter.name("hasCoin");
            jsonWriter.value(characterCard.getHasCoin());

            additionalInfo(jsonWriter, characterCard);

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
            CharacterCard card = null;
            String type = null;
            int cost = 0;
            Character character = null;
            boolean hasCoin = false;
            int noEntryCount = 0;
            Color[] students = null;

            String fieldName = null;

            jsonReader.beginObject();

            while (jsonReader.hasNext()) {
                JsonToken jsonToken = jsonReader.peek();

                if (jsonToken == JsonToken.NAME)
                    fieldName = jsonReader.nextName();

                if (fieldName != null)
                    switch (fieldName) {
                        case "type" -> type = jsonReader.nextString();
                        case "cost" -> cost = jsonReader.nextInt();
                        case "character" -> character = Character.valueOf(jsonReader.nextString());
                        case "hasCoin" -> hasCoin = jsonReader.nextBoolean();
                        case "noEntryCount" -> jsonReader.nextInt();
                        case "students" -> students = json.fromJson(jsonReader.nextString(), Color[].class);
                    }

                jsonReader.peek();
            }

            jsonReader.endObject();

            switch (type) {
                case "base" -> {
                    card = CharacterCard.build(character);
                    card.setCost(cost);
                    card.setHasCoin(hasCoin);
                }

                case "noEntry" -> {
                    CharacterCardNoEntry cardNoEntry = (CharacterCardNoEntry) CharacterCard.build(character);
                    cardNoEntry.setCost(cost);
                    cardNoEntry.setHasCoin(hasCoin);
                    cardNoEntry.setNoEntryCount(noEntryCount);

                    card = cardNoEntry;
                }

                case "student" -> {
                    CharacterCardStudent cardStudent = (CharacterCardStudent) CharacterCard.build(character);
                    cardStudent.setCost(cost);
                    cardStudent.setHasCoin(hasCoin);

                    for (int i = 0; i < students.length; ++i)
                        cardStudent.setStudents(students[i], i);

                    card = cardStudent;
                }
            }

            characterCardList.add(card);
        }

        jsonReader.endArray();
        jsonReader.endObject();

        return characterCardList.toArray(CharacterCard[]::new);
    }

    private static String cardType(CharacterCard characterCard) {
        if (characterCard instanceof CharacterCardNoEntry)
            return "noEntry";

        if (characterCard instanceof CharacterCardStudent)
            return "student";

        return "base";
    }

    private static void additionalInfo(JsonWriter jsonWriter, CharacterCard characterCard) throws IOException {
        if (characterCard instanceof CharacterCardNoEntry c) {
            jsonWriter.name("noEntryCount");
            jsonWriter.value(c.getNoEntryCount());
        }

        if (characterCard instanceof CharacterCardStudent c) {
            jsonWriter.name("students");
            jsonWriter.value(json.toJson(c.getStudents()));
        }
    }
}
