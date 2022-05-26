package it.polimi.ingsw.client.view.gui.sceneHandlers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ClientInfoHandler {

    @FXML
    private AnchorPane clientInfo_pane;

    @FXML
    private ImageView clientInfo_imgView;

    @FXML
    private Label username_label;

    @FXML
    private TextField username_field;

    @FXML
    private Label magicAge_label;

    @FXML
    private TextField magicAge_field;

    @FXML
    private Button clientInfo_submit;
}
