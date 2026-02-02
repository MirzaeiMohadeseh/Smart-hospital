package hospital;

import java.util.ArrayList;
import java.util.List;

public class Pharmacy {

    private List<Drug> drugInventory;

    public Pharmacy() {
        this.drugInventory = new ArrayList<>();
    }

    public void addDrug(Drug drug) {
        drugInventory.add(drug);
    }

    public List<Drug> getPendingDrugs() {
        List<Drug> pending = new ArrayList<>();
        for (Drug d : drugInventory) {
            if (!d.isDelivered()) pending.add(d);
        }
        return pending;
    }

    public void deliverDrug(int index) {
        if (index < 0 || index >= drugInventory.size()) return;
        Drug d = drugInventory.get(index);
        if (!d.isDelivered()) {
            d.setDelivered(true);
            System.out.println("دارو " + d.getName() + " برای بیمار " +
                    d.getPatient().getName() + " توسط داروخانه تحویل داده شد.");
        } else {
            System.out.println("این دارو قبلا تحویل داده شده است.");
        }
    }

    public void showPendingDrugs() {
        List<Drug> pending = getPendingDrugs();
        if (pending.isEmpty()) {
            System.out.println("هیچ داروی تجویز شده برای تحویل وجود ندارد.");
            return;
        }
        for (int i = 0; i < pending.size(); i++) {
            Drug d = pending.get(i);
            System.out.printf("%d. بیمار: %s | دارو: %s | دوز: %s | تعداد دفعات: %s | پزشک: %s%n",
                    i + 1, d.getPatient().getName(), d.getName(), d.getDose(), 
                    d.getFrequency(), d.getDoctor().getName());
        }
    }
}
