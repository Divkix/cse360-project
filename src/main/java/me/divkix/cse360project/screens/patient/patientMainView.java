package me.divkix.cse360project.screens.patient;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class patientMainView {
    // Method to switch to the patient signup screen
    public static void switchToPatientMainView(Stage primaryStage) {
        GridPane screen = new patientMainView().patientMainViewScreen(primaryStage);
        primaryStage.getScene().setRoot(screen);
    }

    public GridPane patientMainViewScreen(Stage primaryStage) {
        GridPane patientMainViewLayout = new GridPane(); // Create a GridPane layout
        patientMainViewLayout.setHgap(10); // Set horizontal gap
        patientMainViewLayout.setVgap(10); // Set vertical gap

        return patientMainViewLayout;
    }
}
