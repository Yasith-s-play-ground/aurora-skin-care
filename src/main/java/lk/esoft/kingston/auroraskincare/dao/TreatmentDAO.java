package lk.esoft.kingston.auroraskincare.dao;

import lk.esoft.kingston.auroraskincare.db.SingletonConnection;
import lk.esoft.kingston.auroraskincare.model.Treatment;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreatmentDAO {
    public boolean addTreatment(Treatment treatment) throws SQLException {
        String query = "INSERT INTO treatment (name, price) VALUES (?, ?)";
        Connection conn = SingletonConnection.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, treatment.getName());
        stmt.setDouble(2, treatment.getPrice());
        return stmt.executeUpdate() > 0;

    }

    public List<Treatment> getAllTreatments() throws SQLException {
        List<Treatment> treatments = new ArrayList<>();
        String query = "SELECT * FROM treatment";
        Connection conn = SingletonConnection.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Treatment treatment = new Treatment(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price")
            );
            treatments.add(treatment);
        }

        return treatments;
    }

    public Treatment getTreatmentById(int treatmentId) throws SQLException {
        String query = "SELECT id, name, price FROM treatment WHERE id = ?";
        Treatment treatment = null;

        // Use the singleton connection
        Connection connection = SingletonConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        // Set the treatmentId in the query
        preparedStatement.setInt(1, treatmentId);

        // Execute the query and process the result set
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");

                // Create a new Treatment object
                treatment = new Treatment(id, name, price);
            }
        }

        return treatment;
    }

}

