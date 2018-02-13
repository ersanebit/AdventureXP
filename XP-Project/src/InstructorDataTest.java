import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class InstructorDataTest {

    @Test
    public void searchInstructor() throws Exception {
        InstructorData data = new InstructorData();
        ArrayList<Instructor> list = new ArrayList<>();
        list.add(new Instructor(1, "first", 0b1));
        list.add(new Instructor(2, "second", 0b10));
        list.add(new Instructor(3, "third", 0b100));
        list.add(new Instructor(4, "fourth", 0b1000));
        data.setList(list);

        assertEquals(data.getInstructorList().get(2), data.searchInstructor(3));
        assertNull(data.searchInstructor(5));
    }

    @Test
    public void getInstructorsByDay() throws Exception {
        InstructorData data = new InstructorData();
        ArrayList<Instructor> list = new ArrayList<>();
        list.add(new Instructor(1, "first", 0b1));
        list.add(new Instructor(2, "second", 0b10));
        list.add(new Instructor(3, "third", 0b100));
        list.add(new Instructor(4, "fourth", 0b1000));
        data.setList(list);

        ArrayList<Instructor> secondList = new ArrayList<>();
        secondList.add(list.get(3));

        assertEquals(secondList, data.getInstructorsByDay(4));
    }

}