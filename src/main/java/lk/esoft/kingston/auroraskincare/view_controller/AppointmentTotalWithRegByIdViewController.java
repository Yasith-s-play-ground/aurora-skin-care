package lk.esoft.kingston.auroraskincare.view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.esoft.kingston.auroraskincare.dao.AppointmentDAO;
import lk.esoft.kingston.auroraskincare.dao.DermatologistDAO;
import lk.esoft.kingston.auroraskincare.dao.PatientDAO;
import lk.esoft.kingston.auroraskincare.dao.TreatmentDAO;
import lk.esoft.kingston.auroraskincare.model.Appointment;
import lk.esoft.kingston.auroraskincare.model.Dermatologist;
import lk.esoft.kingston.auroraskincare.model.Patient;
import lk.esoft.kingston.auroraskincare.model.Treatment;

import java.sql.SQLException;

public class AppointmentTotalWithRegByIdViewController {

    @FXML
    private Button btnClose;
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

    private void clearDetails() {
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
        lblTotal.setText(String.format("%.2f", totalAmount + 500));

    }

    public void btnCloseOnAction(ActionEvent actionEvent) {
        //close this window
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.close();
    }
}
