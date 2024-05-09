package com.hospitalmanagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient(){
        System.out.println("Enter the patient Name");
        String name=scanner.next();
        System.out.println("Enter the patient Age");
        int age=scanner.nextInt();
        System.out.println("Enter the patient Gender");
        String gender=scanner.next();

        try{
            String query="INSERT INTO PATIENTS(PATIENT_NAME,PATIENT_AGE,PATIENT_GENDER) VALUES(?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);

            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);

            int affected_roles= preparedStatement.executeUpdate();
            if(affected_roles>0){
                System.out.println("Patient data added successfully!!");
            }
            else{
                System.out.println("data not added,Failure!");
            }



        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void viewPatient(){
        String query = "select * from PATIENTS";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+--------------------+----------+------------+");
            System.out.println("| Patient Id | Name               | Age      | Gender     |");
            System.out.println("+------------+--------------------+----------+------------+");
            while(resultSet.next()){
                int id = resultSet.getInt("P_ID");
                String name = resultSet.getString("PATIENT_NAME");
                int age = resultSet.getInt("PATIENT_AGE");
                String gender = resultSet.getString("PATIENT_GENDER");
                System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n", id, name, age, gender);
                System.out.println("+------------+--------------------+----------+------------+");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getPatientID(int id) {
        String query = "SELECT * FROM PATIENTS WHERE P_ID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }return false;
    }

}


