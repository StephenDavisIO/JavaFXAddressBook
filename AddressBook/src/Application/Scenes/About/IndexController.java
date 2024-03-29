package Application.Scenes.About;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class IndexController implements Initializable {
    @FXML
    private void order66(MouseEvent event){
        System.exit(66);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
