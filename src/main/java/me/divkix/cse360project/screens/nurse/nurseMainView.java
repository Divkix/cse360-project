package me.divkix.cse360project.screens.nurse;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.divkix.cse360project.helperFunctions.sqlHelpers;

import java.util.List;
import java.util.Map;

import static me.divkix.cse360project.Healnet.layoutStyleString;
import static me.divkix.cse360project.Healnet.userDetailsTable;

public class nurseMainView {
    // Method to switch to the patient signup screen
    public static void switchToNurseMainView(Stage primaryStage) {
        VBox screen = new nurseMainView().screen(primaryStage);
        primaryStage.getScene().setRoot(screen);
    }

    public VBox screen(Stage primaryStage) {
        VBox layout = new VBox(15); // Create a layout with vertical spacing of 15
        layout.setAlignment(Pos.CENTER); // Center the components
        layout.setStyle(layoutStyleString); // Add padding and center the components

        // get the list of patients from the database where role is patient
        // display the list of patients in a vbox with clickable labels
        // when a label is clicked, switch to the patient info view screen
        List<Map<String, String>> patients = sqlHelpers.getMultipleData(userDetailsTable, "role", "patient");

        // create a label called "Patient List"
        Label titleLabel = new Label("Patient List"); // Create a label
        titleLabel.setStyle("-fx-font-size: 16pt;"); // Set the font size
        layout.getChildren().add(titleLabel); // Add the label to the layout

        // create a vbox to hold the list of patients
        VBox patientList = new VBox(5); // Create a layout with vertical spacing of 5
        patientList.setAlignment(Pos.CENTER); // Center the components

        // loop through the list of patients and create a clickable label for each patient with their name with their name with first letter capitalized
        for (Map<String, String> patient : patients) {
            // format the patient name with first letter capitalized
            String patientFirstNameFormat = patient.get("first_name").substring(0, 1).toUpperCase() + patient.get("first_name").substring(1);
            String patientLastNameFormat = patient.get("last_name").substring(0, 1).toUpperCase() + patient.get("last_name").substring(1);
            // create a label with the patient name
            Label patientLabel = new Label(patientFirstNameFormat + " " + patientLastNameFormat); // Create a label
            patientLabel.setStyle("-fx-font-size: 14pt; -fx-text-fill: blue; -fx-underline: true;"); // Set the font size, color, and underline
            // when the label is clicked, switch to the patient info view screen as viewed by nurse
            patientLabel.setOnMouseClicked(e -> {
                // When the label is clicked, switch to the patient info view screen
                nurseSinglePatientView.switchToNurseSinglePatientView(primaryStage, patient.get("username"));
            });
            patientList.getChildren().add(patientLabel); // Add the label to the layout
        }

        // Add the layout to the main layout
        layout.getChildren().add(patientList);

        return layout;
    }
}