import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;

class GamePanelTest {
    @Test
    void testFarmStartzustand() {
        Farm farm = new Farm("TestFarm");

        int stallCount = 0;
        farm.getStaelle().toFirst();
        while (farm.getStaelle().hasAccess()) {
            stallCount++;
            farm.getStaelle().next();
        }

        assertEquals(1, stallCount); // Startstall
    }


}