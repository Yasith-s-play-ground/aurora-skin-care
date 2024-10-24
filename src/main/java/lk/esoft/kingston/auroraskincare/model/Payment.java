package lk.esoft.kingston.auroraskincare.model;

import java.time.LocalDateTime;

public class Payment {
    private int id;
    private int appointmentId;
    private PaymentCategory category;
    private double amount;
    private LocalDateTime paymentDate;

    public Payment(int id, int appointmentId, PaymentCategory category, double amount,
                   LocalDateTime paymentDate) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.category = category;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public Payment(int appointmentId, PaymentCategory category, double amount, LocalDateTime paymentDate) {
        this.appointmentId = appointmentId;
        this.category = category;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public PaymentCategory getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }
}

