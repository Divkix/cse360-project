package me.divkix.cse360project.screens.doctor;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.divkix.cse360project.Healnet;
import me.divkix.cse360project.helperFunctions.sqlHelpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class doctorMainView extends Healnet {
    public static void switchScreen(Stage primaryStage) {
        VBox screen = new doctorMainView().screen(primaryStage);
        primaryStage.getScene().setRoot(screen);
    }

    private VBox screen(Stage primaryStage) {
        VBox layout = new VBox(15); // Create a layout with vertical spacing of 15
        layout.setAlignment(Pos.TOP_CENTER); // Center the components
        layout.setStyle(layoutStyleString); // Add padding and center the components

        HBox mainHBox = new HBox(35); // Create a layout with horizontal spacing of 35
        mainHBox.setAlignment(Pos.CENTER); // Center the components
        mainHBox.setStyle(layoutStyleString); // Add padding and center the components

        VBox leftVBox = new VBox(15); // Create a layout with horizontal spacing of 15
        leftVBox.setAlignment(Pos.CENTER); // Center the components
        leftVBox.setStyle(layoutStyleString); // Add padding and center the components

        // get the list of patients from the database where role is patient
        // display the list of patients in a vbox with clickable labels
        // when a label is clicked, switch to the patient info view screen
        List<Map<String, String>> patients = sqlHelpers.getMultipleDataFromTable(userDetailsTable, "role", "patient");

        // create a label called "Welcome Doctor <doctor name>"
        Map<String, String> doctorDetails = sqlHelpers.getMultipleDataFromTable(userDetailsTable, "role", "doctor").getFirst();
        Label welcomeLabel = new Label("Welcome Doctor " + doctorDetails.get("first_name")); // Create a label
        welcomeLabel.setStyle("-fx-font-size: 16pt;"); // Set the font size
        layout.getChildren().add(welcomeLabel); // Add the label to the layout

        // create a label called "Patient List"
        Label titleLabel = new Label("Patient List"); // Create a label
        titleLabel.setStyle("-fx-font-size: 16pt;"); // Set the font size
        leftVBox.getChildren().add(titleLabel); // Add the label to the layout

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
            // when the label is clicked, switch to the patient info view screen as viewed by doctor
            patientLabel.setOnMouseClicked(e -> {
                // When the label is clicked, switch to the patient info view screen
                doctorSinglePatientView.switchScreen(primaryStage, patient.get("username"));
            });
            patientList.getChildren().add(patientLabel); // Add the label to the layout
        }

        // Add the layout to the main layout
        leftVBox.getChildren().add(patientList);


        // right vbox
        VBox rightVBox = new VBox(15); // Create a layout with horizontal spacing of 15
        rightVBox.setAlignment(Pos.CENTER); // Center the components
        rightVBox.setStyle(layoutStyleString); // Add padding and center the components

        // create a label called "Upcoming Appointments"
        Label upcomingAppointmentsLabel = new Label("Upcoming Appointments"); // Create a label
        upcomingAppointmentsLabel.setStyle("-fx-font-size: 16pt;"); // Set the font size
        rightVBox.getChildren().add(upcomingAppointmentsLabel); // Add the label to the layout

        // get the list of appointments from the database where role is patient
        // display the list of appointments in a vbox with clickable labels
        // when a label is clicked, switch to the appointment info view screen
        List<Map<String, String>> appointments = sqlHelpers.getMultipleDataFromTable(patientVisitsTable, "doctor_username", doctorDetails.get("username"));


        // create a vbox to hold the list of appointments
        VBox appointmentList = new VBox(5); // Create a layout with vertical spacing of 5
        appointmentList.setAlignment(Pos.CENTER); // Center the components
        appointmentList.setStyle("-fx-font-size: 14pt;");

        // filter and sort the date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate currentDate = LocalDate.now();

        List<Map<String, String>> filteredAppointments = appointments.stream().filter(appointment -> !java.time.LocalDate.parse(appointment.get("visit_date"), formatter).isBefore(currentDate)).sorted(Comparator.comparing(appointment -> LocalDate.parse(appointment.get("visit_date"), formatter))).collect(Collectors.toList());

        // loop through the list of appointments and create a clickable label for each appointment with the patient name and date
        for (Map<String, String> appointment : filteredAppointments) {
            // get the patient details
            Map<String, String> patientDetails = sqlHelpers.getMultipleDataFromTable(userDetailsTable, "username", appointment.get("username")).getFirst();
            // format the patient name with first letter capitalized
            String patientFirstNameFormat = patientDetails.get("first_name").substring(0, 1).toUpperCase() + patientDetails.get("first_name").substring(1);
            String patientLastNameFormat = patientDetails.get("last_name").substring(0, 1).toUpperCase() + patientDetails.get("last_name").substring(1);
            // create a label with the patient name and date
            Label appointmentLabel = new Label(patientFirstNameFormat + " " + patientLastNameFormat + " - " + appointment.get("visit_date")); // Create a label
            appointmentLabel.setStyle("-fx-font-size: 14pt; -fx-text-fill: blue; -fx-underline: true;"); // Set the font size, color, and underline
            // when the label is clicked, switch to the appointment info view screen
            appointmentLabel.setOnMouseClicked(e -> {
                // When the label is clicked, switch to the appointment info view screen
                doctorSinglePatientVisitView.switchScreen(primaryStage, appointment.get("username"), appointment.get("visit_id"));
            });

            appointmentList.getChildren().add(appointmentLabel); // Add the label to the layout
        }

        // Add the layout to the main layout
        rightVBox.getChildren().add(appointmentList);

        // add left and right vbox to main hbox
        mainHBox.getChildren().addAll(leftVBox, rightVBox);

        // logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle(setStyleButtonString);
        logoutButton.setOnAction(e -> {
            try {
                new Healnet().start(primaryStage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        layout.getChildren().addAll(mainHBox, logoutButton);

        return layout;
    }
}
