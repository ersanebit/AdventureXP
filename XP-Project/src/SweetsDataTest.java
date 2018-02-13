import org.junit.Test;

import static org.junit.Assert.*;

public class SweetsDataTest {
    @Test
    public void getSweets() throws Exception {

        int candyAm = 5; // the amount of candies in the DB

        SweetsData sweetsData = new SweetsData();
        assertEquals(0, sweetsData.getSweets().size());
        sweetsData.loadSweets();
        assertEquals(candyAm, sweetsData.getSweets().size());
    }

    @Test
    public void loadSweets() throws Exception {

        int candyAm = 5; // the amount of candies in the DB

        SweetsData sweetsData = new SweetsData();
        assertEquals(0, sweetsData.getSweets().size());
        sweetsData.loadSweets();
        assertEquals(candyAm, sweetsData.getSweets().size());
    }

}