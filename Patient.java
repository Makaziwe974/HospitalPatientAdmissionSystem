/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.medicare.hospital;

/**
 *
 * @author Makaziwe Mtsweni
 */
public class Patient {
    
    
    private String patientId;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String medicalCondition;
    private PatientCategory category;

    public Patient(String patientId,
                   String firstName,
                   String lastName,
                   int age,
                   String gender,
                   String medicalCondition,
                   PatientCategory category) {

        setPatientId(patientId);
        setFirstName(firstName);
        setLastName(lastName);
        setAge(age);
        setGender(gender);
        setMedicalCondition(medicalCondition);
        setCategory(category);
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {

        if (patientId == null || patientId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Patient ID cannot be empty."
            );
        }

        this.patientId = patientId.trim();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {

        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "First name cannot be empty."
            );
        }

        this.firstName = firstName.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {

        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Last name cannot be empty."
            );
        }

        this.lastName = lastName.trim();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {

        if (age < 0 || age > 120) {
            throw new IllegalArgumentException(
                    "Age must be between 0 and 120."
            );
        }

        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {

        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Gender cannot be empty."
            );
        }

        this.gender = gender.trim();
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(String medicalCondition) {

        if (medicalCondition == null ||
            medicalCondition.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Medical condition cannot be empty."
            );
        }

        this.medicalCondition = medicalCondition.trim();
    }

    public PatientCategory getCategory() {
        return category;
    }

    public void setCategory(PatientCategory category) {

        if (category == null) {
            throw new IllegalArgumentException(
                    "Category cannot be null."
            );
        }

        this.category = category;
    }

    public String displayDetails() {

        return String.format(
                "ID: %s | Name: %s %s | Age: %d | "
                + "Gender: %s | Condition: %s | Category: %s",
                patientId,
                firstName,
                lastName,
                age,
                gender,
                medicalCondition,
                category
        );
    }
}
    

