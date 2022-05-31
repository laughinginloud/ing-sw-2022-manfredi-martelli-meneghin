package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.gui.ViewGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GameChoiceHandler implements GUIHandler {

    private ViewGUI gui;

    // region FXML_Ids

    @FXML
    private AnchorPane gameChoice_pane;

    @FXML
    private ImageView gameChoice_imgView;

    @FXML
    private Button continue_button;

    @FXML
    private Button newGame_button;

    // endregion FXML_Ids

    /**
     * Method called on the click of "newGame_button"
     * Signals to the Server the will of the player of starting a new game
     */
    public void loadNewGame(){

    }

    /**
     * Method called on the click of "continueGame_button"
     * Signals to the Server the will of the player of continuing an old game
     */
    public void loadOldGame(){

    }

    /**
     * Sets the ViewGUI at which the ServerInfoHandler is related
     * @param gui the ViewGUI instance
     */
    @Override
    public void setGUI(ViewGUI gui) {
        this.gui = gui;
        // TODO: Check if the setGUI method is complete
    }
}
