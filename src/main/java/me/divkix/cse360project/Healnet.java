package me.divkix.cse360project;

// Import the necessary classes

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.divkix.cse360project.helperFunctions.sqlHelper;
import me.divkix.cse360project.screens.employeeLogin;
import me.divkix.cse360project.screens.patientLogin;

import java.util.HashMap;

// Main class
public class Healnet extends Application {

    // Constants
    public static final String setStyleButtonString = "-fx-font-size: 16pt; -fx-background-color: rgb(54, 94, 187); -fx-text-fill: black;"; // Set the font size and background color
    public static final String layoutStyleString = "-fx-padding: 20; -fx-alignment: center;"; // Add padding and center the components
    public static final String userDetailsTable = "user_details";
    public static final String patientDetailsTable = "patient_details";

    // Main method to launch the application
    public static void main(String[] args) {
        // Initialize the database
        initializeDatabase();

        // Launch the application
        launch(args);
    }

    // Method to initialize the database
    public static void initializeDatabase() {
        // Create a new database
        sqlHelper.createNewDatabase();
        // Create a new table for user details
        sqlHelper.createNewTable(userDetailsTable, new HashMap<>() {{
            put("username", "text PRIMARY KEY NOT NULL");
            put("first_name", "text DEFAULT NULL");
            put("last_name", "text DEFAULT NULL");
            put("email", "text DEFAULT NULL");
            put("phone", "varchar DEFAULT NULL");
            put("date_of_birth", "text DEFAULT NULL");
            put("address", "text DEFAULT NULL");
            put("insurance_provider", "text DEFAULT NULL");
            put("insurance_id", "text DEFAULT NULL");
            put("role", "text DEFAULT 'patient' NOT NULL");
            put("password", "text");
        }});

        // Add new patient
        sqlHelper.insertData(userDetailsTable, new HashMap<>() {{
            put("username", "doe_98765");
            put("role", "patient");
            put("first_name", "John");
            put("last_name", "Doe");
            put("email", "john.doe@example.com");
            put("phone", "123-456-7890");
            put("date_of_birth", "01/01/1990");
            put("address", "123 Main St, Anytown, USA");
            put("insurance_provider", "Blue Cross Blue Shield");
            put("insurance_id", "123456789");
            put("password", "password123");
        }});
        // Add new doctor
        sqlHelper.insertData(userDetailsTable, new HashMap<>() {{
            put("username", "jane_12345");
            put("first_name", "Jane");
            put("last_name", "Smith");
            put("role", "doctor");
            put("password", "password123");
        }});
        // Add a new nurse
        sqlHelper.insertData(userDetailsTable, new HashMap<>() {{
            put("username", "janice_12345");
            put("first_name", "Janice");
            put("last_name", "Johnson");
            put("role", "nurse");
            put("password", "password123");
        }});
    }

    // Start method to build the initial view
    @Override
    public void start(Stage primaryStage) {

        // Initial view
        VBox mainView = mainScreen(primaryStage);

        // Set the scene with height and width
        Scene scene = new Scene(mainView, 1200, 800);

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
        Button patientLoginButton = new Button("Patient Login"); // Create a button
        patientLoginButton.setStyle(setStyleButtonString); // Set the font size and background color
        patientLoginButton.setPrefWidth(250); // Set the button width
        patientLoginButton.setOnAction(e -> patientLogin.switchToPatientLoginScreen(primaryStage)); // Switch to patient login form

        // Patient View Screen
        Button patientViewButton = new Button("Employee Login"); // Create a button
        patientViewButton.setStyle(setStyleButtonString); // Set the font size
        patientViewButton.setPrefWidth(250); // Set the button width
        patientViewButton.setOnAction(e -> employeeLogin.switchToEmployeeLoginScreen(primaryStage)); // Switch to patient view

        // Add the components to the layout
        mainScreenLayout.getChildren().addAll(titleLabel, patientLoginButton, patientViewButton);

        // Return the layout
        return mainScreenLayout;
    }
}
