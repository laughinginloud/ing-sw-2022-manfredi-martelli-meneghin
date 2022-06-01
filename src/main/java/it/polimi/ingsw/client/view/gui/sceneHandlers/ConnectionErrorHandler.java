package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.gui.ViewGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * Handler (or Controller) of the scene ConnectionError (connectionErrorPage.fxml)
 * The client is informed that the connection to the server was lost
 * and advises him to close the application and try again
 * @author Giovanni Manfredi
 */
public class ConnectionErrorHandler implements GUIHandler {

    private ViewGUI gui;

    // region FXML_Ids

    @FXML
    private AnchorPane connectionError_pane;

    @FXML
    private ImageView connectionError_imgView;

    @FXML
    private Label connectionError_title;

    @FXML
    private Label connectionError_text;

    @FXML
    private Button return_button;

    // endregion FXML_Ids

    /**
     * Method called on the click of "return_button"
     * Returns to "initialPage" with virtualController and
     * model set to null
     */
    public void pressReturnButton() {
        gui.playExitMenu();
    }

    /**
     * Sets the ViewGUI at which the ServerInfoHandler is related
     * @param gui the ViewGUI instance
     */
    @Override
    public void setGUI(ViewGUI gui) {
        this.gui = gui;
    }
}
