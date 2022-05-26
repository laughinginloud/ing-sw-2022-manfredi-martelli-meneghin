package it.polimi.ingsw.client.view.gui.sceneHandlers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AskForRulesHandler {

    @FXML
    private AnchorPane askForRules_pane;

    @FXML
    private ImageView askForRules_imgView;

    @FXML
    private Label askForRules_label;

    @FXML
    private Spinner numOfPlayers_spinner;

    @FXML
    private Label numOfPlayers_label;

    @FXML
    private Label expertMode_label;

    @FXML
    private CheckBox expertMode_checkBox;


    private Stage stage;
    private Scene scene;
    private Parent root;
}
