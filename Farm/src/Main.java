
import javax.swing.*;
import java.net.URL;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {


            JFrame window = new JFrame("Einhornfarm");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(true);

            GamePanel gamePanel = new GamePanel(window);
            window.add(gamePanel);
            window.pack();                 // nutzt PreferredSize
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }
}
