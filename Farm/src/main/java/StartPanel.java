import javax.swing.*;

/**
 * Das StartPanel ist der Startbildschirm des Spiels.
 * Hier kann der Spieler das Spiel starten und den Namen
 * seiner Farm festlegen.
 */
public class StartPanel extends JPanel {

    /** Referenz auf das Hauptfenster */
    private JFrame window;

    /**
     * Konstruktor des StartPanels.
     *
     * @param window Das Hauptfenster des Spiels
     */
    public StartPanel(JFrame window) {
        this.window = window;

        JButton startBtn = new JButton("Start");

        // Startet das Spiel beim Klick auf den Button
        startBtn.addActionListener(e -> startGame());

        add(startBtn);
    }

    /**
     * Startet das Spiel.
     * Der Spieler gibt einen Namen für die Farm ein,
     * anschließend wird die Farm erstellt und das
     * GamePanel angezeigt.
     */
    private void startGame() {

        String farmName = JOptionPane.showInputDialog(
                this,
                "Wie soll deine Farm heißen?"
        );

        // Falls kein Name eingegeben wurde, wird ein Standardname gesetzt
        if (farmName == null || farmName.isEmpty()) {
            farmName = "Meine Farm";
        }

        // Farm wird erstellt
        Farm farm = new Farm(farmName);

        // GamePanel mit der Farm wird erstellt
        GamePanel gamePanel = new GamePanel(farm);

        // StartPanel wird durch das GamePanel ersetzt
        window.getContentPane().removeAll();
        window.add(gamePanel);
        window.revalidate();
        window.repaint();
    }
}
