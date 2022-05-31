package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.gui.ViewGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameSceneHandler implements GUIHandler {
    //TODO see if it's possible to center correctly the scene when launched

    // TODO regions and JavaDocs
    // TODO Ids of panes
    private ViewGUI gui;

    @FXML
    private BorderPane border_pane;

    @FXML
    private AnchorPane game_pane;

    @FXML
    private AnchorPane sky_pane;

    @FXML
    private AnchorPane islands_pane;

    @FXML
    private AnchorPane isl0_pane;

    @FXML
    private AnchorPane isl1_pane;

    @FXML
    private AnchorPane isl2_pane;

    @FXML
    private AnchorPane isl3_pane;

    @FXML
    private AnchorPane isl4_pane;

    @FXML
    private AnchorPane isl5_pane;

    @FXML
    private AnchorPane isl6_pane;

    @FXML
    private AnchorPane isl7_pane;

    @FXML
    private AnchorPane isl8_pane;

    @FXML
    private AnchorPane isl9_pane;

    @FXML
    private AnchorPane isl10_pane;

    @FXML
    private AnchorPane isl11_pane;

    @FXML
    private GridPane cloudTiles_grid;

    @FXML
    private AnchorPane grid00_pane;

    @FXML
    private AnchorPane cloudTile0_pane;

    // TODO Full implementation

    /**
     * @param gui
     */
    @Override
    public void setGUI(ViewGUI gui) {
        this.gui = gui;
    }

    // Example of access to the nodes using the filter function
    // pane.getchildren().stream().filter( x -> x instanceOf ...).
}
