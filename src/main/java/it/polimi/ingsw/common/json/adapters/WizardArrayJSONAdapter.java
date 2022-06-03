package it.polimi.ingsw.common.json.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.Wizard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for an array of wizards
 * @author Mattia Martelli
 */
public class WizardArrayJSONAdapter extends TypeAdapter<Wizard[]> {
    @Override
    public void write(JsonWriter jsonWriter, Wizard[] wizards) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("wizards");
        jsonWriter.beginArray();
        for (Wizard wizard : wizards)
            jsonWriter.value(wizard.name());
        jsonWriter.endArray();
        jsonWriter.endObject();
    }

    @Override
    public Wizard[] read(JsonReader jsonReader) throws IOException {
        List<Wizard> wizardList = new ArrayList<>();

        jsonReader.beginObject();
        jsonReader.nextName();
        jsonReader.beginArray();
        while (jsonReader.hasNext())
            wizardList.add(Wizard.valueOf(jsonReader.nextString()));
        jsonReader.endArray();
        jsonReader.endObject();

        return wizardList.toArray(Wizard[]::new);
    }
}
