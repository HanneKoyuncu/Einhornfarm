public enum Seltenheit {
    GEWÖHNLICH(10),
    SELTEN(25),
    EPISCH(50),
    LEGENDÄR(100);

    private int wert;

    Seltenheit(int wert) {
        this.wert = wert;
    }

    public int getWert() {
        return wert;
    }
}
