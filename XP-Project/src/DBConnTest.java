import javafx.collections.ObservableList;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class DBConnTest {

    DBConn dbConn = new DBConn();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM-yyyy");

    @Test
    public void addInstructor() throws Exception {
        InstructorData instructorData = new InstructorData();
        instructorData.loadList();
        int instructorCount = instructorData.getInstructorList().size();

        dbConn.addInstructor("Kim");
        instructorData = new InstructorData();
        instructorData.loadList();
        assertEquals("Kim", instructorData.getInstructorList().get(instructorCount).getName());

    }

    @Test
    public void updateInstructorWorkdays() throws Exception {
        InstructorData instructorData = new InstructorData();
        instructorData.loadList();
        int instructorId = instructorData.getInstructorList().get(0).getId();

        dbConn.updateInstructorWorkdays(instructorId, 0b10101);
        instructorData = new InstructorData();
        instructorData.loadList();
        assertEquals(0b10101, instructorData.getInstructorList().get(0).getWorkdays());
    }

    @Test
    public void getAllInstructors() throws Exception {
        InstructorData instructorData = new InstructorData();
        instructorData.loadList();
        int size = instructorData.getList().size();

        dbConn.addInstructor("Kim");
        instructorData = new InstructorData();
        instructorData.loadList();
        assertNotSame(size, instructorData.getList().size());
    }

    @Test
    public void addActivity() throws Exception {
        ActivityData activityData = new ActivityData();
        activityData.loadList();
        int size = activityData.getList().size();
        String name = activityData.getList().get(size - 1).getName();
        dbConn.addActivity("1", 1, 1, 1.1);
        activityData.loadList();
        assertNotSame(name, activityData.getList().get(size).getName());
    }

    @Test
    public void updateActivity() throws Exception {
        ActivityData activityData = new ActivityData();
        activityData.loadList();
        String name = activityData.getList().get(0).getName();
        dbConn.updateActivity(1, "newActivity", 1, 1, 1.1);
        activityData.loadList();
        assertNotSame(name, activityData.getList().get(0).getName());
    }

    @Test
    public void getActivities() throws Exception {
        ActivityData activityData = new ActivityData();
        activityData.loadList();
        int size = activityData.getList().size();
        dbConn.addActivity("1", 1, 1, 1.1);
        activityData.loadList();
        assertNotSame(size, activityData.getList().size());
    }

    @Test
    public void getAllBookings() throws Exception {
        BookingData bookingData = new BookingData();
        bookingData.loadBookings();
        int size = bookingData.getBookingList().size();
        dbConn.addBooking(LocalDate.now(), 7, 8, "a", "a", "123", 1, 1, 7);
        bookingData.loadBookings();
        assertSame(size, bookingData.getBookingList().size());
    }

    @Test
    public void getBookingsByDates() throws Exception {

        LocalDate date1 = LocalDate.parse("27/09-2017", formatter);
        LocalDate date2 = LocalDate.parse("30/09-2017", formatter);

        assertEquals(2, dbConn.getBookingsByDates(date1, date2).size());
    }

    @Test
    public void addBooking() throws Exception {
        BookingData bookingData = new BookingData();
        bookingData.loadBookings();
        int size = bookingData.getBookingList().size();
        dbConn.addBooking(LocalDate.now(), 7, 8, "testing", "a", "123", 1, 1, 7);
        bookingData.loadBookings();
        assertEquals("testing", bookingData.getBookingList().get(size).getName());
    }

    @Test
    public void updateBooking() throws Exception {
        BookingData bookingData = new BookingData();
        bookingData.loadBookings();
        dbConn.updateBooking(LocalDate.now(), 7, 8, "testing", "a", "123", 1, 1, 7, 2);
        bookingData.loadBookings();
        assertEquals("testing", bookingData.getBookingList().get(0).getName());
    }

    @Test
    public void deleteBooking() throws Exception {
        BookingData bookingData = new BookingData();
        bookingData.loadBookings();
        int size = bookingData.getBookingList().size();
        dbConn.deleteBooking(12);
        bookingData.loadBookings();
        assertEquals(size - 1, bookingData.getBookingList().size());
    }

    @Test
    public void getSweets() throws Exception {
        SweetsData data = new SweetsData();
        data.loadSweets();
        int size = data.getSweets().size();
        dbConn.addforShop("sweet", 100);
        data.loadSweets();
        assertEquals(size + 1, data.getSweets().size());
    }

    @Test
    public void addforShop() throws Exception {
        SweetsData data = new SweetsData();
        data.loadSweets();
        int size = data.getSweets().size();
        dbConn.addforShop("sweet", 100);
        data.loadSweets();
        assertEquals(size + 1, data.getSweets().size());
    }

    @Test
    public void updateSweets() throws Exception {
        SweetsData data = new SweetsData();
        data.loadSweets();
        dbConn.updateSweets("sweet", "sweetnew", 100.0);
        data.loadSweets();
        assertEquals("sweetnew", data.getSweets().get(data.getSweets().size() - 1).getName());
    }
}