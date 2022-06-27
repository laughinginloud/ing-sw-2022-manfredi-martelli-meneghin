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
 * Adapter for a single team player
 * @author Mattia Martelli
 */
public class PlayerTeamJSONAdapter extends TypeAdapter<PlayerTeam> {
    private static final Gson json = new GsonBuilder()
        .registerTypeAdapter(AssistantCard.class, new AssistantCardJSONAdapter())
        .registerTypeAdapter(AssistantCard[].class, new AssistantCardArrayJSONAdapter())
        .registerTypeAdapter(Wizard.class, new WizardJSONAdapter())
        .registerTypeAdapter(SchoolBoard.class, new SchoolBoardJSONAdapter())
        .setPrettyPrinting()
        .create();

    @Override
    public void write(JsonWriter jsonWriter, PlayerTeam playerTeam) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("assistantDeck");
        jsonWriter.value(json.toJson(playerTeam.getAssistantDeck()));

        jsonWriter.name("lastPlayedCard");
        jsonWriter.value(playerTeam.getLastPlayedCard() != null ? json.toJson(playerTeam.getLastPlayedCard()) : "void");

        jsonWriter.name("playerID");
        jsonWriter.value(playerTeam.getPlayerID());

        jsonWriter.name("playerWizard");
        jsonWriter.value(json.toJson(playerTeam.getPlayerWizard()));

        jsonWriter.name("schoolBoard");
        jsonWriter.value(json.toJson(playerTeam.getSchoolBoard()));

        jsonWriter.name("username");
        jsonWriter.value(playerTeam.getUsername());

        jsonWriter.name("teamMember");
        jsonWriter.value(playerTeam.getTeamMemberID());

        jsonWriter.endObject();
    }

    @Override
    public PlayerTeam read(JsonReader jsonReader) throws IOException {
        AssistantCard[] assistantDeck = null;
        AssistantCard lastPlayedCard = null;
        int playerID = 0;
        Wizard playerWizard = null;
        SchoolBoard schoolBoard = null;
        String username = null;
        int teamMember = 0;

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
                    case "teamMember"     -> teamMember     = jsonReader.nextInt();
                }

            jsonReader.peek();
        }

        jsonReader.endObject();

        PlayerTeam player = new PlayerTeam(playerID, username, playerWizard, schoolBoard);
        setAssistantDeck(player, assistantDeck);
        if (lastPlayedCard != null)
            player.setLastPlayedCard(lastPlayedCard);
        player.setTeamMemberID(teamMember);

        return player;
    }

    private static void setAssistantDeck(Player player, AssistantCard[] assistantDeck) {
        try {
            Field assistantDeckField = player.getClass().getDeclaredField("assistantDeck");
            assistantDeckField.setAccessible(true);
            assistantDeckField.set(player, assistantDeck);
            assistantDeckField.setAccessible(false);
        }

        catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
