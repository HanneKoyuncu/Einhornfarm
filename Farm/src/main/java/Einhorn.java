public class Einhorn {

    private Attribute attribut;
    private Seltenheit seltenheit;

    public Einhorn(Attribute attribut) {
        this.attribut = attribut;
        this.seltenheit = attribut.getSeltenheit();
    }

    public Attribute getAttribut() {
        return attribut;
    }

    public Seltenheit getSeltenheit() {
        return seltenheit;
    }
}


