import java.util.Random;

/**
 * Das Enum Attribute beschreibt alle möglichen Elementar- und Spezialattribute
 * eines Einhorns. Jedes Attribut besitzt eine feste Seltenheit.
 *
 * Zusätzlich enthält die Klasse eine Kombinationslogik, mit der aus zwei
 * Attributen ein neues Attribut erzeugt werden kann (z.B. FEUER + WASSER = DAMPF).
 */
public enum Attribute {

    // ===== Basisattribute =====
    // Diese Attribute sind häufig und bilden die Grundlage für Kombinationen
    FEUER(Seltenheit.GEWÖHNLICH),
    WASSER(Seltenheit.GEWÖHNLICH),
    ERDE(Seltenheit.GEWÖHNLICH),
    LUFT(Seltenheit.GEWÖHNLICH),
    LICHT(Seltenheit.GEWÖHNLICH),
    DUNKEL(Seltenheit.GEWÖHNLICH),

    // ===== Seltene Attribute =====
    // Entstehen aus der Kombination zweier Basisattribute
    BLITZ(Seltenheit.SELTEN),      // Feuer + Luft
    LAVA(Seltenheit.SELTEN),       // Feuer + Erde
    STAUB(Seltenheit.SELTEN),      // Erde + Luft
    DAMPF(Seltenheit.SELTEN),      // Feuer + Wasser
    SCHNEE(Seltenheit.SELTEN),     // Wasser + Luft
    METALL(Seltenheit.SELTEN),     // Erde + Dunkel

    // ===== Epische Attribute =====
    STURM(Seltenheit.EPISCH),      // Blitz + Luft
    MAGNA(Seltenheit.EPISCH),      // Lava + Feuer
    TSUNAMI(Seltenheit.EPISCH),    // Wasser + Schnee
    GEIST(Seltenheit.EPISCH),      // Licht + Dunkel

    // ===== Legendäre Attribute =====
    SOLAR(Seltenheit.LEGENDÄR),    // Feuer + Licht
    ENERGIE(Seltenheit.LEGENDÄR),  // Lava + Blitz
    TITAN(Seltenheit.LEGENDÄR);    // Erde + Metall

    /**
     * Die Seltenheit des Attributs, beeinflusst z.B. den Verkaufswert.
     */
    private final Seltenheit seltenheit;

    /**
     * Konstruktor für ein Attribut mit fest zugewiesener Seltenheit.
     *
     * @param seltenheit die Seltenheit des Attributs
     */
    Attribute(Seltenheit seltenheit) {
        this.seltenheit = seltenheit;
    }

    /**
     * Gibt die Seltenheit des Attributs zurück.
     *
     * @return Seltenheit des Attributs
     */
    public Seltenheit getSeltenheit() {
        return seltenheit;
    }

    // ===== Kombinationsmatrix =====

    /**
     * Zweidimensionales Array, das alle gültigen Attribut-Kombinationen speichert.
     * kombinationen[a][b] enthält das Ergebnis von a + b.
     */
    private static final Attribute[][] kombinationen;

    /*
     * Statischer Initialisierungsblock.
     * Hier werden alle gültigen Kombinationen einmalig festgelegt.
     */
    static {
        int size = Attribute.values().length;
        kombinationen = new Attribute[size][size];

        setKombination(FEUER, WASSER, DAMPF);
        setKombination(FEUER, ERDE, LAVA);
        setKombination(FEUER, LUFT, BLITZ);
        setKombination(ERDE, LUFT, STAUB);
        setKombination(WASSER, LUFT, SCHNEE);
        setKombination(ERDE, DUNKEL, METALL);
        setKombination(LICHT, DUNKEL, GEIST);
        setKombination(BLITZ, LUFT, STURM);
        setKombination(LAVA, FEUER, MAGNA);
        setKombination(WASSER, SCHNEE, TSUNAMI);
        setKombination(FEUER, LICHT, SOLAR);
        setKombination(LAVA, BLITZ, ENERGIE);
        setKombination(ERDE, METALL, TITAN);
    }

    /**
     * Trägt eine Kombination in die Matrix ein.
     * Die Reihenfolge spielt keine Rolle (a + b == b + a).
     *
     * @param a erstes Attribut
     * @param b zweites Attribut
     * @param result Ergebnis der Kombination
     */
    private static void setKombination(Attribute a, Attribute b, Attribute result) {
        kombinationen[a.ordinal()][b.ordinal()] = result;
        kombinationen[b.ordinal()][a.ordinal()] = result;
    }

    /**
     * Zufallsgenerator für den Fallback-Fall.
     */
    private static final Random zufall = new Random();

    /**
     * Kombiniert zwei Attribute.
     *
     * - Sind beide Attribute gleich, wird dieses Attribut zurückgegeben.
     * - Existiert eine definierte Kombination, wird diese verwendet.
     * - Existiert keine Kombination, wird zufällig eines der beiden Attribute gewählt.
     *
     * @param a erstes Attribut
     * @param b zweites Attribut
     * @return resultierendes Attribut
     */
    public static Attribute kombiniere(Attribute a, Attribute b) {

        // Gleiches Attribut bleibt erhalten
        if (a == b) return a;

        // Prüfen, ob eine Kombination definiert ist
        Attribute result = kombinationen[a.ordinal()][b.ordinal()];

        if (result != null) {
            return result;
        }

        // Fallback: zufällige Auswahl eines der beiden Attribute
        return zufall.nextBoolean() ? a : b;
    }
}
