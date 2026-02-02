package hospital;

import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {
    private Appointment appointment;
    private String healthStatus;
    private List<Drug> prescriptions;

    public Patient(String name, String id, String address, String phoneNumber,
                   String healthStatus) {
        super(name, id, address, phoneNumber);
        this.healthStatus = healthStatus;
        this.prescriptions = new ArrayList<>();
    }

    @Override
    public String getRole() {
        return "Patient";
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void addPrescription(Drug drug) {
        prescriptions.add(drug);
    }

    public List<Drug> getPrescriptions() {
        return prescriptions;
    }

    public void showPrescriptions() {
        if (prescriptions.isEmpty()) {
            System.out.println(getName() + " هیچ نسخه‌ای ندارد.");
            return;
        }
        System.out.println("نسخه‌های " + getName() + ":");
        for (int i = 0; i < prescriptions.size(); i++) {
            Drug drug = prescriptions.get(i);
            System.out.printf("%d. دارو: %s | دوز: %s | تعداد دفعات: %s%n",
                    i + 1, drug.getName(), drug.getDose(), drug.getFrequency());
        }
    }

    public void showStatus() {
        System.out.println("Patient Name: " + getName());
        System.out.println("Health Status: " + healthStatus);
        System.out.println("Number of Prescriptions: " + prescriptions.size());
    }

    public void takeMedicin() {
        System.out.println(getName() + " is taking medicine");
    }

    public String getHealthStatus() {
        return healthStatus;
    }
}