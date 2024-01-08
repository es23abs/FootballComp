
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.*;

public class Alerts {
    public void infoAlert(String alertMsg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Alert!!!!.");
        alert.setHeaderText(null);
        alert.setContentText(alertMsg);
        alert.showAndWait();
    }

    public boolean confirmationAlert(String alertMsg) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Alert!!!!");
        alert.setHeaderText(null);
        alert.setContentText(alertMsg);
        Optional<ButtonType> result = alert.showAndWait();
        if (!result.isPresent() || result.get() != ButtonType.OK) {
            return false;
        }
        return true;
    }

    // if any field is empty then show return true
    public boolean isEmptyField(TextField[] textFields) {
        for (TextField field : textFields) {
            if (field.getText().equals(""))
                return true;
        }
        return false;
    }
}
