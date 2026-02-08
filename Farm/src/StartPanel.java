import javax.swing.*;

public class StartPanel extends JPanel {

    private JFrame window;

    public StartPanel(JFrame window) {
        this.window = window;

        JButton startBtn = new JButton("Start");

        startBtn.addActionListener(e -> startGame());

        add(startBtn);
    }

    private void startGame() {

        String farmName = JOptionPane.showInputDialog(
                this,
                "Wie soll deine Farm heißen?"
        );

        if (farmName == null || farmName.isEmpty()) {
            farmName = "Meine Farm";
        }

        // ✅ Farm wird JETZT erstellt
        Farm farm = new Farm(farmName);

        // Wechsel zum GamePanel
        GamePanel gamePanel = new GamePanel(farm);

        window.getContentPane().removeAll();
        window.add(gamePanel);
        window.revalidate();
        window.repaint();
    }
}

