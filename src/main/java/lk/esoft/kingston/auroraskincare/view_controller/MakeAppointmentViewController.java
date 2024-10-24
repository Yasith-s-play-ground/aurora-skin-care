package lk.esoft.kingston.auroraskincare.view_controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MakeAppointmentViewController {

    private final DermatologistDAO dermatologistDAO = new DermatologistDAO();
    private final PatientDAO patientDAO = new PatientDAO();
    private final TreatmentDAO treatmentDAO = new TreatmentDAO();
    private final AppointmentDAO appointmentDAO = new AppointmentDAO();

    @FXML
    private Button btnSave;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<Dermatologist> doctorCombo;

    @FXML
    private ComboBox<Patient> patientCombo;

    @FXML
    private ComboBox<LocalTime> timeSlotCombo;

    @FXML
    private ComboBox<Treatment> treatmentCombo;

    public void initialize() {
        ObservableList<Dermatologist> doctors = doctorCombo.getItems();
        ObservableList<Patient> patients = patientCombo.getItems();
        ObservableList<Treatment> treatments = treatmentCombo.getItems();
        try {
            List<Dermatologist> allDermatologists = dermatologistDAO.getAllDermatologists();
            doctors.addAll(allDermatologists);

            List<Patient> allPatients = patientDAO.getAllPatients();
            patients.addAll(allPatients);

            List<Treatment> allTreatments = treatmentDAO.getAllTreatments();
            treatments.addAll(allTreatments);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (validate()) {
            try {
                boolean saved = saveAppointment();
                if (saved) {
                    Alert newAlert = new Alert(Alert.AlertType.INFORMATION, "Appointment registered successfully!", ButtonType.OK);
                    newAlert.show();

                    showRegistrationPaymentWindow();

                } else {
                    Alert newAlert = new Alert(Alert.AlertType.ERROR, "Appointment registration failed!", ButtonType.OK);
                    newAlert.show();
                }
            } catch (SQLException e) {
                Alert newAlert = new Alert(Alert.AlertType.ERROR, "Appointment registration failed!", ButtonType.OK);
                newAlert.show();
                throw new RuntimeException(e);
            }

        }
    }

    @FXML
    void datePickerOnAction(ActionEvent event) {
        LocalDate date = datePicker.getValue();
        Dermatologist dermatologist = doctorCombo.getValue();
        if (date != null && dermatologist != null) {
            try {
                List<LocalTime> availableTimeSlots = appointmentDAO.getAvailableTimeSlots(dermatologist.getId(), date);
                timeSlotCombo.getItems().clear(); // remove all slots
                timeSlotCombo.getItems().addAll(availableTimeSlots);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean saveAppointment() throws SQLException {
        String patientNic = patientCombo.getValue().getNic();
        int doctorId = doctorCombo.getValue().getId();
        int treatmentId = treatmentCombo.getValue().getId();
        LocalDate date = datePicker.getValue();
        LocalTime time = timeSlotCombo.getValue();

        Appointment appointment = new Appointment(date, time, patientNic, doctorId, treatmentId);
        return appointmentDAO.createAppointment(appointment);
    }

    private boolean validate() {
        if (patientCombo.getValue() == null) return false;
        if (doctorCombo.getValue() == null) return false;
        if (timeSlotCombo.getValue() == null) return false;
        if (treatmentCombo.getValue() == null) return false;
        return datePicker.getValue() != null;
    }

    private void showRegistrationPaymentWindow() {
        Stage paymentStage = new Stage();
        paymentStage.setResizable(false);
        paymentStage.setTitle("Confirm Payment");
        paymentStage.centerOnScreen();
        try {
            paymentStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/esoft/kingston/auroraskincare/view/RegistrationPaymentView.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        paymentStage.show();

        //close this window
        Stage currentStage = (Stage) btnSave.getScene().getWindow();
        currentStage.close();
    }


}
