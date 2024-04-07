package me.divkix.cse360project.screens.nurse;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.divkix.cse360project.helperFunctions.sqlHelpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.divkix.cse360project.Healnet.*;

public class nurseSinglePatientView {
    // Method to switch to the patient signup screen
    public static void switchToNurseSinglePatientView(Stage primaryStage, String patientId) {
        VBox screen = new nurseSinglePatientView().screen(primaryStage, patientId);
        primaryStage.getScene().setRoot(screen);
    }

    private VBox screen(Stage primaryStage, String patientId) {
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER); // Center the components
        layout.setStyle(layoutStyleString); // Add padding and center the components

        // create a label called "Patient Details"
        Label titleLabel = new Label("Patient Details");
        titleLabel.setStyle("-fx-font-size: 16pt;"); // Set the font size
        layout.getChildren().add(titleLabel); // Add the label to the layout

        // make a hbox with 2 vboxes
        HBox mainHBox = new HBox();
        mainHBox.setSpacing(10); // set the spacing between the vboxes

        VBox leftVBox = new VBox(); // left vbox will have the patient info
        leftVBox.setSpacing(20); // Set the spacing between the components
        leftVBox.setPrefWidth(600); // Set the width of the vbox
        leftVBox.setStyle(layoutStyleString); // Add padding and center the components
        leftVBox.setAlignment(Pos.CENTER); // Center the components

        VBox rightVBox = new VBox(); // right vbox will have the patient's visitis
        rightVBox.setSpacing(20); // Set the spacing between the components
        rightVBox.setPrefWidth(600); // Set the width of the vbox
        rightVBox.setStyle(layoutStyleString); // Add padding and center the components
        rightVBox.setAlignment(Pos.TOP_CENTER); // Center the components

        // get current details of the patient
        Map<String, String> patient = sqlHelpers.getDataUsingUsername(userDetailsTable, patientId);
        Map<String, String> patientRecord = sqlHelpers.getDataUsingUsername(patientDetailsTable, patientId);

        // make a gridpane with the patient details
        // add the gridpane to the left vbox
        GridPane patientDetails = new GridPane();
        patientDetails.setHgap(10); // Set the horizontal gap between the components
        patientDetails.setVgap(10); // Set the vertical gap between the components
        // create a label for each field
        // create a textfield for each field
        // add the label and textfield to the gridpane
        String patientFirstNameFormat = patient.get("first_name").substring(0, 1).toUpperCase() + patient.get("first_name").substring(1);
        String patientLastNameFormat = patient.get("last_name").substring(0, 1).toUpperCase() + patient.get("last_name").substring(1);
        Label patientNameLabel = new Label("Name: ");
        patientNameLabel.setStyle("-fx-font-size: 14pt;"); // using inline css to set the font size
        Label patientNameValue = new Label(patientFirstNameFormat + " " + patientLastNameFormat);
        patientNameValue.setStyle("-fx-font-size: 14pt;"); // using inline css to set the font size
        // add the label and textfield to the gridpane
        patientDetails.add(patientNameLabel, 0, 0);
        patientDetails.add(patientNameValue, 1, 0);

        // make a array here to replace the keys with proper label names
        Map<String, String> replaceKeys = new HashMap<>() {{
            put("dob", "Date of Birth: ");
            put("first_name", "First Name: ");
            put("last_name", "Last Name: ");
            put("height", "Height: ");
            put("weight", "Weight: ");
            put("blood_type", "Blood Type: ");
            put("allergies", "Allergies: ");
            put("medications", "Medications: ");
            put("medical_conditions", "Medical Conditions: ");
            put("gender", "Gender: ");
        }};

        // Create a map to store the TextFields
        Map<String, TextField> textFieldMap = new HashMap<>();

