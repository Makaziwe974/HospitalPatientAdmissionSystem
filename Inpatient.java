/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.medicare.hospital;

/**
 *
 * @author Makaziwe Mtsweni
 */
public class Inpatient extends Patient {
    
   

    private int wardNumber;
    private String bedNumber;

    public Inpatient(String patientId,
                     String firstName,
                     String lastName,
                     int age,
                     String gender,
                     String medicalCondition) {

        super(
                patientId,
                firstName,
                lastName,
                age,
                gender,
                medicalCondition,
                PatientCategory.INPATIENT
        );

        this.wardNumber = 1;
        this.bedNumber = "Unassigned";
    }

    public int getWardNumber() {
        return wardNumber;
    }

    public void setWardNumber(int wardNumber) {

        if (wardNumber <= 0) {
            throw new IllegalArgumentException(
                    "Ward number must be positive."
            );
        }

        this.wardNumber = wardNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {

        if (bedNumber == null ||
            bedNumber.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Bed number cannot be empty."
            );
        }

        this.bedNumber = bedNumber;
    }

    @Override
    public String displayDetails() {

        return super.displayDetails()
                + String.format(
                        " | Ward: %d | Bed: %s",
                        wardNumber,
                        bedNumber
                );
    }
}
    

