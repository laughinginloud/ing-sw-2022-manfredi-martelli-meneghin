package it.polimi.ingsw.client.view.gui.sceneHandlers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ServerInfoHandler {

    @FXML
    private AnchorPane serverInfo_pane;

    @FXML
    private ImageView serverInfo_imgView;

    @FXML
    private Label serverIP_label;

    @FXML
    private TextField serverIP_field;

    @FXML
    private Label serverPort_label;

    @FXML
    private TextField serverPort_field;

    @FXML
    private Button serverInfo_submit;

}