        // for all of the other fields, add a label and textfield to the gridpane and set the textfield to the value from the database
        // also, set the label to the field name and apply inline css to set the font size
        for (Map.Entry<String, String> entry : patientRecord.entrySet()) {
            if (!entry.getKey().equals("username") && !entry.getKey().equals("password") && !entry.getKey().equals("role")) {
                Label label = new Label(replaceKeys.getOrDefault(entry.getKey(), entry.getKey()));
                label.setStyle("-fx-font-size: 14pt;");

                if (entry.getKey().equals("gender")) {
                    Label valueLabel = new Label(entry.getValue());
                    valueLabel.setStyle("-fx-font-size: 14pt;");
                    patientDetails.add(label, 0, patientDetails.getRowCount());
                    patientDetails.add(valueLabel, 1, patientDetails.getRowCount() - 1);
                } else {
                    TextField value = new TextField(entry.getValue());
                    value.setStyle("-fx-font-size: 14pt;");
                    patientDetails.add(label, 0, patientDetails.getRowCount());
                    patientDetails.add(value, 1, patientDetails.getRowCount() - 1);

                    // Store the TextField in the map
                    textFieldMap.put(entry.getKey(), value);
                }
            }
        }

        // add a update button to the left vbox
        Button updateButton = new Button("Update Patient Info");
        updateButton.setStyle(setStyleButtonString); // Set the font size and background color
        // when the button is clicked, update the patient info in the database
        updateButton.setOnAction(e -> {
            Map<String, String> newPatientRecord = new HashMap<>();
            for (Map.Entry<String, String> entry : patientRecord.entrySet()) {
                if (!entry.getKey().equals("username") && !entry.getKey().equals("password") && !entry.getKey().equals("role")) {
                    // Retrieve the TextField from the map
                    TextField value = textFieldMap.get(entry.getKey());
                    if (value != null) {
                        newPatientRecord.put(entry.getKey(), value.getText());
                    }
                }
            }
            String alertBoxString = "The fields are already up to date.";
            if (!newPatientRecord.equals(patientRecord)) {
                sqlHelpers.updateData(patientDetailsTable, patientId, newPatientRecord);
                alertBoxString = "Patient details have been updated successfully.";
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText(alertBoxString);
            alert.showAndWait();
            ButtonType buttonTypeCancel = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeCancel);
        });
        // add the patient details and update button to the left vbox
        leftVBox.getChildren().addAll(patientDetails, updateButton);

        Label visitsLabel = new Label("Visits");
        visitsLabel.setStyle("-fx-font-size: 16pt;"); // Set the font size

        // get the list of visits for the patient
        List<Map<String, String>> userVisits = sqlHelpers.getMultipleData(patientVisitsTable, "username", patientId);

        // create a vbox to hold the list of visits
        VBox visitList = new VBox(5); // Create a layout with vertical spacing of 5
        visitList.setAlignment(Pos.CENTER); // Center the components
        visitList.setStyle(layoutStyleString); // Add padding and center the components

        if (!userVisits.isEmpty()) {
            // loop through the list of visits and create a label for each visit with the date and time
            for (Map<String, String> visit : userVisits) {
                // create a label with the visit date and time
                Label visitLabel = new Label(visit.get("visit_date")); // Create a label
                visitLabel.setStyle("-fx-font-size: 14pt; -fx-text-fill: blue; -fx-underline: true;"); // Set the font size, color, and underline
                visitLabel.setOnMouseClicked(e -> {
                    // When the label is clicked, switch to the patient info view screen
                    nurseSinglePatientVisitView.switchToNurseSinglePatientVisitView(primaryStage, patientId, visit.get("visit_id"));
                });
                visitList.getChildren().add(visitLabel); // Add the label to the layout
            }
        } else {
            Label noVisitsLabel = new Label("No visits found.");
            noVisitsLabel.setStyle("-fx-font-size: 14pt;");
            visitList.getChildren().add(noVisitsLabel);
        }
        // add the visits label and visit list to the right vbox
        rightVBox.getChildren().addAll(visitsLabel, visitList);

        // Add the left and right vboxes to the main hbox
        mainHBox.getChildren().addAll(leftVBox, rightVBox);

        // add a back button to the HBox to return back to the patient list
        Button backButton = new Button("Back");
        backButton.setStyle(setStyleButtonString);
        backButton.setOnAction(e -> nurseMainView.switchToNurseMainView(primaryStage));

        layout.getChildren().addAll(mainHBox, backButton); // Add the main hbox to the layout
        return layout;
    }
}
