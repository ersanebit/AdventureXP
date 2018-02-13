import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private void login(ActionEvent event) { // need to actually make a login and change resource to adminscreen
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("GUI/AdminScreen.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Activity");
            stage.setScene(new Scene(root));
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void customerScreen(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("GUI/CustomerMain.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Customer screen");
            stage.setScene(new Scene(root));
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
