package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.gui.ViewGUI;

public class EndGameHandler implements GUIHandler {
    private ViewGUI gui;

    // TODO [EndGame] - FXML ids + implementation
    // TODO - regions and JavaDocs

    /**
     * Sets the ViewGUI at which the EndGameHandler is related
     * @param gui the ViewGUI instance
     */
    @Override
    public void setGUI(ViewGUI gui) {
        this.gui = gui;
    }
}
