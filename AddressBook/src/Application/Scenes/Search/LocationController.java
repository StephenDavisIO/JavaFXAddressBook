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
import java.util.Locale;
import java.util.ResourceBundle;

import static Application.Window.WindowController.theatreViewport;

public class LocationController implements Initializable {
    @FXML private TableView<Contact> tbvContactsList;
    @FXML private TableColumn<Contact, String> clmFirstName;
    @FXML private TableColumn<Contact, String> clmLastName;
    @FXML private TableColumn<Contact, String> clmHomePhone;
    @FXML private TableColumn<Contact, String> clmWorkPhone;
    @FXML private TableColumn<Contact, String> clmEmailAddress;
    @FXML private TextField txtInput;
    @FXML private RadioButton rdbCity;
    @FXML private RadioButton rdbProvince;
    @FXML private RadioButton rdbCountry;
    @FXML private RadioButton rdbPostalCode;

    @FXML
    private void searchContacts(MouseEvent event){
        String inputLocation = txtInput.getText().toLowerCase();

        if(inputLocation.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Required");
            alert.setHeaderText("Please enter a location to search.");
            alert.setContentText("Please input a location and try your search again.");
            alert.showAndWait();
        } else {
            ArrayList<Contact> results = new ArrayList<>();

            for(Contact entry : Main.contactManager.getContacts()){
                boolean result = false;
                if(rdbCity.isSelected())
                    result = entry.getHomeAddress().city.toLowerCase().contains(inputLocation);

                if(rdbProvince.isSelected())
                    result = entry.getHomeAddress().province.toLowerCase().contains(inputLocation);

                if(rdbCountry.isSelected())
                    result = entry.getHomeAddress().country.toLowerCase().contains(inputLocation);

                if(rdbPostalCode.isSelected())
                    result = entry.getHomeAddress().postalCode.toLowerCase().contains(inputLocation);

                if(result)
                    results.add(entry);
            }

            if(results.size() == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Results");
                alert.setHeaderText("No Results");
                alert.setContentText("No results found for: " + inputLocation);
                alert.showAndWait();
            } else {
                displayContactList(results);
            }
        }
    }

    private void displayContactList(ArrayList<Contact> list){
        clmFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        clmLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        clmHomePhone.setCellValueFactory(new PropertyValueFactory<>("homePhone"));
        clmWorkPhone.setCellValueFactory(new PropertyValueFactory<>("workPhone"));
        clmEmailAddress.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbvContactsList.setItems(FXCollections.observableList(list));
    }

    @FXML
    private void viewDetails(MouseEvent event) throws IOException {
        Contact contact = tbvContactsList.getSelectionModel().getSelectedItem();
        String path = Constants.PATH_TO_SCENES + "Contacts/Details.fxml";
        TableRow<Contact> row = new TableRow<>();
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
