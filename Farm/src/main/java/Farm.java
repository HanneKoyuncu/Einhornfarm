import abitur.Stack;
import abitur.List;

/**
 * Die Klasse Farm repräsentiert die gesamte Einhornfarm des Spielers.
 * Sie verwaltet alle Ställe, das Einhornhandbuch (Inventar),
 * die vorhandenen Coins sowie die Preise für neue Ställe.
 *
 * Die Farm ist das zentrale Verwaltungsobjekt der Spiellogik
 * und enthält keine GUI-Funktionalität.
 */
public class Farm {

    /**
     * Stack, der alle bekannten Einhörner der Farm speichert.
     * Neue Einhörner werden oben auf den Stack gelegt.
     */
    private Stack<Einhorn> Einhornhandbuch;

    /**
     * Liste aller vorhandenen Ställe auf der Farm.
     */
    private List<Stall> staelle;

    /**
     * Aktuelle Anzahl an Coins (Währung der Farm).
     */
    private int coins;

    /**
     * Preisübersicht für den Kauf neuer Ställe.
     * Der Index entspricht der Anzahl der vorhandenen Ställe.
     */
    private int[] stallPreise = {0, 100, 150, 200, 300, 450};

    /**
     * Konstruktor für eine neue Farm.
     * Erstellt einen Startstall und fügt zwei Startereinhörner hinzu.
     *
     * @param name Name der Farm (z.B. vom Spieler gewählt)
     */
    public Farm(String name) {
        staelle = new List<>();
        Einhornhandbuch = new Stack<>();
        coins = 0;

        // Startstall mit zwei Starteinhörnern
        Stall startStall = new Stall(1);
        startStall.einfuegen(new Einhorn(Attribute.FEUER));
        startStall.einfuegen(new Einhorn(Attribute.WASSER));
        staelle.append(startStall);
    }

    /**
     * Gibt das Array mit den Stallpreisen zurück.
     *
     * @return Array der Stallpreise
     */
    public int[] getStallPreise() {
        return stallPreise;
    }

    /**
     * Ermittelt den Preis für den nächsten kaufbaren Stall.
     * Falls bereits die maximale Anzahl an Ställen erreicht ist,
     * wird der höchste verfügbare Preis zurückgegeben.
     *
     * @return Preis des nächsten Stalles
     */
    public int getNaechsterStallPreis() {
        int anzahl = 0;
        staelle.toFirst();
        while (staelle.hasAccess()) {
            anzahl++;
            staelle.next();
        }

        if (anzahl >= stallPreise.length) {
            return stallPreise[stallPreise.length - 1];
        }

        return stallPreise[anzahl];
    }

    /**
     * Fügt der Farm eine bestimmte Anzahl an Coins hinzu.
     *
     * @param coins Anzahl der hinzuzufügenden Coins
     */
    public void addCoins(int coins) {
        this.coins += coins;
    }

    /**
     * Versucht, einen neuen Stall zu kaufen.
     * Der Kauf ist nur möglich, wenn genügend Coins vorhanden sind
     * und die maximale Anzahl an Ställen noch nicht erreicht ist.
     *
     * @return true, wenn der Stall erfolgreich gekauft wurde,
     *         false sonst
     */
    public boolean kaufeStall() {
        int anzahlStaelle = 0;

        staelle.toFirst();
        while (staelle.hasAccess()) {
            anzahlStaelle++;
            staelle.next();
        }

        // Maximale Anzahl an Ställen erreicht
        if (anzahlStaelle >= stallPreise.length) {
            return false;
        }

        int preis = stallPreise[anzahlStaelle];

        if (coins >= preis) {
            coins -= preis;
            staelle.append(new Stall(naechsteStallNummer()));
            return true;
        }

        return false;
    }

    /**
     * Berechnet die Nummer des nächsten zu erstellenden Stalls.
     *
     * @return nächste Stallnummer
     */
    private int naechsteStallNummer() {
        int count = 0;
        staelle.toFirst();
        while (staelle.hasAccess()) {
            count++;
            staelle.next();
        }
        return count + 1;
    }

    /**
     * Gibt das Einhornhandbuch (Inventar) zurück.
     *
     * @return Stack mit allen Einhörnern
     */
    public Stack<Einhorn> getEinhornhandbuch() {
        return Einhornhandbuch;
    }

    /**
     * Gibt die Liste aller Ställe der Farm zurück.
     *
     * @return Liste der Ställe
     */
    public List<Stall> getStaelle() {
        return staelle;
    }

    /**
     * Gibt die aktuelle Anzahl an Coins zurück.
     *
     * @return aktuelle Coins
     */
    public int getCoins() {
        return coins;
    }
}
