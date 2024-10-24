package lk.esoft.kingston.auroraskincare.dao;

import lk.esoft.kingston.auroraskincare.db.SingletonConnection;
import lk.esoft.kingston.auroraskincare.model.Invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class InvoiceDAO {

    // Method to create a new Invoice record
    public void createInvoice(Invoice invoice) throws SQLException {
        String query = "INSERT INTO invoice (appointment_id, payment_id, total_amount, tax_amount, issue_date) VALUES (?, ?, ?, ?, ?)";

        Connection connection = SingletonConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, invoice.getAppointmentId());
        preparedStatement.setInt(2, invoice.getPaymentId());
        preparedStatement.setDouble(3, invoice.getTotalAmount());
        preparedStatement.setDouble(4, invoice.getTaxAmount());
        preparedStatement.setDate(5, java.sql.Date.valueOf(LocalDate.now()));

        preparedStatement.executeUpdate();

    }

    // Method to retrieve an Invoice by its ID
    public Invoice getInvoiceById(int invoiceId) throws SQLException {
        String query = "SELECT id, appointment_id, payment_id, total_amount, tax_amount, issue_date FROM invoice WHERE id = ?";
        Invoice invoice = null;

        Connection connection = SingletonConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, invoiceId);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int appointmentId = resultSet.getInt("appointment_id");
                int paymentId = resultSet.getInt("payment_id");
                double totalAmount = resultSet.getDouble("total_amount");
                double taxAmount = resultSet.getDouble("tax_amount");
                LocalDate issueDate = resultSet.getDate("issue_date").toLocalDate();

                invoice = new Invoice(id, appointmentId, paymentId, issueDate, totalAmount, taxAmount);
            }

        }
        return invoice;
    }

    // Method to update an existing Invoice
    public void updateInvoice(int id, double totalAmount, double taxAmount) throws SQLException {
        String query = "UPDATE invoice SET total_amount = ?, tax_amount = ? WHERE id = ?";

        Connection connection = SingletonConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setDouble(1, totalAmount);
        preparedStatement.setDouble(2, taxAmount);
        preparedStatement.setInt(3, id);

        preparedStatement.executeUpdate();

    }

    // Method to delete an Invoice by its ID
    public void deleteInvoice(int invoiceId) throws SQLException {
        String query = "DELETE FROM invoice WHERE id = ?";

        Connection connection = SingletonConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, invoiceId);
        preparedStatement.executeUpdate();
    }

}
