package me.divkix.cse360project.screens.doctor;

import javafx.stage.Stage;
import me.divkix.cse360project.screens.nurse.nurseMainView;

public class doctorMainView {
    // as doctor and nurse main views are the same, we can switch to the nurse main view
    public static void switchToDoctorMainView(Stage primaryStage) {
        nurseMainView.switchToNurseMainView(primaryStage);
    }
}
