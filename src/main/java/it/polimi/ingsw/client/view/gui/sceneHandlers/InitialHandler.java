package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.view.gui.ViewGUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

/**
 * Handler (or Controller) of the scene Initial (initialPage.fxml)
 * The client is shown a landing page with few informations (authors and logos)
 * and a play button. If the button is pressed the application starts notifying
 * the Client.java otherwise if the window is closed the application is closed
 * @author Giovanni Manfredi
 */
public class InitialHandler implements GUIHandler{
    private ViewGUI gui;

    // region FXML_Ids

    @FXML
    private AnchorPane initial_pane;

    @FXML
    private ImageView initial_imgView;

    @FXML
    private Button play_button;

    @FXML
    private Label welcome_label;

    @FXML
    private ImageView cranio_img;

    @FXML
    private Rectangle polimi_background;

    @FXML
    private ImageView polimi_img;

    @FXML
    private Label love_label;

    // endregion FXML_Ids

    /**
     * Method called on the click of "play_button"
     * Signals to the client that the player wants to play a new game
     */
    public void pressPlayButton() {
        Platform.runLater(() -> gui.askAddress());
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
