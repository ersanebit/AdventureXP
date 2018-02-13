import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;

public class CandyController {
    @FXML
    public TextField nameShop;
    @FXML
    public TextField priceShop;
    @FXML
    public TableView<Sweets> sweet;
    @FXML
    public TableColumn priceColm;
    @FXML
    public TableColumn nameColm;
    @FXML
    public TableColumn idColm;

    private ObservableList<Sweets> availProducts = FXCollections.observableArrayList();

    @FXML
    public void initialize(){

        idColm.setCellValueFactory(new PropertyValueFactory<Activity,Integer>("ID"));
        nameColm.setCellValueFactory(new PropertyValueFactory<Activity, String>("Name"));
        priceColm.setCellValueFactory(new PropertyValueFactory<Activity, Double>("Price"));

        SweetsData sweetsData = new SweetsData();
        sweetsData.loadSweets();
        availProducts = sweetsData.getSweets();
        sweet.setItems(availProducts);
    }
    @FXML
    public void addStuff(){
        String name=null;
        double price;
        DBConn dbConn = new DBConn();
        if(!nameShop.getText().equals("")){
            name=nameShop.getText();
        }
        else{
            String message="Please add a name to the thing you want to add";
            JOptionPane.showMessageDialog(new JFrame(),message);
        }
        if(!priceShop.getText().equals("")){
            price= Double.parseDouble(priceShop.getText());
            dbConn.addforShop(name,price);
            initialize();
        }
        else{
            String message="Please add a price to the thing you want to add";
            JOptionPane.showMessageDialog(new JFrame(),message);
        }
    }
    @FXML
    public void updateSelected(){
        if(sweet.getSelectionModel().getSelectedItems()!=null){
            Sweets selectedSweet = sweet.getSelectionModel().getSelectedItem();
            String sel=selectedSweet.getName().toString();
            String name=null;
            double price;
            DBConn dbConn = new DBConn();
            if(!nameShop.getText().equals("")){
                name=nameShop.getText();
            }
            else{
                String message="Please add a name to the thing you want to edit";
                JOptionPane.showMessageDialog(new JFrame(),message);
            }
            if(!priceShop.getText().equals("")){
                price= Double.parseDouble(priceShop.getText());
                dbConn.updateSweets(sel,name,price);
                initialize();
            }
            else{
                String message="Please add a price to the thing you want to edit";
                JOptionPane.showMessageDialog(new JFrame(),message);
            }

        }
    }
}
