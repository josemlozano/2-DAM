/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examreminder;

import examreminder.utils.FileUtils;
import examreminder.utils.MessageUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jose Muñoz
 */
public class FXMLLoginViewController implements Initializable {

    @FXML
    private TextField textFieldLogin;
    @FXML
    private PasswordField passwordFieldPass;
    @FXML
    private Button bLogin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void login(ActionEvent event) throws IOException {
        String textLogin = textFieldLogin.getText();
        String textPass = org.apache.commons.codec.digest.DigestUtils.
                sha256Hex(passwordFieldPass.getText());
        if (FileUtils.read(textLogin, textPass)) {
            Parent view1 = FXMLLoader.load(
                    FXMLMainViewController.class.getResource("/fxml/FXMLMainView.fxml"));
            Scene view1Scene = new Scene(view1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.hide();
            stage.setScene(view1Scene);
            stage.show();
        } 
        else {
            MessageUtils.showError(textPass);
        }
    }

}
