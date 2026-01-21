public class Einhorn {
    private Attribut attribut;
    String Klasse;
    String Seltenheit;
    int Level;

    public Einhorn(Attribut attribut){
        this.attribut = attribut;
        this.Seltenheit= Seltenheit;
        this.Level=1;

    }

    public int getLevel() {
        return Level;
    }

    public Attribut getAttribut() {
        return attribut;
    }

    public String getSeltenheit() {
        return Seltenheit;
    }

    public String getKlasse() {
        return Klasse;
    }

    public void setAttribut(Attribut pAttribut) {
        attribut = pAttribut;
    }

    public void setSeltenheit(String seltenheit) {
        Seltenheit = seltenheit;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public void setKlasse(String klasse) {
        Klasse = klasse;
    }

    public Einhorn zuechten(Einhorn partner) {

        Attribut babyAttribut;



        if (this.attribut.equals(partner.attribut)) {
            babyAttribut = this.attribut;
        } else {

            babyAttribut = Math.random() < 0.5
                    ? this.attribut
                    : partner.attribut;
        }


        return new Einhorn(babyAttribut);
    }

}


