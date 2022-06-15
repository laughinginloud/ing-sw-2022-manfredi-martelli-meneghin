package it.polimi.ingsw.common.json.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.*;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Adapter for a single expert player
 * @author Mattia Martelli
 */
public class PlayerExpertJSONAdapter extends TypeAdapter<PlayerExpert> {
    private static final Gson json = new GsonBuilder()
        .registerTypeAdapter(AssistantCard.class, new AssistantCardJSONAdapter())
        .registerTypeAdapter(AssistantCard[].class, new AssistantCardArrayJSONAdapter())
        .registerTypeAdapter(Wizard.class, new WizardJSONAdapter())
        .registerTypeAdapter(SchoolBoard.class, new SchoolBoardJSONAdapter())
        .setPrettyPrinting()
        .create();

    @Override
    public void write(JsonWriter jsonWriter, PlayerExpert playerExpert) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("assistantDeck");
        jsonWriter.value(json.toJson(playerExpert.getAssistantDeck()));

        jsonWriter.name("lastPlayedCard");
        jsonWriter.value(playerExpert.getLastPlayedCard() != null ? json.toJson(playerExpert.getLastPlayedCard()) : "void");

        jsonWriter.name("playerID");
        jsonWriter.value(playerExpert.getPlayerID());

        jsonWriter.name("playerWizard");
        jsonWriter.value(json.toJson(playerExpert.getPlayerWizard()));

        jsonWriter.name("schoolBoard");
        jsonWriter.value(json.toJson(playerExpert.getSchoolBoard()));

        jsonWriter.name("username");
        jsonWriter.value(playerExpert.getUsername());

        jsonWriter.name("coinCount");
        jsonWriter.value(playerExpert.getCoinCount());

        jsonWriter.endObject();
    }

    @Override
    public PlayerExpert read(JsonReader jsonReader) throws IOException {
        AssistantCard[] assistantDeck = null;
        AssistantCard lastPlayedCard = null;
        int playerID = 0;
        Wizard playerWizard = null;
        SchoolBoard schoolBoard = null;
        String username = null;
        int coinCount = 0;

        String fieldName = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken jsonToken = jsonReader.peek();

            if (jsonToken == JsonToken.NAME)
                fieldName = jsonReader.nextName();

            if (fieldName != null)
                switch (fieldName) {
                    case "assistantDeck"  -> assistantDeck  = json.fromJson(jsonReader.nextString(), AssistantCard[].class);
                    case "lastPlayedCard" -> {
                        String temp = jsonReader.nextString();

                        if (!temp.equals("void"))
                            lastPlayedCard = json.fromJson(temp, AssistantCard.class);
                    }
                    case "playerID"       -> playerID       = jsonReader.nextInt();
                    case "playerWizard"   -> playerWizard   = json.fromJson(jsonReader.nextString(), Wizard.class);
                    case "schoolBoard"    -> schoolBoard    = json.fromJson(jsonReader.nextString(), SchoolBoard.class);
                    case "username"       -> username       = jsonReader.nextString();
                    case "coinCount"      -> coinCount      = jsonReader.nextInt();
                }

            jsonReader.peek();
        }

        jsonReader.endObject();

        PlayerExpert player = new PlayerExpert(playerID, username, playerWizard, schoolBoard);
        setAssistantDeck(player, assistantDeck);
        if (lastPlayedCard != null)
            player.setLastPlayedCard(lastPlayedCard);
        player.setCoinCount(coinCount);

        return player;
    }

    private static void setAssistantDeck(PlayerExpert playerExpert, AssistantCard[] assistantDeck) {
        try {
            Field assistantDeckField = playerExpert.getClass().getSuperclass().getDeclaredField("assistantDeck");
            assistantDeckField.setAccessible(true);
            assistantDeckField.set(playerExpert, assistantDeck);
            assistantDeckField.setAccessible(false);
        }

        catch (NoSuchFieldException | IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }
}
