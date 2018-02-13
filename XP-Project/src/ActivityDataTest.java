import org.junit.Test;

import static org.junit.Assert.*;

public class ActivityDataTest {
    @Test
    public void getActivityList() throws Exception {

        int expectedAmount = 4; //should be same as amount of activities in DB

        ActivityData activityData = new ActivityData();
        activityData.loadList();
        assertEquals(expectedAmount, activityData.getActivityList().size());
    }

    @Test
    public void loadList() throws Exception {

        int expectedAmount = 4; //should be same as amount of activities in DB

        ActivityData activityData = new ActivityData();
        activityData.loadList();
        assertEquals(expectedAmount, activityData.getActivityList().size());
    }

    @Test
    public void searchActivity() throws Exception {

        String expectedName = "activityjakub"; // name of activity with ID 4

        ActivityData activityData = new ActivityData();
        activityData.loadList();
        assertEquals(expectedName, activityData.searchActivity(4).getName());
    }

}