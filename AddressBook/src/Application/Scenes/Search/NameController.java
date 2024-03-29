package Application.Scenes.Search;

import Application.Constants;
import Application.Main;
import Application.Scenes.Contacts.DetailsController;
import Classes.Contact;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static Application.Window.WindowController.theatreViewport;

public class NameController implements Initializable {
    @FXML private TableView<Contact> tbvContactsList;
    @FXML private TableColumn<Contact, String> clmFirstName;
    @FXML private TableColumn<Contact, String> clmLastName;
    @FXML private TableColumn<Contact, String> clmHomePhone;
    @FXML private TableColumn<Contact, String> clmWorkPhone;
    @FXML private TableColumn<Contact, String> clmEmailAddress;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private Button btnSearch;

    @FXML
    private void searchContacts(MouseEvent event){
        String inputFirstName = txtFirstName.getText().toLowerCase();
        String inputLastName = txtLastName.getText().toLowerCase();

        if(inputFirstName.isEmpty() && inputLastName.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Required");
            alert.setHeaderText("Please enter a name to search.");
            alert.setContentText("Either a first name, last name or both is required to search by name.\nPlease input a name and try your search again.");
            alert.showAndWait();
        } else {
            clmFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            clmLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            clmHomePhone.setCellValueFactory(new PropertyValueFactory<>("homePhone"));
            clmWorkPhone.setCellValueFactory(new PropertyValueFactory<>("workPhone"));
            clmEmailAddress.setCellValueFactory(new PropertyValueFactory<>("email"));
            ArrayList<Contact> results = new ArrayList<>();

            for(Contact entry : Main.contactManager.getContacts()){
                String tempFirstName = entry.getFirstName().toLowerCase();
                String tempLastName = entry.getLastName().toLowerCase();
                boolean result = (tempFirstName.contains(inputFirstName) && !inputFirstName.equals("")) ||
                        (tempFirstName.contains(inputLastName) && !inputLastName.equals("")) ||
                        (tempLastName.contains(inputFirstName) && !inputFirstName.equals("")) ||
                        (tempLastName.contains(inputLastName) && !inputLastName.equals(""));
                if(result)
                    results.add(entry);
            }

            if(results.size() == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Results");
                alert.setHeaderText("No Results");
                alert.setContentText("No results found for: " + inputFirstName + " " + inputLastName);
                alert.showAndWait();
            } else {
                tbvContactsList.setItems(FXCollections.observableList(results));
            }
        }
    }

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
            theatreViewport.setCenter(parent);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
