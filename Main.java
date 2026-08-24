/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.medicare.hospital;

/**
 *
 * @author Makaziwe Mtsweni
 */

import java.util.List;
import java.util.Scanner;

public class Main {
    

    private static final Scanner scanner =
            new Scanner(System.in);

    private static final HospitalSystem hospital =
            new HospitalSystem();

    public static void main(String[] args) {

        boolean running = true;

        

        System.out.println(
                " MEDICARE HOSPITAL ADMISSION SYSTEM"
        );


        while (running) {

            displayMenu();

            int choice =
                    readInt("Choose an option: ");

            try {

                switch (choice) {

                    case 1:
                        registerPatient();
                        break;

                    case 2:
                        searchPatient();
                        break;

                    case 3:
                        updatePatient();
                        break;

                    case 4:
                        deletePatient();
                        break;

                    case 5:
                        hospital.displayAllPatients();
                        break;

                    case 6:
                        allocateBed();
                        break;

                    case 7:
                        releaseBed();
                        break;

                    case 8:
                        hospital.displayWardLayout();
                        break;

                    case 9:
                        hospital.displayAvailableBeds();
                        break;

                    case 10:
                        hospital.displayOccupiedBeds();
                        break;

                    case 11:
                        hospital.displayReports();
                        break;

                    case 12:
                        displaySortedPatientsBySurname();
                        break;

                    case 13:
                        displaySortedPatientsById();
                        break;

                    case 0:

                        running = false;

                        System.out.println(
                                "Thank you for using "
                                + "MediCare Hospital System."
                        );

                        break;

                    default:

                        System.out.println(
                                "Invalid option. "
                                + "Please choose from the menu."
                        );
                }

            } catch (HospitalException |
                     IllegalArgumentException e) {

                System.out.println(
                        "\nERROR: "
                        + e.getMessage()
                );
            }
        }

        scanner.close();
    }

    private static void displayMenu() {

        System.out.println(
                "\n MENU"
        );

        System.out.println(
                "1. Register Patient"
        );

        System.out.println(
                "2. Search Patient"
        );

        System.out.println(
                "3. Update Patient"
        );

        System.out.println(
                "4. Delete Patient"
        );

        System.out.println(
                "5. Display All Patients"
        );

        System.out.println(
                "6. Allocate Bed"
        );

        System.out.println(
                "7. Release Bed"
        );

        System.out.println(
                "8. Display Ward Layout"
        );

        System.out.println(
                "9. Display Available Beds"
        );

        System.out.println(
                "10. Display Occupied Beds"
        );

        System.out.println(
                "11. Generate Ward Report"
        );

        System.out.println(
                "12. Sort Patients by Surname"
        );

        System.out.println(
                "13. Sort Patients by Patient ID"
        );

        System.out.println(
                "0. Exit"
        );

       
    }

    private static void registerPatient()
            throws HospitalException {

        
        String id =
                readText("Patient ID: ");

        String firstName =
                readText("First Name: ");

        String lastName =
                readText("Last Name: ");

        int age =
                readInt("Age: ");

        String gender =
                readText("Gender: ");

        String condition =
                readText("Medical Condition: ");

        PatientCategory category =
                chooseCategory();

        hospital.registerPatient(
                id,
                firstName,
                lastName,
                age,
                gender,
                condition,
                category
        );

        System.out.println(
                "Patient registered successfully."
        );
    }

    private static void searchPatient() {

        String id =
                readText(
                        "Enter Patient ID to search: "
                );

        Patient patient =
                hospital.findPatient(id);

        if (patient == null) {

            System.out.println(
                    "Patient not found."
            );

        } else {

            System.out.println(
                    "\nPatient found:"
            );

            System.out.println(
                    patient.displayDetails()
            );
        }
    }

    private static void updatePatient()
            throws HospitalException {

        System.out.println(
                "\n========== UPDATE PATIENT =========="
        );

        String id =
                readText("Patient ID: ");

        Patient patient =
                hospital.findPatient(id);

        if (patient == null) {

            System.out.println(
                    "Patient not found."
            );

            return;
        }

        String firstName =
                readText("New First Name: ");

        String lastName =
                readText("New Last Name: ");

        int age =
                readInt("New Age: ");

        String gender =
                readText("New Gender: ");

        String condition =
                readText(
                        "New Medical Condition: "
                );

        PatientCategory category =
                chooseCategory();

        hospital.updatePatient(
                id,
                firstName,
                lastName,
                age,
                gender,
                condition,
                category
        );

        System.out.println(
                "Patient updated successfully."
        );
    }

    private static void deletePatient()
            throws HospitalException {

        String id =
                readText(
                        "Enter Patient ID to delete: "
                );

        hospital.deletePatient(id);

        System.out.println(
                "Patient deleted successfully."
        );
    }

    private static void allocateBed()
            throws HospitalException {

        System.out.println(
                "\nALLOCATE BED "
        );

        String patientId =
                readText("Patient ID: ");

        String bedNumber =
                readText(
                        "Bed Number (B01-B20): "
                ).toUpperCase();

        hospital.allocateBed(
                patientId,
                bedNumber
        );

        System.out.println(
                "Bed allocated successfully."
        );
    }

    private static void releaseBed()
            throws HospitalException {

        String patientId =
                readText("Patient ID: ");

        hospital.releaseBed(patientId);

        System.out.println(
                "Bed released successfully."
        );
    }

    private static PatientCategory chooseCategory() {

        System.out.println(
                "\nPatient Category:"
        );

        PatientCategory[] categories =
                PatientCategory.values();

        for (int i = 0;
             i < categories.length;
             i++) {

            System.out.println(
                    (i + 1)
                    + ". "
                    + categories[i]
            );
        }

        int choice =
                readInt("Choose category: ");

        return PatientCategory.fromChoice(
                choice
        );
    }

    private static void displaySortedPatientsBySurname() {

        List<Patient> sorted =
                hospital.getPatientsSortedBySurname();

        System.out.println(
                "\n SORTED BY SURNAME "
        );

        for (Patient patient : sorted) {

            System.out.println(
                    patient.displayDetails()
            );
        }
    }

    private static void displaySortedPatientsById() {

        List<Patient> sorted =
                hospital.getPatientsSortedById();

        System.out.println(
                "\nSORTED BY PATIENT ID "
        );

        for (Patient patient : sorted) {

            System.out.println(
                    patient.displayDetails()
            );
        }
    }

    private static String readText(
            String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine().trim();

            if (!input.isEmpty()) {

                return input;
            }

            System.out.println(
                    "Input cannot be empty."
            );
        }
    }

    private static int readInt(
            String message) {

        while (true) {

            System.out.print(message);

            try {

                return Integer.parseInt(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }
}
    

