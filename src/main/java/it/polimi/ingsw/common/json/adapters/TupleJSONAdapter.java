package it.polimi.ingsw.common.json.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.message.InfoMap;
import it.polimi.ingsw.common.message.Message;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.model.Character;
import it.polimi.ingsw.common.utils.Tuple;
import it.polimi.ingsw.common.viewRecord.GameRules;
import it.polimi.ingsw.common.viewRecord.MoveStudentInfo;
import it.polimi.ingsw.common.viewRecord.UsernameAndMagicAge;

import java.io.IOException;

/**
 * Adapter for a generic tuple
 * @author Mattia Martelli
 */
public class TupleJSONAdapter extends TypeAdapter<Tuple> {
    private static final Gson json = new GsonBuilder()
        .registerTypeAdapter(AssistantCard.class,        new AssistantCardJSONAdapter())
        .registerTypeAdapter(AssistantCard[].class,      new AssistantCardArrayJSONAdapter())
        .registerTypeAdapter(Bag.class,                  new BagJSONAdapter())
        .registerTypeAdapter(Character.class,            new CharacterJSONAdapter())
        .registerTypeAdapter(CharacterCard.class,        new CharacterCardJSONAdapter())
        .registerTypeAdapter(CharacterCardNoEntry.class, new CharacterCardNoEntryJSONAdapter())
        .registerTypeAdapter(CharacterCardStudent.class, new CharacterCardStudentJSONAdapter())
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
        .registerTypeAdapter(InfoMap.class,              new InfoMapJSONAdapter())
        .registerTypeAdapter(Island[].class,             new IslandArrayJSONAdapter())
        .registerTypeAdapter(Message.class,              new MessageJSONAdapter())
        .registerTypeAdapter(MoveStudentInfo.class,      new MoveStudentInfoJSONAdapter())
        .registerTypeAdapter(Player.class,               new PlayerJSONAdapter())
        .registerTypeAdapter(PlayerExpert.class,         new PlayerExpertJSONAdapter())
        .registerTypeAdapter(PlayerTeam.class,           new PlayerTeamJSONAdapter())
        .registerTypeAdapter(PlayerTeamExpert.class,     new PlayerTeamExpertJSONAdapter())
        .registerTypeAdapter(Player[].class,             new PlayerArrayJSONAdapter())
        .registerTypeAdapter(SchoolBoard.class,          new SchoolBoardJSONAdapter())
        .registerTypeAdapter(TowerColor.class,           new TowerColorJSONAdapter())
        .registerTypeAdapter(UsernameAndMagicAge.class,  new UsernameAndMagicAgeJSONAdapter())
        .registerTypeAdapter(Wizard.class,               new WizardJSONAdapter())
        .registerTypeAdapter(Wizard[].class,             new WizardArrayJSONAdapter())
        .setPrettyPrinting().create();

    @Override
    public void write(JsonWriter jsonWriter, Tuple tuple) throws IOException {
        jsonWriter.beginObject();

        if (tuple.left() != null) {
            jsonWriter.name("left");
            jsonWriter.beginObject();
            jsonWriter.name("type");
            jsonWriter.value(tuple.left().getClass().getName());
            jsonWriter.name("value");
            jsonWriter.value(json.toJson(tuple.left()));
            jsonWriter.endObject();
        }

        if (tuple.right() != null) {
            jsonWriter.name("right");
            jsonWriter.beginObject();
            jsonWriter.name("type");
            jsonWriter.value(tuple.right().getClass().getName());
            jsonWriter.name("value");
            jsonWriter.value(json.toJson(tuple.right()));
            jsonWriter.endObject();
        }

        jsonWriter.endObject();
    }

    @Override
    public Tuple read(JsonReader jsonReader) throws IOException {
        Object left  = null;
        Object right = null;

        String fieldName = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken jsonToken = jsonReader.peek();

            if (jsonToken.equals(JsonToken.NAME))
                fieldName = jsonReader.nextName();

            if (fieldName != null) {
                String sideField = null;
                Class  type      = null;
                Object value     = null;

                jsonReader.beginObject();

                while (jsonReader.hasNext()) {
                    JsonToken sideToken = jsonReader.peek();

                    if (jsonToken.equals(JsonToken.NAME))
                        sideField = jsonReader.nextName();

                    if (sideField != null)
                        switch (sideField) {
                            case "type" -> {
                                try {
                                    type = Class.forName(jsonReader.nextString());
                                }

                                // Should never happen, since the class is saved correctly
                                catch (ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            case "value" -> value = json.fromJson(jsonReader.nextString(), type);
                        }

                    jsonReader.peek();
                }

                jsonReader.endObject();

                switch (fieldName) {
                    case "left"  -> left  = value;
                    case "right" -> right = value;
                }
            }
        }

        jsonReader.endObject();

        return new Tuple<>(left, right);
    }
}
