package it.polimi.ingsw.common.json.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.save.SaveData;
import it.polimi.ingsw.server.controller.state.*;

import java.io.IOException;

/**
 * Adapter for the SavaData record
 * @author Mattia Martelli
 */
public class SaveDataJSONAdapter extends TypeAdapter<SaveData> {
    private static final Gson json = new GsonBuilder().registerTypeAdapter(ControllerData.class, new ControllerDataJSONAdapter()).setPrettyPrinting().create();

    @Override
    public void write(JsonWriter jsonWriter, SaveData saveData) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("data");
        jsonWriter.value(json.toJson(saveData.controllerData()));

        jsonWriter.name("state");
        jsonWriter.value(serializeState(saveData.gameState()));

        jsonWriter.endObject();
    }

    @Override
    public SaveData read(JsonReader jsonReader) throws IOException {
        ControllerData controllerData = null;
        GameState      gameState      = null;

        String fieldName = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken jsonToken = jsonReader.peek();

            if (jsonToken == JsonToken.NAME)
                fieldName = jsonReader.nextName();

            if (fieldName != null)
                switch (fieldName) {
                    case "data"  -> controllerData = json.fromJson(jsonReader.nextString(), ControllerData.class);
                    case "state" -> gameState      = deserializeState(jsonReader.nextString());
                }

            jsonReader.peek();
        }

        jsonReader.endObject();

        return new SaveData(controllerData, gameState);
    }

    private static String serializeState(GameState state) {
        if (state instanceof GameStateChooseCloud)
            return "chooseCloud";

        if (state instanceof GameStateComputeIsland)
            return "island";

        if (state instanceof GameStateEndCheckPhase)
            return "endCheck";

        if (state instanceof GameStateEndGame)
            return "endGame";

        if (state instanceof GameStateEndOfTurn)
            return "endTurn";

        if (state instanceof GameStateExpertInitialization)
            return "expert";

        if (state instanceof GameStateFillClouds)
            return "fillCloud";

        if (state instanceof GameStateModelInitialization)
            return "model";

        if (state instanceof GameStateMoveMotherNature)
            return "motherNature";

        if (state instanceof GameStateMoveStudents)
            return "students";

        if (state instanceof GameStatePlaceTokens)
            return "placeTokens";

        if (state instanceof GameStatePlayCard)
            return "playCard";

        if (state instanceof GameStateSelectTurnOrder)
            return "turnOrder";

        throw new IllegalStateException("Serialization: no other concrete state exists");
    }

    private static GameState deserializeState(String stateName) {
        return switch (stateName) {
            case "chooseCloud"  -> new GameStateChooseCloud();
            case "island"       -> new GameStateComputeIsland();
            case "endCheck"     -> new GameStateEndCheckPhase();
            case "endGame"      -> new GameStateEndGame();
            case "endTurn"      -> new GameStateEndOfTurn();
            case "expert"       -> new GameStateExpertInitialization();
            case "fillClouds"   -> new GameStateFillClouds();
            case "model"        -> new GameStateModelInitialization();
            case "motherNature" -> new GameStateMoveMotherNature();
            case "students"     -> new GameStateMoveStudents();
            case "placeTokens"  -> new GameStatePlaceTokens();
            case "playCard"     -> new GameStatePlayCard();
            case "turnOrder"    -> new GameStateSelectTurnOrder();
            default             -> throw new IllegalStateException("Deserialization: no other concrete state exists");
        };
    }
}
