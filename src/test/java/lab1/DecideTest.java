package lab1;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DecideTest {

    @BeforeEach
    void setUp() {
        // Reset parameters before every test to ensure clean state
        Decide.PARAMETERS = new Decide.ParametersT();
    }

    // LIC 1 TEST

    @Test
    @DisplayName("LIC 1 should be true when radius is greater than RADIUS1")
    void testLic1Positive() {
        // Setting up new test parameters
        Decide.PARAMETERS.RADIUS1 = 1.0;
        Decide.NUMPOINTS = 3;
        Decide.X = new double[]{0.0, 2.0, 4.0};
        Decide.Y = new double[]{0.0, 0.0, 0.0};

        assertTrue(Decide.lic1(), "Expected LIC1 to be true when radius > RADIUS1");
    }

    @Test
    @DisplayName("LIC 1 should be false when radius is smaller than RADIUS1")
    void testLic1Negative() {
        // Setting up new test parameters
        Decide.PARAMETERS.RADIUS1 = 10.0;
        Decide.NUMPOINTS = 3;
        Decide.X = new double[]{0.0, 2.0, 4.0};
        Decide.Y = new double[]{0.0, 0.0, 0.0};

        assertFalse(Decide.lic1(), "Expected LIC1 to be false when all radius <= RADIUS1");
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

    // LIC 7 TEST

    @Test
    @DisplayName("Lic 7 should be false when NUMPOINTS is less than 3")
    void testLic7Negative() {
        Decide.NUMPOINTS = 2;
        Decide.PARAMETERS.K_PTS = 1;
        Decide.X = new double[]{1.0, 2.0, 3.0};
        Decide.Y = new double[]{1.0, 2.0, 3.0};

        assertFalse(Decide.lic3(), "Expected Lic 7 to be false when NUMPOINTS < 3");
    }

    @Test
    @DisplayName("Lic 7 should be true when distance is greater than LENGTH1")
    void testLic7Positive() {
        Decide.NUMPOINTS = 3;
        Decide.PARAMETERS.K_PTS = 1;
        Decide.PARAMETERS.LENGTH1 = 1;
        Decide.X = new double[]{1.0, 2.0, 3.0};
        Decide.Y = new double[]{1.0, 2.0, 3.0};

        assertTrue(Decide.lic7(), "Expected Lic 7 to be true when d > LENGTH1");
    }


}
