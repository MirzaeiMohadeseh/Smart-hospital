package hospital;

import java.util.List;

public class Nurse extends Person implements MedicalStaff {

    private List<Patient> assignedPatients;

    public Nurse(String name, String id, String address, String phoneNumber, List<Patient> assignedPatients) {
        super(name, id, address, phoneNumber);
        this.assignedPatients = assignedPatients;
    }

    @Override
    public String getRole() {
        return "Nurse";
    }

    public void assistDoctor(Doctor d, Patient p) {
        System.out.println(getName() + " is assisting Dr. " + d.getName() + " to treat " + p.getName());
    }

    public void checkPatient(Patient p) {
        System.out.println(getName() + " در حال بررسی " + p.getName());
    }

    public void assignedPatients() {
        System.out.println(getName() + " is assigned to the following patients:");
        for (Patient p : assignedPatients) {
            System.out.println("- " + p.getName());
        }
    }

    public void introduce() {
        System.out.println("Nurse Name: " + getName());
        System.out.println("ID: " + getId());
        System.out.println("Phone: " + getPhoneNumber());
        System.out.println("Assigned Patients: " + assignedPatients.size());
    }
    
    public List<Patient> getAssignedPatients() {
        return assignedPatients;
    }

    @Override
    public void provideCare(Patient patient) {
        System.out.println("پرستار " + getName() + " در حال انجام تزریق بیمار " + patient.getName());
    }
}