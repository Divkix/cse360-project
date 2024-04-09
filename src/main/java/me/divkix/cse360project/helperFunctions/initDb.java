package me.divkix.cse360project.helperFunctions;

import static me.divkix.cse360project.Healnet.*;

public class initDb {
    // Method to initialize the database
    // This method will create a new database and a new table for user details
    // It will also add a new patient, doctor, and nurse to the user details table
    public static void initializeDatabase() {
        // Create a new database
        sqlHelpers.createNewDatabase();
        // Create a new table for user details
        sqlHelpers.createNewTable(userDetailsTable, new java.util.HashMap<>() {{
            put("username", "text PRIMARY KEY NOT NULL");
            put("first_name", "text DEFAULT NULL");
            put("last_name", "text DEFAULT NULL");
            put("email", "text DEFAULT NULL");
            put("phone", "varchar DEFAULT NULL");
            put("dob", "text DEFAULT NULL");
            put("address", "text DEFAULT NULL");
            put("insurance_provider", "text DEFAULT NULL");
            put("insurance_id", "text DEFAULT NULL");
            put("role", "text DEFAULT 'patient' NOT NULL");
            put("password", "text NOT NULL");
            put("gender", "text DEFAULT NULL");
        }});
        // create a new table for patient details
        sqlHelpers.createNewTable(patientDetailsTable, new java.util.HashMap<>() {{
            put("username", "text PRIMARY KEY NOT NULL");
            put("height", "text DEFAULT NULL");
            put("weight", "text DEFAULT NULL");
            put("blood_type", "text DEFAULT NULL");
            put("allergies", "text DEFAULT NULL");
            put("medications", "text DEFAULT NULL");
            put("medical_conditions", "text DEFAULT NULL");
        }});
        // create a table for patient visits
        sqlHelpers.createNewTable(patientVisitsTable, new java.util.HashMap<>() {{
            put("visit_id", "integer PRIMARY KEY AUTOINCREMENT");
            put("username", "text NOT NULL");
            put("doctor_username", "text NOT NULL");
            put("visit_date", "text NOT NULL");
            put("visit_reason", "text DEFAULT NULL");
            put("diagnosis", "text DEFAULT 'TO BE DONE' NOT NULL");
            put("prescription", "text DEFAULT 'PENDING' NOT NULL");
        }});

        // Add new patient
        sqlHelpers.insertDataIntoTable(userDetailsTable, new java.util.HashMap<>() {{
            put("username", "doe_98765");
            put("role", "patient");
            put("first_name", "John");
            put("last_name", "Doe");
            put("email", "john.doe@example.com");
            put("phone", "123-456-7890");
            put("dob", "01/01/1990");
            put("address", "123 Main St, Anytown, USA");
            put("insurance_provider", "Blue Cross Blue Shield");
            put("insurance_id", "123456789");
            put("password", "password123");
            put("gender", "Male");
        }});
        // Add some health records for the patient
        sqlHelpers.insertDataIntoTable(patientDetailsTable, new java.util.HashMap<>() {{
            put("username", "doe_98765");
            put("height", "6'0\"");
            put("weight", "180 lbs");
            put("blood_type", "O+");
            put("allergies", "Peanuts");
            put("medications", "Aspirin");
            put("medical_conditions", "Asthma");
        }});
        // Add new visit for the patient
        sqlHelpers.insertDataIntoTable(patientVisitsTable, new java.util.HashMap<>() {{
            put("username", "doe_98765");
            put("doctor_username", "jane_12345");
            put("visit_date", "01/01/2021");
            put("visit_reason", "Annual checkup");
            put("diagnosis", "Healthy");
            put("prescription", "None");
        }});
        // Add upcoming visit for the patient
        sqlHelpers.insertDataIntoTable(patientVisitsTable, new java.util.HashMap<>() {{
            put("username", "doe_98765");
            put("doctor_username", "jane_12345");
            put("visit_date", "05/05/2024");
            put("visit_reason", "Stomach Pain");
        }});

        // Add new doctor
        sqlHelpers.insertDataIntoTable(userDetailsTable, new java.util.HashMap<>() {{
            put("username", "jane_12345");
            put("first_name", "Jane");
            put("last_name", "Smith");
            put("role", "doctor");
            put("password", "password123");
        }});
        // Add a new nurse
        sqlHelpers.insertDataIntoTable(userDetailsTable, new java.util.HashMap<>() {{
            put("username", "janice_12345");
            put("first_name", "Janice");
            put("last_name", "Johnson");
            put("role", "nurse");
            put("password", "password123");
        }});
    }
}
