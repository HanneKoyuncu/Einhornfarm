import abitur.Queue;

/**
 * Die Klasse Stall verwaltet Einhörner in einer Queue.
 * Ein Stall hat eine feste maximale Kapazität von 10 Einhörnern.
 * Einhörner werden in der Reihenfolge ihres Hinzufügens gespeichert.
 */
public class Stall {

    /** Queue zur Verwaltung der Einhörner im Stall */
    private Queue<Einhorn> einhorner;

    /** Aktuelle Anzahl der Einhörner im Stall */
    private int anzahl;

    /** Maximale Anzahl an Einhörnern pro Stall */
    private static final int MAX = 10;

    /** Eindeutige Stallnummer */
    private int nummer;

    /**
     * Konstruktor für einen Stall.
     *
     * @param nummer Die Nummer des Stalls
     */
    public Stall(int nummer) {
        this.nummer = nummer;
        einhorner = new Queue<>();
        anzahl = 0;
    }

    /**
     * Gibt die Queue mit den Einhörnern zurück.
     *
     * @return Queue der Einhörner
     */
    public Queue<Einhorn> getEinhorner() {
        return einhorner;
    }

    /**
     * Gibt die Stallnummer zurück.
     *
     * @return Stallnummer
     */
    public int getNummer() {
        return nummer;
    }

    /**
     * Setzt die Stallnummer.
     *
     * @param nummer neue Stallnummer
     */
    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    /**
     * Prüft, ob der Stall voll ist.
     *
     * @return true, wenn der Stall die maximale Anzahl erreicht hat
     */
    public boolean istVoll() {
        return anzahl >= MAX;
    }

    /**
     * Fügt ein Einhorn zum Stall hinzu.
     * Das Einhorn wird am Ende der Queue eingefügt.
     *
     * @param e Das hinzuzufügende Einhorn
     * @return true, wenn das Einhorn hinzugefügt wurde
     */
    public boolean einfuegen(Einhorn e) {
        if (istVoll()) return false;

        einhorner.enqueue(e);
        anzahl++;
        return true;
    }

    /**
     * Entfernt ein bestimmtes Einhorn aus dem Stall.
     * Da eine Queue keinen direkten Zugriff erlaubt,
     * wird eine temporäre Queue verwendet.
     *
     * @param ziel Das zu entfernende Einhorn
     * @return true, wenn das Einhorn gefunden und entfernt wurde
     */
    public boolean entferne(Einhorn ziel) {
        Queue<Einhorn> temp = new Queue<>();
        boolean gefunden = false;

        while (!einhorner.isEmpty()) {
            Einhorn e = einhorner.front();
            einhorner.dequeue();

            if (!gefunden && e == ziel) {
                gefunden = true;
                anzahl--;
            } else {
                temp.enqueue(e);
            }
        }

        // Ursprüngliche Queue wiederherstellen
        while (!temp.isEmpty()) {
            einhorner.enqueue(temp.front());
            temp.dequeue();
        }

        return gefunden;
    }

    /**
     * Gibt die aktuelle Anzahl der Einhörner im Stall zurück.
     *
     * @return Anzahl der Einhörner
     */
    public int getAnzahl() {
        return anzahl;
    }
}
