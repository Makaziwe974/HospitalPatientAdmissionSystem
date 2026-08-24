/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.medicare.hospital;

/**
 *
 * @author Makaziwe Mtsweni
 */
public enum PatientCategory {
    
    INPATIENT,
    OUTPATIENT,
    EMERGENCY;

    public static PatientCategory fromChoice(int choice) {
        switch (choice) {
            case 1:
                return INPATIENT;
            case 2:
                return OUTPATIENT;
            case 3:
                return EMERGENCY;
            default:
                throw new IllegalArgumentException(
                        "Invalid patient category."
                );
        }
    }
}
    

