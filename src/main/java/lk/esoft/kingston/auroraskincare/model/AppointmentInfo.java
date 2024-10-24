package lk.esoft.kingston.auroraskincare.model;

public class AppointmentInfo {
    private final String appointmentId;
    private final String doctorName;
    private final String patientName;
    private final String treatmentName;
    private final String appointmentDate;
    private final String appointmentTime;

    public AppointmentInfo(String appointmentId, String doctorName, String patientName, String treatmentName, String appointmentDate, String appointmentTime) {
        this.appointmentId = appointmentId;
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.treatmentName = treatmentName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }

    // Getters
    public String getAppointmentId() {
        return appointmentId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }
}
