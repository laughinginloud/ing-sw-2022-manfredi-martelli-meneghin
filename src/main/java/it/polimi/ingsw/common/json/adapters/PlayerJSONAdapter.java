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
 * Adapter for a single player
 * @author Mattia Martelli
 */
public class PlayerJSONAdapter extends TypeAdapter<Player> {
    private static final Gson json = new GsonBuilder()
        .registerTypeAdapter(AssistantCard.class, new AssistantCardJSONAdapter())
        .registerTypeAdapter(AssistantCard[].class, new AssistantCardArrayJSONAdapter())
        .registerTypeAdapter(Wizard.class, new WizardJSONAdapter())
        .registerTypeAdapter(SchoolBoard.class, new SchoolBoardJSONAdapter())
        .setPrettyPrinting()
        .create();

    @Override
    public void write(JsonWriter jsonWriter, Player player) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("type");
        jsonWriter.value(playerType(player));

        jsonWriter.name("assistantDeck");
        jsonWriter.value(json.toJson(player.getAssistantDeck()));

        jsonWriter.name("lastPlayedCard");
        jsonWriter.value(player.getLastPlayedCard() != null ? json.toJson(player.getLastPlayedCard()) : "void");

        jsonWriter.name("playerID");
        jsonWriter.value(player.getPlayerID());

        jsonWriter.name("playerWizard");
        jsonWriter.value(json.toJson(player.getPlayerWizard()));

        jsonWriter.name("schoolBoard");
        jsonWriter.value(json.toJson(player.getSchoolBoard()));

        jsonWriter.name("username");
        jsonWriter.value(player.getUsername());

        additionalInfo(jsonWriter, player);

        jsonWriter.endObject();
    }

    @Override
    public Player read(JsonReader jsonReader) throws IOException {
        AssistantCard[] assistantDeck = null;
        AssistantCard lastPlayedCard = null;
        int playerID = 0;
        Wizard playerWizard = null;
        SchoolBoard schoolBoard = null;
        String username = null;
        String type = null;
        int coinCount = 0;
        int teamMember = 0;

        String fieldName = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken jsonToken = jsonReader.peek();

            if (jsonToken == JsonToken.NAME)
                fieldName = jsonReader.nextName();

            if (fieldName != null)
                switch (fieldName) {
                    case "type"           -> type           = jsonReader.nextString();
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
                    case "teamMember"     -> teamMember     = jsonReader.nextInt();
                }

            jsonReader.peek();
        }

        jsonReader.endObject();

        Player player = null;

        switch (type) {
            case "player" -> {
                player = new Player(playerID, username, playerWizard, schoolBoard);
                setAssistantDeck(player, assistantDeck);
                if (lastPlayedCard != null)
                    player.setLastPlayedCard(lastPlayedCard);
            }

            case "playerExpert" -> {
                PlayerExpert playerE = new PlayerExpert(playerID, username, playerWizard, schoolBoard);
                setAssistantDeck(playerE, assistantDeck);
                if (lastPlayedCard != null)
                    player.setLastPlayedCard(lastPlayedCard);
                playerE.setCoinCount(coinCount);

                player = playerE;
            }

            case "playerTeam" -> {
                PlayerTeam playerT = new PlayerTeam(playerID, username, playerWizard, schoolBoard);
                setAssistantDeck(playerT, assistantDeck);
                if (lastPlayedCard != null)
                    player.setLastPlayedCard(lastPlayedCard);
                playerT.setTeamMember(teamMember);

                player = playerT;
            }

            case "playerTeamExpert" -> {
                PlayerTeamExpert playerTE = new PlayerTeamExpert(playerID, username, playerWizard, schoolBoard);
                setAssistantDeck(playerTE, assistantDeck);
                if (lastPlayedCard != null)
                    player.setLastPlayedCard(lastPlayedCard);
                playerTE.setCoinCount(coinCount);
                playerTE.setTeamMember(teamMember);

                player = playerTE;
            }
        }

        return player;
    }

    private static String playerType(Player player) {
        if (player instanceof PlayerExpert)
            return "playerExpert";

        if (player instanceof PlayerTeam)
            return "playerTeam";

        if (player instanceof PlayerTeamExpert)
            return "playerTeamExpert";

        return "player";
    }

    private static void additionalInfo(JsonWriter jsonWriter, Player player) throws IOException {
        if (player instanceof PlayerExpert p) {
            jsonWriter.name("coinCount");
            jsonWriter.value(p.getCoinCount());
        }

        if (player instanceof PlayerTeam p) {
            jsonWriter.name("teamMember");
            jsonWriter.value(p.getTeamMember());
        }

        if (player instanceof PlayerTeamExpert p) {
            jsonWriter.name("coinCount");
            jsonWriter.value(p.getCoinCount());

            jsonWriter.name("teamMember");
            jsonWriter.value(p.getTeamMember());
        }
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
