
import javax.swing.*;


public class Main {
    public static void main(String[] args){
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Einhornfarm");
        SplashScreen intro= new SplashScreen("images/bg/parallax-mountain-bg.png");
        intro.showFor(3000);
        System.exit(0);


        GamePanel gamePanel=new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }
}
//TIP To <b>Run</b> code, press <shortcut acti