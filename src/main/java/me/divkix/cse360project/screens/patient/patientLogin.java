package me.divkix.cse360project.screens.patient;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.divkix.cse360project.Healnet;
import me.divkix.cse360project.helperFunctions.checkLoginInfo;

public class patientLogin extends Healnet {
    // Method to switch to the patient login screen
    public static void switchScreen(Stage primaryStage) {
        VBox screen = new patientLogin().screen(primaryStage);
        primaryStage.getScene().setRoot(screen);
    }

    private VBox screen(Stage primaryStage) {
        // create a vbox layout with 10 hgap and vgap and 1 text field, 1 password field, 1 button
        VBox layout = new VBox(35); // Create a layout with vertical spacing of 10
        layout.setAlignment(Pos.CENTER); // Center the components
        layout.setStyle(layoutStyleString); // Add padding and center the components

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
        passwordField.setMaxWidth(300); // Set the width of the text field

        // Patient Login button
        Button patientLoginButton = new Button("Login"); // Create a button
        patientLoginButton.setStyle(setStyleButtonString); // Set the font size and background color
        patientLoginButton.setMaxWidth(300); // Set the button width
        patientLoginButton.setOnAction(e -> {
            // Check if the patient ID and password are correct
            // If the patient ID and password are correct, switch to the patient view screen
            // If the patient ID and password are incorrect, display an error message
            boolean correctLogin = checkLoginInfo.check(patientUniqueIDField.getText(), passwordField.getText());
            if (correctLogin) {
                patientMainView.switchScreen(primaryStage, patientUniqueIDField.getText());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Login");
                alert.setContentText("The patient ID or password is incorrect. Please try again.");
                alert.showAndWait();
                ButtonType buttonTypeCancel = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel);
            }
        });

        // Add a label, which is clickable, to switch to the patient signup form
        Label patientSignupLabel = new Label("Don't have an account? Sign up here!"); // Create a label
        patientSignupLabel.setStyle("-fx-font-size: 12pt; -fx-underline: true;"); // Set the font size and underline
        patientSignupLabel.setOnMouseClicked(e -> patientSignup.switchScreen(primaryStage)); // Switch to patient signup form

        // Add a back button to the top left corner
        Button backButton = new Button("Back"); // Create a back button
        backButton.setOnAction(e -> {
            try {
                new Healnet().start(primaryStage); // Switch to the initial view
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        backButton.setStyle(setStyleButtonString); // Set the font size

        // Add the components to the layout
        layout.getChildren().addAll(titleLabel, patientIDLabel, patientUniqueIDField, passwordLabel, passwordField, patientLoginButton, patientSignupLabel, backButton);

        // Return the layout
        return layout;
    }
}