public enum Attribute {

    // ===== Basisattribute (Start) =====
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
    VULKAN(Seltenheit.EPISCH),     // LAVA + FEUER
    TSUNAMI(Seltenheit.EPISCH),    // WASSER + SCHNEE
    GEISTER(Seltenheit.EPISCH),    // LICHT + DUNKEL

    // ===== Legendäre Kombinationen =====
    PHÖNIX(Seltenheit.LEGENDÄR),   // FEUER + LICHT
    DRACHE(Seltenheit.LEGENDÄR),   // LAVA + BLITZ
    GIGANT(Seltenheit.LEGENDÄR),   // ERDE + METALL
    UNSTERBLICH(Seltenheit.LEGENDÄR); // DUNKEL + LICHT

    private final Seltenheit seltenheit;

    Attribute(Seltenheit seltenheit) {
        this.seltenheit = seltenheit;
    }

    public Seltenheit getSeltenheit() {
        return seltenheit;
    }

    // ===================== Kombination =====================
    public static Attribute kombiniere(Attribute a, Attribute b) {

        if (a == b) return a;

        // Basis-zu-Fortgeschrittene Kombinationen
        if ((a == FEUER && b == WASSER) || (a == WASSER && b == FEUER)) return DAMPF;
        if ((a == FEUER && b == ERDE) || (a == ERDE && b == FEUER)) return LAVA;
        if ((a == FEUER && b == LUFT) || (a == LUFT && b == FEUER)) return BLITZ;
        if ((a == ERDE && b == LUFT) || (a == LUFT && b == ERDE)) return STAUB;
        if ((a == WASSER && b == LUFT) || (a == LUFT && b == WASSER)) return SCHNEE;
        if ((a == ERDE && b == DUNKEL) || (a == DUNKEL && b == ERDE)) return METALL;

        // Fortgeschrittene zu Epische Kombinationen
        if ((a == BLITZ && b == LUFT) || (a == LUFT && b == BLITZ)) return STURM;
        if ((a == LAVA && b == FEUER) || (a == FEUER && b == LAVA)) return VULKAN;
        if ((a == WASSER && b == SCHNEE) || (a == SCHNEE && b == WASSER)) return TSUNAMI;
        if ((a == LICHT && b == DUNKEL) || (a == DUNKEL && b == LICHT)) return GEISTER;

        // Epische zu Legendäre Kombinationen
        if ((a == FEUER && b == LICHT) || (a == LICHT && b == FEUER)) return PHÖNIX;
        if ((a == LAVA && b == BLITZ) || (a == BLITZ && b == LAVA)) return DRACHE;
        if ((a == ERDE && b == METALL) || (a == METALL && b == ERDE)) return GIGANT;
        if ((a == DUNKEL && b == LICHT) || (a == LICHT && b == DUNKEL)) return UNSTERBLICH;

        // Fallback: erstes Attribut bleibt
        return a;
    }
}
