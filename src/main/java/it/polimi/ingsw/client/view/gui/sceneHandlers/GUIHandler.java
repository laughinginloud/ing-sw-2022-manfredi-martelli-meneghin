package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.gui.ViewGUI;

/**
 * GUI Handler's (or controllers) interface
 * Contains the method to set the ViewGUI they are linked with
 */
public sealed interface GUIHandler
    permits AskForRulesHandler,
            ClientInfoHandler,
            ConnectionErrorHandler,
            EndGameHandler,
            GameChoiceHandler,
            GameSceneHandler,
            InitialHandler,
            PlayersSchoolBoardHandler,
            ServerInfoHandler,
            UnableToJoinHandler,
            WaitingRoomHandler,
            WizardChoiceHandler {

    /**
     * Sets the ViewGUI at which the ServerInfoHandler is related
     * @param gui the ViewGUI instance
     */
    void setGUI(ViewGUI gui);
}
