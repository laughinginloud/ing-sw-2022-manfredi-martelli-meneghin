package it.polimi.ingsw.common.json.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.characterCard.CharacterCardStrategy;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Adapter for the ControllerData class
 * @author Mattia Martelli
 */
public class ControllerDataJSONAdapter extends TypeAdapter<ControllerData> {
    public static final Gson json = new GsonBuilder()
        .registerTypeAdapter(CharacterCard[].class, new CharacterCardArrayJSONAdapter())
        .registerTypeAdapter(GameModel      .class, new GameModelJSONAdapter())
        .setPrettyPrinting()
        .create();

    @Override
    public void write(JsonWriter jsonWriter, ControllerData controllerData) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("gameModel");
        jsonWriter.value(json.toJson(controllerData.getGameModel()));

        jsonWriter.name("playerAssistantCardMap");
        jsonWriter.beginArray();
        for (Map.Entry<Player, AssistantCard> entry : controllerData.getPlayerAssistantCardMap().entrySet()) {
            jsonWriter.beginObject();

            jsonWriter.name("player");
            jsonWriter.value(entry.getKey().getPlayerID());

            jsonWriter.name("cardValue");
            jsonWriter.value(entry.getValue().cardValue());

            jsonWriter.name("movementPoints");
            jsonWriter.value(entry.getValue().movementPoints());

            jsonWriter.endObject();
        }
        jsonWriter.endArray();

        if (controllerData.getPlayersOrder() != null) {
            jsonWriter.name("playersOrder");
            jsonWriter.beginArray();
            for (Player player : controllerData.getPlayersOrder())
                jsonWriter.value(player.getPlayerID());
            jsonWriter.endArray();
        }

        jsonWriter.name("emptyBagTrigger");
        jsonWriter.value(controllerData.getEmptyBagTrigger());

        jsonWriter.name("emptyAssistantDeckTrigger");
        jsonWriter.value(controllerData.getEmptyAssistantDeckTrigger());

        jsonWriter.name("hasPlayedCard");
        jsonWriter.value(controllerData.checkPlayedCard());

        jsonWriter.name("numOfPlayers");
        jsonWriter.value(controllerData.getNumOfPlayers());

        jsonWriter.name("expertMode");
        jsonWriter.value(controllerData.getExpertMode());

        if (controllerData.getCurrentPlayer() != null) {
            try {
                Field currentPlayerField = ControllerData.class.getDeclaredField("currentPlayer");
                currentPlayerField.setAccessible(true);

                jsonWriter.name("currentPlayer");
                jsonWriter.value((int) currentPlayerField.get(controllerData));

                currentPlayerField.setAccessible(false);
            }

            catch (IllegalAccessException | NoSuchFieldException ignored) {
                throw new IOException();
            }
        }

        if (controllerData.getExpertMode()) {
            jsonWriter.name("characterCardStrategies");
            try {
                Field characterCardField = CharacterCardStrategy.class.getDeclaredField("card");
                characterCardField.setAccessible(true);

                List<CharacterCard> cards = new ArrayList<>();

                for (CharacterCardStrategy strategy : controllerData.getCardStrategies())
                    cards.add((CharacterCard) characterCardField.get(strategy));

                jsonWriter.value(json.toJson(cards.toArray(CharacterCard[]::new)));

                characterCardField.setAccessible(false);
            }

            catch (IllegalAccessException | NoSuchFieldException ignored) {
                throw new IOException();
            }

            jsonWriter.name("characterCardFlags");
            jsonWriter.beginArray();
            for (ControllerData.Flags flag : ControllerData.Flags.values()) {
                jsonWriter.beginObject();

                jsonWriter.name("flag");
                jsonWriter.value(flag.name());

                jsonWriter.name("value");
                jsonWriter.value(controllerData.getCharacterCardFlag(flag));

                jsonWriter.endObject();
            }
            jsonWriter.endArray();

            if (controllerData.getExcludedColor() != null) {
                jsonWriter.name("excludedColor");
                jsonWriter.value(controllerData.getExcludedColor().name());
            }
        }

        jsonWriter.name("winTrigger");
        jsonWriter.value(controllerData.checkWinTrigger());

