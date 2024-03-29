package Application.Scenes.Contacts;

import Application.Constants;
import Classes.Address;
import Classes.Contact;
import Classes.MyDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ResourceBundle;

import static Application.Constants.CSV_NULL;
import static Application.Constants.PATH_TO_IMAGES;
import static Application.Helpers.*;
import static Application.Main.contactManager;
import static Application.Window.WindowController.theatreViewport;

public class ModifyController implements Initializable {
    private Contact contact;
    private File contactImageFile;
    private final String defaultImagePath = PATH_TO_IMAGES + "default.png";
    @FXML
    private AnchorPane modifyAP;
    @FXML private TextField txtFirstName;
    @FXML private Label lblFirstName;
    @FXML private TextField txtLastName;
    @FXML private Label lblLastName;
    @FXML private ComboBox<Integer> ccbDay;
    @FXML private ComboBox<String> ccbMonth;
    @FXML private ComboBox<Integer> ccbYear;
    @FXML private TextField txtHomePhone;
    @FXML private Label lblHomePhone;
    @FXML private TextField txtWorkPhone;
    @FXML private Label lblWorkPhone;
    @FXML private TextField txtEmailAddress;
    @FXML private Label lblEmailAddress;
    @FXML private TextField txtAddressLineOne;
    @FXML private Label lblAddressLineOne;
    @FXML private TextField txtAddressLineTwo;
    @FXML private TextField txtCity;
    @FXML private Label lblCity;
    @FXML private TextField txtProvince;
    @FXML private Label lblProvince;
    @FXML private TextField txtCountry;
    @FXML private Label lblCountry;
    @FXML private TextField txtPostalCode;
    @FXML private Label lblPostalCode;
    @FXML private TextField txtNotes;
    @FXML private Label lblPhoto;
    @FXML private ImageView imgContact;

