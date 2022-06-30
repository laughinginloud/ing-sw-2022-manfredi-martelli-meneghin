package it.polimi.ingsw.common.json.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.PlayCharacterAction;
import it.polimi.ingsw.common.message.InfoMap;
import it.polimi.ingsw.common.message.Message;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.utils.Tuple;
import it.polimi.ingsw.common.viewRecord.GameRules;
import it.polimi.ingsw.common.viewRecord.MoveStudentInfo;
import it.polimi.ingsw.common.viewRecord.UsernameAndMagicAge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Adapter for an info map
 * @author Mattia Martelli
 */
public class InfoMapJSONAdapter extends TypeAdapter<InfoMap> {
    private static final Gson json = new GsonBuilder()
        .registerTypeAdapter(AssistantCard.class,        new AssistantCardJSONAdapter())
        .registerTypeAdapter(AssistantCard[].class,      new AssistantCardArrayJSONAdapter())
        .registerTypeAdapter(Bag.class,                  new BagJSONAdapter())
        .registerTypeAdapter(CharacterCard[].class,      new CharacterCardArrayJSONAdapter())
        .registerTypeAdapter(CloudTile[].class,          new CloudTileArrayJSONAdapter())
        .registerTypeAdapter(Color.class,                new ColorJSONAdapter())
        .registerTypeAdapter(Color[].class,              new ColorArrayJSONAdapter())
        .registerTypeAdapter(DiningRoom.class,           new DiningRoomJSONAdapter())
        .registerTypeAdapter(Entrance.class,             new EntranceJSONAdapter())
        .registerTypeAdapter(GameActions.class,          new GameActionsJSONAdapter())
        .registerTypeAdapter(GameModel.class,            new GameModelJSONAdapter())
        .registerTypeAdapter(GameRules.class,            new GameRulesJSONAdapter())
        .registerTypeAdapter(GameValues.class,           new GameValuesJSONAdapter())
        .registerTypeAdapter(GlobalProfessorTable.class, new GlobalProfessorTableJSONAdapter())
        .registerTypeAdapter(Island[].class,             new IslandArrayJSONAdapter())
        .registerTypeAdapter(Message.class,              new MessageJSONAdapter())
        .registerTypeAdapter(MoveStudentInfo.class,      new MoveStudentInfoJSONAdapter())
        .registerTypeAdapter(Player.class,               new PlayerJSONAdapter())
        .registerTypeAdapter(Player[].class,             new PlayerArrayJSONAdapter())
        .registerTypeAdapter(SchoolBoard.class,          new SchoolBoardJSONAdapter())
        .registerTypeAdapter(TowerColor.class,           new TowerColorJSONAdapter())
        .registerTypeAdapter(Tuple.class,                new TupleJSONAdapter())
        .registerTypeAdapter(UsernameAndMagicAge.class,  new UsernameAndMagicAgeJSONAdapter())
        .registerTypeAdapter(Wizard.class,               new WizardJSONAdapter())
        .registerTypeAdapter(Wizard[].class,             new WizardArrayJSONAdapter())
        .setPrettyPrinting()
        .create();

