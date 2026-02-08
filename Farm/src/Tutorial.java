import abitur.List;

public class Tutorial {

    private List<String> schritte;
    private int index;

    public Tutorial() {
        schritte = new List<>();
        index = 0;

        // Tutorial-Schritte
        schritte.append("Willkommen auf deiner Farm!");
        schritte.append("Klicke auf 'Shop', um einen Stall oder Einhörner zu kaufen.");
        schritte.append("Klicke auf ein Einhorn im Stall, um es auszuwählen.");
        schritte.append("Wähle zwei Einhörner und klicke auf 'Züchten', um ein neues Einhorn zu bekommen.");
        schritte.append("Du kannst ausgewählte Einhörner auch verkaufen oder bewegen.");
        schritte.append("Viel Spaß beim Spielen!");
    }

    public String naechsterSchritt() {
        if (index < anzahl()) {
            schritte.toFirst();
            for (int i = 0; i < index; i++) schritte.next();
            String s = schritte.getContent();
            index++;
            return s;
        }
        return null;
    }

    public boolean hatMehrSchritte() {
        return index < anzahl();
    }

    private int anzahl() {
        int count = 0;
        schritte.toFirst();
        while (schritte.hasAccess()) {
            count++;
            schritte.next();
        }
        return count;
    }

    public void reset() {
        index = 0;
    }
}
