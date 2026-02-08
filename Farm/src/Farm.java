import abitur.Stack;
import abitur.List;

    public class Farm {

        private Stack<Einhorn> inventar;
        private List<Stall> staelle;
        private int coins;
        private int[] stallPreise = {0,100, 150, 200, 300, 450};


        public Farm(String name) {
            staelle = new List<>(); // <--- WICHTIG: Initialisierung!
            inventar=new Stack<>();
            coins = 0;

            // Optional: Starter-Stall mit Einhörnern
            Stall startStall = new Stall(1);
            startStall.einfuegen(new Einhorn(Attribute.FEUER));
            startStall.einfuegen(new Einhorn(Attribute.WASSER));
            staelle.append(startStall);
        }

        public int[] getStallPreise() {
            return stallPreise;
        }
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


        public void addCoins(int coins) {
            this.coins += coins;
        }
        public boolean kaufeEinhorn(Attribute attribut) {
            int preis = attribut.getSeltenheit().getWert();

            // Nicht genug Coins
            if (coins < preis) {
                return false;
            }

            // Freien Stall suchen
            staelle.toFirst();
            while (staelle.hasAccess()) {
                Stall stall = staelle.getContent();

                if (!stall.istVoll()) {
                    stall.einfuegen(new Einhorn(attribut));
                    coins -= preis;
                    return true;
                }

                staelle.next();
            }

            // Kein Platz in keinem Stall
            return false;
        }



        public void verkaufe(Einhorn e) {
            coins += e.getSeltenheit().getWert();
        }
        public boolean kaufeStall() {
            int anzahlStaelle = 0;

            staelle.toFirst();
            while (staelle.hasAccess()) {
                anzahlStaelle++;
                staelle.next();
            }

            // Keine Preise mehr definiert
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
        // Hilfsmethode: nächste Stall-Nummer bestimmen
        private int naechsteStallNummer() {
            int count = 0;
            staelle.toFirst();
            while (staelle.hasAccess()) {
                count++;
                staelle.next();
            }
            return count + 1;
        }

        public Einhorn zuechten(Einhorn a, Einhorn b) {
            Attribute neu = Attribute.kombiniere(a.getAttribut(), b.getAttribut());
            Einhorn baby = new Einhorn(neu);
            inventar.push(baby);
            return baby;
        }

        public Stack<Einhorn> getInventar() { return inventar; }
        public List<Stall> getStaelle() { return staelle; }
        public int getCoins() { return coins; }
    }
