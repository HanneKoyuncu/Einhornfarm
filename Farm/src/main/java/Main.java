import javax.swing.*;

/**
 * Die Klasse Main ist der Einstiegspunkt des Programms.
 * Sie erstellt das Hauptfenster der Anwendung und zeigt
 * zunächst den Startbildschirm (StartPanel) an.
 *
 * Von hier aus wird das gesamte Spiel gestartet.
 */
public class Main {

    /**
     * Die main-Methode startet das Programm.
     * Sie erstellt das Hauptfenster (JFrame) und fügt
     * das StartPanel als ersten sichtbaren Bildschirm hinzu.
     *
     * @param args Kommandozeilenargumente (werden hier nicht verwendet)
     */
    public static void main(String[] args) {

        // Erstellen des Hauptfensters
        JFrame window = new JFrame("Einhornfarm");

        // Beendet das Programm beim Schließen des Fensters
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Startbildschirm erstellen und zum Fenster hinzufügen
        StartPanel startPanel = new StartPanel(window);
        window.add(startPanel);

        // Fenstergröße an den Inhalt anpassen
        window.pack();

        // Fenster mittig auf dem Bildschirm platzieren
        window.setLocationRelativeTo(null);

        // Fenster sichtbar machen
        window.setVisible(true);
    }
}
