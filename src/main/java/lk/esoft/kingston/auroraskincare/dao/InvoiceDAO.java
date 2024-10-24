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
    public boolean createInvoice(Invoice invoice) throws SQLException {
        String query = "INSERT INTO invoice ( payment_id, total_amount, tax_amount, issue_date) VALUES (?, ?, ?, ?)";

        Connection connection = SingletonConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, invoice.getPaymentId());
        preparedStatement.setDouble(2, invoice.getTotalAmount());
        preparedStatement.setDouble(3, invoice.getTaxAmount());
        preparedStatement.setDate(4, java.sql.Date.valueOf(LocalDate.now()));

        return preparedStatement.executeUpdate() > 0;

    }

    // Method to retrieve an Invoice by its ID
    public Invoice getInvoiceById(int invoiceId) throws SQLException {
        String query = "SELECT id, payment_id, total_amount, tax_amount, issue_date FROM invoice WHERE id = ?";
        Invoice invoice = null;

        Connection connection = SingletonConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, invoiceId);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int paymentId = resultSet.getInt("payment_id");
                double totalAmount = resultSet.getDouble("total_amount");
                double taxAmount = resultSet.getDouble("tax_amount");
                LocalDate issueDate = resultSet.getDate("issue_date").toLocalDate();

                invoice = new Invoice(id, paymentId, issueDate, totalAmount, taxAmount);
            }

        }
        return invoice;
    }

    // Method to update an existing Invoice
    public boolean updateInvoice(Invoice invoice) throws SQLException {
        String query = "UPDATE invoice SET total_amount = ?, tax_amount = ? WHERE id = ?";

        Connection connection = SingletonConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setDouble(1, invoice.getTotalAmount());
        preparedStatement.setDouble(2, invoice.getTaxAmount());
        preparedStatement.setInt(3, invoice.getId());

        return preparedStatement.executeUpdate() > 0;

    }

    // Method to delete an Invoice by its ID
    public boolean deleteInvoice(int invoiceId) throws SQLException {
        String query = "DELETE FROM invoice WHERE id = ?";

        Connection connection = SingletonConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, invoiceId);
        return preparedStatement.executeUpdate() > 0;
    }

}
