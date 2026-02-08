public enum Attribute {

    // ===== Basisattribute =====
    FEUER(Seltenheit.GEWÖHNLICH),
    WASSER(Seltenheit.GEWÖHNLICH),
    ERDE(Seltenheit.GEWÖHNLICH),
    LUFT(Seltenheit.GEWÖHNLICH),
    LICHT(Seltenheit.GEWÖHNLICH),
    DUNKEL(Seltenheit.GEWÖHNLICH),

    // ===== Fortgeschrittene Attribute =====
    BLITZ(Seltenheit.SELTEN),      // z.B. FEUER + LUFT
    LAVA(Seltenheit.SELTEN),       // FEUER + ERDE
    STAUB(Seltenheit.SELTEN),      // ERDE + LUFT
    DAMPF(Seltenheit.SELTEN),      // FEUER + WASSER
    SCHNEE(Seltenheit.SELTEN),     // WASSER + LUFT
    METALL(Seltenheit.SELTEN),     // ERDE + DUNKEL

    // ===== Epische Kombinationen =====
    STURM(Seltenheit.EPISCH),      // BLITZ + LUFT
    MAGNA(Seltenheit.EPISCH),     // LAVA + FEUER
    TSUNAMI(Seltenheit.EPISCH),    // WASSER + SCHNEE
    GEIST(Seltenheit.EPISCH),    // LICHT + DUNKEL

    // ===== Legendäre Kombinationen =====
    SOLAR(Seltenheit.LEGENDÄR),   // FEUER + LICHT
    ENERGIE(Seltenheit.LEGENDÄR),   // LAVA + BLITZ
    TITAN(Seltenheit.LEGENDÄR);  // ERDE + METALL

    private final Seltenheit seltenheit;

    Attribute(Seltenheit seltenheit) {
        this.seltenheit = seltenheit;
    }

    public Seltenheit getSeltenheit() {
        return seltenheit;
    }

    // ===== 2D-Array für Kombinationen =====
    private static final Attribute[][] kombinationen;

    static {
        int size = Attribute.values().length;
        kombinationen = new Attribute[size][size];

        // Basis-Kombinationen
        setKombination(FEUER, WASSER, DAMPF);
        setKombination(FEUER, ERDE, LAVA);
        setKombination(FEUER, LUFT, BLITZ);
        setKombination(ERDE, LUFT, STAUB);
        setKombination(WASSER, LUFT, SCHNEE);
        setKombination(ERDE, DUNKEL, METALL);
        setKombination(LICHT, DUNKEL, GEIST);

        // Epische Kombinationen
        setKombination(BLITZ, LUFT, STURM);
        setKombination(LAVA, FEUER, MAGNA);
        setKombination(WASSER, SCHNEE, TSUNAMI);

        // Legendäre Kombinationen
        setKombination(FEUER, LICHT,SOLAR);
        setKombination(LAVA, BLITZ,ENERGIE);
        setKombination(ERDE, METALL,TITAN);
        // UNSTERBLICH bleibt optional, kann bei Bedarf gesetzt werden
    }

    private static void setKombination(Attribute a, Attribute b, Attribute result) {
        kombinationen[a.ordinal()][b.ordinal()] = result;
        kombinationen[b.ordinal()][a.ordinal()] = result; // symmetrisch
    }

    // ===== Methode zum Kombinieren =====
    public static Attribute kombiniere(Attribute a, Attribute b) {
        if (a == b) return a;
        Attribute result = kombinationen[a.ordinal()][b.ordinal()];
        return result != null ? result : a; // Fallback: erstes Attribut
    }
}
