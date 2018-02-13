import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;

public class BookingData {

    private ObservableList<Booking> bookingList = FXCollections.observableArrayList();

    public ObservableList<Booking> getBookingList() {
        return bookingList;
    }

    public void sortByActivity (int activityId) {

        ArrayList<Booking> sorted = new ArrayList<>();

        for (Booking booking : bookingList) {
            if (booking.getActivityId() == activityId) {
                sorted.add(booking);
            }
        }
        bookingList = FXCollections.observableArrayList(sorted);
    }

    public ArrayList<Booking> search(String name) {
        ArrayList<Booking> arrayList = new ArrayList<>();
        for (Booking booking : bookingList) {
            if (booking.getName().contains(name)) {
                arrayList.add(booking);
            }
        }
        return arrayList;
    }

    public void loadFromDate(LocalDate startDate, LocalDate endDate) {

        DBConn dbConn = new DBConn();
        bookingList = FXCollections.observableArrayList(dbConn.getBookingsByDates(startDate, endDate));
    }

    public void loadBookings() {
        DBConn dbConn = new DBConn();
        bookingList.setAll(dbConn.getAllBookings());
    }

    public boolean removeBooking(int bookingId) {

        int loops = bookingList.size();
        for (int i = 0; i < loops; i++) {

            if (bookingList.get(i).getId() == bookingId) {
                bookingList.remove(i);
                return true;
            }
        }

        return false;
    }
}
