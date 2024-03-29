package Application.Scenes.Contacts;

import Application.Constants;
import Application.Main;
import Classes.Contact;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IndexController implements Initializable {
    @FXML private BorderPane stageContacts;
    @FXML private TableView<Contact> tbvContactsList;
    @FXML private TableColumn<Contact, String> clmFirstName;
    @FXML private TableColumn<Contact, String> clmLastName;
    @FXML private TableColumn<Contact, String> clmHomePhone;
    @FXML private TableColumn<Contact, String> clmWorkPhone;
    @FXML private TableColumn<Contact, String> clmEmailAddress;

    @FXML
    private void viewDetails(MouseEvent event) throws IOException {
        Contact contact = tbvContactsList.getSelectionModel().getSelectedItem();
        String path = Constants.PATH_TO_SCENES + "Contacts/Details.fxml";
        if (event.getClickCount() > 1) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            Parent parent = loader.load();
            DetailsController controller = loader.getController();
            controller.contactDetails(contact);
            stageContacts.setCenter(parent);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // setup the columns in the table
        clmFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        clmLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        clmHomePhone.setCellValueFactory(new PropertyValueFactory<>("homePhone"));
        clmWorkPhone.setCellValueFactory(new PropertyValueFactory<>("workPhone"));
        clmEmailAddress.setCellValueFactory(new PropertyValueFactory<>("email"));

        // load contacts into table
        tbvContactsList.setItems(FXCollections.observableList(Main.contactManager.getContacts()));
    }
}
