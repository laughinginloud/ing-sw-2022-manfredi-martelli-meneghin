package it.polimi.ingsw.server.controller.command;

import java.io.Serializable;

/**
 * Interface representing a generic command used by the controller
 * @author Mattia Martelli
 */
public sealed interface GameCommand
    extends Serializable
    permits GameCommandChooseCloud,
            GameCommandChosenCharacterCardFields,
            GameCommandEndGame,
            GameCommandEndTurn,
            GameCommandGameProgress,
            GameCommandGameStart,
            GameCommandInterruptGame,
            GameCommandMoveMotherNature,
            GameCommandMoveStudent,
            GameCommandNotifyBeginningTurn,
            GameCommandNotifyEndTurn,
            GameCommandPlayAssistantCard,
            GameCommandPlayCharacterCard,
            GameCommandRequestAction,
            GameCommandRequestValue,
            GameCommandResponseAction,
            GameCommandResponseRules,
            GameCommandResponseWizard,
            GameCommandSendInfo,
            GameCommandUsernameAndMagicAge {

    /**
     * Execute the command representend by this class
     * @return The return value of the command, if it exists
     */
    Object executeCommand();
}
