package lk.esoft.kingston.auroraskincare.dao;

import lk.esoft.kingston.auroraskincare.db.SingletonConnection;
import lk.esoft.kingston.auroraskincare.model.ConsultationTime;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultationTimeDAO {
    public boolean addConsultationTime(ConsultationTime consultationTime) throws SQLException {
        String query = "INSERT INTO consultation_time (day_of_week, start_time, end_time) VALUES (?, ?, ?)";
        Connection conn = SingletonConnection.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, consultationTime.getDayOfWeek());
        stmt.setTime(2, Time.valueOf(consultationTime.getStartTime()));
        stmt.setTime(3, Time.valueOf(consultationTime.getEndTime()));
        return stmt.executeUpdate() > 0;

    }

    public List<ConsultationTime> getConsultationTimesByDay(String dayOfWeek) throws SQLException {
        List<ConsultationTime> consultationTimes = new ArrayList<>();
        String query = "SELECT * FROM consultation_time WHERE day_of_week = ?";
        Connection conn = SingletonConnection.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, dayOfWeek);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            ConsultationTime consultationTime = new ConsultationTime(
                    rs.getInt("id"),
                    rs.getString("day_of_week"),
                    rs.getTime("start_time").toLocalTime(),
                    rs.getTime("end_time").toLocalTime()
            );
            consultationTimes.add(consultationTime);
        }

        return consultationTimes;
    }

}

