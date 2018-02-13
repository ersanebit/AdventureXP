import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Random;

public class InstructorData {

    private ObservableList<Instructor> instructorList = FXCollections.observableArrayList();

    public ObservableList<Instructor> getInstructorList() {
        return instructorList;
    }

    public void loadList() {
        DBConn dbConn = new DBConn();
        instructorList = FXCollections.observableArrayList(dbConn.getAllInstructors());
    }

    public void setList(ArrayList<Instructor> instructors) {
        instructorList.setAll(instructors);
    }
    public ObservableList<Instructor> getList() {
        return instructorList;
    }

    public Instructor searchInstructor(int id) {
        for (Instructor i : instructorList) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }

    public ArrayList<Instructor> getInstructorsByDay(int day) {
        ArrayList<Instructor> arrayList = new ArrayList<>();
        for (Instructor i : instructorList) {
            if ((i.getWorkdays() & 0b1) == dayToFlag(day)) {
                arrayList.add(i);
                continue;
            }
            if ((i.getWorkdays() & 0b10) == dayToFlag(day)) {
                arrayList.add(i);
                continue;
            }
            if ((i.getWorkdays() & 0b100) == dayToFlag(day)) {
                arrayList.add(i);
                continue;
            }
            if ((i.getWorkdays() & 0b1000) == dayToFlag(day)) {
                arrayList.add(i);
                continue;
            }
            if ((i.getWorkdays() & 0b10000) == dayToFlag(day)) {
                arrayList.add(i);
                continue;
            }
            if ((i.getWorkdays() & 0b100000) == dayToFlag(day)) {
                arrayList.add(i);
                continue;
            }
            if ((i.getWorkdays() & 0b1000000) == dayToFlag(day)) {
                arrayList.add(i);
                continue;
            }
        }
        return arrayList;
    }

    private int dayToFlag(int day) {
        if (day == 1) {
            return 0b1;
        }
        if (day == 2) {
            return 0b10;
        }
        if (day == 3) {
            return 0b100;
        }
        if (day == 4) {
            return 0b1000;
        }
        if (day == 5) {
            return 0b10000;
        }
        if (day == 6) {
            return 0b100000;
        }
        if (day == 7) {
            return 0b1000000;
        }
        return 0;
    }

    public Instructor getRandomInstructor(ArrayList<Instructor> array) {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(array.size());
        return array.get(index);
    }

}
