package me.divkix.cse360project.screens.patient;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class patientMainView {
    // Method to switch to the patient signup screen
    public static void switchToPatientMainView(Stage primaryStage) {
        GridPane screen = new patientMainView().screen(primaryStage);
        primaryStage.getScene().setRoot(screen);
    }

    public GridPane screen(Stage primaryStage) {
        GridPane layout = new GridPane(); // Create a GridPane layout
        layout.setHgap(10); // Set horizontal gap
        layout.setVgap(10); // Set vertical gap

        return layout;
    }
}
