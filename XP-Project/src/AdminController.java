import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminController {

    @FXML
    private AnchorPane anchor;
    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private void bookingScreen(ActionEvent e) {
        changeAnchor("GUI/BookingSubScreen.fxml");
    }

    @FXML
    private void activityScreen(ActionEvent e) {
        changeAnchor("GUI/ActivitySubScreen.fxml");
    }

    @FXML
    private void statisticScreen(ActionEvent e) {
        changeAnchor("GUI/StatsSubScreen.fxml");
    }

    @FXML
    private void instructorScreen(ActionEvent e) {
        changeAnchor("GUI/InstructorSubScreen.fxml");
    }

    @FXML
    private void logout(ActionEvent e) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("GUI/LoginScreen.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Login screen");
            stage.setScene(new Scene(root));
            stage.show();
            mainAnchor.getScene().getWindow().hide();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void changeAnchor(String path) {
        try {
            AnchorPane tempAnchor = FXMLLoader.load(getClass().getResource(path));
            anchor.getChildren().setAll(tempAnchor);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    private void shopOpen(){
        changeAnchor("GUI/CandySodas.fxml");
    }

}

