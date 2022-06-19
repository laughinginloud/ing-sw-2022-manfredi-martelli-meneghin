package it.polimi.ingsw.common.json.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.common.model.Wizard;

import java.io.IOException;

/**
 * Adapter for a wizard
 * @author Mattia Martelli
 */
public class WizardJSONAdapter extends TypeAdapter<Wizard> {
    @Override
    public void write(JsonWriter jsonWriter, Wizard wizard) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("wizard");
        jsonWriter.value(wizard.name());

        jsonWriter.endObject();
    }

    @Override
    public Wizard read(JsonReader jsonReader) throws IOException {
        Wizard wizard;

        jsonReader.beginObject();

        jsonReader.nextName();
        wizard = Wizard.valueOf(jsonReader.nextString());
        jsonReader.peek();

        jsonReader.endObject();

        return wizard;
    }
}
