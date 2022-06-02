package it.polimi.ingsw.common.json.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.utils.Tuple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Adapter for a game model
 * @author Mattia Martelli
 */
public class GameModelJSONAdapter extends TypeAdapter<GameModel> {
    private static final Gson json = new GsonBuilder()
        .registerTypeAdapter(Player[].class, new PlayerArrayJSONAdapter())
        .registerTypeAdapter(Island[].class, new IslandArrayJSONAdapter())
        .registerTypeAdapter(CloudTile[].class, new CloudTileArrayJSONAdapter())
        .registerTypeAdapter(Bag.class, new BagJSONAdapter())
        .registerTypeAdapter(GlobalProfessorTable.class, new GlobalProfessorTableJSONAdapter())
        .registerTypeAdapter(CharacterCard[].class, new CharacterCardArrayJSONAdapter())
        .setPrettyPrinting()
        .create();

    @Override
    public void write(JsonWriter jsonWriter, GameModel gameModel) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("players");
        jsonWriter.value(json.toJson(gameModel.getPlayer()));

        jsonWriter.name("islands");
        jsonWriter.value(json.toJson(gameModel.getIslands()));

        jsonWriter.name("cloudTiles");
        jsonWriter.value(json.toJson(gameModel.getCloudTile()));

        jsonWriter.name("motherNaturePosition");
        jsonWriter.value(gameModel.getMotherNaturePosition());

        jsonWriter.name("bag");
        jsonWriter.value(json.toJson(gameModel.getBag()));

        jsonWriter.name("globalProfessorTable");
        jsonWriter.beginArray();
        GlobalProfessorTable gpt = gameModel.getGlobalProfessorTable();
        for (Color color : Color.values()){
            jsonWriter.beginObject();
            jsonWriter.name("color");
            jsonWriter.value(color.name());
            jsonWriter.name("playerID");
            jsonWriter.value(gpt.getProfessorLocation(color) == null ? "none" : String.valueOf(gpt.getProfessorLocation(color).getPlayerID()));
            jsonWriter.endObject();
        }
        jsonWriter.endArray();

        jsonWriter.name("expertMode");
        jsonWriter.value(gameModel.getExpertMode());

        if (gameModel.getExpertMode()) {
            jsonWriter.name("characterCards");
            jsonWriter.value(json.toJson(gameModel.getCharacterCards()));

            jsonWriter.name("coinPool");
            jsonWriter.value(gameModel.getCoinPool() == null ? "none" : gameModel.getCoinPool().toString());
        }

        jsonWriter.endObject();
    }

    @Override
    public GameModel read(JsonReader jsonReader) throws IOException {
        Player[] players = null;
        Island[] islands = null;
        CloudTile[] cloudTiles = null;
        int motherNaturePosition = 0;
        Bag bag = null;
        List<Tuple<Color, Integer>> gpt = new ArrayList<>();
        boolean expertMode = false;
        CharacterCard[] characterCards = null;
        Integer coinPool = null;

        String fieldName = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken jsonToken = jsonReader.peek();

            if (jsonToken == JsonToken.NAME)
                fieldName = jsonReader.nextName();

            if (fieldName != null)
                switch (fieldName) {
                    case "players" -> players = json.fromJson(jsonReader.nextString(), Player[].class);
                    case "islands" -> islands = json.fromJson(jsonReader.nextString(), Island[].class);
                    case "cloudTiles" -> cloudTiles = json.fromJson(jsonReader.nextString(), CloudTile[].class);
                    case "motherNaturePosition" -> motherNaturePosition = jsonReader.nextInt();
                    case "bag" -> bag = json.fromJson(jsonReader.nextString(), Bag.class);
                    case "globalProfessorTable" -> {
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            jsonReader.beginObject();
                            Color gptColor = null;
                            Integer gptPlayer = null;
                            String gptName = null;
                            while (jsonReader.hasNext()) {
                                JsonToken gptToken = jsonReader.peek();

                                if (gptToken == JsonToken.NAME)
                                    gptName = jsonReader.nextName();

                                if (gptName != null)
                                    switch (gptName) {
                                        case "color" -> gptColor = Color.valueOf(jsonReader.nextString());
                                        case "playerID" -> {
                                            String id = jsonReader.nextString();
                                            if (!id.equals("none"))
                                                gptPlayer = Integer.parseInt(id);
                                        }
                                    }

                                jsonReader.peek();
                            }
                            jsonReader.endObject();

                            gpt.add(new Tuple<>(gptColor, gptPlayer));
                        }
                        jsonReader.endArray();
                    }
                    case "expertMode" -> expertMode = jsonReader.nextBoolean();
                    case "characterCards" -> characterCards = json.fromJson(jsonReader.nextString(), CharacterCard[].class);
                    case "coinPool" -> {
                        String pool = jsonReader.nextString();
                        if (!pool.equals("none"))
                            coinPool = Integer.parseInt(pool);
                    }
                }

            jsonReader.peek();
        }

        jsonReader.endObject();

        GameModel gameModel = new GameModel(players.length, expertMode);

        GlobalProfessorTable globalProfessorTable = new GlobalProfessorTable();
        for (Tuple<Color, Integer> e : gpt) {
            if (e.right() == null)
                continue;

            Player ePlayer = Arrays.stream(players).reduce((p1, p2) -> p1.getPlayerID() == e.right() ? p1 : p2).get();
            globalProfessorTable.setProfessorLocation(e.left(), ePlayer);
        }

        gameModel.setPlayer(players);
        gameModel.setIsland(islands);
        gameModel.setCloudTile(cloudTiles);
        gameModel.setMotherNaturePosition(motherNaturePosition);
        gameModel.setBag(bag);
        gameModel.setGlobalProfessorTable(globalProfessorTable);
        if (expertMode) {
            gameModel.setCharacterCard(characterCards);
            gameModel.setCoinPool(coinPool);
        }

        return gameModel;
    }
}
