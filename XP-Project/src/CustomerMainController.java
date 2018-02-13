import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sun.plugin.javascript.navig.Anchor;

import java.io.IOException;

public class CustomerMainController {

    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private AnchorPane anchor;


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
    private void bookingOpen(){
        changeAnchor("GUI/CustomerScreen.fxml");
    }
    @FXML
    private void shopOpen(){
        changeAnchor("GUI/Shop.fxml");
    }
}
