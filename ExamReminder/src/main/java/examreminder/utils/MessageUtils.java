package examreminder.utils;

import javafx.scene.control.Alert;

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
        
    }
}
