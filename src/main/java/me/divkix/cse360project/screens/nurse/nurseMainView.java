package me.divkix.cse360project.screens.nurse;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class nurseMainView {
    // Method to switch to the patient signup screen
    public static void switchToNurseMainView(Stage primaryStage) {
        GridPane screen = new nurseMainView().nurseMainViewScreen(primaryStage);
        primaryStage.getScene().setRoot(screen);
    }

    public GridPane nurseMainViewScreen(Stage primaryStage) {
        GridPane nurseMainViewLayout = new GridPane(); // Create a GridPane layout
        nurseMainViewLayout.setHgap(10); // Set horizontal gap
        nurseMainViewLayout.setVgap(10); // Set vertical gap

        return nurseMainViewLayout;
    }
}
