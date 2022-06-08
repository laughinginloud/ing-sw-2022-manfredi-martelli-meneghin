package it.polimi.ingsw.server.virtualView;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.message.*;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.model.Character;
import it.polimi.ingsw.common.utils.Tuple;
import it.polimi.ingsw.common.viewRecord.GameRules;
import it.polimi.ingsw.common.viewRecord.MoveStudentInfo;
import it.polimi.ingsw.common.viewRecord.UsernameAndMagicAge;
import it.polimi.ingsw.server.controller.command.*;

import java.util.Map;

import static it.polimi.ingsw.common.json.Constants.jsonBuilder;

/**
 * Class that contains useful methods to work with messages (class instances and JSON) and commands
 * @author Mattia Martelli, Sebastiano Meneghin
 */
final class MessageBuilder {
    // The constructor is private to emulate a static class (along with the class itself being final)
    private MessageBuilder() {}

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

        if (command instanceof GameCommandRequestValue c)
            return new Message(MessageType.REQUESTVALUE, c.executeCommand());

        if (command instanceof GameCommandSendInfo c)
            return new Message(MessageType.SENDINFO, c.executeCommand());

        if (command instanceof GameCommandRequestAction c)
            return new Message(MessageType.REQUESTACTION, c.executeCommand());

        if (command instanceof GameCommandEndGame c)
            return new Message(c.isDraw() ? MessageType.GAMEDRAW : c.isTeam() ? MessageType.GAMEWINNERTEAM : MessageType.GAMEWINNER, c.executeCommand());

        if (command instanceof GameCommandInterruptGame)
            return new Message(MessageType.GAMEINTERRUPT, null);

        if (command instanceof GameCommandNotifyEndTurn)
            return new Message(MessageType.CUR_PLAYER_END_TURN, null);

        if (command instanceof GameCommandNotifyBeginningTurn c) {
            return new Message(MessageType.OTHER_PLAYER_START_TURN, c.executeCommand());
        }

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

        return switch(message.type()) {
            case RESPONSEACTION -> {
                Tuple<GameActions, Object> tupleReceived = (Tuple<GameActions, Object>) message.value();

                yield switch(tupleReceived.left()) {
                    case CHOSENRULES                -> new GameCommandResponseRules             ((GameRules)               tupleReceived.right());
                    case CHOSENWIZARD               -> new GameCommandResponseWizard            ((Wizard)                  tupleReceived.right());
                    case ENDTHISTURN                -> new GameCommandEndTurn                   ();
                    case CHOSENCHARACTER            -> new GameCommandPlayCharacterCard         ((Character)               tupleReceived.right());
                    case CHOSENFIELDSMAP            -> new GameCommandChosenCharacterCardFields ((Map<GameValues, Object>) tupleReceived.right());
                    case LOADGAMECHOICE             -> new GameCommandResponseAction            (                          tupleReceived.right());
                    case CHOSENASSISTANTCARD        -> new GameCommandPlayAssistantCard         ((AssistantCard)           tupleReceived.right());
                    case INSERTEDUSERNAMEANDAGE     -> new GameCommandUsernameAndMagicAge       ((UsernameAndMagicAge)     tupleReceived.right());
                    case STUDENTSOFSELECTEDCLOUD    -> new GameCommandChooseCloud               ((Color[])                 tupleReceived.right());
                    case MOVESTUDENTINFO            -> new GameCommandMoveStudent               ((MoveStudentInfo)         tupleReceived.right());
                    case CHOSENMOTHERNATUREMOVEMENT -> new GameCommandMoveMotherNature          ((Integer)                 tupleReceived.right());
                    default                         -> null;
                };
            }

            default -> null;
        };
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
