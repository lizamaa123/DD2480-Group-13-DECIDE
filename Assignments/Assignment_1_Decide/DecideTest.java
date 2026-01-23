// https://www.vogella.com/tutorials/JUnit/article.html
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
}
