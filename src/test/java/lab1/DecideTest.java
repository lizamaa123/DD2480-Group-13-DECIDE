package lab1;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DecideTest {
    @BeforeEach                                         
    void setUp() {
        // Reset parameters before every test to ensure clean state
        Decide.PARAMETERS = new Decide.ParametersT();
    }

    // LIC 3 TEST

    @Test
    @DisplayName("LIC 3 should be true when area is bigger than AREA1")
    void testLic3Positive() {
        // Setting up new test parameters
        Decide.PARAMETERS.AREA1 = 0.2;
        Decide.NUMPOINTS = 3;
        Decide.X = new double[]{0.0, 0.0, 1.0}; 
        Decide.Y = new double[]{1.0, 0.0, 0.0};   

        assertTrue(Decide.lic3(), "Expected LIC3 to be true for area > AREA1");
    }
    @Test
    @DisplayName("LIC 3 should be false when area is smaller than AREA1")
    void testLic3Negative() {
        // Setting up new test parameters
        Decide.PARAMETERS.AREA1 = 5.0;
        Decide.NUMPOINTS = 3;
        Decide.X = new double[]{0.0, 2.0, 1.0}; 
        Decide.Y = new double[]{2.0, 0.0, 0.0};

        assertFalse(Decide.lic3(), "Expected LIC3 to be false for area < AREA1");
    }

    // LIC 5 TEST

    @Test
    public void testLic5Positive() {
        // Requirement: moving backwards on x axis
        Decide.NUMPOINTS = 3;
        Decide.X[0] = 1.0;
        Decide.X[1] = 5.0;
        Decide.X[2] = 4.0; // Moved backwards compared to X[1] -> true
        Decide.Y[0] = 0; Decide.Y[1] = 0; Decide.Y[2] = 0;  // Y values don't matter

        assertTrue(Decide.lic5(), "LIC 5 should be true if x decreases");
    }

    @Test
    public void testLic5Negative() {
        Decide.NUMPOINTS = 3;
        Decide.X[0] = 1.0;
        Decide.X[1] = 2.0;
        Decide.X[2] = 2.0; // 2 - 2 = 0 (not < 0)
        Decide.Y[0] = 0; Decide.Y[1] = 0; Decide.Y[2] = 0;

        assertFalse(Decide.lic5(), "LIC 5 should be false if x is not decreasing");
    }
}
