package lk.esoft.kingston.auroraskincare.view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.esoft.kingston.auroraskincare.dao.*;
import lk.esoft.kingston.auroraskincare.model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class InvoiceViewController {

    @FXML
    private Button btnSave;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblDoctor;

    @FXML
    private Label lblPatient;

    @FXML
    private Label lblTax;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblTreatment;

    @FXML
    private TextField txtAppointmentId;

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        Payment payment = new Payment(appointmentId, PaymentCategory.FINAL, totalAmount, LocalDateTime.now());
        try {
            //Do payment
            PaymentDAO paymentDAO = new PaymentDAO();
            boolean paid = paymentDAO.createPayment(payment);

            if (paid) {
                Payment paymentByAppointmentIdAndFinalCategory = paymentDAO.getPaymentByAppointmentIdAndFinalCategory(appointmentId);
                int paymentId = paymentByAppointmentIdAndFinalCategory.getId();

                //Save Invoice
                Invoice invoice = new Invoice(appointmentId, paymentId, LocalDate.now(), totalAmount, taxAmount);
                InvoiceDAO invoiceDAO = new InvoiceDAO();
                boolean saved = invoiceDAO.createInvoice(invoice);
                if (saved) {
                    new Alert(Alert.AlertType.INFORMATION, "Invoice saved successfully").show();
                    clearDetails();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Saving failed!").show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Payment failed!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Saving failed!").show();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtAppointmentIdOnAction(ActionEvent event) {
        clearDetails();

        try {
            getAppointmentDetails();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private double totalAmount = 0;
    private double taxAmount = 0;
    private int appointmentId = 0;

    private void clearDetails() {
        appointmentId = 0;
        totalAmount = 0;
        taxAmount = 0;
        lblAmount.setText("");
        lblDoctor.setText("");
        lblPatient.setText("");
        lblTax.setText("");
        lblTotal.setText("");
        lblTreatment.setText("");
    }

    private void getAppointmentDetails() throws SQLException {
        Appointment appointment = new AppointmentDAO().searchAppointmentById(Integer.parseInt(txtAppointmentId.getText()));
        if (appointment == null) {
            new Alert(Alert.AlertType.ERROR, "Appointment not found", ButtonType.OK).show();
            return;
        }

        appointmentId = appointment.getId();

        Patient patient = new PatientDAO().getPatientByNic(appointment.getPatientId());
        lblPatient.setText(patient.getName() + " " + patient.getNic());

        Dermatologist dermatologist = new DermatologistDAO().getDermatologistById(appointment.getDermatologistId());
        lblDoctor.setText(dermatologist.getName());

        Treatment treatment = new TreatmentDAO().getTreatmentById(appointment.getTreatmentId());
        lblTreatment.setText(treatment.getName());

        lblAmount.setText(treatment.getPrice() + "");

        taxAmount = treatment.getPrice() * 2.5 / 100;
        lblTax.setText(String.format("%.2f", taxAmount));

        totalAmount = treatment.getPrice() * (1 + 2.5 / 100);
        lblTotal.setText(String.format("%.2f", totalAmount));

    }

}
