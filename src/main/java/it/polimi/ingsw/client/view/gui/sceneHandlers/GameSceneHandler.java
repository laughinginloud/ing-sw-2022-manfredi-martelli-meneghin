package it.polimi.ingsw.client.view.gui.sceneHandlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameSceneHandler {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToClientInfoPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("clientInfoPage.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Example of access to the nodes using the filter function
    // pane.getchildren().stream().filter( x -> x instanceOf ...).
}
