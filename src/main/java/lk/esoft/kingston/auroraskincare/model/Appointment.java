package lk.esoft.kingston.auroraskincare.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private int id;                    // Unique identifier for the appointment
    private LocalDate appointmentDate; // Appointment date
    private LocalTime appointmentTime;  // Appointment time
    private String patientId;          // ID of the patient (as a String)
    private int dermatologistId;        // ID of the dermatologist
    private int treatmentId;            // ID of the treatment

    public Appointment(int id, LocalDate appointmentDate, LocalTime appointmentTime,
                       String patientId, int dermatologistId, int treatmentId) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.patientId = patientId;
        this.dermatologistId = dermatologistId;
        this.treatmentId = treatmentId;
    }

    public Appointment(LocalDate appointmentDate, LocalTime appointmentTime, String patientId, int dermatologistId, int treatmentId) {
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.patientId = patientId;
        this.dermatologistId = dermatologistId;
        this.treatmentId = treatmentId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public int getDermatologistId() { return dermatologistId; }
    public void setDermatologistId(int dermatologistId) { this.dermatologistId = dermatologistId; }

    public int getTreatmentId() { return treatmentId; }
    public void setTreatmentId(int treatmentId) { this.treatmentId = treatmentId; }
}

