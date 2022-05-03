package it.polimi.ingsw.virtualView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.controller.command.*;

class MessageBuilder {
    private static final Gson stringBuilder = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {

    }

    public static String fromCommand(GameCommand command) {
        return stringBuilder.toJson(commandSwitch(command));
    }

    private static Message commandSwitch(GameCommand command) {
        if (command instanceof GameCommandRequestValueClient c)
            return new Message(MessageType.REQUEST, c.executeCommand());

        if (command instanceof GameCommandIllegalCommand c)
            return new Message(MessageType.ILLEGALMESSAGE, c.executeCommand());

        if (command instanceof GameCommandIllegalValue c)
            return new Message(MessageType.ILLEGALVALUE, c.executeCommand());

        if (command instanceof GameCommandSendInfo c)
            new Message(MessageType.SENDINFO, c.executeCommand());

        throw new IllegalStateException("No suitable constructor for the provided command");
    }

    public static GameCommand toCommand(String message) {
        return null; //TODO
    }
}


/*
FUNZIONAMENTO

Controller e View comunicano tramite command
VirtualView si occupa di chiamare MessageBuilder, che crea una stringa contenente un JSON che contiene il messaggio
Il messaggio è di tipo Message, record con due campi: MessageType, enumeratore con i tipi di messaggio, e value, object generico (NB: se null semplicemente non viene inviato)
Build riceve un comando, guarda il suo tipo concreto e quindi agisce di conseguenza, trasformando nel JSON

TODO: cambiare nome in CommandToMessage?
 */
