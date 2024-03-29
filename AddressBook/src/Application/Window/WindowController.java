package Application.Window;

import Application.Constants;
import Application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class WindowController implements Initializable {
    public static BorderPane theatreViewport;
    @FXML private BorderPane theatre;
    @FXML private AnchorPane stageHome;

    @FXML
    private void homeScreen(MouseEvent event){
        theatre.setCenter(stageHome);
    }

    @FXML
    private void addContact(MouseEvent event){
        loadScene("Contacts/Add.fxml");
    }

    @FXML
    private void searchName(MouseEvent event){ loadScene("Search/Name.fxml"); }

    @FXML
    private void searchLocation(MouseEvent event){ loadScene("Search/Location.fxml"); }

    @FXML
    private void viewContacts(MouseEvent event){
        loadScene("Contacts/Index.fxml");
    }

    @FXML
    private void aboutApplication(MouseEvent event){
        loadScene("About/Index.fxml");
    }

    @FXML
    private void exitApplication(MouseEvent event) {
        loadScene("Exit/Index.fxml");
    }

    public void loadScene(String scene) {
        String url = Constants.PATH_TO_SCENES + scene;
        try {
            Parent root = FXMLLoader.load(getClass().getResource(url));
            theatreViewport.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        theatreViewport = theatre;
    }
}
