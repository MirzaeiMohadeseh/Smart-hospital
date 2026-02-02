package hospital;

public class Doctor extends Person implements MedicalStaff {

    private String speciality;

    public Doctor(String name, String id, String address, String phoneNumber, String speciality) {
        super(name, id, address, phoneNumber);
        this.speciality = speciality;
    }

    public String getSpeciality() {
        return speciality;
    }

    @Override
    public String getRole() {
        return "Doctor";
    }

    public void introduce() {
        System.out.println("Doctor Name: " + getName());
        System.out.println("ID: " + getId());
        System.out.println("Address: " + getAddress());
        System.out.println("Phone: " + getPhoneNumber());
        System.out.println("Speciality: " + speciality);
    }

    public void treat(Patient patient) {
        System.out.println(getName() + " is treating " + patient.getName());
    }

    public void diagnose(Patient patient) {
        String disease = patient.getHealthStatus();

        System.out.println("\n🩺 تشخیص پزشک:");
        switch (disease) {
            case "سرماخوردگی":
                System.out.println("تشخیص: سرماخوردگی ویروسی");
                System.out.println("درمان: استراحت، مایعات گرم، مسکن");
                break;

            case "آنفلوانزا":
                System.out.println("تشخیص: آنفلوانزا");
                System.out.println("درمان: داروی ضد ویروس و استراحت");
                break;

            case "سردرد مزمن":
                System.out.println("تشخیص: سردرد مزمن");
                System.out.println("درمان: داروهای ضد التهاب");
                break;

            case "درد معده":
                System.out.println("تشخیص: مشکل گوارشی");
                System.out.println("درمان: کاهش اسید معده");
                break;

            case "آلرژی فصلی":
                System.out.println("تشخیص: آلرژی فصلی");
                System.out.println("درمان: آنتی‌هیستامین");
                break;

            default:
                System.out.println("نیاز به بررسی بیشتر");
        }
    }

    @Override
    public void provideCare(Patient patient) {
        System.out.println("Dr. " + getName() + " (" + speciality + ") is providing medical care to " + patient.getName());
        treat(patient);
    }
}
