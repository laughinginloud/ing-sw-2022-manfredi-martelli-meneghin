package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.gui.ViewGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * Handler (or Controller) of the scene WaitingRoom (waitingRoomPage.fxml)
 * The client is put in a waiting room screen informing him/her
 * that the game will start once enough players are connected
 *
 * @author Giovanni Manfredi
 */
public final class WaitingRoomHandler implements GUIHandler {

    private ViewGUI gui;

    // region FXML_Ids

    @FXML
    private AnchorPane waitingRoom_pane;

    @FXML
    private ImageView waitingRoom_imgView;

    @FXML
    private Label waitingRoom_title;

    @FXML
    private Label waitingRoom_text;

    // endregion FXML_Ids

    /**
     * Sets the ViewGUI at which the WaitingRoomHandler is related
     *
     * @param gui the ViewGUI instance
     */
    @Override
    public void setGUI(ViewGUI gui) {
        this.gui = gui;
    }
}
