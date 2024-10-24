package lk.esoft.kingston.auroraskincare.view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainViewController {

    @FXML
    private Button btnAddPatient;

    @FXML
    private Button btnCalculateTotalForAppointment;

    @FXML
    private Button btnNewAppointment;

    @FXML
    private Button btnNewInvoice;

    @FXML
    private Button btnUpdateAppointment;

    @FXML
    private Button btnViewAppointmentsByDate;

    @FXML
    private Button btnViewAppointmentsById;

    @FXML
    private Button btnViewAppointmentsByPatient;

    @FXML
    void btnAddPatientOnAction(ActionEvent event) {
        Stage addPatientStage = new Stage();
        addPatientStage.setResizable(false);
        addPatientStage.setTitle("Register New Patient");
        addPatientStage.centerOnScreen();
        try {
            addPatientStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/esoft/kingston/auroraskincare/view/AddPatientView.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addPatientStage.show();
    }

    @FXML
    void btnCalculateTotalForAppointmentOnAction(ActionEvent event) {
        Stage totalForAppointmentStage = new Stage();
        totalForAppointmentStage.setResizable(false);
        totalForAppointmentStage.setTitle("Total of Appointment");
        totalForAppointmentStage.centerOnScreen();
        try {
            totalForAppointmentStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/esoft/kingston/auroraskincare/view/AppointmentTotalWithRegByIdView.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        totalForAppointmentStage.show();
    }

    @FXML
    void btnNewAppointmentOnAction(ActionEvent event) {
        Stage addAppointmentStage = new Stage();
        addAppointmentStage.setResizable(false);
        addAppointmentStage.setTitle("Make New Appointment");
        addAppointmentStage.centerOnScreen();
        try {
            addAppointmentStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/esoft/kingston/auroraskincare/view/MakeAppointment.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addAppointmentStage.show();
    }

    @FXML
    void btnNewInvoiceOnAction(ActionEvent event) {
        Stage addInvoiceStage = new Stage();
        addInvoiceStage.setResizable(false);
        addInvoiceStage.setTitle("Save New Invoice");
        addInvoiceStage.centerOnScreen();
        try {
            addInvoiceStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/esoft/kingston/auroraskincare/view/InvoiceView.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addInvoiceStage.show();
    }

    @FXML
    void btnUpdateAppointmentOnAction(ActionEvent event) {
        Stage updateAppointmentStage = new Stage();
        updateAppointmentStage.setResizable(false);
        updateAppointmentStage.setTitle("Update Appointment");
        updateAppointmentStage.centerOnScreen();
        try {
            updateAppointmentStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/esoft/kingston/auroraskincare/view/UpdateAppointment.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        updateAppointmentStage.show();
    }

    @FXML
    void btnViewAppointmentsByDateOnAction(ActionEvent event) {
        Stage appointmentsByDateStage = new Stage();
        appointmentsByDateStage.setResizable(false);
        appointmentsByDateStage.setTitle("View Appointments By Date");
        appointmentsByDateStage.centerOnScreen();
        try {
            appointmentsByDateStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/esoft/kingston/auroraskincare/view/AppointmentsByDateView.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        appointmentsByDateStage.show();
    }

    @FXML
    void btnViewAppointmentsByIdOnAction(ActionEvent event) {
        Stage viewAppointmentStage = new Stage();
        viewAppointmentStage.setResizable(false);
        viewAppointmentStage.setTitle("View Appointment");
        viewAppointmentStage.centerOnScreen();
        try {
            viewAppointmentStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/esoft/kingston/auroraskincare/view/AppointmentByIdView.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        viewAppointmentStage.show();
    }

    @FXML
    void btnViewAppointmentsByPatientOnAction(ActionEvent event) {
        Stage appointmentsByPatientStage = new Stage();
        appointmentsByPatientStage.setResizable(false);
        appointmentsByPatientStage.setTitle("View Appointments By Patient");
        appointmentsByPatientStage.centerOnScreen();
        try {
            appointmentsByPatientStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/esoft/kingston/auroraskincare/view/AppointmentsByPatientNameView.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        appointmentsByPatientStage.show();
    }

}
