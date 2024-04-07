package me.divkix.cse360project.screens;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import me.divkix.cse360project.Healnet;
import me.divkix.cse360project.helperFunctions.sqlHelper;

import java.util.HashMap;

import static me.divkix.cse360project.Healnet.setStyleButtonString;
import static me.divkix.cse360project.Healnet.userDetailsTable;

public class patientSignup {
    // Method to switch to the patient signup screen
    public static void switchToPatientSignupScreen(Stage primaryStage) {
        GridPane patientSignupScreen = new patientSignup().patientSignupScreen(primaryStage);
        primaryStage.getScene().setRoot(patientSignupScreen);
    }

    public GridPane patientSignupScreen(Stage primaryStage) {
        GridPane patientSignUp = new GridPane(); // Create a GridPane layout
        patientSignUp.setHgap(10); // Set horizontal gap
        patientSignUp.setVgap(10); // Set vertical gap
        patientSignUp.setAlignment(Pos.CENTER); // Center the components

        // create a label called "Patient Intake Form"
        Label titleLabel = new Label("Patient Signup Form"); // Create a label
        titleLabel.setStyle("-fx-font-size: 16pt;"); // Set the font size
        patientSignUp.add(titleLabel, 0, 0, 2, 1); // Add to GridPane at column 0, row 0, span 2 columns

        // First Name text field
        Label patientFirstNameLabel = new Label("First Name:"); // Create a label for patient name
        TextField patientFirstNameField = new TextField(); // Create a text field for patient name
        patientFirstNameField.prefWidth(10);
        patientSignUp.add(patientFirstNameLabel, 0, 1); // Add to GridPane at column 0, row 1
        patientSignUp.add(patientFirstNameField, 1, 1); // Add to GridPane at column 1, row 1

        // Last Name text field
        Label patientLastNameLabel = new Label("Last Name:"); // Create a label for last name
        TextField patientLastNameField = new TextField(); // Create a text field for last name
        patientLastNameField.setPrefWidth(150); // Set the width of the text field
        patientSignUp.add(patientLastNameLabel, 0, 2); // Add to GridPane at column 0, row 3
        patientSignUp.add(patientLastNameField, 1, 2); // Add to GridPane at column 1, row 3

        // email field
        Label emailLabel = new Label("Email:"); // Create a label for email
        TextField emailField = new TextField(); // Create a text field for email
        emailField.setPrefWidth(150); // Set the width of the text field
        patientSignUp.add(emailLabel, 0, 3); // Add to GridPane at column 0, row 4
        patientSignUp.add(emailField, 1, 3); // Add to GridPane at column 1, row 4

        // phone number field
        Label phoneLabel = new Label("Phone Number:"); // Create a label for phone number
        TextField phoneField = new TextField(); // Create a text field for phone number
        phoneField.setPrefWidth(150); // Set the width of the text field
        patientSignUp.add(phoneLabel, 0, 4); // Add to GridPane at column 0, row 5
        patientSignUp.add(phoneField, 1, 4); // Add to GridPane at column 1, row 5

        // date of birth
        Label dobLabel = new Label("Date of Birth:"); // Create a label for date of birth
        TextField dobField = new TextField(); // Create a text field for date of birth
        dobField.setPrefWidth(150); // Set the width of the text field
        patientSignUp.add(dobLabel, 0, 5); // Add to GridPane at column 0, row 6
        patientSignUp.add(dobField, 1, 5); // Add to GridPane at column 1, row 6

        // home address
        Label addressLabel = new Label("Home Address:"); // Create a label for home address
        TextField addressField = new TextField(); // Create a text field for home address
        addressField.setPrefWidth(150); // Set the width of the text field
        patientSignUp.add(addressLabel, 0, 6); // Add to GridPane at column 0, row 7
        patientSignUp.add(addressField, 1, 6); // Add to GridPane at column 1, row 7

        // Insurance Provider Box
        Label insuranceProviderLabel = new Label("Insurance Provider:");
        TextField insuranceProviderField = new TextField();
        insuranceProviderField.setPrefWidth(150); // Set the width of the text field
        patientSignUp.add(insuranceProviderLabel, 0, 7); // Add to GridPane at column 0, row 7
        patientSignUp.add(insuranceProviderField, 1, 7); // Add to GridPane at column 1, row 7

        // Insurance ID
        Label insuranceIDLabel = new Label("Insurance ID:");
        TextField insuranceIDField = new TextField();
        insuranceIDField.setPrefWidth(150); // Set the width of the text field
        patientSignUp.add(insuranceIDLabel, 0, 8); // Add to GridPane at column 0, row 8
        patientSignUp.add(insuranceIDField, 1, 8); // Add to GridPane at column 1, row 8

        // password
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(150); // Set the width of the text field
        patientSignUp.add(passwordLabel, 0, 9); // Add to GridPane at column 0, row 8
        patientSignUp.add(passwordField, 1, 9); // Add to GridPane at column 1, row 8

        // confirm password
        Label conformPasswordLabel = new Label("Confirm Password:");
        PasswordField conformPasswordField = new PasswordField();
        conformPasswordField.setPrefWidth(150); // Set the width of the text field
        patientSignUp.add(conformPasswordLabel, 0, 10); // Add to GridPane at column 0, row 8
        patientSignUp.add(conformPasswordField, 1, 10); // Add to GridPane at column 1, row 8

        // Save Patient Info Button
        Button savePatientInfoButton = new Button("Register"); // Create a button for save patient info
        savePatientInfoButton.setStyle(setStyleButtonString); // Set the font size
        patientSignUp.add(savePatientInfoButton, 0, 11, 2, 1); // Add to GridPane at column 0, row 9, span 2 columns


        // Add Event Handler for savePatientInfoButton to handle the save info logic
        savePatientInfoButton.setOnAction(e -> {
            String patientId = patientLastNameField.getText() + "_" + phoneField.getText();
            sqlHelper.insertData(userDetailsTable,
                    new HashMap<>() {{
                        put("username", patientId);
                        put("first_name", patientFirstNameField.getText());
                        put("last_name", patientLastNameField.getText());
                        put("email", emailField.getText());
                        put("phone", phoneField.getText());
                        put("date_of_birth", dobField.getText());
                        put("address", addressField.getText());
                        put("insurance_provider", insuranceProviderField.getText());
                        put("insurance_id", insuranceIDField.getText());
                        put("role", "patient");
                        put("password", "password123");
                    }});
        });

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
        patientSignUp.add(backButton, 0, 12, 2, 1); // Add to GridPane at column 0, row 10, span 2 columns

        // Return the layout
        return patientSignUp;
    }
}
