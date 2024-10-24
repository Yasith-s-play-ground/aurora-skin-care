package lk.esoft.kingston.auroraskincare.dao;

import lk.esoft.kingston.auroraskincare.db.SingletonConnection;
import lk.esoft.kingston.auroraskincare.model.Dermatologist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DermatologistDAO {
    public boolean addDermatologist(Dermatologist dermatologist) throws SQLException {
        String query = "INSERT INTO dermatologist (name, email, phone) VALUES (?, ?, ?)";
        Connection conn = SingletonConnection.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, dermatologist.getName());
        stmt.setString(2, dermatologist.getEmail());
        stmt.setString(3, dermatologist.getPhone());
        return stmt.executeUpdate() > 0;

    }

    public Dermatologist getDermatologistById(int id) throws SQLException {
        String query = "SELECT * FROM dermatologist WHERE id = ?";
        Connection conn = SingletonConnection.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Dermatologist(rs.getString("name"), rs.getString("email"), rs.getString("phone"), id);
        }

        return null;
    }

    public List<Dermatologist> getAllDermatologists() throws SQLException {
        List<Dermatologist> dermatologists = new ArrayList<>();
        String sql = "SELECT * FROM dermatologist";

        Connection conn = SingletonConnection.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String phone = rs.getString("phone");

            Dermatologist dermatologist = new Dermatologist(name, email, phone, id);
            dermatologists.add(dermatologist);
        }

        return dermatologists;
    }

}

