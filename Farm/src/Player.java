public class Player {

    private String name;
    private Farm farm;

        public Player(String name) {
            this.name = name;
            this.farm = new Farm();
        }

        public String getName() {
            return name;
        }

        public Farm getFarm() {
            return farm;
        }

    public void setName(String pName) {
        this.name = pName;
    }

    public void setFarm(Farm pFarm) {
        this.farm = pFarm;
    }
}


