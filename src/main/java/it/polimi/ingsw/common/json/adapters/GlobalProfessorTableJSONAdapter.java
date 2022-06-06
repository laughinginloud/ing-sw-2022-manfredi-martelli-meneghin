package it.polimi.ingsw.common.json.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.Color;
import it.polimi.ingsw.common.model.GlobalProfessorTable;
import it.polimi.ingsw.common.model.Player;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Adapter for a global professor table
 * @author Mattia Martelli
 */
public class GlobalProfessorTableJSONAdapter extends TypeAdapter<GlobalProfessorTable> {
    private static final Gson json = new GsonBuilder().registerTypeAdapter(Player[].class, new PlayerArrayJSONAdapter()).setPrettyPrinting().create();

    @Override
    public void write(JsonWriter jsonWriter, GlobalProfessorTable globalProfessorTable) throws IOException {
        try {
            Field professorsLocationField = globalProfessorTable.getClass().getDeclaredField("professorLocations");
            professorsLocationField.setAccessible(true);
            Player[] professorsLocation = (Player[]) professorsLocationField.get(globalProfessorTable);
            professorsLocationField.setAccessible(false);

            jsonWriter.beginObject();
            jsonWriter.name("professorLocations");
            jsonWriter.value(json.toJson(professorsLocation));
            jsonWriter.endObject();
        }

        catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GlobalProfessorTable read(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        jsonReader.nextName();
        Player[] professorsLocation = json.fromJson(jsonReader.nextString(), Player[].class);
        jsonReader.endObject();

        GlobalProfessorTable globalProfessorTable = new GlobalProfessorTable();
        for (Color color : Color.values())
            globalProfessorTable.setProfessorLocation(color, professorsLocation[color.ordinal()]);

        return globalProfessorTable;
    }
}
