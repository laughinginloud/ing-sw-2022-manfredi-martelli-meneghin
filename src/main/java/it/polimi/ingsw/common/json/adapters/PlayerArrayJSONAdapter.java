package it.polimi.ingsw.common.json.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.Player;
import it.polimi.ingsw.common.model.PlayerExpert;
import it.polimi.ingsw.common.model.PlayerTeam;
import it.polimi.ingsw.common.model.PlayerTeamExpert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for an array of players
 * @author Mattia Martelli
 */
public class PlayerArrayJSONAdapter extends TypeAdapter<Player[]> {
    private static final Gson json = new GsonBuilder()
        .registerTypeAdapter(Player.class, new PlayerJSONAdapter())
        .registerTypeAdapter(PlayerExpert.class, new PlayerExpertJSONAdapter())
        .registerTypeAdapter(PlayerTeam.class, new PlayerTeamJSONAdapter())
        .registerTypeAdapter(PlayerTeamExpert.class, new PlayerTeamExpertJSONAdapter())
        .setPrettyPrinting()
        .create();

    @Override
    public void write(JsonWriter jsonWriter, Player[] players) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("players");
        jsonWriter.beginArray();
        for (Player player : players) {
            jsonWriter.beginObject();

            jsonWriter.name("type");
            jsonWriter.value(playerType(player));

            jsonWriter.name("player");
            jsonWriter.value(json.toJson(player));

            jsonWriter.endObject();
        }
        jsonWriter.endArray();

        jsonWriter.endObject();
    }

    @Override
    public Player[] read(JsonReader jsonReader) throws IOException {
        List<Player> playerList = new ArrayList<>();

        jsonReader.beginObject();
        jsonReader.nextName();
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            String type      = null;
            String fieldName = null;

            jsonReader.beginObject();

            while (jsonReader.hasNext()) {
                JsonToken jsonToken = jsonReader.peek();

                if (jsonToken == JsonToken.NAME)
                    fieldName = jsonReader.nextName();

                if (fieldName != null)
                    switch (fieldName) {
                        case "type"   -> type = jsonReader.nextString();
                        case "player" -> playerList.add((Player) json.fromJson(jsonReader.nextString(), playerType(type)));
                    }

                jsonReader.peek();
            }

            jsonReader.endObject();
        }
        jsonReader.endArray();
        jsonReader.endObject();

        return playerList.toArray(Player[]::new);
    }

    private static String playerType(Player player) {
        if (player instanceof PlayerTeam)
            return "team";

        if (player instanceof PlayerExpert)
            return "expert";

        if (player instanceof PlayerTeamExpert)
            return "teamExpert";

        return "base";
    }

    private static Class playerType(String type) {
        return switch (type) {
            case "base"       -> Player.class;
            case "team"       -> PlayerTeam.class;
            case "expert"     -> PlayerExpert.class;
            case "teamExpert" -> PlayerTeamExpert.class;
            default           -> throw new AssertionError();
        };
    }
}
