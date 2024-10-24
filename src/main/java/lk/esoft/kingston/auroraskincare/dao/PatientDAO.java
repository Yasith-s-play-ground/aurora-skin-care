package lk.esoft.kingston.auroraskincare.dao;

import lk.esoft.kingston.auroraskincare.db.SingletonConnection;
import lk.esoft.kingston.auroraskincare.model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    public boolean addPatient(Patient patient) throws SQLException {
        String query = "INSERT INTO patient (nic, name, email, phone) VALUES (?, ?, ?, ?)";
        Connection conn = SingletonConnection.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, patient.getNic());
        stmt.setString(2, patient.getName());
        stmt.setString(3, patient.getEmail());
        stmt.setString(4, patient.getPhone());
        return stmt.executeUpdate() > 0;

    }

    public Patient getPatientByNic(String nic) throws SQLException {
        String query = "SELECT * FROM patient WHERE nic = ?";
        Connection conn = SingletonConnection.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, nic);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Patient(rs.getString("nic"), rs.getString("name"), rs.getString("email"), rs.getString("phone"));
        }

        return null;
    }

    // Method to fetch all patients from the database
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();

        String query = "SELECT name, nic, email, phone FROM patient";

        PreparedStatement stmt = SingletonConnection.getInstance().getConnection().prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            // Create a new Patient object and populate it with data from the result set
            Patient patient = new Patient(rs.getString("nic"), rs.getString("name"), rs.getString("email"), rs.getString("phone"));

            // Add the patient object to the list
            patients.add(patient);
        }

        return patients;
    }
}

