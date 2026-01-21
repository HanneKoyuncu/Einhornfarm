import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SplashScreen extends JWindow {

        private BufferedImage background;
        public SplashScreen(String image){
            JPanel contentPane = new JPanel();
               contentPane.setLayout(new BorderLayout());
              Border bd1 = BorderFactory.createBevelBorder(
                    BevelBorder.RAISED);
            Border bd2 = BorderFactory.createEtchedBorder();
                 Border bd3 = BorderFactory.createCompoundBorder(bd1, bd2);
                 ImageIcon icon = new ImageIcon(image);
                 contentPane.add(new JLabel(" ", JLabel.CENTER), BorderLayout.NORTH);
            contentPane.add(new JLabel(icon, JLabel.CENTER), BorderLayout.CENTER);

                setContentPane(contentPane);
        }
        public void showFor(int millis)
        {
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            setLocation(dim.width / 3, dim.height / 3);
            setSize(dim.width / 3, dim.height / 3);
            setVisible(true);
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
            }
            setVisible(false);
        }


    }


