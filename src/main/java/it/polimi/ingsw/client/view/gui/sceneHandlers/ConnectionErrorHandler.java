package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.gui.ViewGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * Handler (or Controller) of the scene ConnectionError (connectionErrorPage.fxml)
 * The client is informed that the connection to the server was lost
 * and advises him to close the application and try again
 *
 * @author Giovanni Manfredi
 */

// Warnings for unused fields have been disabled, since the fields that these fields are accessing
// could be useful in the future and difficult to add without opening the .fxml
// Warnings for gui field has been suppressed to allow future modification of the class
@SuppressWarnings({"unused, FieldCanBeLocal"})

public final class ConnectionErrorHandler implements GUIHandler {

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

    /**
     * Sets the ViewGUI at which the ConnectionErrorHandler is related
     *
     * @param gui the ViewGUI instance
     */
    @Override
    public void setGUI(ViewGUI gui) {
        this.gui = gui;
    }
}
