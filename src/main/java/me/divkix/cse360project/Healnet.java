package me.divkix.cse360project;

// Import the necessary classes

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.divkix.cse360project.helperFunctions.initDb;
import me.divkix.cse360project.screens.employeeLogin;
import me.divkix.cse360project.screens.patient.patientLogin;

// Main class
public class Healnet extends Application {

    // Constants
    public static final String setStyleButtonString = "-fx-font-size: 16pt; -fx-background-color: rgb(54, 94, 187); -fx-text-fill: black;"; // Set the font size and background color
    public static final String layoutStyleString = "-fx-padding: 20; -fx-alignment: center;"; // Add padding and center the components
    public static final String userDetailsTable = "user_details";
    public static final String patientDetailsTable = "patient_details";
    public static final String patientVisitsTable = "patient_visits";

    // Main method to launch the application
    public static void main(String[] args) {
        // Initialize the database
        initDb.initializeDatabase();

        // Launch the application
        launch(args);
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
        VBox layout = new VBox(35); // Create a layout with vertical spacing of 35
        layout.setAlignment(Pos.CENTER); // Center the components
        layout.setStyle(layoutStyleString); // Add padding and center the components

        // create a label called "Healnet Dashboard"
        Label titleLabel = new Label("Welcome to Healnet Health Management System"); // Create a label
        titleLabel.setStyle("-fx-font-size: 16pt;"); // Set the font size

        // Patient Login button
        Button patientLoginButton = new Button("Patient Login"); // Create a button
        patientLoginButton.setStyle(setStyleButtonString); // Set the font size and background color
        patientLoginButton.setPrefWidth(250); // Set the button width
        patientLoginButton.setOnAction(e -> patientLogin.switchScreen(primaryStage)); // Switch to patient login form

        // Employee Login button
        Button employeeLoginButton = new Button("Employee Login"); // Create a button
        employeeLoginButton.setStyle(setStyleButtonString); // Set the font size
        employeeLoginButton.setPrefWidth(250); // Set the button width
        employeeLoginButton.setOnAction(e -> employeeLogin.switchScreen(primaryStage)); // Switch to patient view

        // Add the components to the layout
        layout.getChildren().addAll(titleLabel, patientLoginButton, employeeLoginButton);

        // Return the layout
        return layout;
    }
}
