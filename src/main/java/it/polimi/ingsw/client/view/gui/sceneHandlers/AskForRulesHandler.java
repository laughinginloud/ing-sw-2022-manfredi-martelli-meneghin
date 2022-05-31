package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.gui.Pages;
import it.polimi.ingsw.client.view.gui.ViewGUI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AskForRulesHandler implements GUIHandler, Initializable {

    private ViewGUI gui;

    // region FXML_Ids

    @FXML
    private AnchorPane askForRules_pane;

    @FXML
    private ImageView askForRules_imgView;

    @FXML
    private Label askForRules_label;

    @FXML
    private Spinner<Integer> numOfPlayers_spinner;

    @FXML
    private Label numOfPlayers_label;

    @FXML
    private Label expertMode_label;

    @FXML
    private CheckBox expertMode_checkBox;

    @FXML
    private Button askRules_submit;

    // endregion FXML_Ids

    /**
     * Method called on the click of "askRules_submit" and
     * gets the inserted data in the fields (default 2 players and ExperMode = false)
     */
    public void submitGameRules(){
        int readNumOfPlayers = numOfPlayers_spinner.getValue();
        boolean readExpertMode = expertMode_checkBox.isSelected();

        // gui.sendInformationToVirtualControllerGameRulse(readNumOfPlayers, readExpertMode)

        // ******************************************
        // Code used only for testing the application
        gui.changeScene(Pages.WIZARD_CHOICE);
        // ******************************************
    }

    /**
     * Implementation of the interface Initializable. Needed to initialize the Spinner to a
     * defaul set of integers [2,4]
     * @param url resource url
     * @param resourceBundle bundle of the resource
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Sets a factory integers between 2 and 4
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2,4);
        // Sets default value
        valueFactory.setValue(2);

        // Assigns the spinnerFactory to the spinner
        numOfPlayers_spinner.setValueFactory(valueFactory);
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
