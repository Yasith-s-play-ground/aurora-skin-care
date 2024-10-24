package lk.esoft.kingston.auroraskincare.view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.esoft.kingston.auroraskincare.dao.AppointmentDAO;
import lk.esoft.kingston.auroraskincare.dao.DermatologistDAO;
import lk.esoft.kingston.auroraskincare.dao.PatientDAO;
import lk.esoft.kingston.auroraskincare.dao.TreatmentDAO;
import lk.esoft.kingston.auroraskincare.model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AppointmentsByDateViewController {

    @FXML
    private Button btnClose;

    @FXML
    private TableColumn<AppointmentInfo, Dermatologist> colDoctor;

    @FXML
    private TableColumn<AppointmentInfo, String> colId;

    @FXML
    private TableColumn<AppointmentInfo, Patient> colPatient;

    @FXML
    private TableColumn<AppointmentInfo, LocalDateTime> colTime;

    @FXML
    private TableColumn<AppointmentInfo, Treatment> colTreatment;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<AppointmentInfo> tblAppointment;

    private final AppointmentDAO appointmentDAO = new AppointmentDAO();


    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("appointmentTime"));
        colPatient.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colDoctor.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        colTreatment.setCellValueFactory(new PropertyValueFactory<>("treatmentName"));
    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        //close this window
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void datePickerOnAction(ActionEvent event) {
        if (datePicker.getValue() != null) {
            try {
                loadAppointments();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void loadAppointments() throws SQLException {
        LocalDate date = datePicker.getValue();
        List<AppointmentInfo> appointmentsByDate = appointmentDAO.getAppointmentsWithDetailsByDate(date);
        tblAppointment.getItems().clear();
        tblAppointment.getItems().addAll(appointmentsByDate);
    }

}
