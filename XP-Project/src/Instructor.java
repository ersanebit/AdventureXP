public class Instructor {

    private static final int MONDAY = 0b1;
    private static final int TUESDAY = 0b10;
    private static final int WEDNESDAY = 0b100;
    private static final int THURSDAY = 0b1000;
    private static final int FRIDAY = 0b10000;
    private static final int SATURDAY = 0b100000;
    private static final int SUNDAY = 0b1000000;

    private int id;
    private String name;
    private int workdays;

    public Instructor(int id, String name, int workdays) {
        this.id = id;
        this.name = name;
        this.workdays = workdays;
    }


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getWorkdays() {
        return workdays;
    }

    public void setWorkdays(int workdays) {
        this.workdays = workdays;
    }

    public String getFormattedWorkdays() {
        String result = "";
        if ((workdays & MONDAY) == MONDAY) {
            result += "Monday, ";
        }
        if ((workdays & TUESDAY) == TUESDAY) {
            result += "Tuesday, ";
        }
        if ((workdays & WEDNESDAY) == WEDNESDAY) {
            result += "Wednesday, ";
        }
        if ((workdays & THURSDAY) == THURSDAY) {
            result += "Thursday, ";
        }
        if ((workdays & FRIDAY) == FRIDAY) {
            result += "Friday, ";
        }
        if ((workdays & SATURDAY) == SATURDAY) {
            result += "Saturday, ";
        }
        if ((workdays & SUNDAY) == SUNDAY) {
            result += "Sunday";
        }
        result = result.trim();
        if (result.length() > 2 && result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

}
