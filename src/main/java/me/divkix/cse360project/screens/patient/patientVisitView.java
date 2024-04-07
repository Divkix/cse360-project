package me.divkix.cse360project.screens.patient;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class patientVisitView {
    // Method to switch to the patient signup screen
    public static void switchToPatientVisitView(Stage primaryStage, String username) {
        VBox screen = new patientVisitView().screen(primaryStage, username);
        primaryStage.getScene().setRoot(screen);
    }

    private VBox screen(Stage primaryStage, String username) {
        VBox layout = new VBox(); // Create a GridPane layout
        layout.setSpacing(10); // Set the spacing between the components

        return layout;
    }
}
