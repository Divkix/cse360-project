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

public class employeeLogin {
    public VBox employeeLoginScreen(Stage primaryStage) {
        // create a vbox layout with 10 hgap and vgap and 1 text field, 1 password field, 1 button
        VBox employeeLoginLayout = new VBox(35); // Create a layout with vertical spacing of 10
        employeeLoginLayout.setAlignment(Pos.CENTER); // Center the components
        employeeLoginLayout.setStyle(layoutStyleString); // Add padding and center the components

        // create a label called "Patient Login"
        Label titleLabel = new Label("Employee Login"); // Create a label
        titleLabel.setStyle("-fx-font-size: 16pt;"); // Set the font size

        // Patient unique ID text field
        Label employeeIDLabel = new Label("Employee ID:"); // Create a label for patient ID
        employeeIDLabel.setStyle("-fx-font-size: 12pt;");
        TextField employeeUniqueIDField = new TextField(); // Create a text field for patient ID
        employeeUniqueIDField.setPrefWidth(250); // Set the width of the text field

        // Password text field
        Label passwordLabel = new Label("Password:"); // Create a label for password
        passwordLabel.setStyle("-fx-font-size: 12pt;");
        PasswordField passwordField = new PasswordField(); // Create a text field for password
        passwordField.setPrefWidth(250); // Set the width of the text field

        // Patient Login button
        Button employeeLoginButton = new Button("Login"); // Create a button
        employeeLoginButton.setStyle(setStyleButtonString); // Set the font size and background color
        employeeLoginButton.setPrefWidth(250); // Set the button width
        // TODO: Add action to switch to patient login form
//        employeeLoginButton.setOnAction(e -> switchToPatientLoginScreen(primaryStage)); // Switch to patient login form

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
        employeeLoginLayout.getChildren().addAll(titleLabel, employeeIDLabel, employeeUniqueIDField, passwordLabel, passwordField, employeeLoginButton, backButton);

        return employeeLoginLayout;
    }

    // Method to switch to the employee login screen
    public static void switchToEmployeeLoginScreen(Stage primaryStage) {
        VBox employeeLoginScreen = new employeeLogin().employeeLoginScreen(primaryStage);
        primaryStage.getScene().setRoot(employeeLoginScreen);
    }
}