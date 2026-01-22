import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class GamePanel extends JPanel {
    private JFrame parentFrame;

    private BufferedImage background;

    final int originalTileSize = 16;
    final int scale = 3;

    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    public GamePanel(JFrame parentFrame) {

        try {
            background = ImageIO.read(
                    getClass().getResource("/images/bg/parallax-mountain-bg.png")
            );
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        JButton startBtn = new JButton("Start");
        JButton optionsBtn = new JButton("Optionen");
        JButton exitBtn = new JButton("Beenden");

        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        startBtn.setFocusPainted(false);
        optionsBtn.setFocusPainted(false);
        exitBtn.setFocusPainted(false);

        add(Box.createVerticalGlue());
        add(startBtn);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(optionsBtn);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(exitBtn);
        add(Box.createVerticalGlue());
        startBtn.addActionListener(e -> {


            JFrame gameWindow = new JFrame("Einhornfarm");
            gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
            gameWindow.setSize(800, 600);
            gameWindow.setLocationRelativeTo(null);


            JPanel playPanel = new JPanel();
            JButton backBtn = new JButton("Back");
            playPanel.setOpaque(false);
            playPanel.add(backBtn);

            add(playPanel, BorderLayout.NORTH);
            backBtn.addActionListener(l -> {
                
                parentFrame.getContentPane().removeAll();
                parentFrame.getContentPane().add(new GamePanel(parentFrame));
                parentFrame.revalidate();
                parentFrame.repaint();
            });


            playPanel.setBackground(Color.DARK_GRAY);
            gameWindow.add(playPanel);
            gameWindow.pack();                 
            gameWindow.setLocationRelativeTo(null);



            gameWindow.setVisible(true);



            SwingUtilities.getWindowAncestor(startBtn).setVisible(false);
        });

        optionsBtn.addActionListener(e -> {

            JFrame gameWindow = new JFrame("Optionen");
            gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
            gameWindow.setSize(800, 600);
            gameWindow.setLocationRelativeTo(null);


            JPanel playPanel = new JPanel();
            playPanel.setBackground(Color.pink);
            gameWindow.add(playPanel);



            gameWindow.setVisible(true);


            SwingUtilities.getWindowAncestor(optionsBtn).setVisible(false);
        });

    }
    public void addBackButton() {
        JButton backBtn = new JButton("Back");
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        topPanel.add(backBtn);

        add(topPanel, BorderLayout.NORTH);

        backBtn.addActionListener(e -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.getContentPane().add(new GamePanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (background != null) {
            g.drawImage(
                    background,
                    0, 0,
                    getWidth(), getHeight(),
                    null
            );
        }
    }
}
