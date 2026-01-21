import java.util.ArrayList;
import java.util.List;

public class Inventar {
    private List<Einhorn> einhornListe;

    public Inventar() {
        einhornListe = new ArrayList<>();
    }

    public void addEinhorn(Einhorn e) {
        einhornListe.add(e);
    }

    public void removeEinhorn(Einhorn e) {
        einhornListe.remove(e);
    }

    public void showEinhornInventar() {
        System.out.println("Deine Einhörner:");
        for (Einhorn e : einhornListe) {
            System.out.println("- " + e);
        }
    }

    public List<Einhorn> getEinhornListe() {
        return einhornListe;
    }
}
