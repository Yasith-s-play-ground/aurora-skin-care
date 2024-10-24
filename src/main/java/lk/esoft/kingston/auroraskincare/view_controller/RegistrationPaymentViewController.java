package lk.esoft.kingston.auroraskincare.view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lk.esoft.kingston.auroraskincare.dao.AppointmentDAO;
import lk.esoft.kingston.auroraskincare.dao.PaymentDAO;
import lk.esoft.kingston.auroraskincare.model.Payment;
import lk.esoft.kingston.auroraskincare.model.PaymentCategory;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class RegistrationPaymentViewController {

    private int lastAppointmentId;

    public void initialize() {
        try {
            lastAppointmentId = new AppointmentDAO().getLastAppointmentId();
            lblAppointmentId.setText(String.format("APP%04d", lastAppointmentId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private Button btnSave;

    @FXML
    private Label lblAppointmentId;

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        Payment payment = new Payment(lastAppointmentId, PaymentCategory.REGISTRATION, 500.00, LocalDateTime.now());
        try {
            boolean paid = new PaymentDAO().createPayment(payment);
            if (paid) {
                Alert newAlert = new Alert(Alert.AlertType.INFORMATION, "Payment successful!", ButtonType.OK);
                newAlert.show();

                //close this window
                Stage currentStage = (Stage) btnSave.getScene().getWindow();
                currentStage.close();
            } else {
                Alert newAlert = new Alert(Alert.AlertType.ERROR, "Payment failed!", ButtonType.OK);
                newAlert.show();
            }
        } catch (SQLException e) {
            Alert newAlert = new Alert(Alert.AlertType.ERROR, "Payment failed!", ButtonType.OK);
            newAlert.show();
            throw new RuntimeException(e);
        }
    }

}

