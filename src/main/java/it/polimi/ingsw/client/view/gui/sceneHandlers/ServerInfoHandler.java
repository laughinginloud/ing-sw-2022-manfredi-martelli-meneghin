package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.Address;
import it.polimi.ingsw.client.view.gui.Pages;
import it.polimi.ingsw.client.view.gui.ViewGUI;
import it.polimi.ingsw.client.virtualController.VirtualController;
import it.polimi.ingsw.common.model.Color;
import it.polimi.ingsw.common.utils.Tuple;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Handler (or Controller) of the scene ServerInfo (serverInfoPage.fxml)
 * The client is asked for an IP Address and a port to connect to.
 * @author Giovanni Manfredi
 */
public class ServerInfoHandler implements GUIHandler {

    private ViewGUI gui;

    // region FXML_Ids

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

    // endregion FXML_Ids

    /**
     * Method called on the click of "submit_button" which takes care
     * of checking if the submitted data is correct. If so it sends
     * the information to the VirtualController
     */
    public void submitServerInfo() {
        // Reads an IP, sanitizing it in the process as the unsanitized version is currently useless
        String readIP     = Address.sanitizeIP(serverIP_field.getText());
        Alert submitAgain = null;

        // If the IP is incorrect, give an alert and then end the function
        if (!Address.checkIP(readIP))
            submitAgain = GUIAlert.getAlert(GUIAlert.INVALID_IP, readIP);

        // Reads a Port, trying to parse it
        String readPort = serverPort_field.getText();
        int portInt     = 0;

        if (submitAgain == null && readPort != null) {
            // Otherwise, read the port and try to parse it
            try {
                portInt = Integer.parseInt(readPort);

                // Filters for well known ports, that will not be accepted
                if (!(portInt > 1023 && portInt < 65536)) {
                    submitAgain = GUIAlert.getAlert(GUIAlert.INVALID_PORT, readPort);
                }
            }

            // If the port cannot be parsed, raise an alert
            catch (NumberFormatException ignored) {
                submitAgain = GUIAlert.getAlert(GUIAlert.INVALID_PORT, readPort);
            }
        }
        else if (readPort == null)
            submitAgain = GUIAlert.getAlert(GUIAlert.INVALID_PORT, null);

        if(submitAgain != null) {
            // Clears fields
            serverIP_field.setText("");
            serverPort_field.setText("");

            submitAgain.showAndWait();
        }

        else {
            try{
                gui.setVirtualController(new VirtualController(new Address(readIP, portInt), gui));
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets the ViewGUI at which the ServerInfoHandler is related
     * @param gui the ViewGUI instance
     */
    public void setGUI(ViewGUI gui) {
        this.gui = gui;
    }
}