    @FXML
    private void uploadImage(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a Profile Picture");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("PNG Files Only (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(filter);
        Stage window = (Stage)modifyAP.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(window);
        if(selectedFile != null){
            Image image;
            try {
                image = new Image(selectedFile.toURI().toString());
            } catch (IllegalArgumentException e) {

                image = new Image(defaultImagePath);
            }
            imgContact.setImage(image);
            contactImageFile = selectedFile;
        }
    }

    @FXML
    private void createContact(ActionEvent event) throws IOException {
        dataProcessor("create");
    }

    @FXML
    private void updateContact(ActionEvent event) throws IOException {
        dataProcessor("update");
    }

    public void saveImage(String contactId) throws IOException {
        if(contactImageFile != null){
            String imageName = contactId + ".png";
            String destinationPath = new File("Profiles").getAbsolutePath() + "\\";
            File userImage = new File(destinationPath + "\\" + imageName);
            File tempImage = new File(destinationPath + "\\" + imageName);
            userImage.createNewFile();
            tempImage.createNewFile();
            FileChannel source = null;
            FileChannel destination = null;
            try {
                source = new FileInputStream(contactImageFile).getChannel();
                destination = new FileOutputStream(tempImage).getChannel();
                destination.transferFrom(source, 0, source.size());
            }
            finally {
                if(source != null)
                    source.close();
                if(destination != null)
                    destination.close();
            }
            tempImage.renameTo(userImage);
        }
    }

    public void loadContactDetails(Contact input){
        contact = input;
        txtFirstName.setText(contact.getFirstName());
        txtLastName.setText(contact.getLastName());
        txtHomePhone.setText(contact.getHomePhone());
        txtWorkPhone.setText(contact.getWorkPhone());
        txtEmailAddress.setText(contact.getEmail());
        txtAddressLineOne.setText(contact.getHomeAddress().streetInfo1);
        txtAddressLineTwo.setText(contact.getHomeAddress().streetInfo2);
        txtCity.setText(contact.getHomeAddress().city);
        txtProvince.setText(contact.getHomeAddress().province);
        txtCountry.setText(contact.getHomeAddress().country);
        txtPostalCode.setText(contact.getHomeAddress().postalCode);
        txtNotes.setText(contact.getNotes());

        int day = contact.getBirthday().getDay();
        if(day == 0)
            ccbDay.setValue(null);
        else
            ccbDay.setValue(day);

        String month = contact.getBirthday().getMonthLongForm();
        if(month.equals(CSV_NULL) || month.isEmpty())
            ccbMonth.setValue(null);
        else
            ccbMonth.setValue(month);

        int year = contact.getBirthday().getYear();
        if(year == 0)
            ccbYear.setValue(null);
        else
            ccbYear.setValue(year);

        String fileName = contact.getContactId() + ".png";
        String destinationPath = "file:\\" + new File("Profiles").getAbsolutePath() + "\\" + fileName;
        Image image;
        try {
            image = new Image(destinationPath);
        } catch (IllegalArgumentException e) {
            String defaultImage = PATH_TO_IMAGES + "default.png";
            image = new Image(defaultImage);
        }
        imgContact.setImage(image);
    }

    private void dataProcessor(String callerMethod) throws IOException {
        String strFirstName = txtFirstName.getText();
        String strLastName = txtLastName.getText();
        if(strFirstName.isEmpty() && strLastName.isEmpty()){
            showInputError(lblFirstName);
            showInputError(lblLastName);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Either a First Name or Last Name is required for a contact.\nPlease enter either one to continue.");
            alert.showAndWait();
        } else {
            String strHomePhone = txtHomePhone.getText();
            String strWorkPhone = txtWorkPhone.getText();
            String strEmailAddress = txtEmailAddress.getText();
            String strLineOne = txtAddressLineOne.getText();
            String strLineTwo = txtAddressLineTwo.getText();
            String strCity = txtCity.getText();
            String strProvince = txtProvince.getText();
            String strCountry = txtCountry.getText();
            String strPostalCode = txtPostalCode.getText();
            String strNotes = txtNotes.getText();
            Integer numDay = ccbDay.getSelectionModel().getSelectedItem();
            String tempMonth = ccbMonth.getSelectionModel().getSelectedItem();
            int numMonth;
            Integer numYear = ccbYear.getSelectionModel().getSelectedItem();

            if (tempMonth != null)
                numMonth = getMonthNumber(tempMonth);
            else
                numMonth = 0;

            if (strLineOne.isEmpty() && !strLineTwo.isEmpty()) {
                strLineOne = strLineTwo;
                strLineTwo = "";
            }

            //copyImage();

            Contact temporaryContact = null;

            MyDate birthday = null;
            if(numDay != null && numMonth != 0 && numYear != null)
                birthday = new MyDate(numDay, numMonth, numYear);
            else
                birthday = new MyDate(0,0,0);

            Address address = new Address(strLineOne, strLineTwo, strCity, strPostalCode, strProvince, strCountry);

            if (callerMethod.equalsIgnoreCase("create")) {
                temporaryContact = contactManager.newContact(strFirstName, strLastName, strHomePhone, strWorkPhone, address, strEmailAddress, birthday, strNotes);
                saveImage(temporaryContact.getContactId());
                contactManager.saveContactsToFile(false);
                viewContactDetails(temporaryContact);
            } else if (callerMethod.equalsIgnoreCase("update")) {
                temporaryContact = contactManager.generateTemporaryContact(contact.getContactId(), strFirstName, strLastName, strHomePhone, strWorkPhone, address, strEmailAddress, birthday, strNotes);
                saveImage(temporaryContact.getContactId());
                boolean isContactUpdated = contactManager.updateContact(contact, temporaryContact);
                boolean isSaved = contactManager.saveContactsToFile(false);
                if (isContactUpdated && isSaved) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Contact Updated");
                    alert.setHeaderText(strFirstName + " has been updated");
                    alert.setContentText("This contact has been successfully updated in the program.\n" +
                            "Please check the contacts list to view the change.");
                    alert.showAndWait();
                    viewContactDetails(temporaryContact);
                }
            }
        }
    }

    private void viewContactDetails(Contact temporaryContact) throws IOException {
        String url = Constants.PATH_TO_SCENES + "Contacts/Details.fxml";
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(url));
        Parent parent = loader.load();
        DetailsController controller = loader.getController();
        controller.contactDetails(temporaryContact);
        theatreViewport.setCenter(parent);
    }

    private void showInputError(Label name){
        name.setTextFill(Color.rgb(255, 0, 0));
        name.setStyle("-fx-font-weight: bold");
        if(name.equals(lblPhoto))
            name.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ccbDay.getItems().setAll(generateDays());
        ccbMonth.getItems().setAll(generateMonths());
        ccbYear.getItems().setAll(generateYears());
    }
}
