package hospital;

public class Drug {
    private String name;
    private String dose;
    private String frequency;
    private String instructions;
    private Patient patient;
    private Doctor doctor;
    private String prescriptionTime;
    private boolean delivered;   

    public Drug(String name, String dose, String frequency, String instructions, Patient patient, Doctor doctor, String prescriptionTime, boolean delivered) {
        this.name = name;
        this.dose = dose;
        this.frequency = frequency;
        this.instructions = instructions;
        this.patient = patient;
        this.doctor = doctor;
        this.prescriptionTime = prescriptionTime;
        this.delivered = delivered;
    }

    public String getName() {
        return name;
    }

    public String getDose() {
        return dose;
    }

    public String getInstructions() {
        return instructions;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getFrequency() {
    return frequency;
    }

    public void setFrequency(String frequency) {
    this.frequency = frequency;
    }
    
    public String getPrescriptionTime() {
        return prescriptionTime;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }
}
