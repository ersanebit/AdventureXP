import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class SellingController {


    public TableView<Sweets> productsTbl;
    @FXML
    public TableColumn productClmn;
    @FXML
    public TableColumn unitPriceClmn;
    @FXML
    public TableView<SweetsQuan> selectedTbl;
    @FXML
    public TableColumn selectedProductClmn;
    @FXML
    public TableColumn quanClmn;
    @FXML
    public TableColumn selectedsPriceClmn;
    @FXML
    public TextField idField;
    @FXML
    public TextField phoneField;
    @FXML
    public Label msgLbl;
    @FXML
    public Button cancelBtn;
    @FXML
    public Button purchaseBtn;

    private ObservableList<Sweets> availProducts = FXCollections.observableArrayList();
    private ObservableList<SweetsQuan> selectedProducts = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        productClmn.setCellValueFactory(new PropertyValueFactory<Activity, String>("Name"));
        unitPriceClmn.setCellValueFactory(new PropertyValueFactory<Activity, Double>("Price"));

        selectedProductClmn.setCellValueFactory(new PropertyValueFactory<Activity, String>("Name"));
        quanClmn.setCellValueFactory(new PropertyValueFactory<Activity, Integer>("Quantity"));
        selectedsPriceClmn.setCellValueFactory(new PropertyValueFactory<Activity, Double>("Price"));

        SweetsData sweetsData = new SweetsData();
        sweetsData.loadSweets();
        availProducts = sweetsData.getSweets();
        productsTbl.setItems(availProducts);
    }

    @FXML
    public void productsClick(MouseEvent mouseEvent) {

        Sweets sweet = productsTbl.getSelectionModel().getSelectedItem();

        if (sweet == null) {
            return;
        }

        for (SweetsQuan sweetQuan: selectedProducts) {

            if (sweetQuan.getSweet().getId() == sweet.getId()) {

                sweetQuan.addProduct();
                updateSelectedTable();
                return;
            }
        }

        selectedProducts.add(new SweetsQuan(sweet));
        updateSelectedTable();
    }

    @FXML
    public void selectedClick(MouseEvent mouseEvent) {

        SweetsQuan quan = selectedTbl.getSelectionModel().getSelectedItem();

        if (quan == null) {
            return;
        }

        if (quan.subtractProduct() <= 0) {

            int loops = selectedProducts.size();
            for (int i = 0; i < loops; i++) {

                if (selectedProducts.get(i) == quan) {

                    selectedProducts.remove(i);
                    break;
                }
            }
        }
        updateSelectedTable();
    }

    public void cancelClick(ActionEvent actionEvent) {

        msgLbl.setText("");
        idField.setText("");
        phoneField.setText("");

        selectedProducts = FXCollections.observableArrayList();
        updateSelectedTable();
    }

    public void purchaseClick(ActionEvent actionEvent) {

        BookingData bookingData = new BookingData();
        bookingData.loadBookings();

        ArrayList<Booking> bookings = new ArrayList<>();
        bookings.addAll(bookingData.getBookingList());

        Integer bookingId = checkInt(idField.getText());
        if (bookingId == null) {
            msgLbl.setText("couldn't complete purchase");
            return;
        }

        for (Booking booking : bookings) {

            if (bookingId.equals(booking.getId()) &&
                    phoneField.getText().equals(booking.getPhoneNo())) {

                DBConn dbConn = new DBConn();

                if (dbConn.saveSweetsPurchase(bookingId, selectedProducts)) {

                    msgLbl.setText("purchase completed");
                } else {

                    msgLbl.setText("couldn't complete purchase");
                }
                return;
            } else {

                msgLbl.setText("couldn't complete purchase");
                return;
            }
        }
    }

    private void updateSelectedTable() {
        selectedTbl.setItems(selectedProducts);
        selectedTbl.refresh();
    }

    private Integer checkInt(String text) {

        try {
            return Integer.parseInt(text);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }
}
