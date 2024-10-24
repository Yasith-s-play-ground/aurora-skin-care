package lk.esoft.kingston.auroraskincare.view_controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import lk.esoft.kingston.auroraskincare.dao.PatientDAO;
import lk.esoft.kingston.auroraskincare.model.Patient;

import java.sql.SQLException;

public class AddPatientViewController {

    private PatientDAO patientDAO = new PatientDAO();
    @FXML
    private Button btnSave;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblName;

    @FXML
    private Label lblNic;

    @FXML
    private Label lblPhone;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtPhone;

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String nic = txtNic.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();

        if (validateInput(nic, name, email, phone)) {
            // Create a new Patient object
            Patient newPatient = new Patient(nic, name, email, phone);
            try {
                boolean saved = patientDAO.addPatient(newPatient);
                if (saved) {
                    Alert newAlert = new Alert(Alert.AlertType.INFORMATION, "Patient registered successfully!", ButtonType.OK);
                    newAlert.show();

                    clear(); // clear fields

                } else {
                    Alert newAlert = new Alert(Alert.AlertType.ERROR, "Patient registration failed!", ButtonType.OK);
                    newAlert.show();
                }
            } catch (SQLException e) {
                Alert newAlert = new Alert(Alert.AlertType.ERROR, "Patient registration failed!", ButtonType.OK);
                newAlert.show();
                throw new RuntimeException(e);
            }

        }
    }

    //Method to clear fields
    private void clear() {
        txtNic.clear();
        txtName.clear();
        txtPhone.clear();
        txtEmail.clear();
    }

    // Basic input validation
    private boolean validateInput(String nic, String name, String email, String phone) {
        return !nic.isEmpty() && !name.isEmpty() && !email.isEmpty() && !phone.isEmpty();
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {

    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtNicOnAction(ActionEvent event) {

    }

    @FXML
    void txtNicOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtPhoneOnAction(ActionEvent event) {

    }

    @FXML
    void txtPhoneOnKeyReleased(KeyEvent event) {

    }


}