        jsonWriter.endObject();
    }

    @Override
    public ControllerData read(JsonReader jsonReader) throws IOException {
        GameModel model = null;
        Map<Integer, AssistantCard> idAssistantCardMap = new HashMap<>();
        List<Integer> playerIDOrder = new ArrayList<>();
        boolean emptyBagTrigger = false;
        boolean emptyAssistantDeckTrigger = false;
        boolean hasPlayedCard = false;
        int numOfPlayers = 0;
        boolean expertMode = false;
        int currentPlayer = 0;
        CharacterCardStrategy[] characterCardStrategies = null;
        boolean[] flags = new boolean[ControllerData.Flags.values().length];
        Color excludedColor = null;
        boolean winTrigger = false;

        String fieldName = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken jsonToken = jsonReader.peek();

            if (jsonToken == JsonToken.NAME)
                fieldName = jsonReader.nextName();

            if (fieldName != null)
                switch (fieldName) {
                    case "gameModel" -> model = json.fromJson(jsonReader.nextString(), GameModel.class);
                    case "playerAssistantCardMap" -> {
                        jsonReader.beginArray();

                        while (jsonReader.hasNext()) {
                            int playerID = 0;
                            int cardValue = 0;
                            int movementPoints = 0;

                            String mapField = null;

                            jsonReader.beginObject();

                            while (jsonReader.hasNext()) {
                                JsonToken jsonTokenMap = jsonReader.peek();

                                if (jsonTokenMap == JsonToken.NAME)
                                    mapField = jsonReader.nextName();

                                if (mapField != null)
                                    switch (mapField) {
                                        case "player"         -> playerID       = jsonReader.nextInt();
                                        case "cardValue"      -> cardValue      = jsonReader.nextInt();
                                        case "movementPoints" -> movementPoints = jsonReader.nextInt();
                                    }

                                jsonReader.peek();
                            }

                            jsonReader.endObject();

                            idAssistantCardMap.put(playerID, new AssistantCard(cardValue, movementPoints));

                            jsonReader.peek();
                        }

                        jsonReader.endArray();
                    }
                    case "playersOrder" -> {
                        jsonReader.beginArray();

                        while (jsonReader.hasNext())
                            playerIDOrder.add(jsonReader.nextInt());

                        jsonReader.endArray();
                    }
                    case "emptyBagTrigger" -> emptyBagTrigger = jsonReader.nextBoolean();
                    case "emptyAssistantDeckTrigger" -> emptyAssistantDeckTrigger = jsonReader.nextBoolean();
                    case "hasPlayedCard" -> hasPlayedCard = jsonReader.nextBoolean();
                    case "numOfPlayers" -> numOfPlayers = jsonReader.nextInt();
                    case "expertMode" -> expertMode = jsonReader.nextBoolean();
                    case "currentPlayer" -> currentPlayer = jsonReader.nextInt();
                    case "characterCardStrategies" -> {
                        CharacterCard[] cards   = json.fromJson(jsonReader.nextString(), CharacterCard[].class);

                        characterCardStrategies = Arrays.stream(cards)
                            .map(CharacterCardStrategy::build)
                            .toArray(CharacterCardStrategy[]::new);
                    }
                    case "characterCardFlags" -> {
                        jsonReader.beginArray();

                        while (jsonReader.hasNext()) {
                            jsonReader.beginObject();

                            ControllerData.Flags flag = null;
                            boolean flagValue = false;

                            String flagField = null;

                            while (jsonReader.hasNext()) {
                                JsonToken jsonTokenFlag = jsonReader.peek();

                                if (jsonToken == JsonToken.NAME)
                                    flagField = jsonReader.nextName();

                                if (flagField != null)
                                    switch (flagField) {
                                        case "flag"  -> flag      = ControllerData.Flags.valueOf(jsonReader.nextString());
                                        case "value" -> flagValue = jsonReader.nextBoolean();
                                    }

                                jsonReader.peek();
                            }

                            flags[flag.ordinal()] = flagValue;

                            jsonReader.endObject();
                        }

                        jsonReader.endArray();
                    }
                    case "excludedColor" -> excludedColor = Color.valueOf(jsonReader.nextString());
                    case "winTrigger" -> winTrigger = jsonReader.nextBoolean();
                }

            jsonReader.peek();
        }

        jsonReader.endObject();

        Map<Player, AssistantCard> assistantCardMap = new HashMap<>();
        GameModel finalModel = model;
        idAssistantCardMap.forEach((i, a) -> assistantCardMap.put(finalModel.getPlayer(i), a));

        return ControllerData.loadData(
            model,
            assistantCardMap,
            playerIDOrder.stream().map(finalModel::getPlayer).toArray(Player[]::new),
            emptyBagTrigger,
            emptyAssistantDeckTrigger,
            hasPlayedCard,
            numOfPlayers,
            expertMode,
            currentPlayer,
            characterCardStrategies,
            flags,
            excludedColor,
            winTrigger
        );
    }
}
