package lab1;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PUMTest {

    @BeforeEach
    void setUp() {
        // Reset parameters before every test to ensure clean state
        Decide.PARAMETERS = new Decide.ParametersT();
        Decide.LCM = new Decide.Connectors[15][15];
        Decide.PUM = new boolean[15][15];
        Decide.CMV = new boolean[15];
    }

    @Test
    @DisplayName("PUM should handle ANDD logic correctly")
    void testPumAndd() {
        // Case: LIC 0 is TRUE, LIC 1 is FALSE.
        Decide.CMV[0] = true;
        Decide.CMV[1] = false;
        Decide.LCM[0][1] = Decide.Connectors.ANDD;

        Decide.calculatePUM();

        assertFalse(Decide.PUM[0][1], "true AND false should result in false");
    }

    @Test
    @DisplayName("PUM should handle ORR logic correctly")
    void testPumOrr() {
        // Case: LIC 0 is TRUE, LIC 1 is FALSE.
        Decide.CMV[0] = true;
        Decide.CMV[1] = false;
        Decide.LCM[0][1] = Decide.Connectors.ORR;

        Decide.calculatePUM();

        assertTrue(Decide.PUM[0][1], "true OR false should result in true");
    }

    @Test
    @DisplayName("PUM should handle NOTUSED logic correctly")
    void testPumNotUsed() {
        // Case: Both LICs are FALSE.
        Decide.CMV[0] = false;
        Decide.CMV[1] = false;
        Decide.LCM[0][1] = Decide.Connectors.NOTUSED;

        Decide.calculatePUM();

        assertTrue(Decide.PUM[0][1], "NOTUSED should always result in true");
    }
    
}
