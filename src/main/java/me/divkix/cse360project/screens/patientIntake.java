package me.divkix.cse360project.screens;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import me.divkix.cse360project.Healnet;

import static me.divkix.cse360project.Healnet.patient_intake_db_table;
import static me.divkix.cse360project.Healnet.setStyleButtonString;
import static me.divkix.cse360project.helperFunctions.sqlHelper.saveData;

public class patientIntake {
    public GridPane patientIntakeScreenClass(Stage primaryStage) {
        GridPane patientIntakeLayout = new GridPane(); // Create a GridPane layout
        patientIntakeLayout.setHgap(10); // Set horizontal gap
        patientIntakeLayout.setVgap(10); // Set vertical gap
        patientIntakeLayout.setAlignment(Pos.CENTER); // Center the components

        // create a label called "Patient Intake Form"
        Label titleLabel = new Label("Patient Intake Form"); // Create a label
        titleLabel.setStyle("-fx-font-size: 16pt;"); // Set the font size
        patientIntakeLayout.add(titleLabel, 0, 0, 2, 1); // Add to GridPane at column 0, row 0, span 2 columns

        // First Name text field
        Label patientFirstNameLabel = new Label("First Name:"); // Create a label for patient name
        TextField patientFirstNameField = new TextField(); // Create a text field for patient name
        patientFirstNameField.prefWidth(10);
        patientIntakeLayout.add(patientFirstNameLabel, 0, 1); // Add to GridPane at column 0, row 1
        patientIntakeLayout.add(patientFirstNameField, 1, 1); // Add to GridPane at column 1, row 1

        // Middle Name text field
        Label patientMiddleNameLabel = new Label("Middle Name:"); // Create a label for patient name
        TextField patientMiddleNameField = new TextField(); // Create a text field for patient name
        patientMiddleNameField.prefWidth(10);
        patientIntakeLayout.add(patientMiddleNameLabel, 0, 2); // Add to GridPane at column 0, row 2
        patientIntakeLayout.add(patientMiddleNameField, 1, 2); // Add to GridPane at column 1, row 2

        // Last Name text field
        Label patientLastNameLabel = new Label("Last Name:"); // Create a label for last name
        TextField patientLastNameField = new TextField(); // Create a text field for last name
        patientLastNameField.setPrefWidth(150); // Set the width of the text field
        patientIntakeLayout.add(patientLastNameLabel, 0, 3); // Add to GridPane at column 0, row 3
        patientIntakeLayout.add(patientLastNameField, 1, 3); // Add to GridPane at column 1, row 3

        // email field
        Label emailLabel = new Label("Email:"); // Create a label for email
        TextField emailField = new TextField(); // Create a text field for email
        emailField.setPrefWidth(150); // Set the width of the text field
        patientIntakeLayout.add(emailLabel, 0, 4); // Add to GridPane at column 0, row 4
        patientIntakeLayout.add(emailField, 1, 4); // Add to GridPane at column 1, row 4

        // phone number field
        Label phoneLabel = new Label("Phone Number:"); // Create a label for phone number
        TextField phoneField = new TextField(); // Create a text field for phone number
        phoneField.setPrefWidth(150); // Set the width of the text field
        patientIntakeLayout.add(phoneLabel, 0, 5); // Add to GridPane at column 0, row 5
        patientIntakeLayout.add(phoneField, 1, 5); // Add to GridPane at column 1, row 5

        // Health History
        Label healthHistoryLabel = new Label("Health History:");
        TextField healthHistoryField = new TextField();
        healthHistoryField.setPrefWidth(150); // Set the width of the text field
        patientIntakeLayout.add(healthHistoryLabel, 0, 6); // Add to GridPane at column 0, row 6
        patientIntakeLayout.add(healthHistoryField, 1, 6); // Add to GridPane at column 1, row 6

        // Insurance Provider Box
        Label insuranceProviderLabel = new Label("Insurance Provider:");
        TextField insuranceProviderField = new TextField();
        insuranceProviderField.setPrefWidth(150); // Set the width of the text field
        patientIntakeLayout.add(insuranceProviderLabel, 0, 7); // Add to GridPane at column 0, row 7
        patientIntakeLayout.add(insuranceProviderField, 1, 7); // Add to GridPane at column 1, row 7

        // Insurance ID
        Label insuranceIDLabel = new Label("Insurance ID:");
        TextField insuranceIDField = new TextField();
        insuranceIDField.setPrefWidth(150); // Set the width of the text field
        patientIntakeLayout.add(insuranceIDLabel, 0, 8); // Add to GridPane at column 0, row 8
        patientIntakeLayout.add(insuranceIDField, 1, 8); // Add to GridPane at column 1, row 8

        // Save Patient Info Button
        Button savePatientInfoButton = new Button("Save Patient Info"); // Create a button for save patient info
        savePatientInfoButton.setStyle(setStyleButtonString); // Set the font size
        patientIntakeLayout.add(savePatientInfoButton, 0, 9, 2, 1); // Add to GridPane at column 0, row 9, span 2 columns

        // Add Event Handler for savePatientInfoButton to handle the save info logic
        savePatientInfoButton.setOnAction(e -> {
            String patientId = patientLastNameField.getText() + "_" + patientFirstNameField.getText() + "_" + phoneField.getText();
            saveData(patient_intake_db_table, "first_name", patientFirstNameField.getText(), "middle_name", patientMiddleNameField.getText(), "last_name", patientLastNameField.getText(), "email", emailField.getText(), "phone_number", phoneField.getText(), "insurance_provider", insuranceProviderField.getText(), "patient_id", patientId);
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
        patientIntakeLayout.add(backButton, 0, 10, 2, 1); // Add to GridPane at column 0, row 10, span 2 columns

        // Return the layout
        return patientIntakeLayout;
    }
}
