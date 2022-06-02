package it.polimi.ingsw.common.json.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for an array of players
 * @author Mattia Martelli
 */
public class PlayerArrayJSONAdapter extends TypeAdapter<Player[]> {
    private static final Gson json = new GsonBuilder().registerTypeAdapter(Player.class, new PlayerJSONAdapter()).setPrettyPrinting().create();

    @Override
    public void write(JsonWriter jsonWriter, Player[] players) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("players");
        jsonWriter.beginArray();
        for (Player player : players)
            jsonWriter.value(json.toJson(player));
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
            playerList.add(json.fromJson(jsonReader.nextString(), Player.class));
        }
        jsonReader.endArray();
        jsonReader.endObject();

        return playerList.toArray(Player[]::new);
    }
}
