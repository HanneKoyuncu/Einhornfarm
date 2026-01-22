import java.util.ArrayList;
import java.util.List;

public class Stall {

    private int maxCapacity;
    private List<Einhorn> einhorner;

    public Stall(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.einhorner = new ArrayList<>();
    }

    public boolean addEinhorn(Einhorn e) {
        if (einhorner.size() >= maxCapacity) {
            return false;
        }
        einhorner.add(e);
        return true;
    }

    public void removeEinhorn(Einhorn e) {
        einhorner.remove(e); // Spieler wählt selbst
    }

    public List<Einhorn> getEinhorner() {
        return einhorner;
    }

    public boolean isFull() {
        return einhorner.size() >= maxCapacity;
    }
}
