import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DBConn {

    private final String URL = "jdbc:mysql://localhost:3306/";
    private final String DB_NAME = "adventurexp";
    private final String USER = "root";
    private final String PASS = "";


    private Connection getConn() {
        Connection conn;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    this.URL + this.DB_NAME,
                    this.USER,
                    this.PASS);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void addInstructor(String name) {
        Connection connection = getConn();
        String sql = "INSERT INTO `instructor` (`id`, `name`, `workdays`) VALUES (NULL, ?, 0)";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.execute();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateInstructorWorkdays(int instructorId, int workdays) {
        Connection connection = getConn();
        String sql = "UPDATE `instructor` SET `workdays` = ? WHERE `id` = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, workdays);
            ps.setInt(2, instructorId);
            ps.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Instructor> getAllInstructors() {
        Connection connection = getConn();
        String sql = "SELECT * FROM `instructor`";
        ArrayList<Instructor> instructorList = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                instructorList.add(new Instructor(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("workdays")
                ));
            }
            connection.close();
        } catch (SQLException ex) {

        }

        return instructorList;
    }

    public void addActivity(String name, int price, int age, double height) {
        Connection connection = getConn();
        String sql = "INSERT INTO `activity` (`id`, `name`, `price`, `age`, `height`) VALUES " +
                "(NULL, ?, ?, ?, ?)";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, price);
            ps.setInt(3, age);
            ps.setDouble(4, height);
            ps.execute();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateActivity(int id, String name, int price, int age, double height) {
        Connection connection = getConn();
        String sql = "UPDATE `activity` SET `name` = ?, `price` = ?, `age` = ?, `height` = ? WHERE `id` = ?";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, price);
            ps.setInt(3, age);
            ps.setDouble(4, height);
            ps.setInt(5, id);
            ps.execute();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Activity> getActivities() {
        Connection connection = getConn();
        String sql = "SELECT * FROM `activity`";
        ArrayList<Activity> activityList = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                activityList.add(new Activity(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getDouble(5)
                ));
            }
            connection.close();
        } catch (SQLException ex) {

        }
        return activityList;
    }

    public ArrayList<Booking> getAllBookings() {
        ArrayList<Booking> bookingList = new ArrayList<>();
        Connection connection = getConn();
        String sql = "SELECT * FROM `booking`";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                bookingList.add(new Booking(
                        resultSet.getInt(1),
                        resultSet.getDate(2).toLocalDate(),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getInt(8),
                        resultSet.getInt(9),
                        resultSet.getInt(10)
                ));
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return bookingList;
    }

    public ArrayList<Booking> getBookingsByDates(LocalDate startDate, LocalDate endDate) {

        ArrayList<Booking> bookings = new ArrayList<>();

        if (startDate == null) {

            return bookings;
        }

        Connection connection = getConn();

        String sql = "SELECT * FROM `booking` " +
                "WHERE date >= ? \n" +
                "AND date <= ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {

                Calendar calendar = Calendar.getInstance();

                calendar.setTime(resultSet.getTime(3));
                int startTime = calendar.get(Calendar.HOUR_OF_DAY);

                calendar.setTime(resultSet.getTime(4));
                int endTime = calendar.get(Calendar.HOUR_OF_DAY);


                bookings.add(new Booking(
                        resultSet.getInt(1),
                        resultSet.getDate(2).toLocalDate(),
                        startTime,
                        endTime,
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getInt(8),
                        resultSet.getInt(9),
                        resultSet.getInt(10)
                ));
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return bookings;
    }

    public int addBooking(LocalDate date, int startTime, int endTime, String name,
                          String email, String phoneNo, int partAmount, int activityId, int instructorId) {

        int bookingId = 0;

        Connection connection = getConn();
        String sql = "INSERT INTO `booking` (`date`, `starttime`, `endtime`, `name`, `email`, " +
                "`phonenr`, `participants`, `activity`, `instructorId`) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Calendar cStart = Calendar.getInstance();
        cStart.set(Calendar.HOUR_OF_DAY, startTime);
        Time timeStart = new Time(cStart.getTimeInMillis());
        Calendar cEnd = Calendar.getInstance();
        cEnd.set(Calendar.HOUR_OF_DAY, endTime);
        Time timeEnd = new Time(cEnd.getTimeInMillis());

        try {

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, timeStart);
            ps.setTime(3, timeEnd);
            ps.setString(4, name);
            ps.setString(5, email);
            ps.setString(6, phoneNo);
            ps.setInt(7, partAmount);
            ps.setInt(8, activityId);
            ps.setInt(9, instructorId);
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                bookingId = rs.getInt(1);
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookingId;
    }

    public void updateBooking(LocalDate date, int startTime, int endTime, String name, String email, String phoneNo,
                              int partAmount, int activityId, int instructorId, int bookingId) {

        Connection connection = getConn();
        String sql = "UPDATE `booking` SET `date`=?,`starttime`=?,`endtime`=?," +
                "`name`=?,`email`=?,`phonenr`=?,`participants`=?,`activity`=?," +
                "`instructorId`=? WHERE `id`=?";

        Calendar cStart = Calendar.getInstance();
        cStart.set(Calendar.HOUR_OF_DAY, startTime);
        Time timeStart = new Time(cStart.getTimeInMillis());
        Calendar cEnd = Calendar.getInstance();
        cEnd.set(Calendar.HOUR_OF_DAY, endTime);
        Time timeEnd = new Time(cEnd.getTimeInMillis());

        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, timeStart);
            ps.setTime(3, timeEnd);
            ps.setString(4, name);
            ps.setString(5, email);
            ps.setString(6, phoneNo);
            ps.setInt(7, partAmount);
            ps.setInt(8, activityId);
            ps.setInt(9, instructorId);
            ps.setInt(10, bookingId);

            ps.execute();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteBooking(int bookingId) {
        Connection connection = getConn();
        String sql = "DELETE FROM `booking` WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bookingId);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Sweets> getSweets() {

        ArrayList<Sweets> sweetList = new ArrayList<>();
        Connection connection = getConn();
        String sql = "SELECT * FROM `candysodas`";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                sweetList.add(new Sweets(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3)
                ));
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return sweetList;
    }

    public boolean saveSweetsPurchase(int bookingId, List<SweetsQuan> sweetsQuans) {

        Connection connection = getConn();
        String sql = "";

        for (SweetsQuan sweetsQuan : sweetsQuans) {

            if (sql.equals("")) {

                sql = "INSERT INTO `sweetsquantity` (`bookingid`, `quantity`, `sweetsid`) VALUES ";
            } else {

                sql += ", ";
            }
            sql += "(?, ?, ?)\n";
        }

        try {

            PreparedStatement ps = connection.prepareStatement(sql);

            int index = 0;

            for (SweetsQuan sweetsQuan : sweetsQuans) {

                ps.setInt(index + 1, bookingId);
                ps.setInt(index + 2, sweetsQuan.getQuantity());
                ps.setInt(index + 3, sweetsQuan.getSweet().getId());
                index += 3;
            }

            ps.execute();
            connection.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addforShop(String name, double price){
        Connection con = getConn();

        String sql="iNSERT INTO `candysodas` (`ID`,`Name`,`Price`) VALUES (NULL,?,?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,name);
            ps.setDouble(2,price);
            ps.execute();
            con.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void updateSweets(String nameC, String name, Double price) {
        Connection connection = getConn();
        String sql = "UPDATE `candysodas` SET `Name` = ?, `Price` = ? WHERE `Name` = ?";
            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1,name);
                ps.setDouble(2,price);
                ps.setString(3,nameC);
                ps.executeUpdate();
                connection.close();
            } catch (SQLException ex)
        {
            ex.printStackTrace();
        }

    }
}
