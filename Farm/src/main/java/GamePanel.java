import abitur.List;
import abitur.Queue;

import javax.swing.*;
import java.awt.*;

/**
 * Das GamePanel stellt die Hauptspieloberfläche dar.
 * Hier kann der Spieler:
 * - Einhörner auswählen
 * - züchten, verkaufen und bewegen
 * - Coins verwalten
 * - Ställe anzeigen
 * - Shop und Handbuch öffnen
 * - das Tutorial sehen
 */
public class GamePanel extends JPanel {

    /** Die Farm des Spielers */
    private Farm farm;

    /** Liste der aktuell ausgewählten Einhörner */
    private List<Einhorn> ausgewaehlt;

    /** Informationsanzeige für Aktionen */
    private JLabel infoLabel;

    /** Anzeige der aktuellen Coins */
    private JLabel coinsLabel;

    /** Anzeige für kurzfristige Coin-Veränderungen */
    private JLabel coinsChangeLabel;

    /** Oberes Panel mit Infos und Buttons */
    private JPanel topPanel;

    /** Container für alle Stall-Panels */
    private JPanel stallContainer;

    /**
     * Konstruktor des GamePanels.
     * Initialisiert die Oberfläche und startet das Tutorial.
     *
     * @param farm Die Farm des Spielers
     */
    public GamePanel(Farm farm) {
        this.farm = farm;
        this.ausgewaehlt = new List<>();

        setLayout(new BorderLayout());

        // ===== TOP PANEL =====
        topPanel = new JPanel(new BorderLayout());

        infoLabel = new JLabel("Wähle Einhörner für Aktionen", SwingConstants.CENTER);

        coinsLabel = new JLabel("Coins: " + farm.getCoins());
        coinsChangeLabel = new JLabel("", SwingConstants.CENTER);

        JPanel coinsPanel = new JPanel(new GridLayout(2, 1));
        coinsPanel.add(coinsLabel);
        coinsPanel.add(coinsChangeLabel);

        JButton inventarBtn = new JButton("Einhornhandbuch");
        inventarBtn.addActionListener(e -> zeigeInventar());

        JButton shopBtn = new JButton("Shop");
        shopBtn.addActionListener(e -> oeffneShop());

        JPanel leftPanel = new JPanel(new FlowLayout());
        leftPanel.add(inventarBtn);
        leftPanel.add(shopBtn);

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(infoLabel, BorderLayout.CENTER);
        topPanel.add(coinsPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // ===== AKTIONSPANEL =====
        JPanel actionPanel = new JPanel(new FlowLayout());

        JButton zuechtenBtn = new JButton("Züchten");
        zuechtenBtn.addActionListener(e -> zuechten());

        JButton verkaufenBtn = new JButton("Verkaufen");
        verkaufenBtn.addActionListener(e -> verkaufen());

        JButton bewegenBtn = new JButton("Bewegen");
        bewegenBtn.addActionListener(e -> bewegen());

        actionPanel.add(zuechtenBtn);
        actionPanel.add(verkaufenBtn);
        actionPanel.add(bewegenBtn);

        add(actionPanel, BorderLayout.CENTER);

        // ===== STALLBEREICH =====
        stallContainer = new JPanel();
        stallContainer.setLayout(new BoxLayout(stallContainer, BoxLayout.Y_AXIS));

        add(new JScrollPane(stallContainer), BorderLayout.SOUTH);

        updateAnzeige();
        starteTutorial();
    }

    /**
     * Aktualisiert die komplette Anzeige:
     * - Ställe
     * - Einhörner
     * - Coins
     */
    private void updateAnzeige() {
        stallContainer.removeAll();

        farm.getStaelle().toFirst();
        int nr = 1;
        while (farm.getStaelle().hasAccess()) {
            stallContainer.add(erstelleStallPanel(farm.getStaelle().getContent(), nr));
            nr++;
            farm.getStaelle().next();
        }

        coinsLabel.setText("Coins: " + farm.getCoins());

        revalidate();
        repaint();
    }

    /**
     * Erstellt ein Panel für einen einzelnen Stall
     * inklusive aller Einhörner als Buttons.
     */
    private JPanel erstelleStallPanel(Stall stall, int nr) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                "Stall " + nr + " (" + stall.getAnzahl() + "/10)"
        ));

        Queue<Einhorn> temp = new Queue<>();

        while (!stall.getEinhorner().isEmpty()) {
            Einhorn e = stall.getEinhorner().front();
            stall.getEinhorner().dequeue();
            temp.enqueue(e);

            JButton btn = new JButton(
                    "Einhorn (" + e.getAttribut() + ", " + e.getSeltenheit() + ")"
            );
            btn.addActionListener(ev -> einhornAuswaehlen(e));
            panel.add(btn);
        }

        // Queue wiederherstellen
        while (!temp.isEmpty()) {
            stall.getEinhorner().enqueue(temp.front());
            temp.dequeue();
        }

        return panel;
    }

    /**
     * Wählt ein Einhorn aus oder ab.
     * Maximal zwei Einhörner können ausgewählt werden.
     */
    private void einhornAuswaehlen(Einhorn e) {
        ausgewaehlt.toFirst();
        while (ausgewaehlt.hasAccess()) {
            if (ausgewaehlt.getContent() == e) {
                ausgewaehlt.remove();
                infoLabel.setText("Einhorn abgewählt");
                return;
            }
            ausgewaehlt.next();
        }

        if (anzahl(ausgewaehlt) >= 2) {
            infoLabel.setText("Maximal zwei Einhörner auswählbar");
            return;
        }

        ausgewaehlt.append(e);
        infoLabel.setText("Ausgewählt: " + e.getAttribut());
    }

    /**
     * Züchtet zwei ausgewählte Einhörner,
     * wenn sie sich im selben Stall befinden.
     */
    private void zuechten() {
        if (anzahl(ausgewaehlt) != 2) {
            infoLabel.setText("Wähle genau zwei Einhörner");
            return;
        }

        ausgewaehlt.toFirst();
        Einhorn e1 = ausgewaehlt.getContent();
        ausgewaehlt.next();
        Einhorn e2 = ausgewaehlt.getContent();

        Stall s1 = findeStallVonEinhorn(e1);
        Stall s2 = findeStallVonEinhorn(e2);

        if (s1 == null || s1 != s2 || s1.istVoll()) {
            infoLabel.setText("Zucht nur im selben Stall möglich");
            ausgewaehlt = new List<>();
            return;
        }

        Attribute neu = Attribute.kombiniere(e1.getAttribut(), e2.getAttribut());
        s1.einfuegen(new Einhorn(neu));

        infoLabel.setText("Neues Einhorn geboren!");
        ausgewaehlt = new List<>();
        updateAnzeige();
    }

    /**
     * Verkauft alle ausgewählten Einhörner
     * und erhöht die Coins entsprechend der Seltenheit.
     */
    private void verkaufen() {
        if (anzahl(ausgewaehlt) == 0) return;

        int verdient = 0;

        ausgewaehlt.toFirst();
        while (ausgewaehlt.hasAccess()) {
            Einhorn e = ausgewaehlt.getContent();
            Stall s = findeStallVonEinhorn(e);

            if (s != null && s.entferne(e)) {
                int wert = e.getSeltenheit().getWert();
                farm.addCoins(wert);
                verdient += wert;
            }
            ausgewaehlt.next();
        }

        zeigeCoinsChange(verdient);
        ausgewaehlt = new List<>();
        updateAnzeige();
    }

    /**
     * Zeigt eine temporäre Anzeige,
     * wie viele Coins gewonnen oder verloren wurden.
     */
    private void zeigeCoinsChange(int betrag) {
        coinsChangeLabel.setText((betrag > 0 ? "+" : "") + betrag);
        coinsChangeLabel.setForeground(betrag >= 0 ? Color.GREEN : Color.RED);

        Timer t = new Timer(1000, e -> coinsChangeLabel.setText(""));
        t.setRepeats(false);
        t.start();
    }

    /**
     * Startet das Tutorial in einem separaten Fenster.
     */
    private void starteTutorial() {
        Tutorial tutorial = new Tutorial();

        JFrame tutorialFenster = new JFrame("Tutorial");
        tutorialFenster.setSize(400, 200);
        tutorialFenster.setLocationRelativeTo(this);

        JLabel schrittLabel = new JLabel(
                tutorial.naechsterSchritt(),
                SwingConstants.CENTER
        );

        JButton weiterBtn = new JButton("Weiter");
        weiterBtn.addActionListener(e -> {
            if (tutorial.hatMehrSchritte()) {
                schrittLabel.setText(tutorial.naechsterSchritt());
            } else {
                tutorialFenster.dispose();
            }
        });

        tutorialFenster.add(schrittLabel, BorderLayout.CENTER);
        tutorialFenster.add(weiterBtn, BorderLayout.SOUTH);
        tutorialFenster.setVisible(true);
    }

    // ===== HILFSMETHODEN =====

    /** Findet den Stall eines bestimmten Einhorns */
    private Stall findeStallVonEinhorn(Einhorn e) {
        farm.getStaelle().toFirst();
        while (farm.getStaelle().hasAccess()) {
            Stall s = farm.getStaelle().getContent();
            Queue<Einhorn> temp = new Queue<>();
            boolean found = false;

            while (!s.getEinhorner().isEmpty()) {
                Einhorn x = s.getEinhorner().front();
                s.getEinhorner().dequeue();
                temp.enqueue(x);
                if (x == e) found = true;
            }

            while (!temp.isEmpty()) {
                s.getEinhorner().enqueue(temp.front());
                temp.dequeue();
            }

            if (found) return s;
            farm.getStaelle().next();
        }
        return null;
    }

    /** Zählt die Anzahl der Elemente einer List */
    private int anzahl(List<?> l) {
        int c = 0;
        l.toFirst();
        while (l.hasAccess()) {
            c++;
            l.next();
        }
        return c;
    }
}
