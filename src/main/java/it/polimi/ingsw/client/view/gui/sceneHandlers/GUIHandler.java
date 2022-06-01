package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.gui.ViewGUI;

/**
 * GUI Handler's (or controllers) interface
 * Contains the method to set the ViewGUI they are linked with
 */
public interface GUIHandler {

    /**
     * Sets the ViewGUI at which the ServerInfoHandler is related
     * @param gui the ViewGUI instance
     */
    void setGUI(ViewGUI gui);
}
