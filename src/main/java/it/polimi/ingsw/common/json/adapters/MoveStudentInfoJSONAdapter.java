package it.polimi.ingsw.common.json.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.viewRecord.MoveStudentInfo;

import java.io.IOException;

/**
 * Adapter for a move student info
 * @author Mattia Martelli
 */
public class MoveStudentInfoJSONAdapter extends TypeAdapter<MoveStudentInfo> {
    @Override
    public void write(JsonWriter jsonWriter, MoveStudentInfo moveStudentsInfo) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("toDiningRoom");
        jsonWriter.value(moveStudentsInfo.toDiningRoom());

        if (moveStudentsInfo.islandNum() != null) {
            jsonWriter.name("islandNum");
            jsonWriter.value(moveStudentsInfo.islandNum());
        }

        jsonWriter.name("studentIndex");
        jsonWriter.value(moveStudentsInfo.studentIndex());

        jsonWriter.endObject();
    }

    @Override
    public MoveStudentInfo read(JsonReader jsonReader) throws IOException {
        boolean toDiningRoom = false;
        int islandNum = 0;
        int studentIndex = 0;

        String fieldName = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken jsonToken = jsonReader.peek();

            if (jsonToken == JsonToken.NAME)
                fieldName = jsonReader.nextName();

            if (fieldName != null)
                switch (fieldName) {
                    case "toDiningRoom" -> toDiningRoom = jsonReader.nextBoolean();
                    case "islandNum"    -> islandNum    = jsonReader.nextInt();
                    case "studentIndex" -> studentIndex = jsonReader.nextInt();
                }

            jsonReader.peek();
        }

        jsonReader.endObject();

        return new MoveStudentInfo(toDiningRoom, islandNum, studentIndex);
    }
}
