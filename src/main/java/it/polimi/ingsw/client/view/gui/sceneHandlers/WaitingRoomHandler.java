package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.gui.ViewGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class WaitingRoomHandler implements GUIHandler {

    @FXML
    private AnchorPane waitingRoom_pane;

    @FXML
    private ImageView waitingRoom_imgView;

    @FXML
    private Label waitingRoom_title;

    @FXML
    private Label waitingRoom_text;

    /**
     * @param gui
     */
    @Override
    public void setGUI(ViewGUI gui) {
        //TODO: [GUI implementation]
    }
}
