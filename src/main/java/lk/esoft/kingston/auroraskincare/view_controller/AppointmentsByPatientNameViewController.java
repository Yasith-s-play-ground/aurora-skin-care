package lk.esoft.kingston.auroraskincare.view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.esoft.kingston.auroraskincare.dao.AppointmentDAO;
import lk.esoft.kingston.auroraskincare.model.AppointmentInfo;
import lk.esoft.kingston.auroraskincare.model.Dermatologist;
import lk.esoft.kingston.auroraskincare.model.Patient;
import lk.esoft.kingston.auroraskincare.model.Treatment;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AppointmentsByPatientNameViewController {

    @FXML
    private Button btnClose;

    @FXML
    private TextField txtName;


    @FXML
    void txtNameOnAction(ActionEvent event) {
        try {
            loadAppointments();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private TableColumn<AppointmentInfo, LocalDate> colDate;

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
    private TableView<AppointmentInfo> tblAppointment;

    private final AppointmentDAO appointmentDAO = new AppointmentDAO();


    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
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

    private void loadAppointments() throws SQLException {
        String name = txtName.getText();
        if (name == null) return;
        List<AppointmentInfo> appointmentsWithDetailsByPatientName = appointmentDAO.getAppointmentsWithDetailsByPatientName(name);
        tblAppointment.getItems().clear();
        tblAppointment.getItems().addAll(appointmentsWithDetailsByPatientName);
    }

}
