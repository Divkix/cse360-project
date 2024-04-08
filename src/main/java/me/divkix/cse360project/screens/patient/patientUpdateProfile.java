package me.divkix.cse360project.screens.patient;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.divkix.cse360project.Healnet;
import me.divkix.cse360project.helperFunctions.sqlHelpers;

import java.util.HashMap;
import java.util.Map;

public class patientUpdateProfile extends Healnet {
    // method to switch to the patient update profile screen
    public static void switchScreen(Stage primaryStage, String username) {
        VBox screen = new patientUpdateProfile().screen(primaryStage, username);
        primaryStage.getScene().setRoot(screen);
    }

    private VBox screen(Stage primaryStage, String username) {
        // create new vbox
        VBox layout = new VBox(10);
        layout.setStyle(layoutStyleString);
        layout.setAlignment(Pos.CENTER);

        // get current details of the patient
        Map<String, String> patient = sqlHelpers.getDataUsingUsernameFromTable(userDetailsTable, username);

        // create gridpane
        GridPane gridpane = new GridPane();
        gridpane.setAlignment(Pos.CENTER);
        gridpane.setHgap(10);
        gridpane.setVgap(10);

        // make a array here to replace the keys with proper label names
        java.util.Map<String, String> replaceKeys = new java.util.HashMap<>() {{
            put("dob", "Date of Birth: ");
            put("first_name", "First Name: ");
            put("last_name", "Last Name: ");
            put("email", "Email: ");
            put("gender", "Gender: ");
            put("phone", "Phone: ");
            put("address", "Address: ");
        }};

        // Create a map to store the TextFields
        Map<String, TextField> textFieldMap = new java.util.HashMap<>();

        int row = 0;
        for (java.util.Map.Entry<String, String> entry : patient.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            // Check if the key exists in replaceKeys
            if (replaceKeys.containsKey(key)) {
                // Create a label with the key or its replacement
                Label label = new Label(replaceKeys.get(key));
                label.setStyle("-fx-font-size: 14pt;");

                // Create a textfield with the value
                TextField textField = new TextField(value);
                textField.setPrefWidth(300);
                textField.setStyle("-fx-font-size: 14pt;");

                // Store the TextField in the map
                textFieldMap.put(key, textField);

                // Add the label and textfield to the gridpane
                gridpane.add(label, 0, row);
                gridpane.add(textField, 1, row);

                row++;
            }
        }

        // Create a button to update the profile
        Button updateButton = new Button("Update Profile");
        updateButton.setStyle(setStyleButtonString);
        updateButton.setPrefWidth(250);
        updateButton.setOnAction(e -> {
            Map<String, String> newPatientRecord = new HashMap<>();
            for (Map.Entry<String, String> entry : patient.entrySet()) {
                if (!entry.getKey().equals("username") && !entry.getKey().equals("password") && !entry.getKey().equals("role")) {
                    // Retrieve the TextField from the map
                    TextField value = textFieldMap.get(entry.getKey());
                    if (value != null) {
                        newPatientRecord.put(entry.getKey(), value.getText());
                    }
                }
            }
            String alertBoxString = "The fields are already up to date.";
            if (!newPatientRecord.equals(patient)) {
                sqlHelpers.updateDataIntoTable(userDetailsTable, username, newPatientRecord);
                alertBoxString = "Your details have been updated successfully.";
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText(alertBoxString);
            alert.showAndWait();
            ButtonType buttonTypeCancel = new ButtonType("Close", javafx.scene.control.ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeCancel);
        });

        // add a back button to go back to patient main view
        Button backButton = new Button("Back");
        backButton.setStyle(setStyleButtonString);
        backButton.setPrefWidth(250);
        backButton.setOnAction(e -> {
            patientMainView.switchScreen(primaryStage, username);
        });

        layout.getChildren().addAll(gridpane, updateButton, backButton);
        return layout;
    }
}
