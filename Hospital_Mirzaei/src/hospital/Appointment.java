package hospital;

import java.util.ArrayList;
import java.util.List;

public class Appointment {
    private Patient patient;
    private Doctor doctor;
    private String time;

    public Appointment(Patient patient, Doctor doctor, String time) {
        this.patient = patient;
        this.doctor = doctor;
        this.time = time;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getTime() {
        return time;
    }
}
