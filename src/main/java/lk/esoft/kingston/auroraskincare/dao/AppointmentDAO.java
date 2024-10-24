package lk.esoft.kingston.auroraskincare.dao;

import lk.esoft.kingston.auroraskincare.db.SingletonConnection;
import lk.esoft.kingston.auroraskincare.model.Appointment;
import lk.esoft.kingston.auroraskincare.model.AppointmentInfo;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    public boolean createAppointment(Appointment appointment) throws SQLException {
        String query = "INSERT INTO appointment (appointment_date, appointment_time, patient_id, " +
                       "dermatologist_id, treatment_id) VALUES (?, ?, ?, ?, ?)";
        Connection conn = SingletonConnection.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setDate(1, Date.valueOf(appointment.getAppointmentDate()));
        stmt.setTime(2, Time.valueOf(appointment.getAppointmentTime()));
        stmt.setString(3, appointment.getPatientId()); // Now treating patientId as String
        stmt.setInt(4, appointment.getDermatologistId());
        stmt.setInt(5, appointment.getTreatmentId());
        return stmt.executeUpdate() > 0;

    }

    public boolean updateAppointment(Appointment appointment) throws SQLException {
        String query = "UPDATE appointment SET appointment_date = ?, appointment_time = ?, " +
                       "dermatologist_id = ?, treatment_id = ?,patient_id=? WHERE id = ?";
        Connection conn = SingletonConnection.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setDate(1, Date.valueOf(appointment.getAppointmentDate()));
        stmt.setTime(2, Time.valueOf(appointment.getAppointmentTime()));
        stmt.setInt(3, appointment.getDermatologistId());
        stmt.setInt(4, appointment.getTreatmentId());
        stmt.setString(5, appointment.getPatientId());
        stmt.setInt(6, appointment.getId());
        return stmt.executeUpdate() > 0;
    }

    public List<Appointment> getAppointmentsByDate(LocalDate date) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointment WHERE appointment_date = ?";
        Connection conn = SingletonConnection.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setDate(1, Date.valueOf(date));
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            LocalTime appointmentTime = rs.getTime("appointment_time").toLocalTime();
            String patientId = rs.getString("patient_id");
            int dermatologistId = rs.getInt("dermatologist_id");
            int treatmentId = rs.getInt("treatment_id");

            Appointment appointment = new Appointment(id, date, appointmentTime, patientId, dermatologistId, treatmentId);
            appointments.add(appointment);
        }

        return appointments;
    }

    public List<Appointment> searchAppointmentsByPatientName(String patientName) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT a.* FROM appointment a " +
                     "JOIN patient p ON a.patient_id = p.nic " +
                     "WHERE p.name ILIKE ?"; // ILIKE for case-insensitive matching

        Connection conn = SingletonConnection.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + patientName + "%"); // Use wildcard for partial matching
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            LocalDate appointmentDate = rs.getDate("appointment_date").toLocalDate();
            LocalTime appointmentTime = rs.getTime("appointment_time").toLocalTime();
            String patientId = rs.getString("patient_id");
            int dermatologistId = rs.getInt("dermatologist_id");
            int treatmentId = rs.getInt("treatment_id");

            Appointment appointment = new Appointment(id, appointmentDate, appointmentTime, patientId, dermatologistId, treatmentId);
            appointments.add(appointment);
        }

        return appointments;
    }

    public Appointment searchAppointmentById(int appointmentId) throws SQLException {
        Appointment appointment = null;
        String sql = "SELECT * FROM appointment WHERE id = ?";

        Connection conn = SingletonConnection.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, appointmentId); // Set the appointment ID parameter
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            LocalDate appointmentDate = rs.getDate("appointment_date").toLocalDate();
            LocalTime appointmentTime = rs.getTime("appointment_time").toLocalTime();
            String patientId = rs.getString("patient_id");
            int dermatologistId = rs.getInt("dermatologist_id");
            int treatmentId = rs.getInt("treatment_id");

            appointment = new Appointment(appointmentId, appointmentDate, appointmentTime, patientId, dermatologistId, treatmentId);
        }

        return appointment; // Return null if no appointment found
    }

    // Method to get available time slots for a specific dermatologist on a selected date
    public List<LocalTime> getAvailableTimeSlots(int dermatologistId, LocalDate selectedDate) throws SQLException {
        List<LocalTime> availableSlots = new ArrayList<>();
        String dayOfWeek = selectedDate.getDayOfWeek().name(); // Get the day of the week

        // Get consultation times for the dermatologist
        String consultationSql = "SELECT start_time, end_time FROM consultation_time " +
                                 "WHERE day_of_week = ?";
        Connection conn = SingletonConnection.getInstance().getConnection();
        PreparedStatement consultationStmt = conn.prepareStatement(consultationSql);
        consultationStmt.setString(1, dayOfWeek);
        ResultSet consultationRs = consultationStmt.executeQuery();

        if (consultationRs.next()) {
            LocalTime startTime = consultationRs.getTime("start_time").toLocalTime();
            LocalTime endTime = consultationRs.getTime("end_time").toLocalTime();

            // Fetch booked appointments for the selected date
            String appointmentSql = "SELECT appointment_time FROM appointment " +
                                    "WHERE dermatologist_id = ? AND appointment_date = ?";
            try (PreparedStatement appointmentStmt = conn.prepareStatement(appointmentSql)) {
                appointmentStmt.setInt(1, dermatologistId);
                appointmentStmt.setDate(2, Date.valueOf(selectedDate));
                ResultSet appointmentRs = appointmentStmt.executeQuery();

                List<LocalTime> bookedTimes = new ArrayList<>();
                while (appointmentRs.next()) {
                    bookedTimes.add(appointmentRs.getTime("appointment_time").toLocalTime());
                }

                // Calculate available time slots
                LocalTime currentTime = startTime;
                while (currentTime.isBefore(endTime)) {
                    if (!bookedTimes.contains(currentTime)) {
                        availableSlots.add(currentTime);
                    }
                    currentTime = currentTime.plusMinutes(15); // Increment by 15 minutes
                }
            }
        }

        return availableSlots;
    }

    // Method to get the last appointment ID
    public int getLastAppointmentId() throws SQLException {
        String query = "SELECT MAX(id) FROM appointment";
        int lastId = -1; // Default value in case no appointments exist

        // Using the singleton connection instance
        Connection connection = SingletonConnection.getInstance().getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                lastId = resultSet.getInt(1); // Get the max ID from the result
            }
        }
        return lastId;
    }

    public List<AppointmentInfo> getAppointmentsWithDetailsByDate(LocalDate selectedDate) throws SQLException {
        List<AppointmentInfo> appointments = new ArrayList<>();
        String sql = "SELECT a.id AS appointment_id, d.name AS doctor_name, p.name AS patient_name, " +
                     "t.name AS treatment_name, a.appointment_date, a.appointment_time " +
                     "FROM appointment a " +
                     "JOIN dermatologist d ON a.dermatologist_id = d.id " +
                     "JOIN patient p ON a.patient_id = p.nic " +
                     "JOIN treatment t ON a.treatment_id = t.id " +
                     "WHERE a.appointment_date = ?";  // Filter by appointment_date

        Connection conn = SingletonConnection.getInstance().getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // Set the date parameter
        pstmt.setDate(1, java.sql.Date.valueOf(selectedDate));

        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String appointmentId = rs.getString("appointment_id");
                String doctorName = rs.getString("doctor_name");
                String patientName = rs.getString("patient_name");
                String treatmentName = rs.getString("treatment_name");
                String appointmentDate = rs.getString("appointment_date");
                String appointmentTime = rs.getString("appointment_time");
                appointments.add(new AppointmentInfo(appointmentId, doctorName, patientName, treatmentName, appointmentDate, appointmentTime));
            }
        }

        return appointments;
    }

    public List<AppointmentInfo> getAppointmentsWithDetailsByPatientName(String patientName) throws SQLException {
        List<AppointmentInfo> appointments = new ArrayList<>();
        String sql = "SELECT a.id AS appointment_id, d.name AS doctor_name, p.name AS patient_name, " +
                     "t.name AS treatment_name, a.appointment_date, a.appointment_time " +
                     "FROM appointment a " +
                     "JOIN dermatologist d ON a.dermatologist_id = d.id " +
                     "JOIN patient p ON a.patient_id = p.nic " +
                     "JOIN treatment t ON a.treatment_id = t.id " +
                     "WHERE p.name ILIKE ?";  // Use ILIKE for case-insensitive search

        Connection conn = SingletonConnection.getInstance().getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // Set the patient name parameter with wildcards for partial matches
        pstmt.setString(1, "%" + patientName + "%");

        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String appointmentId = rs.getString("appointment_id");
                String doctorName = rs.getString("doctor_name");
                String patientNameResult = rs.getString("patient_name");
                String treatmentName = rs.getString("treatment_name");
                String appointmentDate = rs.getString("appointment_date");
                String appointmentTime = rs.getString("appointment_time");
                appointments.add(new AppointmentInfo(appointmentId, doctorName, patientNameResult, treatmentName, appointmentDate, appointmentTime));
            }
        }

        return appointments;
    }


}

