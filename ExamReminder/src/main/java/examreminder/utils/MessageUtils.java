package examreminder.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Jose Muñoz
 */
public class MessageUtils {
    public static void showError (String message){
        Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error");
            dialog.setHeaderText("Error validating user");
            dialog.setContentText("User and/or password are not correct");
            dialog.showAndWait();
    }
    
    public static void showMessage (String message) {
        String[] messages = message.split(" ## ");
        Alert dialog = new Alert(AlertType.INFORMATION);
        dialog.setTitle("Alert Dialog");
        dialog.setHeaderText(messages[0]);
        dialog.setContentText(messages[1]);
        dialog.showAndWait();
    }
}
