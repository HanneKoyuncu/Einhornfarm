import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class GamePanel extends JPanel {

    private BufferedImage background;
    final int originalTileSize =16;
    final int scale=3;

    final int tileSize= originalTileSize *scale;
    final int maxScreenCol=16;
    final int maxScreenRow=12;
    final int screenWidth=tileSize*maxScreenCol;
    final int screenHeight=tileSize*maxScreenRow;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.add(new JButton("Menü"));
        this.add(new JButton("Einstellungen"));
        this.setDoubleBuffered(true);
           }
    private void loadBackground() {
        try {
            background = ImageIO.read(
                    getClass().getResource("images/bg/parallax-mountain-bg.png")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (background != null) {
            g.drawImage(
                    background,
                    0, 0,
                    screenWidth, screenHeight,
                    null
            );
        }
    }



}
