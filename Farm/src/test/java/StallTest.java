import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StallTest {

    private Stall stall;
    private Einhorn e1;
    private Einhorn e2;

    @BeforeEach
    void setup() {
        stall = new Stall(1);
        e1 = new Einhorn(Attribute.FEUER);
        e2 = new Einhorn(Attribute.WASSER);
    }

    @Test
    void testEinfuegenUndAnzahl() {
        assertTrue(stall.einfuegen(e1));
        assertEquals(1, stall.getAnzahl());
    }

    @Test
    void testIstVoll() {
        for (int i = 0; i < 10; i++) {
            stall.einfuegen(new Einhorn(Attribute.FEUER));
        }
        assertTrue(stall.istVoll());
    }

    @Test
    void testEntferne() {
        stall.einfuegen(e1);
        stall.einfuegen(e2);
        assertTrue(stall.entferne(e1));
        assertEquals(1, stall.getAnzahl());
        assertFalse(stall.entferne(new Einhorn(Attribute.ERDE))); // Nicht vorhanden
    }

}
