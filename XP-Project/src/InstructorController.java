import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class InstructorController {

    @FXML
    private TextField addInstructorField;
    @FXML
    private TableView<Instructor> instructorTable;
    @FXML
    private TextField workdayField;
    @FXML
    private MenuButton workdayMenu;

    private InstructorData instructorData = new InstructorData();

    @FXML
    public void initialize() {
        loadInstructors();

        instructorTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Instructor>() {
            @Override
            public void changed(ObservableValue<? extends Instructor> observable, Instructor oldI, Instructor newI) {
                Instructor instructor = instructorTable.getSelectionModel().getSelectedItem();
                if (instructor != null) {
                    workdayField.setText(instructor.getFormattedWorkdays());
                }
                for (MenuItem item : workdayMenu.getItems()) {
                    ((CheckMenuItem) item).setSelected(false);
                }
            }
        });

        workdayMenu.getItems().setAll(
                new CheckMenuItem("Monday"),
                new CheckMenuItem("Tuesday"),
                new CheckMenuItem("Wednesday"),
                new CheckMenuItem("Thursday"),
                new CheckMenuItem("Friday"),
                new CheckMenuItem("Saturday"),
                new CheckMenuItem("Sunday")
        );


    }

    @FXML
    private void createInstructor(ActionEvent e) {
        DBConn dbConn = new DBConn();
        String name = addInstructorField.getText();
        if (name != null && !name.trim().isEmpty()) {
            dbConn.addInstructor(name);
            loadInstructors();
        }
    }

    @FXML
    private void modifyWorkdays(ActionEvent e) {
        int workdayTotal = 0;
        for(MenuItem item : workdayMenu.getItems()) {
            CheckMenuItem checkMenuItem = (CheckMenuItem) item;
            if(checkMenuItem.isSelected()) {
                workdayTotal += checkWorkday(checkMenuItem.getText());
            }
        }
        DBConn dbConn = new DBConn();
        Instructor instructor = instructorTable.getSelectionModel().getSelectedItem();
        if (instructor != null) {
            dbConn.updateInstructorWorkdays(instructor.getId(), workdayTotal);
            loadInstructors();
        }
    }

    private void loadInstructors() {
        instructorData.loadList();
        instructorTable.setItems(instructorData.getInstructorList());
    }

    private int checkWorkday(String text) {
        if (text.equals("Monday")) {
            return 0b1;
        }
        if (text.equals("Tuesday")) {
            return 0b10;
        }
        if (text.equals("Wednesday")) {
            return 0b100;
        }
        if (text.equals("Thursday")) {
            return 0b1000;
        }
        if (text.equals("Friday")) {
            return 0b10000;
        }
        if (text.equals("Saturday")) {
            return 0b100000;
        }
        if (text.equals("Sunday")) {
            return 0b1000000;
        }
        return 0;
    }

}
