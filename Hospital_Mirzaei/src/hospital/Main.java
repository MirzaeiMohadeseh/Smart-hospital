package hospital;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Hospital hospital = new Hospital();
        Pharmacy pharmacy = new Pharmacy();
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔════════════════════════╗");
        System.out.println("║      سیستم بیمارستان هوشمند       ║");
        System.out.println("╚═════════════════════════╝");

        List<Patient> allPatients = new ArrayList<>();

        // ساخت دکترها
        Doctor d1 = new Doctor("دکتر امیری", "D001", "تهران", "02111111111", "عمومی");
        Doctor d2 = new Doctor("دکتر محمودی", "D002", "تهران", "02122222222", "عمومی");
        Doctor d3 = new Doctor("دکتر علوی", "D003", "تهران", "02133333333", "عمومی");
        hospital.addDoctor(d1);
        hospital.addDoctor(d2);
        hospital.addDoctor(d3);

        hospital.setDoctorShift(d1, "08:00-16:00");
        hospital.setDoctorShift(d2, "16:00-24:00");
        hospital.setDoctorShift(d3, "00:00-08:00");

        // ساخت پرستارها
        List<Patient> emptyPatients = new ArrayList<>();
        Nurse n1 = new Nurse(" نورایی", "N001", "تهران", "02144444444", emptyPatients);
        Nurse n2 = new Nurse(" ثنایی", "N002", "تهران", "02155555555", emptyPatients);
        hospital.addNurse(n1);
        hospital.addNurse(n2);

        hospital.setNurseShift(n1, "08:00-20:00");
        hospital.setNurseShift(n2, "20:00-08:00");

        while (true) {
            System.out.println("\n══════════════════════");
            System.out.println("1. ثبت بیمار جدید");
            System.out.println("2. دریافت نوبت پزشکی و تشخیص");
            System.out.println("3. مشاهده نسخه‌ها");
            System.out.println("4. تزریق آمپول/سرم توسط پرستار");
            System.out.println("5. نمایش مشخصات کاربران");
            System.out.println("6. داروخانه - تحویل دارو");
            System.out.println("7. خروج");
            System.out.print("انتخاب: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> registerPatient(scanner, allPatients, hospital);
                case 2 -> {
                    if (allPatients.isEmpty()) {
                        System.out.println("هیچ بیماری ثبت نشده است.");
                        break;
                    }

                    System.out.println("انتخاب بیمار برای ویزیت:");
                    for (int i = 0; i < allPatients.size(); i++) {
                        System.out.println((i + 1) + ". " + allPatients.get(i).getName());
                    }
                    int patientChoice = scanner.nextInt() - 1;
                    scanner.nextLine();
                    if (patientChoice < 0 || patientChoice >= allPatients.size()) {
                        System.out.println("انتخاب بیمار نامعتبر است.");
                        break;
                    }
                    Patient patient = allPatients.get(patientChoice);

                    Doctor doctor = selectDoctor(hospital, new Doctor[]{d1, d2, d3}, scanner);
                    if (doctor == null) break;

                    LocalTime now = LocalTime.now();
                    hospital.scheduleAppointment(patient, doctor, now.toString());
                    Drug prescribedDrug = diagnoseAndPrescribe(patient, doctor, hospital);
                    if (prescribedDrug != null) pharmacy.addDrug(prescribedDrug);
                }

                case 3 -> showPrescriptions(allPatients, hospital);
                case 4 -> performNursing(scanner, allPatients, hospital, new Nurse[]{n1, n2});
                case 5 -> showUserInfo(scanner, allPatients, new Doctor[]{d1,d2,d3}, new Nurse[]{n1,n2});
                case 6 -> pharmacyMenu(scanner, pharmacy);
                case 7 -> {
                    System.out.println("خروج...");
                    return;
                }
                default -> System.out.println("گزینه نامعتبر است");
            }
        }
    }

    // متد داروخانه
    private static void pharmacyMenu(Scanner scanner, Pharmacy pharmacy) {
        System.out.println("\nنسخه‌های تجویز شده برای تحویل:");
        pharmacy.showPendingDrugs();
        System.out.print("شماره دارو برای تحویل انتخاب کنید (0 برای بازگشت): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 0) return;
        pharmacy.deliverDrug(choice - 1);
    }

    // ثبت بیمار
    private static void registerPatient(Scanner scanner, List<Patient> allPatients, Hospital hospital) {
        String name, id, address, phone;
        do { System.out.print("نام بیمار: "); name = scanner.nextLine().trim();
            if(name.isEmpty()) System.out.println("نام نمی‌تواند خالی باشد"); } while(name.isEmpty());

        do { System.out.print("کد ملی (10 رقم): "); id = scanner.nextLine().trim();
            if(!id.matches("\\d{10}")) System.out.println("کد ملی باید 10 رقم باشد"); } while(!id.matches("\\d{10}"));

        do { System.out.print("آدرس: "); address = scanner.nextLine().trim();
            if(address.isEmpty()) System.out.println("آدرس نمی‌تواند خالی باشد"); } while(address.isEmpty());

        do { System.out.print("شماره تماس (11 رقم با 09 شروع شود): "); phone = scanner.nextLine().trim();
            if(!phone.matches("09\\d{9}")) System.out.println("شماره تماس باید 11 رقم و با 09 شروع شود"); } while(!phone.matches("09\\d{9}"));

        System.out.println("علائم بیمار:\n1. تب و گلودرد\n2. تب شدید و بدن درد\n3. سردرد طولانی\n4. سوزش معده\n5. عطسه و خارش\n6. سایر");
        int symptom;
        do { System.out.print("انتخاب کنید: "); symptom = scanner.nextInt(); scanner.nextLine();
            if(symptom<1||symptom>6) System.out.println("گزینه نامعتبر است"); } while(symptom<1||symptom>6);

        String symptomText = switch(symptom){
            case 1 -> "تب و گلودرد";
            case 2 -> "تب شدید و بدن درد";
            case 3 -> "سردرد طولانی";
            case 4 -> "سوزش معده";
            case 5 -> "عطسه و خارش";
            default -> "سایر";
        };

        Patient p = new Patient(name, id, address, phone, symptomText);
        allPatients.add(p);
        hospital.addPatient(p);
        System.out.println("بیمار ثبت شد");
    }

    // انتخاب دکتر
    private static Doctor selectDoctor(Hospital hospital, Doctor[] doctors, Scanner scanner) {
        Doctor doctor = null;
        while(true){
            LocalTime now = LocalTime.now();
            boolean[] active = new boolean[doctors.length];
            System.out.println("\nانتخاب پزشک عمومی:");
            for(int i=0;i<doctors.length;i++){
                String shift=hospital.getDoctorShift(doctors[i]);
                String[] parts=shift.split("-");
                LocalTime start=LocalTime.parse(parts[0]);
                LocalTime end=parts[1].equals("24:00")? LocalTime.MIDNIGHT : LocalTime.parse(parts[1]);
                if(end.equals(LocalTime.MIDNIGHT)) active[i]=!now.isBefore(start);
                else if(end.isBefore(start)) active[i]=now.isAfter(start)||now.isBefore(end);
                else active[i]=!now.isBefore(start)&&!now.isAfter(end);
                System.out.printf("%d. %s (شیفت: %s) [%s]%n",i+1,doctors[i].getName(),shift,active[i]?"فعال":"غیرفعال");
            }
            int choice=scanner.nextInt(); scanner.nextLine();
            if(choice<1||choice>doctors.length){ System.out.println("گزینه نامعتبر است"); continue; }
            if(!active[choice-1]) { System.out.println("این دکتر در شیفت نیست."); continue; }
            doctor=doctors[choice-1]; break;
        }
        return doctor;
    }

    // تشخیص و تجویز دارو
    private static Drug diagnoseAndPrescribe(Patient patient, Doctor doctor, Hospital hospital) {
        String s = patient.getHealthStatus();
        String diagnosis = "", drug = "", dose = "", frequency = "";
        
        if (s.equals("تب و گلودرد")) {
            diagnosis = "سرماخوردگی";
            drug = "استامینوفن";
            dose = "500mg";
            frequency = "3";
        } else if (s.equals("تب شدید و بدن درد")) {
            diagnosis = "آنفلوانزا";
            drug = "اسلتامیویر";
            dose = "75mg";
            frequency = "2";
        } else if (s.equals("سردرد طولانی")) {
            diagnosis = "سردرد مزمن";
            drug = "ایبوپروفن";
            dose = "400mg";
            frequency = "3";
        } else if (s.equals("سوزش معده")) {
            diagnosis = "درد معده";
            drug = "امپرازول";
            dose = "20mg";
            frequency = "1";
        } else if (s.equals("عطسه و خارش")) {
            diagnosis = "آلرژی فصلی";
            drug = "لوراتادین";
            dose = "10mg";
            frequency = "1";
        } else {
            diagnosis = "نیاز به بررسی بیشتر توسط دکتر متخصص";
            return null;
        }

        System.out.println("تشخیص پزشک: " + diagnosis);
        
        Drug newDrug = new Drug(drug, dose, frequency, "طبق دستور پزشک", 
                               patient, doctor, LocalTime.now().toString(), false);
        hospital.prescribeDrug(newDrug);
        
        System.out.println("دارو: " + drug + " | دوز: " + dose + " | تعداد دفعات: " + frequency + " بار در روز");
        return newDrug;
    }
    // مشاهده نسخه‌ها
    private static void showPrescriptions(List<Patient> allPatients, Hospital hospital) {
        boolean hasPrescription = false;
        for (Patient pa : allPatients) {
            List<Drug> drugs = hospital.getDrugsForPatient(pa);
            if (!drugs.isEmpty()) {
                hasPrescription = true;
                System.out.println("\nبیمار: " + pa.getName());
                for (Drug dr : drugs) {
                    System.out.printf("دارو: %s | دوز: %s | تعداد دفعات: %s | پزشک: %s%n",
                            dr.getName(), dr.getDose(), dr.getFrequency(), dr.getDoctor().getName());
                }
            }
        }
        if (!hasPrescription) System.out.println("هیچ نسخه‌ای ثبت نشده است.");
    }

    	private static void performNursing(Scanner scanner, List<Patient> allPatients, Hospital hospital, Nurse[] nurses){
        if(allPatients.isEmpty()){ 
            System.out.println("هیچ بیماری ثبت نشده است"); 
            return;
        }

        System.out.println("انتخاب بیمار:");
        for(int i=0;i<allPatients.size();i++) 
            System.out.println((i+1)+". "+allPatients.get(i).getName());
        
        int pChoice = scanner.nextInt()-1; 
        scanner.nextLine();
        if(pChoice<0 || pChoice>=allPatients.size()) return;
        Patient patient = allPatients.get(pChoice);

        LocalTime now = LocalTime.now();
        System.out.println("انتخاب پرستار:");
        boolean[] activeStatus = new boolean[nurses.length];

        for(int i=0;i<nurses.length;i++){
            String shift = hospital.getNurseShift(nurses[i]);
            String[] parts = shift.split("-");
            LocalTime start = LocalTime.parse(parts[0]);
            LocalTime end = parts[1].equals("24:00") ? LocalTime.MIDNIGHT : LocalTime.parse(parts[1]);

            boolean active;
            if(end.equals(LocalTime.MIDNIGHT)) active = !now.isBefore(start);
            else if(end.isBefore(start)) active = now.isAfter(start) || now.isBefore(end);
            else active = !now.isBefore(start) && !now.isAfter(end);

            activeStatus[i] = active;
            System.out.printf("%d. %s (شیفت: %s) [%s]%n", i+1, nurses[i].getName(), shift, active ? "فعال" : "غیرفعال");
        }

        int nChoice = scanner.nextInt()-1; 
        scanner.nextLine();
        if(nChoice<0 || nChoice>=nurses.length) return;

        if(!activeStatus[nChoice]){
            System.out.println("این پرستار در شیفت نیست و نمی‌تواند کار را انجام دهد.");
            return;
        }

        Nurse nurse = nurses[nChoice];

        System.out.println("انتخاب درمان:\n1. سرم\n2. امپول");
        int tChoice = scanner.nextInt(); scanner.nextLine();
        String treatment = tChoice==1 ? "سرم" : "امپول";

        hospital.provideNursing(nurse, patient, treatment);
        System.out.println(treatment+" برای بیمار "+patient.getName()+" توسط "+nurse.getName()+" انجام شد.");
    }

    // نمایش مشخصات کاربران
    private static void showUserInfo(Scanner scanner, List<Patient> allPatients, Doctor[] doctors, Nurse[] nurses){
        System.out.println("\nانتخاب نوع کاربر برای نمایش:");
        System.out.println("1. بیمار");
        System.out.println("2. دکتر");
        System.out.println("3. پرستار");
        int choice = scanner.nextInt(); scanner.nextLine();
        switch(choice){
            case 1:
                if(allPatients.isEmpty()){ System.out.println("هنوز بیمار ثبت نشده است."); return; }
                for(int i=0;i<allPatients.size();i++) System.out.println((i+1)+". "+allPatients.get(i).getName());
                int pChoice = scanner.nextInt()-1; scanner.nextLine();
                if(pChoice>=0 && pChoice<allPatients.size()){
                    Patient p=allPatients.get(pChoice);
                    System.out.println("نام: "+p.getName()+" | کد ملی: "+p.getId()+" | شماره: "+p.getPhoneNumber()+" | آدرس: "+p.getAddress()+" | علائم: "+p.getHealthStatus());
                }
                break;
            case 2:
                for(int i=0;i<doctors.length;i++) System.out.println((i+1)+". "+doctors[i].getName());
                int dChoice = scanner.nextInt()-1; scanner.nextLine();
                if(dChoice>=0 && dChoice<doctors.length){
                    Doctor d=doctors[dChoice];
                    System.out.println("نام: "+d.getName()+" | کد: "+d.getId()+" | شماره: "+d.getPhoneNumber()+" | تخصص: "+d.getSpeciality());
                }
                break;
            case 3:
                for(int i=0;i<nurses.length;i++) System.out.println((i+1)+". "+nurses[i].getName());
                int nChoice = scanner.nextInt()-1; scanner.nextLine();
                if(nChoice>=0 && nChoice<nurses.length){
                    Nurse n=nurses[nChoice];
                    System.out.println("نام: "+n.getName()+" | کد: "+n.getId()+" | شماره: "+n.getPhoneNumber()+" | تعداد بیماران: "+n.getAssignedPatients().size());
                }
                break;
        }
    }
}
