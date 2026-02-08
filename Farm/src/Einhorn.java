public class Einhorn {

    private Attribute attribut;
    private Seltenheit seltenheit;

    // Konstruktor
    public Einhorn(Attribute attribut) {
        this.attribut = attribut;
        this.seltenheit = attribut.getSeltenheit();
    }

    // Getter
    public Attribute getAttribut() {
        return attribut;
    }

    public Seltenheit getSeltenheit() {
        return seltenheit;
    }

    // Für Anzeige (optional, aber praktisch)
    @Override
    public String toString() {
        return "Einhorn (" + this.getAttribut() + ", " + this.getSeltenheit() + ")";
    }
}


