import org.junit.Test;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;


import static org.junit.Assert.*;

public class BookingDataTest {


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM-yyyy");

    @Test
    public void getBookingList() throws Exception {

        int expectedAmount = 6; //should match amount of bookings in DB

        BookingData bookingData = new BookingData();
        assertEquals(0, bookingData.getBookingList().size());
        bookingData.loadBookings();
        assertEquals(expectedAmount, bookingData.getBookingList().size());
    }

    @Test
    public void sortByActivity() throws Exception {
        

        int expectedAmount = 3; //should match bookings with activity ID 1

        BookingData bookingData = new BookingData();
        bookingData.loadBookings();
        bookingData.sortByActivity(1);
        assertEquals(expectedAmount, bookingData.getBookingList().size());
    }

    @Test
    public void search() throws Exception {

        int expectedAmount = 4; //should match bookings with name tyr

        BookingData bookingData = new BookingData();
        bookingData.loadBookings();
        assertEquals(expectedAmount, bookingData.search("tyr").size());
    }

    @Test
    public void loadFromDate() throws Exception {

        int expectedAmount = 6; //should match bookings between the two dates

        LocalDate date1 = LocalDate.parse("10/09-2017", formatter);
        LocalDate date2 = LocalDate.parse("11/09-2017", formatter);

        BookingData bookingData = new BookingData();
        bookingData.loadFromDate(date1, date2);
        assertEquals(0, bookingData.getBookingList().size());

        date2 = LocalDate.parse("13/09-2017", formatter);

        bookingData = new BookingData();
        bookingData.loadFromDate(date1, date2);
        assertEquals(expectedAmount, bookingData.getBookingList().size());
    }

    @Test
    public void loadBookings() throws Exception {

        int expectedAmount = 6; //should match amount of bookings in DB

        BookingData bookingData = new BookingData();
        bookingData.loadBookings();
        assertEquals(expectedAmount, bookingData.getBookingList().size());
    }

    @Test
    public void removeBooking() throws Exception {

        int bookingAm;

        BookingData bookingData = new BookingData();
        bookingData.loadBookings();
        bookingAm = bookingData.getBookingList().size();
        bookingData.removeBooking(7);
        assertEquals(bookingAm-1, bookingData.getBookingList().size());
    }

}