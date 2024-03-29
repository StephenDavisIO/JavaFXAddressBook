package Application.Scenes.Contacts;

import Application.Constants;
import Classes.Contact;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

import static Application.Constants.CSV_NULL;
import static Application.Main.contactManager;
import static Application.Window.WindowController.theatreViewport;

public class DetailsController implements Initializable {
    private Contact contact;
    private String contactFullName;

    @FXML private BorderPane stageContacts;
    @FXML private AnchorPane detailsStage;
    @FXML private Label lblFullName;
    @FXML private Label lblFirstName;
    @FXML private Label lblLastName;
    @FXML private Label lblHomePhone;
    @FXML private Label lblWorkPhone;
    @FXML private Label lblEmailAddress;
    @FXML private Label lblBirthday;
    @FXML private Label lblCurrentAge;
    @FXML private TextField lblAddress;
    @FXML private TextArea lblNotes;
    @FXML private ImageView imgContact;

    @FXML
    private void editContact() throws IOException {
        String path = Constants.PATH_TO_SCENES + "Contacts/Edit.fxml";
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        Parent parent = loader.load();
        ModifyController controller = loader.getController();
        controller.loadContactDetails(contact);
        theatreViewport.setCenter(parent);
    }

    @FXML
    private void deleteContact() throws IOException {
        boolean status = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Contact");
        alert.setHeaderText("Delete Contact: " + contactFullName);
        alert.setContentText("Would you like to delete this contact? This action is permanent and can not be undone.");
        Optional<ButtonType> selection = alert.showAndWait();
        if(selection.get() == ButtonType.OK)
            status = contactManager.deleteContact(contact);
        if(status){
            Alert prompt = new Alert(Alert.AlertType.INFORMATION);
            prompt.setTitle("Contact Deleted");
            prompt.setHeaderText("Contact Deleted");
            prompt.setContentText(contactFullName + " has been deleted.");
            prompt.showAndWait();
            try {
                String url = Constants.PATH_TO_SCENES + "Contacts/Index.fxml";
                Parent root = FXMLLoader.load(getClass().getResource(url));
                theatreViewport.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void contactDetails(Contact input) throws MalformedURLException {
        contact = input;
        String notRecorded = "Not Recorded";
        String contactId = contact.getContactId();
        String firstName = contact.getFirstName();
        String lastName = contact.getLastName();
        String homePhone = contact.getHomePhone();
        String workPhone = contact.getWorkPhone();
        String email = contact.getEmail();
        String address = contact.getHomeAddress().toString();
        String notes = contact.getNotes();
        int day = contact.getBirthday().getDay();
        String monthShort = contact.getBirthday().getMonthShortForm();
        String monthLong = contact.getBirthday().getMonthLongForm();
        int year = contact.getBirthday().getYear();

        if(day != 0 && !monthShort.equals(CSV_NULL) && year != 0){
            try {
                String birthdayShort = day + "-" + monthShort + "-" + year;
                String birthdayLong = day + " " + monthLong + " " + year;
                SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
                Instant instant = format.parse(birthdayShort).toInstant();
                ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
                LocalDate date = zone.toLocalDate();
                int age = Period.between(date, LocalDate.now()).getYears();
                lblCurrentAge.setText(age + " years old");
                lblBirthday.setText(birthdayLong);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if(day == 0 && monthShort.equals(CSV_NULL) && year == 0) {
            lblCurrentAge.setText("Age Unknown");
            lblBirthday.setText(notRecorded);
        } else {
            lblCurrentAge.setText("Age Unknown");
            String output = "";
            if(day != 0)
                output += (day + " ");
            if(!monthLong.equals(CSV_NULL))
                output += (monthLong + " ");
            if(year != 0)
                output += year;
            lblBirthday.setText(output);
        }

        if(!firstName.equals(""))
            lblFirstName.setText(firstName);
        else
            lblFirstName.setText(notRecorded);

        if(!lastName.equals(""))
            lblLastName.setText(lastName);
        else
            lblLastName.setText(notRecorded);

        if(!homePhone.equals(""))
            lblHomePhone.setText(homePhone);
        else
            lblHomePhone.setText(notRecorded);

        if(!workPhone.equals(""))
            lblWorkPhone.setText(workPhone);
        else
            lblWorkPhone.setText(notRecorded);

        if(!email.equals(""))
            lblEmailAddress.setText(email);
        else
            lblEmailAddress.setText(notRecorded);

//        if(contact.getHomeAddress().streetInfo1.equals("") && contact.getHomeAddress().city.equals("") && contact.getHomeAddress().province.equals("") && contact.getHomeAddress().country.equals("") && contact.getHomeAddress().postalCode.equals(""))
//            lblAddress.setText(notRecorded);
//        else
//            lblAddress.setText(address);

        lblAddress.setText(address);

        if(!notes.equals(""))
            lblNotes.setText(notes);
        else
            lblNotes.setText(notRecorded);

        contactFullName = contact.getFirstName() + " " + contact.getLastName();
        lblFullName.setText(contactFullName);

        String fileName = contact.getContactId() + ".png";
        String destinationPath = "file:\\" + new File("Profiles").getAbsolutePath() + "\\" + fileName;

        Image image;

        try {
            image = new Image(destinationPath);

            if(image.errorProperty().getValue()){
                String defaultImage = Constants.PATH_TO_IMAGES + "default.png";
                image = new Image(defaultImage);
            }
        } catch (IllegalArgumentException e) {
            String defaultImage = Constants.PATH_TO_IMAGES + "default.png";
            image = new Image(defaultImage);
        }

        imgContact.setImage(image);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
