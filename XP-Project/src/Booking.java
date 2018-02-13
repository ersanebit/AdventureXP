import java.time.LocalDate;
public class Booking {

    private int id;
    private LocalDate date;
    private int startTime;
    private int endTime;
    private String name;
    private String email;
    private String phoneNo;
    private int partAmount;
    private int activityId;
    private int instructorId;

    public Booking(int id, LocalDate date, int startTime, int endTime, String name,
                   String email, String phoneNo, int partAmount, int activityId, int instructorId) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.partAmount = partAmount;
        this.activityId = activityId;
        this.instructorId = instructorId;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public int getPartAmount() {
        return partAmount;
    }

    public int getActivityId() {
        return activityId;
    }

    public int getInstructorId() {
        return instructorId;
    }
}
