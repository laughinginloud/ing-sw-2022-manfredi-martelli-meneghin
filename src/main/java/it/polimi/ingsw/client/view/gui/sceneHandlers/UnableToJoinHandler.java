package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.gui.ViewGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class UnableToJoinHandler implements GUIHandler {

    @FXML
    private AnchorPane unableToJoin_pane;

    @FXML
    private ImageView unableToJoin_imgView;

    @FXML
    private Label unableToJoin_title;

    @FXML
    private Label unableToJoin_text;

    /**
     * @param gui
     */
    @Override
    public void setGUI(ViewGUI gui) {
        //TODO: [GUI implementation]
    }
}
