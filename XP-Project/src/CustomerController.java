import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class CustomerController {

    @FXML
    private TableView<Activity> activityTable;
    @FXML
    public TableColumn activityClmn;
    @FXML
    private TextField priceField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField heightField;
    @FXML
    private DatePicker dateField;
    @FXML
    private ChoiceBox<Integer> startTimeBox;
    @FXML
    private ChoiceBox<Integer> endTimeBox;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneNoField;
    @FXML
    private TextField participantsField;
    @FXML
    private ChoiceBox<Instructor> instructorBox;
    @FXML
    public Label msgLabel;
    @FXML
    private Button candshirt;

    private ActivityData activityData = new ActivityData();
    private InstructorData instructorData = new InstructorData();

    private final int OPENTIME = 7;
    private final int CLOSETIME = 20;

    @FXML
    public void initialize() {
        loadInstructors();

        instructorBox.setConverter(new StringConverter<Instructor>() {
            @Override
            public String toString(Instructor instructor) {
                return instructor.getName();
            }

            @Override
            public Instructor fromString(String string) {
                return null;
            }
        });

        activityClmn.setCellValueFactory(new PropertyValueFactory<Activity, String>("Name"));
        loadActivities();

        ObservableList<Integer> bookingTimes = FXCollections.observableArrayList();
        for (int i = OPENTIME; i <= CLOSETIME; i++){

            bookingTimes.add(i);
        }
        startTimeBox.setItems(bookingTimes);
        startTimeBox.setValue(OPENTIME);
        endTimeBox.setItems(bookingTimes);
        endTimeBox.setValue(OPENTIME + 1);

        activityTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Activity>() {
            @Override
            public void changed(ObservableValue<? extends Activity> observable, Activity oldValue, Activity newValue) {
                Activity activity = activityTable.getSelectionModel().getSelectedItem();
                if (activity != null) {
                    setFields(activity);
                }
            }
        });
    }

    private void setFields(Activity activity) {
        priceField.setText(String.valueOf(activity.getPrice()));
        ageField.setText(String.valueOf(activity.getAge()));
        heightField.setText(String.valueOf(activity.getHeight()));
    }

    private void loadInstructors() {
        instructorData.loadList();
        instructorBox.setItems(instructorData.getInstructorList());
    }

    public void reserveButton(ActionEvent actionEvent) {

        LocalDate date = dateField.getValue();
        int startTime = startTimeBox.getSelectionModel().getSelectedItem();
        int endTime = endTimeBox.getSelectionModel().getSelectedItem();

        if (activityTable.getSelectionModel().getSelectedItem() == null ||
                startTime > endTime || date == null) {

            msgLabel.setText("invalid data input");
            return;
        }
        int activityId = activityTable.getSelectionModel().getSelectedItem().getId();

        if (!checkDate(activityId, date, startTime, endTime)) {

            msgLabel.setText("activity not available");
            return;
        }

        String name = nameField.getText();
        String email = emailField.getText();
        String phoneNo = phoneNoField.getText();
        Integer partAmount = checkInt(participantsField.getText());

        if (name != null && !name.trim().isEmpty() &&
                email != null && !email.trim().isEmpty() &&
                phoneNo != null &&
                partAmount != null &&
                !instructorBox.getSelectionModel().isEmpty()) {

            DBConn dbConn = new DBConn();
            int bookingId = dbConn.addBooking(date, startTime, endTime, name, email, phoneNo, partAmount,
                    activityId, instructorBox.getSelectionModel().getSelectedItem().getId());

            if (bookingId < 1) {
                msgLabel.setText("booking not created");
            } else {
                msgLabel.setText("booking completed. ID: " + bookingId);
            }

        } else {

            msgLabel.setText("invalid data input");
        }
    }

    private boolean checkDate (int activityId, LocalDate date, int startTime, int endTime) {

        BookingData bookingData = new BookingData();
        bookingData.loadFromDate(date, date);
        bookingData.sortByActivity(activityId);

        ArrayList<Booking> bookings = new ArrayList<>();
        bookings.addAll(bookingData.getBookingList());

        for (Booking booking : bookings) {

            if ((startTime >= booking.getStartTime() && startTime < booking.getEndTime()) ||
                    (endTime > booking.getStartTime() && endTime <= booking.getEndTime()) ||
                    (startTime <= booking.getStartTime() && endTime >= booking.getEndTime())) {

                return false;
            }
        }

        return true;
    }

    private Integer checkInt(String text) {

        try {
            return Integer.parseInt(text);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    private void loadActivities () {
        activityData.loadList();
        activityTable.setItems(activityData.getActivityList());
    }

    @FXML
    private void openCandy(){

    }
}
