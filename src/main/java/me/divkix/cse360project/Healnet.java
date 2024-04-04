package me.divkix.cse360project;

// Import the necessary classes
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

// Import the static methods from the sql_helper class
import static me.divkix.cse360project.sql_helper.getData;
import static me.divkix.cse360project.sql_helper.saveData;

// Main class
public class Healnet extends Application {

    // Constants
    static final String setStyleButtonString = "-fx-font-size: 16pt; -fx-background-color: rgb(54, 94, 187); -fx-text-fill: black;"; // Set the font size and background color
    private static final String layoutStyleString = "-fx-padding: 20; -fx-alignment: center;"; // Add padding and center the components
    private static final String patient_intake_db_table = "patient_intake";
    private static final String patient_results_db_table = "patient_results";

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
            saveData(
                    patient_intake_db_table,
                    "first_name", patientFirstNameField.getText(),
                    "middle_name", patientMiddleNameField.getText(),
                    "last_name", patientLastNameField.getText(),
                    "email", emailField.getText(),
                    "phone_number", phoneField.getText(),
                    "insurance_provider", insuranceProviderField.getText(),
                    "patient_id", patientId
            );
        });

        // Add a back button to the top left corner
        Button backButton = new Button("Back"); // Create a back button
        backButton.setOnAction(e -> start(primaryStage)); // Switch to the initial view
        backButton.setStyle(setStyleButtonString); // Set the font size
        patientIntakeLayout.add(backButton, 0, 10, 2, 1); // Add to GridPane at column 0, row 10, span 2 columns

        // Set the scene with height and width
        Scene patientIntakeScene = new Scene(patientIntakeLayout, 600, 600);
        primaryStage.setScene(patientIntakeScene); // Set the scene

    }

    // Method to switch to patient information view
    private void switchToPatientView(Stage primaryStage) {
        VBox patientViewLayout = new VBox(10); // Create a layout with vertical spacing of 10
        patientViewLayout.setAlignment(Pos.CENTER); // Center the components
        patientViewLayout.setStyle(layoutStyleString); // Add padding and center the components

        // patient id label and text field
        Label enterPatientIdLabel = new Label("Enter the Patient ID: "); // Create a label for patient id
        TextField patientIdTextField = new TextField(); // Create a text field for patient id

        // patient info reload button
        Button patientReloadInformationButton = new Button("Load Patient Information"); // Create a button for load information
        patientReloadInformationButton.setStyle(setStyleButtonString); // Set the font size

        // Add Event Handler for patientReloadInformationButton to handle the loading information logic
        patientReloadInformationButton.setOnAction(e -> { // Convert the patient id to an integer
            String patientId = patientIdTextField.getText();
            loadPatientResults(primaryStage, patientId);
        });

        // Add a back button to the top left corner
        Button backButton = new Button("Back"); // Create a back button
        backButton.setOnAction(e -> start(primaryStage)); // Switch to the initial view
        backButton.setStyle(setStyleButtonString); // Set the font size

        // Add the components to the layout
        patientViewLayout.getChildren().addAll(enterPatientIdLabel, patientIdTextField, patientReloadInformationButton, backButton);

        // Set the scene with height and width
        Scene patientViewScene = new Scene(patientViewLayout, 600, 600);
        primaryStage.setScene(patientViewScene); // Set the scene
    }

    private void loadPatientResults(Stage primaryStage, String insuranceOrPatientId) {
        VBox loadPatientResults = new VBox(10); // Create a layout with vertical spacing of 10
        loadPatientResults.setAlignment(Pos.CENTER); // Center the components
        loadPatientResults.setStyle(layoutStyleString); // Add padding and center the components

        // put base variables here to be used in the try catch block
        String firstName = "";
        double totalAgatstonCACScore = 0.0;
        double lmScore = 0.0;
        double ladScore = 0.0;
        double lcxScore = 0.0;
        double rcaScore = 0.0;
        double pdaScore = 0.0;

        // retrieve first_name from patient_info table
        try {
            ResultSet patientResults = getData(patient_intake_db_table, insuranceOrPatientId);
            if (patientResults.next()) { // Move the cursor to the first row
                firstName = patientResults.getString("first_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // retrieve patient information from the database
        try {
            ResultSet patientResults = getData(patient_results_db_table, insuranceOrPatientId);
            if (patientResults.next()) { // Move the cursor to the first row
                totalAgatstonCACScore = patientResults.getDouble("agaston_cac_score");
                lmScore = patientResults.getDouble("lm_score");
                ladScore = patientResults.getDouble("lad_score");
                lcxScore = patientResults.getDouble("lcx_score");
                rcaScore = patientResults.getDouble("rca_score");
                pdaScore = patientResults.getDouble("pda_score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // add label for patient name
        Label patientResultsLabel = new Label(String.format("Hello %s, Patient Results:", firstName)); // Create a label showcasing patient name

        // Create a GridPane with horizontal and vertical gaps of 10
        GridPane riskScores = new GridPane();
        riskScores.setHgap(10);
        riskScores.setVgap(10);

        // Create labels for each of the risk scores
        Label totalAgatstonCACScoreLabel = new Label("Total Agatston CAC Score: ");
        Label lmScoreLabel = new Label("LM Score: ");
        Label ladScoreLabel = new Label("LAD Score: ");
        Label lcxScoreLabel = new Label("LCX Score: ");
        Label rcaScoreLabel = new Label("RCA Score: ");
        Label pdaScoreLabel = new Label("PDA Score: ");

        // Create labels for each of the risk score values
        Label totalAgatstonCACScoreValue = new Label(String.valueOf(totalAgatstonCACScore));
        Label lmScoreValue = new Label(String.valueOf(lmScore));
        Label ladScoreValue = new Label(String.valueOf(ladScore));
        Label lcxScoreValue = new Label(String.valueOf(lcxScore));
        Label rcaScoreValue = new Label(String.valueOf(rcaScore));
        Label pdaScoreValue = new Label(String.valueOf(pdaScore));

        // Add the labels to the GridPane
        riskScores.add(totalAgatstonCACScoreLabel, 0, 0);
        riskScores.add(totalAgatstonCACScoreValue, 1, 0);
        riskScores.add(lmScoreLabel, 0, 1);
        riskScores.add(lmScoreValue, 1, 1);
        riskScores.add(ladScoreLabel, 0, 2);
        riskScores.add(ladScoreValue, 1, 2);
        riskScores.add(lcxScoreLabel, 0, 3);
        riskScores.add(lcxScoreValue, 1, 3);
        riskScores.add(rcaScoreLabel, 0, 4);
        riskScores.add(rcaScoreValue, 1, 4);
        riskScores.add(pdaScoreLabel, 0, 5);
        riskScores.add(pdaScoreValue, 1, 5);

        // Add a back button to the top left corner
        Button backButton = new Button("Back"); // Create a back button
        backButton.setOnAction(e -> start(primaryStage)); // Switch to the initial view
        backButton.setStyle(setStyleButtonString); // Set the font size

        // Add the components to the layout
        loadPatientResults.getChildren().addAll(patientResultsLabel, riskScores, backButton);

        // Set the scene with height and width
        Scene loadPatientResultsScene = new Scene(loadPatientResults, 600, 600);
        primaryStage.setScene(loadPatientResultsScene); // Set the scene
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }
}
