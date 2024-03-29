package Application.Scenes.Exit;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static Application.Main.contactManager;

public class IndexController implements Initializable {
    @FXML
    private void order66(MouseEvent event) throws IOException {
        boolean isSaved = contactManager.saveContactsToFile(false);
        if(isSaved){
            Alert alert = new Alert((Alert.AlertType.INFORMATION));
            alert.setTitle("Exit Application");
            alert.setHeaderText("Have a great day!");
            alert.setContentText("Your contacts list has been saved.");
            alert.showAndWait();
            Platform.exit();
        } else {
            Alert alert = new Alert((Alert.AlertType.WARNING));
            alert.setTitle("Exit Application");
            alert.setHeaderText("WARNING - CONTACTS NOT SAVED");
            alert.setContentText("Something went wrong and your contacts could not be saved.\nAre you sure you want to exit? All data will be lost.");
            Optional<ButtonType> input = alert.showAndWait();
            if(input.get() == ButtonType.OK)
                Platform.exit();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
