package lab1;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

class DecideTest {

    @BeforeEach
    public void setUp() {
        Decide.PARAMETERS = new Decide.ParametersT();
        Decide.X = new double[100];
        Decide.Y = new double[100];
        Decide.LCM = new Decide.Connectors[15][15];
        Decide.PUV = new boolean[15];
        Decide.CMV = new boolean[15];
        Decide.PUM = new boolean[15][15];
        Decide.FUV = new boolean[15];
        
        // Setting default values to paramaters 
        Decide.PARAMETERS.N_PTS = 3; 
        Decide.PARAMETERS.Q_PTS = 3;
        Decide.PARAMETERS.K_PTS = 1;
        Decide.PARAMETERS.A_PTS = 1;
        Decide.PARAMETERS.B_PTS = 1;
        Decide.PARAMETERS.C_PTS = 1;
        Decide.PARAMETERS.D_PTS = 1;
        Decide.PARAMETERS.E_PTS = 1;
        Decide.PARAMETERS.F_PTS = 1;
        Decide.PARAMETERS.G_PTS = 1;

        // Initializeing some simulated data
        Decide.NUMPOINTS = 10;
        Arrays.fill(Decide.X, 0.0);
        Arrays.fill(Decide.Y, 0.0);

        // Default LCM to NOTUSED
        for (int i = 0; i < 15; i++) {
            Arrays.fill(Decide.LCM[i], Decide.Connectors.NOTUSED);
        }
    }

    @Test
    @DisplayName("LAUNCH should be true (=YES)")
    void testDecidePositive() {
        // Setting all PUV to false -> FUV beomes true -> LAUNCH = true
        Arrays.fill(Decide.PUV, false);

        Decide.DECIDE();

        assertTrue(Decide.LAUNCH, "Expected LAUNCH to be true (YES)");
    }

    @Test
    @DisplayName("LAUNCH should be false (=NO)")
    void testDecideNegative() {
        // SCENARIO: Force a failure on LIC 0.
        
        // Set LIC 0 as true
        Decide.PUV[0] = true;

        // Connect LIC 0 to LIC 1 using ANDD. This means PUM[0][1] will be FALSE if CMV[0] is false (when LIC0 is false).
        Decide.LCM[0][1] = Decide.Connectors.ANDD; 
        Decide.LCM[1][0] = Decide.Connectors.ANDD; 

        // Points are all at origin so the distance is 0. 5 > 0, so not greater than LENGTH1 -> LAUNCH = false 
        Arrays.fill(Decide.X, 0.0);
        Arrays.fill(Decide.Y, 0.0);
        Decide.PARAMETERS.LENGTH1 = 5.0;

        Decide.DECIDE();

        assertFalse(Decide.LAUNCH, "Expected LAUNCH to be false (NO)");
    }
}