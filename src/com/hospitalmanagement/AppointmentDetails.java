package com.hospitalmanagement;

import java.sql.*;


public class AppointmentDetails {

    private Connection connection;

    public AppointmentDetails(Connection connection) {
        this.connection = connection;
    }


    public void printAppointmentsByPatientID(int patientID) {
        String query = "SELECT AP_ID, DOCTOR_ID, APPOINTMENT_DATE, PATIENT_NAME " +
                "FROM APPOINTMENTS " +
                "JOIN PATIENTS ON APPOINTMENTS.PATIENT_ID = PATIENTS.P_ID " +
                "WHERE APPOINTMENTS.PATIENT_ID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, patientID);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Appointments for Patient ID: "+patientID);
            System.out.println("+---------------+---------------+-------------------+-----------------+");
            System.out.println("| Appointment ID| Doctor ID     | Appointment Date  | Patient Name    |");
            System.out.println("+---------------+---------------+-------------------+-----------------+");

            while (resultSet.next()) {
                int appointmentID = resultSet.getInt("AP_ID");
                int doctorID = resultSet.getInt("DOCTOR_ID");
                String appointmentDate = resultSet.getString("APPOINTMENT_DATE");
                String patientName = resultSet.getString("PATIENT_NAME");

                System.out.printf("| %-14s | %-14s | %-19s | %-16s |\n", appointmentID, doctorID, appointmentDate, patientName);
            }

            System.out.println("+---------------+---------------+-------------------+-----------------+");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void printAppointmentsByAppID(int appID){
        String query = "SELECT AP_ID, DOCTOR_ID, APPOINTMENT_DATE, PATIENT_NAME " +
                "FROM APPOINTMENTS " +
                "JOIN PATIENTS ON APPOINTMENTS.PATIENT_ID = PATIENTS.P_ID " +
                "WHERE APPOINTMENTS.AP_ID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, appID);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Appointment ID: "+appID);
            System.out.println("+---------------+---------------+-------------------+-----------------+");
            System.out.println("| Appointment ID| Doctor ID     | Appointment Date  | Patient Name    |");
            System.out.println("+---------------+---------------+-------------------+-----------------+");

            while (resultSet.next()) {
                int appointmentID = resultSet.getInt("AP_ID");
                int doctorID = resultSet.getInt("DOCTOR_ID");
                String appointmentDate = resultSet.getString("APPOINTMENT_DATE");
                String patientName = resultSet.getString("PATIENT_NAME");

                System.out.printf("| %-13s| %-13s| %-18s| %-15s|\n", appointmentID, doctorID, appointmentDate, patientName);
            }

            System.out.println("+---------------+---------------+-------------------+-----------------+");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }



}