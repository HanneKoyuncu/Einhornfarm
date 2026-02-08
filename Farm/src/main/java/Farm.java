import abitur.Stack;
import abitur.List;

    public class Farm {

        private Stack<Einhorn> Einhornhandbuch;
        private List<Stall> staelle;
        private int coins;
        private int[] stallPreise = {0,100, 150, 200, 300, 450};


        public Farm(String name) {
            staelle = new List<>();
            Einhornhandbuch =new Stack<>();
            coins = 0;

            // Starter Stall mit zwei Einhörnern
            Stall startStall = new Stall(1);
            startStall.einfuegen(new Einhorn(Attribute.FEUER));
            startStall.einfuegen(new Einhorn(Attribute.WASSER));
            staelle.append(startStall);
        }

        public int[] getStallPreise() {
            return stallPreise;
        }
        //Preis des nächsten Stalles wird zurückgegeben
        public int getNaechsterStallPreis() {
            int anzahl = 0;
            staelle.toFirst();
            while (staelle.hasAccess()) {
                anzahl++;
                staelle.next();
            }

            if (anzahl >= stallPreise.length) {
                return stallPreise[stallPreise.length - 1];
            }

            return stallPreise[anzahl];
        }

        //Coins werden den aktuellen Coins hinzugefügt
        public void addCoins(int coins) {
            this.coins += coins;
        }
        public boolean kaufeStall() {
            int anzahlStaelle = 0;

            staelle.toFirst();
            while (staelle.hasAccess()) {
                anzahlStaelle++;
                staelle.next();
            }
            if (anzahlStaelle >= stallPreise.length) {
                return false;
            }

            int preis = stallPreise[anzahlStaelle];

            if (coins >= preis) {
                coins -= preis;
                staelle.append(new Stall(naechsteStallNummer()));
                return true;
            }

            return false;
        }
        //Die nächste Stallnummer wird zurückgegeben
        private int naechsteStallNummer() {
            int count = 0;
            staelle.toFirst();
            while (staelle.hasAccess()) {
                count++;
                staelle.next();
            }
            return count + 1;
        }

        public Stack<Einhorn> getEinhornhandbuch() { return Einhornhandbuch; }
        public List<Stall> getStaelle() { return staelle; }
        public int getCoins() { return coins; }
    }
