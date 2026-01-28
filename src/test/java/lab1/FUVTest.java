package lab1;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FUVTest {

    @BeforeEach
    void setUp() {
        // Reset parameters before every test to ensure clean state
        Decide.PARAMETERS = new Decide.ParametersT();
        Decide.PUM = new boolean[15][15];
        Decide.PUV = new boolean[15];
        Decide.FUV = new boolean[15];
    }

    @Test
    @DisplayName("FUV[i] should be true if PUV[i] is false")
    void testFUV_PUVfalse() {
        // Test using PUV[0] = false (meaning LIC 0 is ignored and FUV[0] should be true)
        Decide.PUV[0] = false;

        Decide.computeFUV();

        assertTrue(Decide.FUV[0], "FUV[0] should always be true when PUV[0] is false");
    }

    @Test
    @DisplayName("If PUV[i] is true, any false value in PUM row i makes FUV[i] false")
    void testFUV_PUVtrue_PUMfalse() {
        // Scenario: we care about LIC 2
        Decide.PUV[2] = true;
        // Set row 2 to mostly true, but one condition fails (e.g. fifth one)
        for (int i = 0; i < 15; i++) {
            if (i == 5) { continue; }  // Boolean arrays false by default
            Decide.PUM[2][i] = true;
        }
        Decide.computeFUV();

        assertFalse(Decide.FUV[2], "FUV[2] should be false if any element in PUM row 2 is false");
    }

    @Test
    @DisplayName("If PUV[i] is true and each value in PUM row i is true, FUV[i] is true")
    void testFUV_PUVtrue_PUMtrue() {
        // Scenario: we care about LIC 2
        Decide.PUV[2] = true;
        // Set row 2 to all true
        for (int i = 0; i < 15; i++) {
            Decide.PUM[2][i] = true;
        }
        Decide.computeFUV();

        assertTrue(Decide.FUV[2], "FUV[2] should be true if each element in PUM row 2 is true");
    }
}
