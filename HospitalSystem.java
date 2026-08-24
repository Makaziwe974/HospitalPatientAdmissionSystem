/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.medicare.hospital;

/**
 *
 * @author Makaziwe Mtsweni
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class HospitalSystem {
    
    

    private static final int ROWS = 4;
    private static final int COLUMNS = 5;

    private final ArrayList<Patient> patients;
    private final String[][] beds;

    public HospitalSystem() {

        patients = new ArrayList<>();
        beds = new String[ROWS][COLUMNS];

        initialiseBeds();
    }

    private void initialiseBeds() {

        for (int row = 0; row < ROWS; row++) {

            for (int column = 0; column < COLUMNS; column++) {

                beds[row][column] = null;
            }
        }
    }

    public void registerPatient(
            String patientId,
            String firstName,
            String lastName,
            int age,
            String gender,
            String medicalCondition,
            PatientCategory category)
            throws HospitalException {

        if (findPatient(patientId) != null) {

            throw new HospitalException(
                    "Patient ID already exists."
            );
        }

        Patient patient;

        if (category == PatientCategory.INPATIENT) {

            patient = new Inpatient(
                    patientId,
                    firstName,
                    lastName,
                    age,
                    gender,
                    medicalCondition
            );

        } else {

            patient = new Patient(
                    patientId,
                    firstName,
                    lastName,
                    age,
                    gender,
                    medicalCondition,
                    category
            );
        }

        patients.add(patient);
    }

    public Patient findPatient(String patientId) {

        for (Patient patient : patients) {

            if (patient.getPatientId()
                    .equalsIgnoreCase(patientId)) {

                return patient;
            }
        }

        return null;
    }

    public void updatePatient(
            String patientId,
            String firstName,
            String lastName,
            int age,
            String gender,
            String medicalCondition,
            PatientCategory category)
            throws HospitalException {

        Patient existing = findPatient(patientId);

        if (existing == null) {

            throw new HospitalException(
                    "Patient not found."
            );
        }

        if (existing.getCategory() == category) {

            existing.setFirstName(firstName);
            existing.setLastName(lastName);
            existing.setAge(age);
            existing.setGender(gender);
            existing.setMedicalCondition(
                    medicalCondition
            );

            return;
        }

        if (existing instanceof Inpatient) {

            Inpatient inpatient = (Inpatient) existing;

            if (!inpatient.getBedNumber()
                    .equals("Unassigned")) {

                releaseBed(patientId);
            }
        }

        int index = patients.indexOf(existing);

        Patient replacement;

        if (category == PatientCategory.INPATIENT) {

            replacement = new Inpatient(
                    patientId,
                    firstName,
                    lastName,
                    age,
                    gender,
                    medicalCondition
            );

        } else {

            replacement = new Patient(
                    patientId,
                    firstName,
                    lastName,
                    age,
                    gender,
                    medicalCondition,
                    category
            );
        }

        patients.set(index, replacement);
    }

    public void deletePatient(String patientId)
            throws HospitalException {

        Patient patient = findPatient(patientId);

        if (patient == null) {

            throw new HospitalException(
                    "Patient not found."
            );
        }

        if (patient instanceof Inpatient) {

            Inpatient inpatient = (Inpatient) patient;

            if (!inpatient.getBedNumber()
                    .equals("Unassigned")) {

                releaseBed(patientId);
            }
        }

        patients.remove(patient);
    }

    public void allocateBed(
            String patientId,
            String bedNumber)
            throws HospitalException {

        Patient patient = findPatient(patientId);

        if (patient == null) {

            throw new HospitalException(
                    "Patient not found."
            );
        }

        if (!(patient instanceof Inpatient)) {

            throw new HospitalException(
                    "Only inpatient patients may "
                    + "be allocated a bed."
            );
        }

        Inpatient inpatient = (Inpatient) patient;

        if (!inpatient.getBedNumber()
                .equals("Unassigned")) {

            throw new HospitalException(
                    "This patient already has a bed."
            );
        }

        if (getOccupiedBedCount() == ROWS * COLUMNS) {

            throw new HospitalException(
                    "No beds are available."
            );
        }

        int[] position =
                getBedPosition(bedNumber);

        if (beds[position[0]][position[1]] != null) {

            throw new HospitalException(
                    "Bed " + bedNumber
                    + " is already occupied."
            );
        }

        beds[position[0]][position[1]] = patientId;

        inpatient.setWardNumber(1);
        inpatient.setBedNumber(bedNumber);
    }

    public void releaseBed(String patientId)
            throws HospitalException {

        Patient patient = findPatient(patientId);

        if (patient == null) {

            throw new HospitalException(
                    "Patient not found."
            );
        }

        if (!(patient instanceof Inpatient)) {

            throw new HospitalException(
                    "Only inpatients can have "
                    + "a hospital bed."
            );
        }

        Inpatient inpatient =
                (Inpatient) patient;

        String bedNumber =
                inpatient.getBedNumber();

        if (bedNumber.equals("Unassigned")) {

            throw new HospitalException(
                    "This patient does not have "
                    + "an assigned bed."
            );
        }

        int[] position =
                getBedPosition(bedNumber);

        beds[position[0]][position[1]] = null;

        inpatient.setBedNumber("Unassigned");
    }

    private int[] getBedPosition(
            String bedNumber)
            throws HospitalException {

        if (bedNumber == null ||
            !bedNumber.matches(
                    "B(0[1-9]|1[0-9]|20)")) {

            throw new HospitalException(
                    "Invalid bed number. "
                    + "Use B01 to B20."
            );
        }

        int number = Integer.parseInt(
                bedNumber.substring(1)
        );

        int index = number - 1;

        int row = index / COLUMNS;
        int column = index % COLUMNS;

        return new int[]{row, column};
    }

    private String getBedLabel(
            int row,
            int column) {

        int number =
                row * COLUMNS + column + 1;

        return String.format(
                "B%02d",
                number
        );
    }

    public void displayWardLayout() {

        System.out.println(
                "\n WARD LAYOUT "
        );

        for (int row = 0; row < ROWS; row++) {

            for (int column = 0;
                 column < COLUMNS;
                 column++) {

                String bed =
                        getBedLabel(row, column);

                if (beds[row][column] == null) {

                    System.out.printf(
                            "[%s: Available] ",
                            bed
                    );

                } else {

                    System.out.printf(
                            "[%s: %s] ",
                            bed,
                            beds[row][column]
                    );
                }
            }

            System.out.println();
        }
    }

    public void displayAvailableBeds() {

        System.out.println(
                "\nAVAILABLE BEDS "
        );

        boolean found = false;

        for (int row = 0; row < ROWS; row++) {

            for (int column = 0;
                 column < COLUMNS;
                 column++) {

                if (beds[row][column] == null) {

                    System.out.print(
                            getBedLabel(row, column)
                            + " "
                    );

                    found = true;
                }
            }
        }

        if (!found) {

            System.out.println(
                    "No beds available."
            );
        }

        System.out.println();
    }

    public void displayOccupiedBeds() {

        System.out.println(
                "\nOCCUPIED BEDS"
        );

        boolean found = false;

        for (int row = 0; row < ROWS; row++) {

            for (int column = 0;
                 column < COLUMNS;
                 column++) {

                if (beds[row][column] != null) {

                    System.out.println(
                            getBedLabel(row, column)
                            + " -> Patient "
                            + beds[row][column]
                    );

                    found = true;
                }
            }
        }

        if (!found) {

            System.out.println(
                    "No beds occupied."
            );
        }
    }

    public void displayAllPatients() {

        System.out.println(
                "\nALL PATIENTS"
        );

        if (patients.isEmpty()) {

            System.out.println(
                    "No registered patients."
            );

            return;
        }

        for (Patient patient : patients) {

            System.out.println(
                    patient.displayDetails()
            );
        }
    }

    public int getPatientCount() {
        return patients.size();
    }

    public int getOccupiedBedCount() {

        int count = 0;

        for (int row = 0; row < ROWS; row++) {

            for (int column = 0;
                 column < COLUMNS;
                 column++) {

                if (beds[row][column] != null) {
                    count++;
                }
            }
        }

        return count;
    }

    public int getAvailableBedCount() {

        return (ROWS * COLUMNS)
                - getOccupiedBedCount();
    }

    public double getOccupancyPercentage() {

        return (getOccupiedBedCount() * 100.0)
                / (ROWS * COLUMNS);
    }

    public List<Patient> getPatientsSortedBySurname() {

        Patient[] patientArray =
                patients.toArray(
                        new Patient[patients.size()]
                );

        sortPatientArray(
                patientArray,
                true
        );

        return new ArrayList<>(
                Arrays.asList(patientArray)
        );
    }

    public List<Patient> getPatientsSortedById() {

        Patient[] patientArray =
                patients.toArray(
                        new Patient[patients.size()]
                );

        sortPatientArray(
                patientArray,
                false
        );

        return new ArrayList<>(
                Arrays.asList(patientArray)
        );
    }

    private void sortPatientArray(
            Patient[] patientArray,
            boolean bySurname) {

        Arrays.sort(
                patientArray,
                Comparator.comparing(
                        patient -> bySurname
                                ? patient.getLastName()
                                : patient.getPatientId(),
                        String.CASE_INSENSITIVE_ORDER
                )
        );

        int numberOfPatients =
                patientArray.length;

        System.out.println(
                "Sorted "
                + numberOfPatients
                + " patient(s)."
        );
    }

    public void displayReports() {

        System.out.println(
                "\n"
        );

        System.out.println(
                "MEDICARE WARD REPORT"
        );


        displayAllPatients();

        displayAvailableBeds();

        displayOccupiedBeds();

        System.out.println(
                "\nTotal registered patients: "
                + getPatientCount()
        );

        System.out.println(
                "Total occupied beds: "
                + getOccupiedBedCount()
        );

        System.out.println(
                "Total available beds: "
                + getAvailableBedCount()
        );

        System.out.printf(
                "Ward occupancy: %.2f%%%n",
                getOccupancyPercentage()
        );
    }
}
    

