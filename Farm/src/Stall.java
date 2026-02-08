import abitur.Queue;

public class Stall {

    private Queue<Einhorn> einhorner;
    private int anzahl;
    private static final int MAX = 10;
    private int nummer;

    public Stall(int nummer) {
        this.nummer = nummer;
        einhorner = new Queue<>();
        anzahl = 0;
    }

    public Queue<Einhorn> getEinhorner() {
        return einhorner;
    }

    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public boolean istVoll() {
        return anzahl >= MAX;
    }

    // Einhorn wird hinzugefügt
    public boolean einfuegen(Einhorn e) {
        if (istVoll()) return false;

        einhorner.enqueue(e);
        anzahl++;
        return true;
    }

    // Einhorn wird entfernt
    public boolean entferne(Einhorn ziel) {
        Queue<Einhorn> temp = new Queue<>();
        boolean gefunden = false;

        while (!einhorner.isEmpty()) {
            Einhorn e = einhorner.front();
            einhorner.dequeue();

            if (!gefunden && e == ziel) {
                gefunden = true;
                anzahl--;
            } else {
                temp.enqueue(e);
            }
        }

        // Queue wiederherstellen
        while (!temp.isEmpty()) {
            einhorner.enqueue(temp.front());
            temp.dequeue();
        }

        return gefunden;
    }

    public int getAnzahl() {
        return anzahl;
    }

}
