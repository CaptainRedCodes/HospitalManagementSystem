package com.hospitalmanagement;


import java.sql.*;
import java.util.Scanner;



public class HospitalManagement {
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password= "RahulVasanth15";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        Scanner scanner=new Scanner(System.in);

        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            Patient patient=new Patient(connection,scanner);
            Doctor doctor = new Doctor(connection);
            AppointmentDetails appointmentDetails=new AppointmentDetails(connection);
            while(true){
                System.out.println("**********HOSPITAL MANAGEMENT SYSTEM**********");
                System.out.println("Write the option corresponding to the services");
                System.out.println("1.Add patient");
                System.out.println("2.View patient");
                System.out.println("3.View Doctors");
                System.out.println("4.Book Appointments");
                System.out.println("5.View Appointment");
                System.out.println("6.Exit");
                System.out.println("Enter your choice");
                int choice=scanner.nextInt();


                switch (choice){
                    case 1://add patient
                        patient.addPatient();
                        break;
                    case 2://view patient
                        patient.viewPatient();
                        break;
                    case 3://view doctor
                        doctor.viewDoctor();
                        break;
                    case 4://book appointment
                        bookAppointment(patient,doctor,connection,scanner);
                        break;
                    case 5://view appointment
                        System.out.println("Do you want to search by patient id(1) or appointment id(2)");
                        int input=scanner.nextInt();
                        if(input==1) {
                            System.out.println("Enter patient id");
                            int p_id = scanner.nextInt();
                            appointmentDetails.printAppointmentsByPatientID(p_id);
                        }else if(input==2){
                            System.out.println("Enter appointment id");
                            int ap_id=scanner.nextInt();
                            appointmentDetails.printAppointmentsByAppID(ap_id);
                        }
                        else{
                            System.out.println("invalid");}
                        break;
                    case 6://exit
                        return;
                    default:
                        System.out.println("Invalid choice");
                        break;

                }
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
scanner.close();

    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
        System.out.print("Enter Patient Id: ");
        int patientId = scanner.nextInt();

        System.out.print("Enter Doctor Id: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();

        if (patient.getPatientID(patientId) && doctor.getDoctorID(doctorId)) {
            if (checkDoctorAvailability(doctorId, appointmentDate, connection)) {
                String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Appointment Booked!");
                    } else {
                        System.out.println("Failed to Book Appointment!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Doctor not available on this date!!");
            }
        } else {
            System.out.println("Either doctor or patient doesn't exist!!!");
        }
    }


    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection){
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                return count == 0;
            } else {

                System.out.println("No result obtained from the query.");
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }




}