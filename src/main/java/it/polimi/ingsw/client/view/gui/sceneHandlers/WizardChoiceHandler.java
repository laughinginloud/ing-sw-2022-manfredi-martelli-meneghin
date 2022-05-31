package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.gui.ViewGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class WizardChoiceHandler implements GUIHandler {
    private ViewGUI gui;

    // TODO JavaDocs + Regions
    // TODO Implementation

    @FXML
    private AnchorPane wizardChoice_pane;

    @FXML
    private ImageView wizardChoice_imgView;

    @FXML
    private AnchorPane wizardChoice_subPane;

    @FXML
    private HBox wizardChoice_hbox;

    @FXML
    private AnchorPane wizard0_pane;

    @FXML
    private ImageView wizard0_img;

    @FXML
    private AnchorPane wizard1_pane;

    @FXML
    private ImageView wizard1_img;

    @FXML
    private AnchorPane wizard2_pane;

    @FXML
    private ImageView wizard2_img;

    @FXML
    private AnchorPane wizard3_pane;

    @FXML
    private ImageView wizard3_img;

    @FXML
    private Label wizardChoice_titleLabel;

    public void testMethod(){
        // How to de-saturate a card "Not playable"
        Effect notAvailableCard = (ColorAdjust) new ColorAdjust(0,-1.0,-0.3,0);
        wizard0_img.effectProperty().set(notAvailableCard);
        // How to make it not clickable
        wizard0_img.setOnMouseClicked(null);
    }

    /**
     * @param gui
     */
    @Override
    public void setGUI(ViewGUI gui) {
        this.gui = gui;
    }
}
