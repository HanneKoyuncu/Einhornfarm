import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AttributeTest {

    @Test
    void testSeltenheitWert() {
        assertEquals(10, Seltenheit.GEWÖHNLICH.getWert());
        assertEquals(100, Seltenheit.LEGENDÄR.getWert());
    }

    @Test
    void testKombinationBekannt() {
        Attribute result = Attribute.kombiniere(Attribute.FEUER, Attribute.WASSER);
        assertEquals(Attribute.DAMPF, result);
    }

    @Test
    void testKombinationGleich() {
        assertEquals(Attribute.FEUER, Attribute.kombiniere(Attribute.FEUER, Attribute.FEUER));
    }

    @Test
    void testKombinationZufall() {
        // Testen, dass Kombination von nicht definierten Attributen zufällig a oder b liefert
        Attribute a = Attribute.LICHT;
        Attribute b = Attribute.ERDE;
        Attribute result = Attribute.kombiniere(a, b);
        assertTrue(result == a || result == b);
    }
}
