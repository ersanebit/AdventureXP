import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javax.swing.text.TableView;

public class SweetsData {


    private ObservableList<Sweets> sweets = FXCollections.observableArrayList();

    public ObservableList<Sweets> getSweets() {
        return sweets;
    }

    public void loadSweets () {
        DBConn dbConn = new DBConn();
        sweets.setAll(dbConn.getSweets());
    }
}
