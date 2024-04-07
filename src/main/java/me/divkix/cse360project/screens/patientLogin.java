package me.divkix.cse360project.screens;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.divkix.cse360project.Healnet;

import static me.divkix.cse360project.Healnet.layoutStyleString;
import static me.divkix.cse360project.Healnet.setStyleButtonString;

public class patientLogin {
    // Method to switch to the patient login screen
    public static void switchToPatientLoginScreen(Stage primaryStage) {
        VBox patientLoginScreen = new patientLogin().patientLoginScreen(primaryStage);
        primaryStage.getScene().setRoot(patientLoginScreen);
    }

    public VBox patientLoginScreen(Stage primaryStage) {
        // create a vbox layout with 10 hgap and vgap and 1 text field, 1 password field, 1 button
        VBox patientLoginLayout = new VBox(35); // Create a layout with vertical spacing of 10
        patientLoginLayout.setAlignment(Pos.CENTER); // Center the components
        patientLoginLayout.setStyle(layoutStyleString); // Add padding and center the components

        // create a label called "Patient Login"
        Label titleLabel = new Label("Patient Login"); // Create a label
        titleLabel.setStyle("-fx-font-size: 16pt;"); // Set the font size

        // Patient unique ID text field
        Label patientIDLabel = new Label("Patient ID:"); // Create a label for patient ID
        patientIDLabel.setStyle("-fx-font-size: 12pt;");
        TextField patientUniqueIDField = new TextField(); // Create a text field for patient ID
        patientUniqueIDField.setPrefWidth(250); // Set the width of the text field

        // Password text field
        Label passwordLabel = new Label("Password:"); // Create a label for password
        passwordLabel.setStyle("-fx-font-size: 12pt;");
        PasswordField passwordField = new PasswordField(); // Create a text field for password
        passwordField.setPrefWidth(250); // Set the width of the text field

        // Patient Login button
        Button patientLoginButton = new Button("Login"); // Create a button
        patientLoginButton.setStyle(setStyleButtonString); // Set the font size and background color
        patientLoginButton.setPrefWidth(250); // Set the button width
        // TODO: Add action to switch to patient login form
//        patientLoginButton.setOnAction(e -> switchToPatientLoginScreen(primaryStage)); // Switch to patient login form

        // Add a label, which is clickable, to switch to the patient signup form
        Label patientSignupLabel = new Label("Don't have an account? Sign up here!"); // Create a label
        patientSignupLabel.setStyle("-fx-font-size: 12pt; -fx-underline: true;"); // Set the font size and underline
        patientSignupLabel.setOnMouseClicked(e -> patientSignup.switchToPatientSignupScreen(primaryStage)); // Switch to patient signup form

        // Add a back button to the top left corner
        Button backButton = new Button("Back"); // Create a back button
        backButton.setOnAction(e ->
        {
            try {
                new Healnet().start(primaryStage); // Switch to the initial view
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        backButton.setStyle(setStyleButtonString); // Set the font size

        // Add the components to the layout
        patientLoginLayout.getChildren().addAll(titleLabel, patientIDLabel, patientUniqueIDField, passwordLabel, passwordField, patientLoginButton, patientSignupLabel, backButton);

        // Return the layout
        return patientLoginLayout;
    }
}