package Application;

import Classes.ContactManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class Main extends Application {
    public static ContactManager contactManager;

    @Override
    public void start(Stage primaryStage) throws Exception{
        String destinationPath = Constants.PATH_TO_IMAGES + "stonecutters.png";
        Image icon = new Image(destinationPath);
        contactManager = new ContactManager();
        contactManager.loadContactsFromFile();
        Parent root = FXMLLoader.load(getClass().getResource("Window/Window.fxml"));
        primaryStage.setTitle(Constants.APPLICATION_NAME);
        primaryStage.setScene(new Scene(root, Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(icon);
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
}

