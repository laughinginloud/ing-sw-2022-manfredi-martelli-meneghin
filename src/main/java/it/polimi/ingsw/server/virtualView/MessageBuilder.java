package it.polimi.ingsw.server.virtualView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.common.message.*;
import it.polimi.ingsw.server.controller.command.*;

/**
 * Class that contains useful methods to work with messages (class instances and JSON) and commands
 * @author Mattia Martelli, Sebastiano Meneghin
 */
final class MessageBuilder {
    // The constructor is private to emulate a static class (along with the class itself being final)
    private MessageBuilder() {}

    // The builder object that morphs between JSON and objects
    private static final Gson jsonBuilder = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Get the message (in JSON form) corresponding to the specified command
     * @param command The command that will be translated
     * @return A string containing the JSON of the corresponding message
     */
    public static String commandToJson(GameCommand command) {
        return jsonBuilder.toJson(commandToMessage(command));
    }

    /**
     * Get the message (in object form) corresponding to the specified command
     * @param command The command that will be translated
     * @return The message corresponding to the provided command
     */
    public static Message commandToMessage(GameCommand command) {
        if (command instanceof GameCommandGameStart)
            return new Message(MessageType.GAMESTART, null);

        if (command instanceof GameCommandGameProgress)
            return new Message(MessageType.GAMEPROGRESS, null);

        if (command instanceof GameCommandRequestValueClient c)
            return new Message(MessageType.REQUESTVALUE, c.executeCommand());

        if (command instanceof GameCommandIllegalCommand c)
            return new Message(MessageType.ILLEGALMESSAGE, c.executeCommand());

        if (command instanceof GameCommandIllegalValue c)
            return new Message(MessageType.ILLEGALVALUE, c.executeCommand());

        if (command instanceof GameCommandSendInfo c)
            return new Message(MessageType.SENDINFO, c.executeCommand());

        if (command instanceof GameCommandRequestAction c)
            return new Message(MessageType.REQUESTACTION, c.executeCommand());

        //TODO: ping

        if (command instanceof GameCommandEndGame c)
            return new Message(c.isDraw() ? MessageType.GAMEDRAW : MessageType.GAMEWINNER, c.executeCommand()); //TODO: ripulire per team?

        if (command instanceof GameCommandInterruptGame)
            return new Message(MessageType.GAMEINTERRUPT, null);

        throw new IllegalStateException("No suitable constructor for the provided command");
    }

    /**
     * Get the command corresponding to the specified message (in object form)
     * @param message The message that will be translated
     * @return The command corresponding to the provided message
     */
    public static GameCommand messageToCommand(Message message) {
        if (message == null)
            return null;

        return null; //TODO

        /*TODO: Gestire il ritorno di GameCommandResponseAction:

            GameActions.LoadGameChoice -> questa viene trasformata in una semplice GameCommandResponseAction che è già gestita
                                          correttamente all'interno di GameController

            GameActions.InsertedUsernameAndAge -> GameCommandUsernameAndMagicAge

            GameActions.MoveStudentInfo -> GameCommandMoveStudent(MoveStudentInfo)

            GameActions.StudentsOfSelectedCloud -> GameCommandChooseCloud(con info relative)

            GameActions.EndHisTurn -> GameCommandEndTurn

            GameActions.ChosenCharacter -> GameCommandPlayCharacterCard
         */
    }

    /**
     * Get the command corresponding to the specified message (in JSON form)
     * @param message The message that will be translated
     * @return The command corresponding to the provided message
     */
    public static GameCommand jsonToCommand(String message) {
        return messageToCommand(jsonToMessage(message));
    }

    /**
     * Translate a message from an object to its JSON counterpart
     * @param message The object to be translated
     * @return A string containing the translation
     */
    public static String messageToJson(Message message) {
        return jsonBuilder.toJson(message);
    }

    /**
     * Translate a message from a JSON to its object counterpart
     * @param json The JSON to be translated
     * @return An object representing the translation
     */
    public static Message jsonToMessage(String json) {
        return jsonBuilder.fromJson(json, Message.class);
    }
}


/*
FUNZIONAMENTO

Controller e View comunicano tramite command
VirtualView si occupa di chiamare MessageBuilder, che crea una stringa contenente un JSON che contiene il messaggio
Il messaggio è di tipo Message, record con due campi: MessageType, enumeratore con i tipi di messaggio, e value, object generico (NB: se null semplicemente non viene inviato)
Build riceve un comando, guarda il suo tipo concreto e quindi agisce di conseguenza, trasformando nel JSON
 */
