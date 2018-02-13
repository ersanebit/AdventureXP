import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.ArrayList;

public class BookingController {
    @FXML
    private TextField filterField;
    @FXML
    private TableView<Booking> bookingTableView;
    @FXML
    private ChoiceBox<Activity> activityBox;
    @FXML
    private DatePicker dateField;
    @FXML
    private ChoiceBox<Integer> startField;
    @FXML
    private ChoiceBox<Integer> endField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField partField;
    @FXML
    private ChoiceBox<Instructor> instructorBox;

    private BookingData bookingData = new BookingData();
    private ActivityData activityData = new ActivityData();
    private InstructorData instructorData = new InstructorData();

    private final int OPENTIME = 7;
    private final int CLOSETIME = 20;

    @FXML
    public void initialize() {
        loadInstructors();

        dateField.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                int day = dateField.getValue().getDayOfWeek().getValue();
                ObservableList collection = FXCollections.observableArrayList();
                collection.setAll(instructorData.getInstructorsByDay(day));
            }
        });

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

        ObservableList<Integer> bookingTimes = FXCollections.observableArrayList();
        for (int i = OPENTIME; i <= CLOSETIME; i++) {
            bookingTimes.add(i);
        }
        startField.setItems(bookingTimes);
        endField.setItems(bookingTimes);

        loadActivities();

        activityBox.setConverter(new StringConverter<Activity>() {
            @Override
            public String toString(Activity activity) {
                return activity.getName();
            }

            @Override
            public Activity fromString(String string) {
                return null;
            }
        });

        filterField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                searchBooking();
            }
        });
        loadBookingTable();

        bookingTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Booking>() {
            @Override
            public void changed(ObservableValue<? extends Booking> observable, Booking oldValue, Booking newValue) {
                Booking booking = bookingTableView.getSelectionModel().getSelectedItem();
                if (booking != null) {
                    setFields(booking);
                }
            }
        });
    }

    private void setFields(Booking booking) {
        activityBox.setValue(activityData.searchActivity(booking.getActivityId()));
        dateField.setValue(booking.getDate());
        startField.setValue(booking.getStartTime());
        endField.setValue(booking.getEndTime());
        nameField.setText(booking.getName());
        emailField.setText(booking.getEmail());
        phoneField.setText(booking.getPhoneNo());
        partField.setText(Integer.toString(booking.getPartAmount()));
        instructorBox.getSelectionModel().select(instructorData.searchInstructor(booking.getInstructorId()));
    }

    private void loadInstructors() {
        instructorData.loadList();
        instructorBox.setItems(instructorData.getInstructorList());
    }

    private void loadActivities() {
        activityData.loadList();
        activityBox.setItems(activityData.getActivityList());
    }

    private void searchBooking() {
        bookingTableView.setItems(FXCollections.observableArrayList(bookingData.search(filterField.getText())));
    }


    public void deleteBookings(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Booking> row = bookingTableView.getSelectionModel();
        Booking booking = row.getSelectedItem();
        if (booking == null) {
            return;
        }
        DBConn conn = new DBConn();
        conn.deleteBooking(booking.getId());
        loadBookingTable();
    }

    @FXML
    private void editBooking(ActionEvent e) {
        LocalDate date = dateField.getValue();
        int startTime = startField.getSelectionModel().getSelectedItem();
        int endTime = endField.getSelectionModel().getSelectedItem();

        Booking current = bookingTableView.getSelectionModel().getSelectedItem();

        if (current == null ||
                startTime > endTime || date == null) {
            return;
        }
        int activityId = current.getActivityId();
// should be fixed now
        if (!checkDate(activityId, date, startTime, endTime, current.getId())) {
            return;
        }

        String name = nameField.getText();
        String email = emailField.getText();
        String phoneNo = phoneField.getText();
        Integer partAmount = checkInt(partField.getText());

        if (name != null && !name.trim().isEmpty() &&
                email != null && !email.trim().isEmpty() &&
                phoneNo != null &&
                partAmount != null &&
                !instructorBox.getSelectionModel().isEmpty()) {

            DBConn dbConn = new DBConn();
            dbConn.updateBooking(date, startTime, endTime, name, email, phoneNo, partAmount,
                    activityId, instructorBox.getSelectionModel().getSelectedItem().getId(),
                    bookingTableView.getSelectionModel().getSelectedItem().getId());

            loadBookingTable();
        }
    }

    private boolean checkDate (int activityId, LocalDate date,
                               int startTime, int endTime, int current) {

        BookingData bookingData = new BookingData();
        bookingData.loadFromDate(date, date);
        bookingData.sortByActivity(activityId);
        bookingData.removeBooking(current);

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
        if (text.matches("^\\d+$")) {
            return Integer.parseInt(text);
        }
        return null;
    }

    private void loadBookingTable() {
        bookingData.loadBookings();
        bookingTableView.setItems(bookingData.getBookingList());
    }
}
