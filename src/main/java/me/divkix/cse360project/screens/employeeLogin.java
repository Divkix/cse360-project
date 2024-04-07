package me.divkix.cse360project.screens;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.divkix.cse360project.Healnet;
import me.divkix.cse360project.helperFunctions.checkLoginInfo;
import me.divkix.cse360project.helperFunctions.sqlHelper;
import me.divkix.cse360project.screens.doctor.doctorMainView;
import me.divkix.cse360project.screens.nurse.nurseMainView;

import java.util.Map;

import static me.divkix.cse360project.Healnet.*;

public class employeeLogin {
    // Method to switch to the employee login screen
    public static void switchToEmployeeLoginScreen(Stage primaryStage) {
        VBox screen = new employeeLogin().employeeLoginScreen(primaryStage);
        primaryStage.getScene().setRoot(screen);
    }

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
        employeeLoginButton.setOnAction(e -> {
            // Check if the patient ID and password are correct
            // If the patient ID and password are correct, switch to the patient view screen
            // If the patient ID and password are incorrect, display an error message
            boolean correctLogin = checkLoginInfo.checkLogin(employeeUniqueIDField.getText(), passwordField.getText());
            if (correctLogin) {
                // get the type of role from database
                Map<String, String> role = sqlHelper.getData(userDetailsTable, employeeUniqueIDField.getText());
                // if the role is doctor, switch to doctor main view
                if (role.get("role").equals("doctor")) {
                    doctorMainView.switchToDoctorMainView(primaryStage);
                }
                // if the role is nurse, switch to nurse main view, which is the default employee view
                nurseMainView.switchToNurseMainView(primaryStage);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Login");
                alert.setContentText("The employee ID or password is incorrect. Please try again.");
                alert.showAndWait();
                ButtonType buttonTypeCancel = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel);
            }
        });

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
}