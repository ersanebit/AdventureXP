import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.WeekFields;
import java.util.*;

public class StatsController {

    @FXML
    public DatePicker datePicker;
    @FXML
    public ChoiceBox choiceBox;
    @FXML
    public TableView tableView;

    public DBConn dbConn = new DBConn();
    private ObservableList<Booking> statsList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        choiceBox.setItems(FXCollections.observableArrayList("Day", "Week", "Month"));
    }
    @FXML
    public void showStats(ActionEvent e){
        loadStatsTable(statsDate());
    }

    public void loadStatsTable(ArrayList<Booking> b){
        try{
            statsList = FXCollections.observableArrayList(b);
            tableView.setItems(statsList);
        } catch (RuntimeException e){
            System.err.println("You have not choosen the date or period!");
        }

    }

    public ArrayList statsDate(){

        LocalDate date = datePicker.getValue();

        if(choiceBox.getSelectionModel().isEmpty() || choiceBox.getValue().equals(null)){
            JOptionPane.showMessageDialog(null,"Please choose a date or period first!");
            return null;
        } else if(choiceBox.getValue().equals("Day")){
            System.out.println("Day selected");

            return dbConn.getBookingsByDates(singleDay(date).get(0),singleDay(date).get(0));

        } else if(choiceBox.getValue().equals("Week")){
            int wN = weekNum(date);
            //Monday of week;
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.WEEK_OF_YEAR, wN);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            Date input1 = cal.getTime();
            LocalDate weekStart = input1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            //Sunday of week
            cal.set(Calendar.WEEK_OF_YEAR, wN);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            Date input2 = cal.getTime();
            LocalDate weekEnd = input2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            System.out.println("Week selected");

            return dbConn.getBookingsByDates(weekStart,weekEnd);

        } else if(choiceBox.getValue().equals("Month")){

            LocalDate firstDayMonth = date.withDayOfMonth(1);
            LocalDate lastDayMonth = date.withDayOfMonth(date.lengthOfMonth());


            System.out.println("Month selected");

            return dbConn.getBookingsByDates(firstDayMonth,lastDayMonth);

        }
        return null;
    }

    public static List<LocalDate> singleDay(LocalDate dayDate){

        List<LocalDate> result = new ArrayList<LocalDate>();
        result.add(dayDate);

        return result;
    }

    public int weekNum(LocalDate date){

        Locale locale = Locale.ENGLISH;
        int weekOfYear = date.get(WeekFields.of(locale).weekOfWeekBasedYear());

        return weekOfYear;
    }

//    public static List<LocalDate> weekStartEnd(LocalDate startDate, LocalDate endDate){
//
//        List<LocalDate> result = new ArrayList<LocalDate>();
//        result.add(startDate);
//        result.add(endDate);
//
//        return result;
//    }


}
