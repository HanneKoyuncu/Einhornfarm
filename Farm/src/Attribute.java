import java.util.Random;

public enum Attribute {

    // Basisattribute, jedes Attribut hat eine feste Seltenheit
    FEUER(Seltenheit.GEWÖHNLICH),
    WASSER(Seltenheit.GEWÖHNLICH),
    ERDE(Seltenheit.GEWÖHNLICH),
    LUFT(Seltenheit.GEWÖHNLICH),
    LICHT(Seltenheit.GEWÖHNLICH),
    DUNKEL(Seltenheit.GEWÖHNLICH),

    BLITZ(Seltenheit.SELTEN),      // Feuer+Luft
    LAVA(Seltenheit.SELTEN),       // Feuer+Erde
    STAUB(Seltenheit.SELTEN),      // Erde+Luft
    DAMPF(Seltenheit.SELTEN),      // Feuer+Wasser
    SCHNEE(Seltenheit.SELTEN),     // Wasser+Luft
    METALL(Seltenheit.SELTEN),     // Erde+Dunkel

    STURM(Seltenheit.EPISCH),      // Blitz+Luft
    MAGNA(Seltenheit.EPISCH),     // Lava+Feuer
    TSUNAMI(Seltenheit.EPISCH),    // Wasser+Schnee
    GEIST(Seltenheit.EPISCH),    // Licht+Dunkel

    SOLAR(Seltenheit.LEGENDÄR),   // Feuer+Licht
    ENERGIE(Seltenheit.LEGENDÄR),   // Lava+Blitz
    TITAN(Seltenheit.LEGENDÄR);  // Erde+Metall

    private final Seltenheit seltenheit;

    Attribute(Seltenheit seltenheit) {
        this.seltenheit = seltenheit;
    }

    public Seltenheit getSeltenheit() {
        return seltenheit;
    }


    private static final Attribute[][] kombinationen;
   //Kombinationen werden festgelegt
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
        setKombination(FEUER, LICHT,SOLAR);
        setKombination(LAVA, BLITZ,ENERGIE);
        setKombination(ERDE, METALL,TITAN);

    }
    //a+b dasselbe wie b+a
    private static void setKombination(Attribute a, Attribute b, Attribute result) {
        kombinationen[a.ordinal()][b.ordinal()] = result;
        kombinationen[b.ordinal()][a.ordinal()] = result;
    }

    private static final Random zufall = new Random();
    // Kombinieren von zwei Attributen, wenn keine Kombination definiert ist oder a!=b ist das resultierende Attribut zufällig
    public static Attribute kombiniere(Attribute a, Attribute b) {
        if (a == b) return a;

        Attribute result = kombinationen[a.ordinal()][b.ordinal()];

        if (result != null) {
            return result;
        } else {

            return zufall.nextBoolean() ? a : b;
        }
    }
}
