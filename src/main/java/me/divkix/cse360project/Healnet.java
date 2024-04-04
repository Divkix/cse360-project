package me.divkix.cse360project;

// Import the necessary classes
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.divkix.cse360project.screens.patientIntake;
import me.divkix.cse360project.screens.patientView;

// Main class
public class Healnet extends Application {

    // Constants
    public static final String setStyleButtonString = "-fx-font-size: 16pt; -fx-background-color: rgb(54, 94, 187); -fx-text-fill: black;"; // Set the font size and background color
    public static final String layoutStyleString = "-fx-padding: 20; -fx-alignment: center;"; // Add padding and center the components
    public static final String patient_intake_db_table = "patient_intake";
    public static final String patient_results_db_table = "patient_results";

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }

    // Start method to build the initial view
    @Override
    public void start(Stage primaryStage) {
        // Initial view
        VBox mainView = mainScreen(primaryStage);

        // Set the scene with height and width
        Scene scene = new Scene(mainView, 600, 600);

        // Set the title of the window
        primaryStage.setScene(scene); // Set the scene
        primaryStage.show(); // Show the window
    }

    // Method to build main menu view
    private VBox mainScreen(Stage primaryStage) {
        VBox mainScreenLayout = new VBox(35); // Create a layout with vertical spacing of 35
        mainScreenLayout.setAlignment(Pos.CENTER); // Center the components
        mainScreenLayout.setStyle(layoutStyleString); // Add padding and center the components

        // create a label called "Healnet Dashboard"
        Label titleLabel = new Label("Welcome to Healnet Health Management System"); // Create a label
        titleLabel.setStyle("-fx-font-size: 16pt;"); // Set the font size

        // Patient Intake button
        Button patientIntakeButton = new Button("Patient Intake"); // Create a button
        patientIntakeButton.setStyle(setStyleButtonString); // Set the font size and background color
        patientIntakeButton.setPrefWidth(250); // Set the button width
        patientIntakeButton.setOnAction(e -> switchToPatientIntake(primaryStage)); // Switch to patient intake form

        // Patient View Screen
        Button patientViewButton = new Button("Patient View"); // Create a button
        patientViewButton.setStyle(setStyleButtonString); // Set the font size
        patientViewButton.setPrefWidth(250); // Set the button width
        patientViewButton.setOnAction(e -> switchToPatientView(primaryStage)); // Switch to patient view

        // Add the components to the layout
        mainScreenLayout.getChildren().addAll(titleLabel, patientIntakeButton, patientViewButton);

        // Return the layout
        return mainScreenLayout;
    }

    private void switchToPatientIntake(Stage primaryStage) {
        GridPane patientIntakeScreenClass = new patientIntake().patientIntakeScreenClass(primaryStage); // Create a GridPane layout

        // Set the scene with height and width
        Scene patientIntakeScene = new Scene(patientIntakeScreenClass, 600, 600);
        primaryStage.setScene(patientIntakeScene); // Set the scene

    }

    // Method to switch to patient information view
    private void switchToPatientView(Stage primaryStage) {
        VBox patientViewLayout = new patientView().patientViewScreen(primaryStage);

        // Set the scene with height and width
        Scene patientViewScene = new Scene(patientViewLayout, 600, 600);
        primaryStage.setScene(patientViewScene); // Set the scene
    }
}
