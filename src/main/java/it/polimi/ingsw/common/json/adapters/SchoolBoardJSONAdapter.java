package it.polimi.ingsw.common.json.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.DiningRoom;
import it.polimi.ingsw.common.model.Entrance;
import it.polimi.ingsw.common.model.SchoolBoard;
import it.polimi.ingsw.common.model.TowerColor;

import java.io.IOException;

/**
 * Adapter for a school board
 * @author Mattia Martelli
 */
public class SchoolBoardJSONAdapter extends TypeAdapter<SchoolBoard> {
    private static final Gson json = new GsonBuilder()
        .registerTypeAdapter(TowerColor.class, new TowerColorJSONAdapter())
        .registerTypeAdapter(Entrance.class, new EntranceJSONAdapter())
        .registerTypeAdapter(DiningRoom.class, new DiningRoomJSONAdapter())
        .setPrettyPrinting()
        .create();

    @Override
    public void write(JsonWriter jsonWriter, SchoolBoard schoolBoard) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("towerColor");
        jsonWriter.value(json.toJson(schoolBoard.getTowerColor()));

        jsonWriter.name("towerCount");
        jsonWriter.value(schoolBoard.getTowerCount());

        jsonWriter.name("entrance");
        jsonWriter.value(json.toJson(schoolBoard.getEntrance()));

        jsonWriter.name("diningRoom");
        jsonWriter.value(json.toJson(schoolBoard.getDiningRoom()));

        jsonWriter.endObject();
    }

    @Override
    public SchoolBoard read(JsonReader jsonReader) throws IOException {
        TowerColor towerColor = null;
        int towerCount = 0;
        Entrance entrance = null;
        DiningRoom diningRoom = null;

        String fieldName = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken jsonToken = jsonReader.peek();

            if (jsonToken == JsonToken.NAME)
                fieldName = jsonReader.nextName();

            if (fieldName != null)
                switch (fieldName) {
                    case "towerColor" -> towerColor = json.fromJson(jsonReader.nextString(), TowerColor.class);
                    case "towerCount" -> towerCount = jsonReader.nextInt();
                    case "entrance"   -> entrance   = json.fromJson(jsonReader.nextString(), Entrance.class);
                    case "diningRoom" -> diningRoom = json.fromJson(jsonReader.nextString(), DiningRoom.class);
                }

            jsonReader.peek();
        }

        jsonReader.endObject();

        return new SchoolBoard(towerColor, towerCount, entrance, diningRoom);
    }
}
