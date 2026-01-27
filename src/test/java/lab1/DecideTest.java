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

    // LIC 0 TEST

    @Test
    @DisplayName("LIC 0 should be true when distance is greater than LENGTH1")
    void testLic0Positive() {
        // Setting up new test parameters
        Decide.PARAMETERS.LENGTH1 = 10.0;
        Decide.NUMPOINTS = 3;
        Decide.X = new double[]{0.0, 11.0, 20.0}; 
        Decide.Y = new double[]{0.0, 0.0, 0.0};   

        assertTrue(Decide.lic0(), "Expected LIC0 to be true when distance > LENGTH1");
    }

    @Test
    @DisplayName("LIC 0 should be false when distance is smaller than LENGTH1")
    void testLic0Negative() {
        // Setting up new test parameters
        Decide.PARAMETERS.LENGTH1 = 10.0;
        Decide.NUMPOINTS = 3;
        Decide.X = new double[]{0.0, 5.0, 9.0}; 
        Decide.Y = new double[]{0.0, 0.0, 0.0};

        assertFalse(Decide.lic0(), "Expected LIC0 to be false when all distances <= LENGTH1");
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

    @Test
    @DisplayName("LIC 10 should be positive when area is smaller than AREA1 with correct spacing")
    void testLic10Positive() {
        Decide.PARAMETERS.AREA1 = 5;
        Decide.PARAMETERS.E_PTS = 1;
        Decide.PARAMETERS.F_PTS = 1;
        Decide.NUMPOINTS = 5;

        // Triangle coords: (0,0), (4,0), (0,4) -> Area = 8.0
        Decide.X = new double[]{0.0, 99.0, 4.0, 99.0, 0.0};
        Decide.Y = new double[]{0.0, 99.0, 0.0, 99.0, 4.0};

        assertTrue(Decide.lic10(), "Expected LIC10 to be true (Area 8.0 > AREA1 5.0)");
    }

    @Test
    @DisplayName("LIC 10 Negative: Triangle area < AREA1")
    void testLic10Negative() {
        Decide.PARAMETERS.AREA1 = 10.0;
        Decide.PARAMETERS.E_PTS = 1;
        Decide.PARAMETERS.F_PTS = 1;
        Decide.NUMPOINTS = 5;

        // Same triangle (Area = 8.0), but requirement is AREA1 = 10.0
        Decide.X = new double[]{0.0, 99.0, 4.0, 99.0, 0.0};
        Decide.Y = new double[]{0.0, 99.0, 0.0, 99.0, 4.0};

        assertFalse(Decide.lic10(), "Expected LIC10 to be false (Area 8.0 < AREA1 10.0)");
    }

    @Test
    @DisplayName("LIC 10 Negative: Not enough data points")
    void testLic10InsufficientPoints() {
        Decide.NUMPOINTS = 4;
        assertFalse(Decide.lic10(), "Expected LIC10 to be false when NUMPOINTS < 5");
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
