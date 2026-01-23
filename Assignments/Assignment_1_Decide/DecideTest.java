package Assignment_1_Decide;

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

    // LIC 2 TEST

    @Test
    @DisplayName("LIC 2 should be true when angle (90 = pi/2) is smaller than PI - EPSILON")
    void testLic2Positive() {
        // Setting up new test parameters
        Decide.PARAMETERS.EPSILON = 0.0;
        Decide.NUMPOINTS = 3;
        Decide.X = new double[]{0.0, 0.0, 1.0}; 
        Decide.Y = new double[]{1.0, 0.0, 0.0};   

        assertTrue(Decide.lic2(), "Expected LIC2 to be true for 90 degree angle");
    }
    @Test
    @DisplayName("LIC 2 should be false for a straight line (angle is 180 = pi)")
    void testLic2Negative() {
        // Setting up new test parameters
        Decide.PARAMETERS.EPSILON = 0.1;
        Decide.NUMPOINTS = 3;
        Decide.X = new double[]{0.0, 1.0, 2.0}; 
        Decide.Y = new double[]{0.0, 0.0, 0.0};

        assertFalse(Decide.lic2(), "Expected LIC2 to be false for a straight line");
    }
}