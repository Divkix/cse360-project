package me.divkix.cse360project.screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.divkix.cse360project.Healnet;

import java.sql.ResultSet;
import java.sql.SQLException;

import static me.divkix.cse360project.Healnet.*;
import static me.divkix.cse360project.helperFunctions.sqlHelper.getData;

public class patientView {
    public VBox patientViewScreen(Stage primaryStage) {
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
        patientViewLayout.getChildren().addAll(enterPatientIdLabel, patientIdTextField, patientReloadInformationButton, backButton);

        return patientViewLayout;
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
        backButton.setOnAction(e ->
        {
            try {
                new Healnet().start(primaryStage); // Switch to the initial view
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }); // Switch to the initial view
        backButton.setStyle(setStyleButtonString); // Set the font size

        // Add the components to the layout
        loadPatientResults.getChildren().addAll(patientResultsLabel, riskScores, backButton);

        // Set the scene with height and width
        Scene loadPatientResultsScene = new Scene(loadPatientResults, 600, 600);
        primaryStage.setScene(loadPatientResultsScene); // Set the scene
    }
}
