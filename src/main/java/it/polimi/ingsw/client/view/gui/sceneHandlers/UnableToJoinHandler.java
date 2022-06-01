package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.gui.ViewGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * Handler (or Controller) of the scene UnableToJoin (unableToJoinPage.fxml)
 * The client is informed that the game is still in progress and
 * he won't be able to join. It also suggests him to close the application
 * and try again.
 * @author Giovanni Manfredi
 */
public class UnableToJoinHandler implements GUIHandler {

    private ViewGUI gui;

    // region FXML_Ids

    @FXML
    private AnchorPane unableToJoin_pane;

    @FXML
    private ImageView unableToJoin_imgView;

    @FXML
    private Label unableToJoin_title;

    @FXML
    private Label unableToJoin_text;

    // endregion FXML_Ids

    /**
     * Sets the ViewGUI at which the ServerInfoHandler is related
     * @param gui the ViewGUI instance
     */
    @Override
    public void setGUI(ViewGUI gui) {
        this.gui = gui;
    }
}
