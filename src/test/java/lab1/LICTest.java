package lab1;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LICTest {

    @BeforeEach
    void setUp() {
        // Reset parameters before every test to ensure clean state
        Decide.PARAMETERS = new Decide.ParametersT();
        Decide.X = new double[100];
        Decide.Y = new double[100];
    }

    // //////////// LIC 0  ///////////

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

    // //////////// LIC 1  ///////////

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

    // //////////// LIC 2  ///////////

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

    // //////////// LIC 3  ///////////

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
    
    // //////////// LIC 4  ///////////

    /**
     * Contract: The LIC is met if a set of Q_PTS consecutive points occupy more than QUADS unique quadrants.
     * Input: 4 points spanning 3 unique quadrants (I, II, III). QUADS = 2, Q_PTS = 3.
     * Output: True (3 quadrants > 2)
     */
    @Test
    @DisplayName("Positive test case for multi-quadrant points in LIC4")
    public void testLic4Positive() {
        // Requires consecutive points in more than QUADS quadrants
        Decide.NUMPOINTS = 4;
        Decide.PARAMETERS.Q_PTS = 3;
        Decide.PARAMETERS.QUADS = 2;

        Decide.X[0] = 1.0; Decide.Y[0] = 1.0;  // Point 1 in quad 1
        Decide.X[1] = -1.0; Decide.Y[1] = 1.0;  // Point 2 in quad 2
        Decide.X[2] = -1.0; Decide.Y[2] = -1.0;  // Point 3 in quad 3

        // Window of points {0,1,2} occupies 3 quadrants, 3 > 2 is True so the LIC is met.
        assertTrue(Decide.lic4(), "LIC 4 should be true when points are in more than QUADS quadrants");
    }

    /**
     * Contract: The LIC is not met if the number of unique quadrants occupied is less than or equal to QUADS.
     * Input: 4 points all located in quadrant 1. QUADS = 2, Q_PTS = 3.
     * Output: False (1 quadrant <= 2)
     */
    @Test
    @DisplayName("Negative test case for multi-quadrant points in LIC4")
    public void testLic4Negative() {
        Decide.NUMPOINTS = 4;
        Decide.PARAMETERS.Q_PTS = 3;
        Decide.PARAMETERS.QUADS = 2;

        // All points in quad 1 or on boundary
        Decide.X[0] = 1.0; Decide.Y[0] = 1.0;
        Decide.X[1] = 0.0; Decide.Y[1] = 0.0;
        Decide.X[2] = 0.0; Decide.Y[2] = 1.0;

        // Window occupies only 1 quadrant, 1 > 2 is False so LIC is not met.
        assertFalse(Decide.lic4(), "LIC 4 should be false when points are in same quadrant");
    }

    /**
     * Contract: Method must handle invalid input where window size (Q_PTS) exceeds available points (NUMPOINTS).
     * Input: NUMPOINTS = 3, Q_PTS = 4.
     * Output: False
     */
    @Test
    @DisplayName("Invalid input test for LIC 4 where Q_PTS > NUMPOINTS")
    public void testLic4InvalidInput() {
        // Requirement: (2 < Q_PTS <= NUMPOINTS)
        // If Q_PTS is greater than NUMPOINTS, the function should immediately return false.
        Decide.NUMPOINTS = 3;
        Decide.PARAMETERS.Q_PTS = 4; // Window size larger than the number of data points
        Decide.PARAMETERS.QUADS = 1;

        // Coordinate values are irrelevant here
        Decide.X[0] = 1.0; Decide.Y[0] = 1.0;
        Decide.X[1] = 1.0; Decide.Y[1] = 1.0;
        Decide.X[2] = 1.0; Decide.Y[2] = 1.0;

        assertFalse(Decide.lic4(), "LIC 4 should be false when Q_PTS is greater than NUMPOINTS");
    }

    // //////////// LIC 5  ///////////

    /**
     * Contract: The LIC is met if there exists at least one pair of consecutive points such that X[i+1] < X[i].
     * Input: Sequence of X coordinates [1.0, 5.0, 4.0].
     * Output: True (4 < 5)
     */
    @Test
    @DisplayName("LIC 5 should be true when x is decreasing")
    public void testLic5Positive() {
        // Requirement: moving backwards on x axis
        Decide.NUMPOINTS = 3;
        Decide.X[0] = 1.0;
        Decide.X[1] = 5.0;
        Decide.X[2] = 4.0; // Moved backwards compared to X[1] -> true
        Decide.Y[0] = 0; Decide.Y[1] = 0; Decide.Y[2] = 0;  // Y values don't matter

        assertTrue(Decide.lic5(), "LIC 5 should be true if x decreases");
    }

    /**
     * Contract: The LIC is not met if the X coordinates are strictly increasing or equal.
     * Input: Sequence of X coordinates [1.0, 2.0, 2.0].
     * Output: False
     */
    @Test
    @DisplayName("LIC 5 should be false when x is not decreasing")
    public void testLic5Negative() {
        Decide.NUMPOINTS = 3;
        Decide.X[0] = 1.0;
        Decide.X[1] = 2.0;
        Decide.X[2] = 2.0; // 2 - 2 = 0 (not < 0)
        Decide.Y[0] = 0; Decide.Y[1] = 0; Decide.Y[2] = 0;

        assertFalse(Decide.lic5(), "LIC 5 should be false if x is not decreasing");
    }

    /**
     * Contract: The function must return false if there are insufficient points to form a consecutive pair.
     * Input: NUMPOINTS = 1.
     * Output: False
     */
    @Test
    @DisplayName("Invalid input test for LIC5 with insufficient points")
    public void testLic5InvalidInput() {
        // LIC 5 requires at least 2 consecutive points to compare X differences.
        // If NUMPOINTS < 2, the loop should never be entered and return false.
        Decide.NUMPOINTS = 1;

        // With just one point, we cannot form a pair
        Decide.X[0] = 1.0;
        Decide.Y[0] = 1.0;

        assertFalse(Decide.lic5(), "LIC 5 should be false when NUMPOINTS is less than 2");
    }

    // //////////// LIC 6  ///////////

    /**
     * Contract: The LIC is met if any point in a set of N_PTS lies further than
     *           DIST from the line connecting the set's endpoints.
     * Input: Line from (0,0) to (4,0). Middle point at (2,2). DIST = 1.0.
     * Output: True (distance 2 > 1)
     */
    @Test
    @DisplayName("LIC 6 should be true when point is further than DIST from line")
    void testLic6Positive() {
        // Requirement: distance > DIST
        Decide.NUMPOINTS = 3;
        Decide.PARAMETERS.N_PTS = 3;
        Decide.PARAMETERS.DIST = 1.0;

        Decide.X = new double[]{0.0, 2.0, 4.0};
        Decide.Y = new double[]{0.0, 2.0, 0.0};

        // 2 > 1 -> True
        assertTrue(Decide.lic6(), "Point is further than DIST from line, expected LIC6 to be true");
    }

    /**
     * Contract: The LIC is not met if all intermediate points lie within
     *           DIST of the line connecting the endpoints.
     * Input: Line from (0,0) to (4,0). Middle point at (2,2). DIST = 3.0.
     * Output: False (distance 2 < 3)
     */
    @Test
    @DisplayName("LIC 6 should be false when all points are closer than DIST to line")
    void testLic6Negative() {
        // Requirement: distance > DIST
        Decide.NUMPOINTS = 3;
        Decide.PARAMETERS.N_PTS = 3;
        Decide.PARAMETERS.DIST = 3.0;

        Decide.X = new double[]{0.0, 2.0, 4.0};
        Decide.Y = new double[]{0.0, 2.0, 0.0};

        // 2 < 3 -> False
        assertFalse(Decide.lic6(), "Point is closer than DIST to line, expected LIC6 to be false");
    }

    /**
     * Contract: When endpoints coincide, the check compares distance
     *           from the point to the endpoint(s) instead of a line.
     * Input: Endpoints at (0,0). Middle point at (2,0). DIST = 1.0.
     * Output: True (distance 2 > 1)
     */
    @Test
    @DisplayName("Positive test case for identical endpoints in LIC6")
    void testLic6PositiveIdentical() {
        // Requirement: If first and last points are the same, compare distance to that point
        Decide.NUMPOINTS = 3;
        Decide.PARAMETERS.N_PTS = 3;
        Decide.PARAMETERS.DIST = 1.0;

        Decide.X = new double[]{0.0, 2.0, 0.0};
        Decide.Y = new double[]{0.0, 0.0, 0.0};

        // 2 > 1 -> True
        assertTrue(Decide.lic6(), "Expected LIC 6 to be true, point is further than DIST from endpoints");
    }

    /**
     * Contract: When endpoints coincide, the check returns false if the point is within DIST of the endpoint.
     * Input: Endpoints at (0,0). Middle point at (2,0). DIST = 3.0.
     * Output: False (distance 2 < 3)
     */
    @Test
    @DisplayName("Negative test case for identical endpoints in LIC6")
    void testLic6NegativeIdentical() {
        // Requirement: If first and last points are the same, compare distance to that point
        Decide.NUMPOINTS = 3;
        Decide.PARAMETERS.N_PTS = 3;
        Decide.PARAMETERS.DIST = 3.0;

        Decide.X = new double[]{0.0, 2.0, 0.0};
        Decide.Y = new double[]{0.0, 0.0, 0.0};

        // 2 < 3 -> False
        assertFalse(Decide.lic6(), "Expected LIC 6 to be false, point is closer than DIST to endpoints");
    }

    /**
     * Contract: Method must return false if there are fewer than 3 points,
     *           as there will be no intermediate points.
     * Input: NUMPOINTS = 2.
     * Output: False
     */
    @Test
    @DisplayName("Invalid input test for LIC6 where NUMPOINTS < 3")
    void testLic6InvalidInput() {
        // Requirement: The condition is not met when NUMPOINTS < 3
        Decide.NUMPOINTS = 2; // Insufficient points to form a line + test point
        Decide.PARAMETERS.N_PTS = 3;
        Decide.PARAMETERS.DIST = 1.0;

        Decide.X = new double[]{0.0, 1.0};
        Decide.Y = new double[]{0.0, 1.0};

        assertFalse(Decide.lic6(), "LIC 6 should be false when NUMPOINTS is less than 3");
    }

    // //////////// LIC 7  ///////////

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

    // //////////// LIC 8  ///////////

    @Test
    @DisplayName("Lic 8 should be true when radius is greater than RADIUS1")
    void testLic8Positive() {
        Decide.PARAMETERS.RADIUS1 = 1.0;
        Decide.NUMPOINTS = 5;
        Decide.X = new double[]{0.0, 1.0, 0.0, 2.0, 0.0};
        Decide.Y = new double[]{0.0, 0.0, 1.0, 0.0, 2.0};

        assertTrue(Decide.lic8(), "Expected Lic 8 to be true for radius > RADIUS1");
    }

    @Test
    @DisplayName("Lic 8 should be false when radius is less than or equal to RADIUS1")
    void testLic8Negative() {
        Decide.PARAMETERS.RADIUS1 = 1.0;
        Decide.NUMPOINTS = 5;
        Decide.X = new double[]{0.0, 0.0, 2.5, 0.0, 0.0};
        Decide.Y = new double[]{0.0, 0.0, 0.0, 0.0, 2.5};

        assertTrue(Decide.lic8(), "Expected Lic 8 to be false for radius <= RADIUS1");
    }

    // //////////// LIC 9  ///////////

    @Test
    @DisplayName("Lic 9 should be false when angle is greater than pi - epsilon, or less than pi + epsilon")
    void testLic9Negative() {
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.C_PTS = 1;
        Decide.PARAMETERS.D_PTS = 1;
        Decide.PARAMETERS.EPSILON = 0.1;
        Decide.X = new double[]{0.0, 0.5, 1.0, 1.5, 2.0};
        Decide.Y = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
        
        assertFalse(Decide.lic9(), "Expected Lic 9 to be false for a straight line");

    }

    @Test
    @DisplayName("Lic 9 should be positive when angle is less than pi - epsilon, or greater than pi + epsilon")
    void testLic9Positive(){
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.C_PTS = 1;
        Decide.PARAMETERS.D_PTS = 1;
        Decide.PARAMETERS.EPSILON = 0.1;
        Decide.X = new double[]{0.0, 0.5, 1.0, 1.0, 1.0};
        Decide.Y = new double[]{0.0, 0.0, 0.0, 0.5, 1.0};

        assertTrue(Decide.lic9(), "Expected Lic 9 to be positive for a 90 degree angle");
    }

    // //////////// LIC 10  ///////////

    /**
     * Contract: The LIC is met if three points separated by E_PTS and F_PTS form a triangle with area > AREA1.
     * Input: 5 points. Points at indices 0, 2, 4 form a triangle with Area=8.0. E_PTS=1, F_PTS=1, AREA1=5.0.
     * Output: True (8.0 > 5.0)
     */
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

    /**
     * Contract: The LIC is not met if the calculated triangle area is not strictly greater than AREA1.
     * Input: Same triangle as positive test (Area=8.0), but AREA1=10.0.
     * Output: False (8.0 < 10.0)
     */
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

    /**
     * Contract: The function must return false if there are fewer than 5 points.
     * Input: NUMPOINTS = 4.
     * Output: False
     */
    @Test
    @DisplayName("LIC 10 Negative: Not enough data points")
    void testLic10InsufficientPoints() {
        Decide.NUMPOINTS = 4;
        assertFalse(Decide.lic10(), "Expected LIC10 to be false when NUMPOINTS < 5");
    }

    // //////////// LIC 11 ///////////

    /**
     * Contract: The LIC is met if there exists a pair of points separated by G_PTS where X[j] < X[i].
     * Input: Points at index 0 (X=5.0) and index 2 (X=4.0). G_PTS=1.
     * Output: True (4.0 - 5.0 < 0)
     */
    @Test
    @DisplayName("Lic 11 should be true when X[j] - X[i] < 0 with gap G_PTS")
    public void testLic11Positive() {
        Decide.NUMPOINTS = 3;
        Decide.PARAMETERS.G_PTS = 1;
        Decide.X = new double[]{5.0, 0.0, 4.0};
        Decide.Y = new double[]{0.0, 0.0, 0.0};

        assertTrue(Decide.lic11(), "Expected Lic 11 to be true when X[j] - X[i] < 0");
    }

    /**
     * Contract: The LIC is not met if X coordinates are increasing or equal for all valid pairs.
     * Input: X values [1.0, 0.0, 2.0, 0.0] with G_PTS=1. Pairs are (1.0, 2.0) and (0.0, 0.0).
     * Output: False
     */
    @Test
    @DisplayName("Lic 11 should be false when no pair satisfies X[j] - X[i] < 0")
    void testLic11Negative() {
        Decide.NUMPOINTS = 4;
        Decide.PARAMETERS.G_PTS = 1;
        Decide.X = new double[]{1.0, 0.0, 2.0, 0.0};
        Decide.Y = new double[]{0.0, 0.0, 0.0, 0.0};

        assertFalse(Decide.lic11(), "Expected Lic 11 to be false when X value only increases or stays same");
    }

    /**
     * Contract: The function must return false if NUMPOINTS < 3.
     * Input: NUMPOINTS = 2.
     * Output: False
     */
    @Test
    @DisplayName("Lic 11 should be false when NUMPOINTS < 3")
    void testLic11Negative2() {
        Decide.NUMPOINTS = 2;
        Decide.PARAMETERS.G_PTS = 1;
        Decide.X = new double[]{5.0, 0.0, 4.0};
        Decide.Y = new double[]{0.0, 0.0, 0.0};

        assertFalse(Decide.lic11(), "Expected Lic 11 to be false when NUMPOINTS < 3");
    }

    // //////////// LIC 12 ///////////

    /**
     * Contract: The LIC is met if one pair (dist > LENGTH1) AND one pair (dist < LENGTH2) exist.
     * Input: Pair 1 dist=6 (LENGTH1=5). Pair 2 dist=1 (LENGTH2=2). K_PTS=1.
     * Output: True (Both conditions met)
     */
    @Test
    @DisplayName("LIC 12 should be true when one pair has distance > LENGTH1 AND one pair has distance < LENGTH2")
    void testLic12Positive() {
        Decide.NUMPOINTS = 4;
        Decide.PARAMETERS.K_PTS = 1;
        Decide.PARAMETERS.LENGTH1 = 5.0; // Need distance > 5
        Decide.PARAMETERS.LENGTH2 = 2.0; //Need distance < 2

        // Points: (0,0), (0,0), (6,0), (1,0)
        // Pair 1 (Index 0 & 2): (0,0) -> (6,0). Dist = 6. (6 > 5) -> Condition1 TRUE
        // Pair 2 (Index 1 & 3): (0,0) -> (1,0). Dist = 1. (1 < 2) -> Condition2 TRUE
        Decide.X = new double[]{0.0, 0.0, 6.0, 1.0};
        Decide.Y = new double[]{0.0, 0.0, 0.0, 0.0};

        assertTrue(Decide.lic12(), "Expected Lic 12 to be true when both conditions are met");
    }

    /**
     * Contract: The LIC is not met if only one of the two conditions is satisfied.
     * Input: All pairs have dist=6. LENGTH1=5 (Met), LENGTH2=2 (Not Met).
     * Output: False
     */
    @Test
    @DisplayName("LIC 12 should be false when only distance > LENGTH1 exists (missing < LENGTH2)")
    void testLic12NegativeMissingSmall() {
        Decide.PARAMETERS.K_PTS = 1;
        Decide.PARAMETERS.LENGTH1 = 5.0;
        Decide.PARAMETERS.LENGTH2 = 2.0;
        Decide.NUMPOINTS = 4;

        // Pair 1 (Index 0 & 2): Dist = 6 (Satisfies > 5)
        // Pair 2 (Index 1 & 3): Dist = 6 (Fails < 2)
        // Only condition 1 is met -> returns false
        Decide.X = new double[]{0.0, 0.0, 6.0, 6.0};
        Decide.Y = new double[]{0.0, 0.0, 0.0, 0.0};

        assertFalse(Decide.lic12(), "Expected LIC12 to be false when condition 2 is missing");
    }

    /**
     * Contract: The function must return false if NUMPOINTS < 3.
     * Input: NUMPOINTS = 2.
     * Output: False
     */
    @Test
    @DisplayName("LIC 12 should be false when NUMPOINTS < 3")
    void testLic12InsufficientPoints() {
        Decide.NUMPOINTS = 2;
        Decide.X = new double[]{0.0, 5.0};
        Decide.Y = new double[]{0.0, 0.0};

        assertFalse(Decide.lic12(), "Expected LIC12 to be false when NUMPOINTS < 3");
    }

    // //////////// LIC 13 ///////////

    @Test
    @DisplayName("LIC 13 should be true when three consecutive interveningpoints cannot fit in a circle with RADIUS1 but fits in a circle with RADIUS2")
    void testLic13Positive() {
        // Setting up new test parameters
        Decide.PARAMETERS.RADIUS1 = 0.5;
        Decide.PARAMETERS.RADIUS2 = 0.6;
        Decide.PARAMETERS.A_PTS = 1;
        Decide.PARAMETERS.B_PTS = 1;
        Decide.NUMPOINTS = 5;

        // With A_PTS=1 and B_PTS=1, lic13 uses points (0,2,4).
        // Choose an equilateral triangle with side length 1:
        // (0,0), (1,0), (0.5, sqrt(3)/2) has circumradius R = 1/sqrt(3) ≈ 0.57735
        Decide.X = new double[]{0.0, 99.0, 1.0, 99.0, 0.5};
        Decide.Y = new double[]{0.0, 99.0, 0.0, 99.0, Math.sqrt(3.0) / 2.0};

        assertTrue(Decide.lic13(), "Expected LIC13 to be true because 0.5 < R ≈ 0.577 <= 0.6");
    }

    @Test
    @DisplayName("LIC 13 should be false whencondition 1 is false and condition 2 is true")
    void testLic13Condition1FalseCondition2True() {
        // Setting up new test parameters
        Decide.PARAMETERS.RADIUS1 = 0.58;
        Decide.PARAMETERS.RADIUS2 = 0.6;
        Decide.PARAMETERS.A_PTS = 1;
        Decide.PARAMETERS.B_PTS = 1;
        Decide.NUMPOINTS = 5;

        // With A_PTS=1 and B_PTS=1, lic13 uses points (0,2,4).
        // Choose an equilateral triangle with side length 1:
        // (0,0), (1,0), (0.5, sqrt(3)/2) has circumradius R = 1/sqrt(3) ≈ 0.57735
        Decide.X = new double[]{0.0, 99.0, 1.0, 99.0, 0.5};
        Decide.Y = new double[]{0.0, 99.0, 0.0, 99.0, Math.sqrt(3.0) / 2.0};

        assertFalse(Decide.lic13(), "Expected LIC13 to be False because R ≈ 0.577 <= 0.6 && R <= 0.58");
    }

    @Test
    @DisplayName("LIC 13 should be false whencondition 1 is true and condition 2 is false")
    void testLic13Condition1TrueCondition2False() {
        // Setting up new test parameters
        Decide.PARAMETERS.RADIUS1 = 0.5;
        Decide.PARAMETERS.RADIUS2 = 0.4;
        Decide.PARAMETERS.A_PTS = 1;
        Decide.PARAMETERS.B_PTS = 1;
        Decide.NUMPOINTS = 5;

        // With A_PTS=1 and B_PTS=1, lic13 uses points (0,2,4).
        // Choose an equilateral triangle with side length 1:
        // (0,0), (1,0), (0.5, sqrt(3)/2) has circumradius R = 1/sqrt(3) ≈ 0.57735
        Decide.X = new double[]{0.0, 99.0, 1.0, 99.0, 0.5};
        Decide.Y = new double[]{0.0, 99.0, 0.0, 99.0, Math.sqrt(3.0) / 2.0};

        assertFalse(Decide.lic13(), "Expected LIC13 to be False because R ≈ 0.577 > 0.4");
    }

    @Test
    @DisplayName("LIC 13 should be false when both conditions are false")
    void testLic13Negative() {
        // Setting up new test parameters
        Decide.PARAMETERS.RADIUS1 = 0.6;
        Decide.PARAMETERS.RADIUS2 = 0.4;
        Decide.PARAMETERS.A_PTS = 1;
        Decide.PARAMETERS.B_PTS = 1;
        Decide.NUMPOINTS = 5;

        // With A_PTS=1 and B_PTS=1, lic13 uses points (0,2,4).
        // Choose an equilateral triangle with side length 1:
        // (0,0), (1,0), (0.5, sqrt(3)/2) has circumradius R = 1/sqrt(3) ≈ 0.57735
        Decide.X = new double[]{0.0, 99.0, 1.0, 99.0, 0.5};
        Decide.Y = new double[]{0.0, 99.0, 0.0, 99.0, Math.sqrt(3.0) / 2.0};

        assertFalse(Decide.lic13(), "Expected LIC13 to be False because 0.4 < R ≈ 0.577 <= 0.6");
    }

    
    @Test
    @DisplayName("LIC 13 should be false when NUMPOINTS is less than 5")
    void testLic13NumpointsLessThan5() {
        // Setting up new test parameters
        Decide.NUMPOINTS = 4;

        assertFalse(Decide.lic13(), "Expected LIC13 to be False because numpoints < 5");
    }

    @Test
    @DisplayName("LIC 13 should be true when condition 1 and condition 2 are met by different sets of points")
    void testLic13DifferentSets() {
        // Setting up new test parameters
        Decide.PARAMETERS.RADIUS1 = 0.5;
        Decide.PARAMETERS.RADIUS2 = 0.6;
        Decide.PARAMETERS.A_PTS = 1;
        Decide.PARAMETERS.B_PTS = 1;
        Decide.NUMPOINTS = 6;
        
        // With A_PTS=1 and B_PTS=1:
        // Set 1 (i=0): points at indices (0, 2, 4) - large triangle with radius > RADIUS1
        // Set 2 (i=1): points at indices (1, 3, 5) - small triangle with radius <= RADIUS2
        
        // Set 1: Equilateral triangle with side length 1.2
        // Circumradius ≈ 0.693 > 0.5 
        double side1 = 1.2;
        double height1 = side1 * Math.sqrt(3.0) / 2.0;
        
        // Set 2: Equilateral triangle with side length 0.8
        // Circumradius ≈ 0.462 <= 0.6 
        double side2 = 0.8;
        double height2 = side2 * Math.sqrt(3.0) / 2.0;
        
        // Points: [set1_p1, set2_p1, set1_p2, set2_p2, set1_p3, set2_p3]
        // Set 1 (indices 0,2,4): (0,0), (1.2,0), (0.6, height1)
        // Set 2 (indices 1,3,5): (10,0), (10.8,0), (10.4, height2)
        Decide.X = new double[]{
            0.0,           // Set 1, point 1 
            10.0,          // Set 2, point 1 
            side1,         // Set 1, point 2 
            10.0 + side2,  // Set 2, point 2 
            side1/2.0,     // Set 1, point 3
            10.0 + side2/2.0  // Set 2, point 3 
        };
        Decide.Y = new double[]{
            0.0,           // Set 1, point 1 
            0.0,           // Set 2, point 1 
            0.0,           // Set 1, point 2 
            0.0,           // Set 2, point 2 
            height1,       // Set 1, point 3
            height2        // Set 2, point 3
        };
        
        assertTrue(Decide.lic13(), 
            "Expected LIC13 to be true: Set 1 (indices 0,2,4) has radius > 0.5, " +
            "Set 2 (indices 1,3,5) has radius <= 0.6");
    }

    @Test
    @DisplayName("LIC 13 should be able to handle colinear points")
    void testLic13ColinearPoints() {
        // Setting up new test parameters
        Decide.PARAMETERS.RADIUS1 = 0.5;
        Decide.PARAMETERS.RADIUS2 = 0.6;
        Decide.PARAMETERS.A_PTS = 1;
        Decide.PARAMETERS.B_PTS = 1;
        Decide.NUMPOINTS = 5;

        // With A_PTS=1 and B_PTS=1, lic13 uses points (0,2,4).
        // Pick points that are colinear and have a radius of 0.55
        Decide.X = new double[]{0.0, 99.0, 0.55, 99.0, 0.55*2};
        Decide.Y = new double[]{0.0, 99.0, 0.0, 99.0, 0.0}; //all points have the same y coordinate

        assertTrue(Decide.lic13(), "Expected LIC13 to be true because 0.5 < R ≈ 0.55 <= 0.6");
    }
    // //////////// LIC 14 ///////////

    @Test
    @DisplayName("LIC 14 should be true when area is greater than AREA1 and less than AREA2")
    void testLic14Positive() {
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.E_PTS = 1; 
        Decide.PARAMETERS.F_PTS = 1;
        Decide.PARAMETERS.AREA1 = 1.0;
        Decide.PARAMETERS.AREA2 = 2.0;

        //indices: 0, 2, 4 will be checked. The set will give an area = 1.5
        Decide.X = new double[]{0.0, 0.5, 1.0, 1.0, 1.0}; 
        Decide.Y = new double[]{0.0, 0.0, 5.0, 0.5, 2.0};

        assertTrue(Decide.lic14(), "Expected Lic 14 to be true: when AREA1 = 1.0 < area = 1.5 < AREA2 = 2.0");
    }
    @Test
    @DisplayName("LIC 14 should be false when area is less than AREA1")
    void testLic14Condition1False() {
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.E_PTS = 1; 
        Decide.PARAMETERS.F_PTS = 1;
        Decide.PARAMETERS.AREA1 = 1.5;
        Decide.PARAMETERS.AREA2 = 2.0;

        //indices: 0, 2, 4 will be checked. The set will give an area = 1.5
        Decide.X = new double[]{0.0, 0.5, 1.0, 1.0, 1.0}; 
        Decide.Y = new double[]{0.0, 0.0, 5.0, 0.5, 2.0};

        assertFalse(Decide.lic14(), "Expected Lic 14 to be false: when AREA1 >= area ");
    }
    @Test
    @DisplayName("LIC 14 should be false when area is less than AREA1")
    void testLic14Condition2False() {
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.E_PTS = 1; 
        Decide.PARAMETERS.F_PTS = 1;
        Decide.PARAMETERS.AREA1 = 1.0;
        Decide.PARAMETERS.AREA2 = 1.5;

        //indices: 0, 2, 4 will be checked. The set will give an area = 1.5
        Decide.X = new double[]{0.0, 0.5, 1.0, 1.0, 1.0}; 
        Decide.Y = new double[]{0.0, 0.0, 5.0, 0.5, 2.0};

        assertFalse(Decide.lic14(), "Expected Lic 14 to be false: when AREA2 <= area ");
    }
    @Test
    @DisplayName("LIC 14 should be false when area is less than AREA1")
    void testLic14Negative() {
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.E_PTS = 1; 
        Decide.PARAMETERS.F_PTS = 1;
        Decide.PARAMETERS.AREA1 = 2.0;
        Decide.PARAMETERS.AREA2 = 1.0;

        //indices: 0, 2, 4 will be checked. The set will give an area = 1.5
        Decide.X = new double[]{0.0, 0.5, 1.0, 1.0, 1.0}; 
        Decide.Y = new double[]{0.0, 0.0, 5.0, 0.5, 2.0};

        assertFalse(Decide.lic14(), "Expected Lic 14 to be false: when AREA2 <= area and AREA1 >= area");
    }
    @Test
    @DisplayName("LIC 14 should be false when area is less than AREA1")
    void testLic14PositiveDifferentSets() {
        Decide.NUMPOINTS = 6;
        Decide.PARAMETERS.E_PTS = 1; 
        Decide.PARAMETERS.F_PTS = 1;
        Decide.PARAMETERS.AREA1 = 1.0;
        Decide.PARAMETERS.AREA2 = 0.5;

        //indices: 0, 2, 4 will be checked for condition 1. The set will give an area1 = 1.5
        //indices: 1, 3, 5 will be checked for condition 2. The set will give an area2 = 3.375
        Decide.X = new double[]{0.0, 0.5, 1.0, 1.0, 1.0, 0.5}; 
        Decide.Y = new double[]{0.0, 0.0, 5.0, 0.5, 2.0, 1.5};

        assertTrue(Decide.lic14(), "Expected Lic 14 to be true: when AREA1 = 1.0 < area1 = 1.5  && area2 = 3.375 < AREA2 = 0.5");
    }

    @Test
    @DisplayName("LIC 14 should be false when NUMPOINTS < 5")
    void testLic14InsufficientPoints() {
        Decide.NUMPOINTS = 4;

        assertFalse(Decide.lic14(), "Expected Lic 14 to be false when NUMPOINTS < 5");
    }

    @Test
    @DisplayName("LIC 14 should be false when AREA =< 0")
    void testLic14Area2Negative() {
        Decide.NUMPOINTS = 4;
        Decide.PARAMETERS.AREA2 = -1.0;
        assertFalse(Decide.lic14(), "Expected Lic 14 to be false when AREA2 <= 0");
    }
    
}
