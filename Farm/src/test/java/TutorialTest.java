import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TutorialTest {

    @Test
    void testNaechsterSchritt() {
        Tutorial t = new Tutorial();
        String s1 = t.naechsterSchritt();
        assertEquals("Willkommen auf deiner Farm!", s1);
        assertTrue(t.hatMehrSchritte());
    }
}
