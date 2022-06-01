package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.Address;
import it.polimi.ingsw.client.view.gui.Pages;
import it.polimi.ingsw.client.view.gui.ViewGUI;
import it.polimi.ingsw.common.viewRecord.UsernameAndMagicAge;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.HashSet;
import java.util.Set;

/**
 * Handler (or Controller) of the scene ClientInfo (clientInfoPage.fxml)
 * The client is asked for a username (different from the forbiddenAddress)
 * and for how many years has he know magic (MagicAge) and sends it to the
 * VirtualController
 * @author Giovanni Manfredi
 */
public class ClientInfoHandler implements GUIHandler {

    private ViewGUI gui;

    private Set<String> forbiddenUsernames;

    // region FXML_Ids

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

    // endregion FXML_Ids

    /**
     *  Method called on the click of "submit_button" which takes care
     *  of checking if the submitted data is correct.
     */
    public void submitClientInfo(){

        Alert submitAgain = null;

        // Reads the username entered sanitizing it and raising an alert if it's incorrect
        String readUsername = UsernameAndMagicAge.sanitizeUsername(username_field.getText());
        if (readUsername == null || (readUsername.length() <= 0 || readUsername.length() >= 20 ) ||
            forbiddenUsernames.contains(readUsername)) {

            submitAgain = GUIAlert.getAlert(GUIAlert.INVALID_USERNAME, readUsername);
        }

        // Reads the magicAge entered parsing it and raising an alert if it's incorrect
        String readMagicAge = magicAge_field.getText();
        int magicAgeInt = 1000;
        if (submitAgain == null && readMagicAge != null){
            // Otherwise, read the magicAge and try to parse it
            try {
                magicAgeInt = Integer.parseInt(readMagicAge);

                // Filter for negative numbers (years), that will not be accepted
                if (magicAgeInt < 0) {
                    submitAgain = GUIAlert.getAlert(GUIAlert.INVALID_MAGICAGE, readMagicAge);
                }
            }

            // If the magicAge cannot be parsed, raise and alert
            catch (NumberFormatException ignored) {
                submitAgain = GUIAlert.getAlert(GUIAlert.INVALID_MAGICAGE, readMagicAge);
            }
        }
        else if (readMagicAge == null) {
            submitAgain = GUIAlert.getAlert(GUIAlert.INVALID_MAGICAGE, null);
        }

        if(submitAgain != null) {
            // Clear fields
            username_field.setText("");
            magicAge_field.setText("");

            submitAgain.showAndWait();
        }

        else {
            gui.forwardViewToVirtualController(new UsernameAndMagicAge(readUsername, magicAgeInt));
        }
    }

    /**
     * Sets the Set of usernames that the client that can't choose
     * @param forbiddenUsernames the set of forbidden names
     */
    public void setForbiddenUsernames(Set<String> forbiddenUsernames){
        this.forbiddenUsernames = forbiddenUsernames;
    }

    /**
     * Sets the ViewGUI at which the ServerInfoHandler is related
     * @param gui the ViewGUI instance
     */
    @Override
    public void setGUI(ViewGUI gui) {
        this.gui = gui;
    }
}
