/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.medicare.hospital;
/**
 *
 * @author Student
 */

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class HospitalSystemTest {
    

    private HospitalSystem hospital;

    @BeforeEach
    public void setUp() {
        hospital = new HospitalSystem();
    }

    @Test
    public void testRegisterPatient() throws HospitalException {

        hospital.registerPatient(
                "P001",
                "John",
                "Smith",
                30,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        Patient patient =
                hospital.findPatient("P001");

        assertNotNull(patient);
        assertEquals("John", patient.getFirstName());
    }

    @Test
    public void testSearchPatient() throws HospitalException {

        hospital.registerPatient(
                "P002",
                "Mary",
                "Jones",
                25,
                "Female",
                "Asthma",
                PatientCategory.EMERGENCY
        );

        Patient patient =
                hospital.findPatient("P002");

        assertNotNull(patient);
        assertEquals("P002", patient.getPatientId());
    }

    @Test
    public void testUpdatePatient()
            throws HospitalException {

        hospital.registerPatient(
                "P003",
                "Peter",
                "Brown",
                40,
                "Male",
                "Headache",
                PatientCategory.OUTPATIENT
        );

        hospital.updatePatient(
                "P003",
                "Peter",
                "Brown",
                41,
                "Male",
                "Migraine",
                PatientCategory.OUTPATIENT
        );

        Patient patient =
                hospital.findPatient("P003");

        assertEquals(41, patient.getAge());
        assertEquals("Migraine",
                     patient.getMedicalCondition());
    }

    @Test
    public void testDeletePatient()
            throws HospitalException {

        hospital.registerPatient(
                "P004",
                "Sarah",
                "White",
                28,
                "Female",
                "Fever",
                PatientCategory.OUTPATIENT
        );

        hospital.deletePatient("P004");

        assertNull(
                hospital.findPatient("P004")
        );
    }

    @Test
    public void testAllocateBed()
            throws HospitalException {

        hospital.registerPatient(
                "P005",
                "David",
                "Molefe",
                50,
                "Male",
                "Injury",
                PatientCategory.INPATIENT
        );

        hospital.allocateBed(
                "P005",
                "B01"
        );

        Inpatient patient =
                (Inpatient) hospital.findPatient("P005");

        assertEquals(
                "B01",
                patient.getBedNumber()
        );

        assertEquals(
                1,
                hospital.getOccupiedBedCount()
        );
    }

    @Test
    public void testReleaseBed()
            throws HospitalException {

        hospital.registerPatient(
                "P006",
                "Linda",
                "Mokoena",
                35,
                "Female",
                "Infection",
                PatientCategory.INPATIENT
        );

        hospital.allocateBed(
                "P006",
                "B02"
        );

        hospital.releaseBed("P006");

        Inpatient patient =
                (Inpatient) hospital.findPatient("P006");

        assertEquals(
                "Unassigned",
                patient.getBedNumber()
        );

        assertEquals(
                0,
                hospital.getOccupiedBedCount()
        );
    }

    @Test
    public void testDuplicatePatientId()
            throws HospitalException {

        hospital.registerPatient(
                "P007",
                "Tom",
                "Black",
                20,
                "Male",
                "Cold",
                PatientCategory.OUTPATIENT
        );

        assertThrows(
                HospitalException.class,
                () -> hospital.registerPatient(
                        "P007",
                        "Another",
                        "Person",
                        25,
                        "Male",
                        "Fever",
                        PatientCategory.OUTPATIENT
                )
        );
    }

    @Test
    public void testOccupiedBed()
            throws HospitalException {

        hospital.registerPatient(
                "P008",
                "A",
                "One",
                20,
                "Male",
                "Cold",
                PatientCategory.INPATIENT
        );

        hospital.registerPatient(
                "P009",
                "B",
                "Two",
                21,
                "Female",
                "Fever",
                PatientCategory.INPATIENT
        );

        hospital.allocateBed(
                "P008",
                "B03"
        );

        assertThrows(
                HospitalException.class,
                () -> hospital.allocateBed(
                        "P009",
                        "B03"
                )
        );
    }

    @Test
    public void testFullWard()
            throws HospitalException {

        for (int i = 1; i <= 20; i++) {

            hospital.registerPatient(
                    "P" + String.format("%03d", i),
                    "First" + i,
                    "Last" + i,
                    20 + i,
                    "Male",
                    "Condition",
                    PatientCategory.INPATIENT
            );

            hospital.allocateBed(
                    "P" + String.format("%03d", i),
                    "B" + String.format("%02d", i)
            );
        }

        hospital.registerPatient(
                "P021",
                "Extra",
                "Patient",
                30,
                "Female",
                "Condition",
                PatientCategory.INPATIENT
        );

        assertEquals(
                20,
                hospital.getOccupiedBedCount()
        );

        assertThrows(
                HospitalException.class,
                () -> hospital.allocateBed(
                        "P021",
                        "B01"
                )
        );
    }

    @Test
    public void testSortPatientsBySurname()
            throws HospitalException {

        hospital.registerPatient(
                "P010",
                "John",
                "Zulu",
                25,
                "Male",
                "Cold",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(
                "P011",
                "Mary",
                "Adams",
                30,
                "Female",
                "Fever",
                PatientCategory.OUTPATIENT
        );

        List<Patient> sorted =
                hospital.getPatientsSortedBySurname();

        assertEquals(
                "Adams",
                sorted.get(0).getLastName()
        );

        assertEquals(
                "Zulu",
                sorted.get(1).getLastName()
        );
    }
}
    