    @Override
    public void write(JsonWriter jsonWriter, InfoMap infoMap) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("infomap");
        jsonWriter.beginArray();
        for (Map.Entry<GameValues, Object> entry : infoMap.entrySet()) {
            jsonWriter.beginObject();
            jsonWriter.name("key");
            jsonWriter.value(entry.getKey().name());
            jsonWriter.name("value");
            serialize(entry.getKey(), entry.getValue(), jsonWriter);
            jsonWriter.endObject();
        }
        jsonWriter.endArray();
        jsonWriter.endObject();
    }

    @Override
    public InfoMap read(JsonReader jsonReader) throws IOException {
        InfoMap map = new InfoMap();

        jsonReader.beginObject();
        jsonReader.nextName();
        jsonReader.beginArray();

        while (jsonReader.hasNext()) {
            jsonReader.beginObject();

            GameValues key   = null;
            Object     value = null;

            String fieldName = null;

            while (jsonReader.hasNext()) {
                JsonToken jsonToken = jsonReader.peek();

                if (jsonToken == JsonToken.NAME)
                    fieldName = jsonReader.nextName();

                if (fieldName != null)
                    switch (fieldName) {
                        case "key"   -> key   = GameValues.valueOf(jsonReader.nextString());
                        case "value" -> value = deserialize(key, jsonReader);
                    }

                jsonReader.peek();
            }

            jsonReader.endObject();

            map.put(key, value);

            jsonReader.peek();
        }

        jsonReader.endArray();
        jsonReader.endObject();

        return map;
    }

    @SuppressWarnings("unchecked")
    private static void serialize(GameValues key, Object value, JsonWriter jsonWriter) throws IOException {
        switch (key) {
            case BOOLARRAY -> {
                boolean[] array = (boolean[]) value;

                jsonWriter.beginArray();

                for (boolean obj : array)
                    jsonWriter.value(json.toJson(obj));

                jsonWriter.endArray();
            }

            case ENTRANCEARRAY -> {
                Entrance[] array = (Entrance[]) value;

                jsonWriter.beginArray();

                for (Entrance obj : array)
                    jsonWriter.value(json.toJson(obj));

                jsonWriter.endArray();
            }

            case DININGROOMARRAY -> {
                DiningRoom[] array = (DiningRoom[]) value;

                jsonWriter.beginArray();

                for (DiningRoom obj : array)
                    jsonWriter.value(json.toJson(obj));

                jsonWriter.endArray();
            }

            case SCHOOLBOARDARRAY -> {
                SchoolBoard[] array = (SchoolBoard[]) value;

                jsonWriter.beginArray();

                for (SchoolBoard obj : array)
                    jsonWriter.value(json.toJson(obj));

                jsonWriter.endArray();
            }

            case CHARACTERVALUE -> jsonWriter.value(((PlayCharacterAction) value).name());

            case BARDSWAPMAP -> {
                Map<Color, Boolean[]> map = (Map<Color, Boolean[]>) value;

                jsonWriter.beginArray();

                for (Map.Entry<Color, Boolean[]> entry : map.entrySet()) {
                    if (entry.getKey() == null)
                        continue;

                    jsonWriter.beginObject();

                    jsonWriter.name("color");
                    jsonWriter.value(entry.getKey().name());

                    jsonWriter.name("bools");
                    jsonWriter.beginArray();

                    for (Boolean bool : entry.getValue())
                        jsonWriter.value(bool);

                    jsonWriter.endArray();

                    jsonWriter.endObject();
                }

                jsonWriter.endArray();
            }

            case DININGROOMTABLECOLOR,
                 MERCHANTCOLOR,
                 REDUCECOLOR -> jsonWriter.value(((Color) value).name());

            default -> jsonWriter.value(json.toJson(value));
        }
    }

    private static Object deserialize(GameValues key, JsonReader jsonReader) throws IOException {
        return switch (key) {
            case ISLANDARRAY,
                 MNPOSSIBLEMOVEMENTS -> json.fromJson(jsonReader.nextString(), Island[].class);

            case PLAYERARRAY -> json.fromJson(jsonReader.nextString(), Player[].class);

            case CLOUDARRAY -> json.fromJson(jsonReader.nextString(), CloudTile[].class);

            case CHARACTERCARDARRAY -> json.fromJson(jsonReader.nextString(), CharacterCard[].class);

            case MODEL -> json.fromJson(jsonReader.nextString(), GameModel.class);

            case COLORARRAY,
                 ENTRANCESTUDENTS,
                 CARDSTUDENTS,
                 BARDSWAPPABLESTUDENTS,
                 REDUCIBLECOLOR -> json.fromJson(jsonReader.nextString(), Color[].class);

            case BOOLARRAY -> {
                List<Boolean> booleanList = new ArrayList<>();

                jsonReader.beginArray();
                while (jsonReader.hasNext())
                    booleanList.add(Boolean.valueOf(jsonReader.nextString()));
                jsonReader.endArray();

                yield booleanList.toArray(Boolean[]::new);
            }

            case CHARACTERCARDPOSITION,
                 MOTHERNATURE,
                 COINPOOL,
                 STUDENTINDEX,
                 ISLANDINDEX,
                 MAXMOVEMENTJESTER,
                 ENTRANCESTUDENTINDEX,
                 CARDSTUDENTINDEX,
                 MOVEMENTBARD,
                 MAXMOVEMENTBARD,
                 MOVEMENTJESTER -> jsonReader.nextInt();

            case CHARACTERVALUE -> PlayCharacterAction.valueOf(jsonReader.nextString());

            case ENTRANCE,
                 DININGROOM -> json.fromJson(jsonReader.nextString(), Tuple.class);

            case ENTRANCEARRAY -> {
                List<Entrance> entranceList = new ArrayList<>();

                jsonReader.beginArray();
                while (jsonReader.hasNext())
                    entranceList.add(json.fromJson(jsonReader.nextString(), Entrance.class));
                jsonReader.endArray();

                yield entranceList.toArray(Entrance[]::new);
            }

            case DININGROOMARRAY -> {
                List<DiningRoom> diningRoomList = new ArrayList<>();

                jsonReader.beginArray();
                while (jsonReader.hasNext())
                    diningRoomList.add(json.fromJson(jsonReader.nextString(), DiningRoom.class));
                jsonReader.endArray();

                yield diningRoomList.toArray(DiningRoom[]::new);
            }

            case SCHOOLBOARDARRAY -> {
                List<SchoolBoard> schoolBoardList = new ArrayList<>();

                jsonReader.beginArray();
                while (jsonReader.hasNext())
                    schoolBoardList.add(json.fromJson(jsonReader.nextString(), SchoolBoard.class));
                jsonReader.endArray();

                yield schoolBoardList.toArray(SchoolBoard[]::new);
            }

            case GLOBALPROFESSORTABLE -> json.fromJson(jsonReader.nextString(), GlobalProfessorTable.class);

            case BARDSWAPMAP -> {
                EnumMap<Color, Boolean[]> map = new EnumMap<>(Color.class);

                jsonReader.beginArray();

                while (jsonReader.hasNext()) {
                    jsonReader.beginObject();

                    Color color = null;
                    Boolean[] booleans = null;

                    String fieldName = null;

                    while (jsonReader.hasNext()) {
                        JsonToken jsonToken = jsonReader.peek();

                        if (jsonToken == JsonToken.NAME)
                            fieldName = jsonReader.nextName();

                        if (fieldName != null)
                            switch (fieldName) {
                                case "color" -> color = Color.valueOf(jsonReader.nextString());
                                case "bools" -> {
                                    List<Boolean> booleanList = new ArrayList<>();

                                    jsonReader.beginArray();
                                    while (jsonReader.hasNext())
                                        booleanList.add(jsonReader.nextBoolean());
                                    jsonReader.endArray();

                                    booleans = booleanList.toArray(Boolean[]::new);
                                }
                            }

                        jsonReader.peek();
                    }

                    jsonReader.endObject();

                    map.put(color, booleans);
                }

                jsonReader.endArray();

                yield map;
            }

            case DININGROOMTABLECOLOR,
                 MERCHANTCOLOR,
                 REDUCECOLOR -> Color.valueOf(jsonReader.nextString());

            case CONFIRMATIONSTRING -> jsonReader.nextString();
        };
    }
}
