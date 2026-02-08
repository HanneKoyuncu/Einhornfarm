import abitur.List;
import abitur.Queue;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private Farm farm;
    private List<Einhorn> ausgewaehlt;

    private JLabel infoLabel;
    private JLabel coinsLabel;
    private JLabel coinsChangeLabel;

    private JPanel topPanel;
    private JPanel stallContainer;

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

        JButton inventarBtn = new JButton("Inventar");
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

        // ===== ACTION BUTTONS =====
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

        // ===== STÄLLE =====
        stallContainer = new JPanel();
        stallContainer.setLayout(new BoxLayout(stallContainer, BoxLayout.Y_AXIS));

        add(new JScrollPane(stallContainer), BorderLayout.SOUTH);

        updateAnzeige();
        starteTutorial();
    }

    // ================= ANZEIGE =================
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

    // ================= EINHORN KAUF =================
    private void kaufeEinhorn(Attribute a) {
        int preis = a.getSeltenheit().getWert();

        if (farm.getCoins() < preis) {
            infoLabel.setText("Nicht genug Coins");
            return;
        }

        JFrame f = new JFrame("Einhorn kaufen");
        f.setSize(250, 200);
        f.setLocationRelativeTo(this);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        farm.getStaelle().toFirst();
        int nr = 1;
        boolean platzGefunden = false;

        while (farm.getStaelle().hasAccess()) {
            Stall s = farm.getStaelle().getContent();

            if (!s.istVoll()) {
                platzGefunden = true;
                int stallNr = nr;

                JButton b = new JButton("Stall " + stallNr);
                b.addActionListener(ev -> {
                    s.einfuegen(new Einhorn(a));
                    farm.addCoins(-preis);

                    // Coins Change Anzeige
                    zeigeCoinsChange(-preis);

                    f.dispose();
                    updateAnzeige();
                });

                p.add(b);
            }

            nr++;
            farm.getStaelle().next();
        }

        if (!platzGefunden) {
            infoLabel.setText("Alle Ställe sind voll");
            f.dispose();
            return;
        }

        f.add(p);
        f.setVisible(true);
    }

    // ================= STALL PANEL =================
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

            JButton btn = new JButton("Einhorn("+e.getAttribut() + " ," + e.getSeltenheit() + ")");
            btn.addActionListener(ev -> einhornAuswaehlen(e));
            panel.add(btn);
        }

        while (!temp.isEmpty()) {
            stall.getEinhorner().enqueue(temp.front());
            temp.dequeue();
        }

        return panel;
    }

    // ================= EINHORN AUSWAHL =================
    private void einhornAuswaehlen(Einhorn e) {
        // Toggle-Logik
        ausgewaehlt.toFirst();
        while (ausgewaehlt.hasAccess()) {
            if (ausgewaehlt.getContent() == e) {
                ausgewaehlt.remove();
                infoLabel.setText("Einhorn abgewählt");
                infoLabel.setForeground(Color.BLACK);
                return;
            }
            ausgewaehlt.next();
        }

        // Maximal 2 nur für Zucht
        if (anzahl(ausgewaehlt) >= 2) {
            infoLabel.setText("Maximal zwei Einhörner zum Züchten auswählbar");
            infoLabel.setForeground(Color.BLACK);
            return;
        }

        ausgewaehlt.append(e);
        infoLabel.setText("Ausgewählt: " + e.getAttribut());
        infoLabel.setForeground(Color.BLACK);
    }

    // ================= ZUCHT =================
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

    // ================= VERKAUF =================
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

        // Coins Change Anzeige
        zeigeCoinsChange(verdient);

        ausgewaehlt = new List<>();
        updateAnzeige();
    }

    // ================= BEWEGEN =================
    private void bewegen() {
        if (anzahl(ausgewaehlt) == 0) {
            infoLabel.setText("Wähle mindestens ein Einhorn zum Bewegen");
            return;
        }

        ausgewaehlt.toFirst();

        while (ausgewaehlt.hasAccess()) {
            Einhorn e = ausgewaehlt.getContent();
            Stall vonStall = findeStallVonEinhorn(e);

            if (vonStall == null) continue;

            JFrame f = new JFrame("Einhorn bewegen");
            f.setSize(250, 200);
            f.setLocationRelativeTo(this);

            JPanel p = new JPanel();
            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

            farm.getStaelle().toFirst();
            int nr = 1;
            boolean optionGefunden = false;

            while (farm.getStaelle().hasAccess()) {
                Stall ziel = farm.getStaelle().getContent();

                if (ziel != vonStall && !ziel.istVoll()) {
                    optionGefunden = true;
                    int stallNr = nr;

                    JButton b = new JButton("Stall " + stallNr);
                    b.addActionListener(ev -> {
                        vonStall.entferne(e);
                        ziel.einfuegen(e);

                        infoLabel.setText("Einhorn in Stall " + stallNr + " bewegt");
                        infoLabel.setForeground(Color.BLACK);

                        f.dispose();
                        updateAnzeige();
                    });

                    p.add(b);
                }

                nr++;
                farm.getStaelle().next();
            }

            if (!optionGefunden) {
                infoLabel.setText("Kein freier Stall verfügbar");
                infoLabel.setForeground(Color.BLACK);
                f.dispose();
            } else {
                f.add(p);
                f.setVisible(true);
            }

            ausgewaehlt.next();
        }

        ausgewaehlt = new List<>();
    }

    // ================= SHOP =================
    private void oeffneShop() {
        JFrame f = new JFrame("Shop");
        f.setSize(300, 350);
        f.setLocationRelativeTo(this);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        // ===== STALL KAUFEN =====
        int stallPreis = farm.getNaechsterStallPreis();

        JButton stallBtn = new JButton("Stall kaufen (" + stallPreis + " Coins)");
        stallBtn.addActionListener(e -> {
            if (farm.kaufeStall()) {
                zeigeCoinsChange(-stallPreis);
                f.dispose();
                updateAnzeige();
            } else {
                infoLabel.setText("Nicht genug Coins für einen Stall");
                infoLabel.setForeground(Color.BLACK);
            }
        });

        p.add(stallBtn);
        p.add(Box.createVerticalStrut(10));

        // ===== EINHÖRNER KAUFEN =====
        JLabel l = new JLabel("Einhörner kaufen:");
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(l);

        for (Attribute a : Attribute.values()) {
            int preis = a.getSeltenheit().getWert();

            JButton b = new JButton(a + " (" + preis + " Coins)");
            b.addActionListener(e -> {
                f.dispose();
                kaufeEinhorn(a);
            });

            p.add(b);
        }

        f.add(new JScrollPane(p));
        f.setVisible(true);
    }

    // ================= INVENTAR =================
    private void zeigeInventar() {
        JFrame f = new JFrame("Figurenhandbuch");
        f.setSize(300, 400);
        f.setLocationRelativeTo(this);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        List<String> arten = new List<>();

        farm.getStaelle().toFirst();
        while (farm.getStaelle().hasAccess()) {
            Stall s = farm.getStaelle().getContent();
            Queue<Einhorn> temp = new Queue<>();

            while (!s.getEinhorner().isEmpty()) {
                Einhorn e = s.getEinhorner().front();
                s.getEinhorner().dequeue();
                temp.enqueue(e);

                String key = "Einhorn("+e.getAttribut() + " ," + e.getSeltenheit() + ")";
                if (!listeHat(arten, key)) arten.append(key);
            }

            while (!temp.isEmpty()) {
                s.getEinhorner().enqueue(temp.front());
                temp.dequeue();
            }
            farm.getStaelle().next();
        }

        arten.toFirst();
        while (arten.hasAccess()) {
            JButton b = new JButton(arten.getContent());
            b.setEnabled(false);
            p.add(b);
            arten.next();
        }

        f.add(new JScrollPane(p));
        f.setVisible(true);
    }

    // ================= COINS CHANGE =================
    private void zeigeCoinsChange(int betrag) {
        coinsChangeLabel.setText((betrag > 0 ? "+" : "") + betrag);
        coinsChangeLabel.setForeground(betrag >= 0 ? Color.GREEN : Color.RED);

        Timer t = new Timer(1000, e -> coinsChangeLabel.setText(""));
        t.setRepeats(false);
        t.start();
    }

    // ================= HILFSMETHODEN =================
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

    private boolean listeHat(List<String> l, String k) {
        l.toFirst();
        while (l.hasAccess()) {
            if (l.getContent().equals(k)) return true;
            l.next();
        }
        return false;
    }

    private int anzahl(List<?> l) {
        int c = 0;
        l.toFirst();
        while (l.hasAccess()) {
            c++;
            l.next();
        }
        return c;
    }
    private void starteTutorial() {
        Tutorial tutorial = new Tutorial();

        JFrame tutorialFenster = new JFrame("Tutorial");
        tutorialFenster.setSize(400, 200);
        tutorialFenster.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel schrittLabel = new JLabel(tutorial.naechsterSchritt(), SwingConstants.CENTER);
        schrittLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton weiterBtn = new JButton("Weiter");
        weiterBtn.addActionListener(e -> {
            if (tutorial.hatMehrSchritte()) {
                schrittLabel.setText(tutorial.naechsterSchritt());
            } else {
                tutorialFenster.dispose(); // Tutorial beenden
            }
        });

        panel.add(schrittLabel, BorderLayout.CENTER);
        panel.add(weiterBtn, BorderLayout.SOUTH);

        tutorialFenster.add(panel);
        tutorialFenster.setVisible(true);
    }


}
