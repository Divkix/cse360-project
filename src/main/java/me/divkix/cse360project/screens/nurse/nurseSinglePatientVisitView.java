package me.divkix.cse360project.screens.nurse;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.divkix.cse360project.helperFunctions.sqlHelpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.divkix.cse360project.Healnet.*;

public class nurseSinglePatientVisitView {
    public static void switchScreen(Stage primaryStage, String patientId, String visitId) {
        VBox screen = new nurseSinglePatientVisitView().screen(primaryStage, patientId, visitId);
        primaryStage.getScene().setRoot(screen);
    }

    private VBox screen(Stage primaryStage, String patientId, String visitId) {
        VBox layout = new VBox(); // Create a layout with vertical spacing of 15
        layout.setAlignment(Pos.CENTER); // Center the components
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;"); // Add padding and center the components
        layout.setSpacing(15); // Set the spacing between the components

        // get the visit details from the database
        List<Map<String, String>> visitDetails = sqlHelpers.getMultipleDataFromTable(patientVisitsTable, "username", patientId);
        Map<String, String> pateintInfo = sqlHelpers.getDataUsingUsernameFromTable(userDetailsTable, patientId);

        // Single Visit Details
        Map<String, String> singleVisitDetails = new HashMap<>();

        // iterate throught the list of visit details and set it to the single visit details
        for (Map<String, String> visit : visitDetails) {
            if (visit.get("visit_id").equals(visitId)) {
                singleVisitDetails = visit;
            }
        }

        Map<String, String> replaceKeys = new HashMap<>() {{
            put("visit_id", "Visit ID: ");
            put("visit_date", "Visit Date: ");
            put("visit_reason", "Visit Reason: ");
            put("diagnosis", "Diagnosis: ");
            put("prescription", "Prescription: ");
        }};

        // create a label called "Visit Details"
        Label titleLabel = new Label("Visit Details for " + pateintInfo.get("first_name")); // Create a label
        // add a blamk space between title and gridpane using a VBox with set length
        VBox blankSpace = new VBox();
        blankSpace.setPrefHeight(20);
        titleLabel.setStyle("-fx-font-size: 16pt;"); // Set the font size

        // create a gridpane with the visit details
        // add the gridpane to the layout
        GridPane visitDetailslayout = new GridPane();
        visitDetailslayout.setHgap(10); // Set the horizontal gap between the components
        visitDetailslayout.setVgap(10); // Set the vertical gap between the components
        visitDetailslayout.setAlignment(Pos.CENTER); // Center the components

        // for all of the other fields, add a label, label to the gridpane and set the second label to the value from the database
        // also, set the label to the field name and apply inline css to set the font size
        for (Map.Entry<String, String> entry : singleVisitDetails.entrySet()) {
            if (replaceKeys.containsKey(entry.getKey())) {
                Label label = new Label(replaceKeys.get(entry.getKey()));
                label.setStyle("-fx-font-size: 14pt;");
                Label value = new Label(entry.getValue());
                value.setStyle("-fx-font-size: 14pt;");
                visitDetailslayout.add(label, 0, visitDetailslayout.getRowCount());
                visitDetailslayout.add(value, 1, visitDetailslayout.getRowCount() - 1);
            }
        }

        // add a back button to the HBox to return back to the patient list
        Button backButton = new Button("Back");
        backButton.setStyle(setStyleButtonString);
        backButton.setOnAction(e -> {
            nurseSinglePatientView.switchScreen(primaryStage, patientId);
        });

        layout.getChildren().addAll(titleLabel, blankSpace, visitDetailslayout, backButton);
        return layout;
    }
}
