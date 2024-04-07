package me.divkix.cse360project.screens.doctor;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class doctorMainView {
    // Method to switch to the patient signup screen
    public static void switchToDoctorMainView(Stage primaryStage) {
        GridPane screen = new doctorMainView().doctorMainViewScreen(primaryStage);
        primaryStage.getScene().setRoot(screen);
    }

    public GridPane doctorMainViewScreen(Stage primaryStage) {
        GridPane doctorMainViewLayout = new GridPane(); // Create a GridPane layout
        doctorMainViewLayout.setHgap(10); // Set horizontal gap
        doctorMainViewLayout.setVgap(10); // Set vertical gap

        return doctorMainViewLayout;
    }
}
