/**
 * Die Enum Seltenheit beschreibt, wie selten ein Einhorn ist.
 * Jede Seltenheit besitzt einen festen Wert, der z.B.
 * beim Verkauf eines Einhorns als Coin-Betrag verwendet wird.
 */
public enum Seltenheit {

    // Häufiges Einhorn, niedriger Verkaufswert
    GEWÖHNLICH(10),

    // Seltener als gewöhnlich
    SELTEN(25),

    // Sehr selten
    EPISCH(50),

    // Extrem selten, hoher Wert
    LEGENDÄR(100);

    /**
     * Der Coin-Wert dieser Seltenheit
     */
    private int wert;

    /**
     * Konstruktor der Enum.
     * Jeder Seltenheit wird ein fester Wert zugewiesen.
     *
     * @param wert Coin-Wert beim Verkauf
     */
    Seltenheit(int wert) {
        this.wert = wert;
    }

    /**
     * Gibt den Coin-Wert der Seltenheit zurück.
     *
     * @return Coin-Wert
     */
    public int getWert() {
        return wert;
    }
}
