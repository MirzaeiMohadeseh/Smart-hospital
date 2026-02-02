package hospital;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hospital {
    private List<Patient> patients;
    private List<Doctor> doctors;
    private List<Nurse> nurses;
    private List<Appointment> appointments;
    private List<Drug> drugs;
    private Map<Doctor, String> doctorShifts;
    private Map<Nurse, String> nurseShifts;

    public Hospital() {
        patients = new ArrayList<>();
        doctors = new ArrayList<>();
        nurses = new ArrayList<>();
        appointments = new ArrayList<>();
        drugs = new ArrayList<>();
        doctorShifts = new HashMap<>();
        nurseShifts = new HashMap<>();
    }

    public void addPatient(Patient patient) { patients.add(patient); }
    public void addDoctor(Doctor doctor) { doctors.add(doctor); }
    public void addNurse(Nurse nurse) { nurses.add(nurse); }

    public Appointment scheduleAppointment(Patient patient, Doctor doctor, String time) {
        if (!isDoctorAvailable(doctor, time)) {
            System.out.println("این پزشک در ساعت " + time + " شیفت ندارد");
            return null;
        }
        Appointment appointment = new Appointment(patient, doctor, time);
        appointments.add(appointment);
        patient.setAppointment(appointment);
        return appointment;
    }

    public void prescribeDrug(Drug drug) { drugs.add(drug); }

    public List<Drug> getDrugsForPatient(Patient patient) {
        List<Drug> patientDrugs = new ArrayList<>();
        for (Drug drug : drugs) {
            if (drug.getPatient().equals(patient)) patientDrugs.add(drug);
        }
        return patientDrugs;
    }

    public void setDoctorShift(Doctor doctor, String shift) { doctorShifts.put(doctor, shift); }
    public void setNurseShift(Nurse nurse, String shift) { nurseShifts.put(nurse, shift); }

    public String getDoctorShift(Doctor doctor) { return doctorShifts.get(doctor); }
    public String getNurseShift(Nurse nurse) { return nurseShifts.get(nurse); }

    public boolean isDoctorAvailable(Doctor doctor, String time) {
        String shift = doctorShifts.get(doctor);
        if (shift == null) return true;
        LocalTime now = LocalTime.parse(time);
        String[] parts = shift.split("-");
        LocalTime start = LocalTime.parse(parts[0]);
        LocalTime end = parts[1].equals("24:00") ? LocalTime.MIDNIGHT : LocalTime.parse(parts[1]);

        if (end.equals(LocalTime.MIDNIGHT) && start.isBefore(end)) return !now.isBefore(start);
        else if (end.isBefore(start)) return !now.isBefore(start) || now.isBefore(end);
        else return !now.isBefore(start) && !now.isAfter(end);
    }
    public void provideNursing(Nurse nurse, Patient patient, String treatment) {
        nurse.provideCare(patient);
    }
}
