package lk.esoft.kingston.auroraskincare.model;

import java.time.LocalDate;

public class Invoice {
    private int id;
    private int appointmentId;
    private int paymentId;
    private LocalDate issueDate;
    private double totalAmount;
    private double taxAmount;

    public Invoice(int id, int appointmentId, int paymentId, LocalDate issueDate, double totalAmount, double taxAmount) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.paymentId = paymentId;
        this.issueDate = issueDate;
        this.totalAmount = totalAmount;
        this.taxAmount = taxAmount;
    }

    public Invoice(int appointmentId, int paymentId, LocalDate issueDate, double totalAmount, double taxAmount) {
        this.appointmentId = appointmentId;
        this.paymentId = paymentId;
        this.issueDate = issueDate;
        this.totalAmount = totalAmount;
        this.taxAmount = taxAmount;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }
}

