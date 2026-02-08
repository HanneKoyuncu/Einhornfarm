import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame("Einhornfarm");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        StartPanel startPanel = new StartPanel(window);
        window.add(startPanel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
