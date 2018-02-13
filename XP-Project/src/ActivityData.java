import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ActivityData {

    private ObservableList<Activity> activityList = FXCollections.observableArrayList();

    public ObservableList<Activity> getActivityList() {
        return activityList;
    }

    public ObservableList<Activity> getList() {
        return activityList;
    }

    public void loadList() {
        DBConn dbConn = new DBConn();
        activityList = FXCollections.observableArrayList(dbConn.getActivities());
    }

    public Activity searchActivity(int id) {
        for (Activity activity : activityList) {
            if (activity.getId() == id) {
                return activity;
            }
        }
        return null;
    }

}
