/**
 * Die Klasse Einhorn repräsentiert ein einzelnes Einhorn im Spiel.
 * Jedes Einhorn besitzt ein Attribut (z.B. FEUER, WASSER, LAVA)
 * sowie eine dazugehörige Seltenheit.
 *
 * Die Seltenheit wird automatisch aus dem Attribut abgeleitet
 * und bestimmt z.B. den Verkaufswert des Einhorns.
 */
public class Einhorn {

    /**
     * Das Attribut des Einhorns, welches seine Eigenschaften festlegt.
     */
    private Attribute attribut;

    /**
     * Die Seltenheit des Einhorns, abhängig vom Attribut.
     */
    private Seltenheit seltenheit;

    /**
     * Konstruktor für ein Einhorn.
     * Beim Erstellen eines Einhorns wird dessen Seltenheit
     * automatisch aus dem Attribut bestimmt.
     *
     * @param attribut das Attribut des Einhorns
     */
    public Einhorn(Attribute attribut) {
        this.attribut = attribut;
        this.seltenheit = attribut.getSeltenheit();
    }

    /**
     * Gibt das Attribut des Einhorns zurück.
     *
     * @return Attribut des Einhorns
     */
    public Attribute getAttribut() {
        return attribut;
    }

    /**
     * Gibt die Seltenheit des Einhorns zurück.
     *
     * @return Seltenheit des Einhorns
     */
    public Seltenheit getSeltenheit() {
        return seltenheit;
    }
}


