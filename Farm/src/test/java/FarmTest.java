import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FarmTest {

    private Farm farm;

    @BeforeEach
    void setup() {
        farm = new Farm("TestFarm");
    }

    @Test
    void testCoinsAdd() {
        farm.addCoins(100);
        assertEquals(100, farm.getCoins());
    }

    @Test
    void testKaufeStall() {
        Farm farm = new Farm("TestFarm");
        farm.addCoins(200);

        boolean gekauft = farm.kaufeStall();
        assertTrue(gekauft);

        int anzahl = 0;
        farm.getStaelle().toFirst();
        while (farm.getStaelle().hasAccess()) {
            anzahl++;
            farm.getStaelle().next();
        }

        assertEquals(2, anzahl); // Startstall + gekaufter Stall
    }

    @Test
    void testNaechsterStallPreis() {
        assertEquals(100, farm.getNaechsterStallPreis());
    }
}
