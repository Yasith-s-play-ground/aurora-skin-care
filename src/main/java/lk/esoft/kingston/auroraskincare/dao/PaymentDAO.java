package lk.esoft.kingston.auroraskincare.dao;

import lk.esoft.kingston.auroraskincare.db.SingletonConnection;
import lk.esoft.kingston.auroraskincare.model.Payment;
import lk.esoft.kingston.auroraskincare.model.PaymentCategory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    // Method to create a new payment
    public boolean createPayment(Payment payment) throws SQLException {
        String sql = "INSERT INTO payment (appointment_id, category, amount) VALUES (?, ?, ?)";
        Connection connection = SingletonConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, payment.getAppointmentId());
        statement.setObject(2, payment.getCategory().name(), java.sql.Types.OTHER);
        statement.setDouble(3, payment.getAmount());
        return statement.executeUpdate() > 0;

    }

    // Method to get a payment by ID
    public Payment getPaymentById(int id) throws SQLException {
        String sql = "SELECT * FROM payment WHERE id = ?";
        Connection connection = SingletonConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return mapRowToPayment(resultSet);
        }

        return null; // No payment found
    }

    // Method to get all payments
    public List<Payment> getAllPayments() throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payment";
        Connection connection = SingletonConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            payments.add(mapRowToPayment(resultSet));
        }

        return payments;
    }

    // Method to get payments by appointment ID
    public List<Payment> getPaymentsByAppointmentId(int appointmentId) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payment WHERE appointment_id = ?";
        Connection connection = SingletonConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, appointmentId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            payments.add(mapRowToPayment(resultSet));
        }

        return payments; // Return the list of payments
    }

    // Method to delete a payment by ID
    public void deletePayment(int id) throws SQLException {
        String sql = "DELETE FROM payment WHERE id = ?";
        Connection connection = SingletonConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();

    }

    // Method to map ResultSet to Payment object
    private Payment mapRowToPayment(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int appointmentId = resultSet.getInt("appointment_id");
        PaymentCategory category = PaymentCategory.valueOf(resultSet.getString("category"));
        double amount = resultSet.getDouble("amount");
        LocalDateTime paymentDate = resultSet.getTimestamp("payment_date").toLocalDateTime();

        return new Payment(id, appointmentId, category, amount, paymentDate);
    }

    // Method to get payment details by appointment ID and category FINAL
    public Payment getPaymentByAppointmentIdAndFinalCategory(int appointmentId) throws SQLException {
        String query = "SELECT id, appointment_id, category, amount, payment_date " +
                       "FROM payment WHERE appointment_id = ? AND category = 'FINAL'";

        Payment payment = null;

        Connection connection = SingletonConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, appointmentId);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int appId = resultSet.getInt("appointment_id");
                String category = resultSet.getString("category");
                double amount = resultSet.getDouble("amount");
                LocalDateTime paymentDate = resultSet.getTimestamp("payment_date").toLocalDateTime();

                payment = new Payment(id, appId, PaymentCategory.valueOf(category), amount, paymentDate);
            }
        }

        return payment;
    }
}
