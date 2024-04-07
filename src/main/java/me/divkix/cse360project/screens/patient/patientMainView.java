package me.divkix.cse360project.screens.patient;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.divkix.cse360project.helperFunctions.sqlHelpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static me.divkix.cse360project.Healnet.*;

public class patientMainView {
    // Method to switch to the patient signup screen
    public static void switchToPatientMainView(Stage primaryStage, String username) {
        VBox screen = new patientMainView().screen(primaryStage, username);
        primaryStage.getScene().setRoot(screen);
    }

    private VBox screen(Stage primaryStage, String username) {
        VBox layout = new VBox(); // Create a GridPane layout
        layout.setSpacing(10); // Set the spacing between the components
        layout.setStyle(layoutStyleString);
        layout.setAlignment(Pos.CENTER); // Center the components

        // get all user visits
        List<Map<String, String>> userVisits = sqlHelpers.getMultipleData(patientVisitsTable, "username", username);
        // get all user details
        Map<String, String> userDetails = sqlHelpers.getDataUsingUsername(userDetailsTable, username);

        // Create a title label
        Label titleLabel = new Label("Welcome, " + userDetails.get("first_name") + " " + userDetails.get("last_name"));
        titleLabel.setStyle("-fx-font-size: 16pt;");
        layout.getChildren().add(titleLabel);

        // get the previous and upcoming visits
        Map<String, List<Map<String, String>>> visits = getVisits(userVisits);
        List<Map<String, String>> previousVisits = visits.get("previousVisits");
        List<Map<String, String>> upcomingVisits = visits.get("upcomingVisits");

        // create a hbox with 2 vboxes
        HBox hbox = new HBox();
        hbox.setStyle(layoutStyleString);
        hbox.setSpacing(40);
        // Create left VBox
        VBox leftVBox = new VBox();
        VBox rightVBox = new VBox();

        addVisitsToVBox(leftVBox, previousVisits, "Previous Appointments", primaryStage);
        addVisitsToVBox(rightVBox, upcomingVisits, "Upcoming Appointments", primaryStage);

        // Add the components to the layout
        hbox.getChildren().addAll(leftVBox, rightVBox);
        layout.getChildren().add(hbox);
        return layout;
    }

    private void addVisitsToVBox(VBox vBox, List<Map<String, String>> visits, String title, Stage primaryStage) {
        // Create a title label
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 16pt;");
        vBox.getChildren().add(titleLabel);

        // create a vbox for spacing
        VBox spacingVBox = new VBox();
        spacingVBox.setPrefHeight(20);
        vBox.getChildren().add(spacingVBox);

        // Loop through all the visits and add them to the VBox
        for (Map<String, String> visit : visits) {
            Label visitLabel = new Label("• " + visit.get("visit_date") + " - " + visit.get("visit_reason"));
            visitLabel.setStyle("-fx-font-size: 14pt; -fx-text-fill: blue; -fx-underline: true;");
            visitLabel.setOnMouseClicked(e -> patientVisitView.switchToPatientVisitView(primaryStage, visit.get("username"), visit.get("visit_id")));
            vBox.getChildren().add(visitLabel);
        }
    }

    // make a method to return 2 lists of visits: previous and upcoming
    private Map<String, List<Map<String, String>>> getVisits(List<Map<String, String>> userVisits) {
        // Create a DateTimeFormatter object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Create 2 lists to store the previous and upcoming visits
        List<Map<String, String>> previousVisits = new ArrayList<>();
        List<Map<String, String>> upcomingVisits = new ArrayList<>();

        // Loop through all the user visits and add them to the appropriate list
        for (Map<String, String> visit : userVisits) {
            LocalDate visitDate = LocalDate.parse(visit.get("visit_date"), formatter);
            if (visitDate.isBefore(currentDate)) {
                previousVisits.add(visit);
            } else if (visitDate.isAfter(currentDate)) {
                upcomingVisits.add(visit);
            }
        }

        // Sort the previous visits in descending order and the upcoming visits in ascending order
        previousVisits.sort((visit1, visit2) -> LocalDate.parse(visit2.get("visit_date"), formatter)
                .compareTo(LocalDate.parse(visit1.get("visit_date"), formatter)));

        // Sort the upcoming visits in ascending order
        upcomingVisits.sort(Comparator.comparing(visit -> LocalDate.parse(visit.get("visit_date"), formatter)));

        // Return the previous and upcoming visits
        return new HashMap<>() {{
            put("previousVisits", previousVisits);
            put("upcomingVisits", upcomingVisits);
        }};
    }
}
