package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.gui.ViewGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class InitialHandler implements GUIHandler{
    private ViewGUI gui;

    // region FXML_Ids

    @FXML
    private AnchorPane initial_pane;

    @FXML
    private ImageView initial_imgView;

    @FXML
    private Button play_button;

    // endregion FXML_Ids

    /**
     *
     * @param gui
     */
    @Override
    public void setGUI(ViewGUI gui) {
        this.gui = gui;
    }
}
