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


    //LIC 13 TEST

    
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
            0.0,           // Set 1, point 1 (index 0)
            0.0,           // Set 2, point 1 (index 1)
            0.0,           // Set 1, point 2 (index 2)
            0.0,           // Set 2, point 2 (index 3)
            height1,       // Set 1, point 3 (index 4)
            height2        // Set 2, point 3 (index 5)
        };
        
        assertTrue(Decide.lic13(), 
            "Expected LIC13 to be true: Set 1 (indices 0,2,4) has radius > 0.5, " +
            "Set 2 (indices 1,3,5) has radius <= 0.6");
    }

}
