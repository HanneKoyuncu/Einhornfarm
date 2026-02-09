import abitur.List;

/**
 * Die Klasse Tutorial verwaltet eine Schritt-für-Schritt-Anleitung
 * für den Spieler. Die Schritte werden nacheinander ausgegeben,
 * z. B. als Hilfetext im Spiel.
 */
public class Tutorial {

    /** Liste aller Tutorial-Schritte */
    private List<String> schritte;

    /** Aktueller Index des Tutorials */
    private int index;

    /**
     * Konstruktor des Tutorials.
     * Initialisiert die Liste der Schritte und fügt alle
     * Tutorial-Texte hinzu.
     */
    public Tutorial() {
        schritte = new List<>();
        index = 0;

        // Tutorial-Schritte
        schritte.append("Willkommen auf deiner Farm!");
        schritte.append("Klicke auf 'Shop', um einen Stall oder Einhörner zu kaufen.");
        schritte.append("Klicke auf ein Einhorn im Stall, um es auszuwählen.");
        schritte.append("Wähle zwei Einhörner und klicke auf 'Züchten', um ein neues Einhorn zu bekommen.");
        schritte.append("Du kannst ausgewählte Einhörner auch verkaufen oder bewegen.");
        schritte.append("Viel Spaß beim Spielen!");
    }

    /**
     * Gibt den nächsten Tutorial-Schritt zurück.
     *
     * @return Der nächste Tutorial-Text oder null,
     *         wenn keine Schritte mehr vorhanden sind.
     */
    public String naechsterSchritt() {
        if (index < anzahl()) {
            schritte.toFirst();

            // Zum aktuellen Schritt springen
            for (int i = 0; i < index; i++) {
                schritte.next();
            }

            String s = schritte.getContent();
            index++;
            return s;
        }
        return null;
    }

    /**
     * Prüft, ob noch weitere Tutorial-Schritte vorhanden sind.
     *
     * @return true, wenn noch Schritte vorhanden sind, sonst false
     */
    public boolean hatMehrSchritte() {
        return index < anzahl();
    }

    /**
     * Zählt die Anzahl der Tutorial-Schritte.
     *
     * @return Anzahl der gespeicherten Schritte
     */
    private int anzahl() {
        int count = 0;
        schritte.toFirst();
        while (schritte.hasAccess()) {
            count++;
            schritte.next();
        }
        return count;
    }
}

