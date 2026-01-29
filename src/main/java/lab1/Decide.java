package lab1;

public class Decide {

    // ////////////
    // CONSTANT //////////
    public static final double PI = 3.1415926535;

    // //////////// TYPE DECLARATIONS ///////////
    public enum Connectors {
        NOTUSED(777),
        ORR(778),
        ANDD(779);

        private final int value;
        Connectors(int value) { this.value = value; }
    }

    public enum CompType {
        LT(1111),
        EQ(1112),
        GT(1113);

        private final int value;
        CompType(int value) { this.value = value; }
    }

    // inputs to the DECIDE() function
    public static class ParametersT {
        public double LENGTH1;     // Length in LICs 0, 7, 12
        public double RADIUS1;     // Radius in LICs 1, 8, 13
        public double EPSILON;     // Deviation from PI in LICs 2, 9
        public double AREA1;       // Area in LICs 3, 10, 14
        public int Q_PTS;          // No. of consecutive points in LIC 4
        public int QUADS;          // No. of quadrants in LIC 4
        public double DIST;        // Distance in LIC 6
        public int N_PTS;          // No. of consecutive pts. in LIC 6
        public int K_PTS;          // No. of int. pts. in LICs 7, 12
        public int A_PTS;          // No. of int. pts. in LICs 8, 13
        public int B_PTS;          // No. of int. pts. in LICs 8, 13
        public int C_PTS;          // No. of int. pts. in LIC 9
        public int D_PTS;          // No. of int. pts. in LIC 9
        public int E_PTS;          // No. of int. pts. in LICs 10, 14
        public int F_PTS;          // No. of int. pts. in LICs 10, 14
        public int G_PTS;          // No. of int. pts. in LIC 11
        public double LENGTH2;     // Maximum length in LIC 12
        public double RADIUS2;     // Maximum radius in LIC 13
        public double AREA2;       // Maximum area in LIC 14
    }

    // //////////// global variable declarations ////////////
    public static ParametersT PARAMETERS = new ParametersT();

    // X coordinates of data points
    public static double[] X;

    // Y coordinates of data points
    public static double[] Y;

    // Number of data points
    public static int NUMPOINTS;

    // Logical Connector Matrix
    public static Connectors[][] LCM;

    // Preliminary Unlocking Vector
    public static boolean[] PUV;

    // Preliminary Unlocking Matrix
    public static boolean[][] PUM;

    // Conditions Met Vector
    public static boolean[] CMV;

    // Final Unlocking Vector
    public static boolean[] FUV;

    // Decision: Launch or No Launch
    public static boolean LAUNCH;

    // compares floating point numbers
    public static CompType DOUBLECOMPARE(double A, double B) {
        if (Math.abs(A - B) < 0.000001) return CompType.EQ;
        if (A < B) return CompType.LT;
        return CompType.GT;
    }

    // //////////// PRIVATE HELPER METHODS  ///////////

    // Shoelace formula for area of triangle
    private static double calculateTriangleArea(double x1, double y1, double x2, double y2, double x3, double y3) {
        return 0.5 * Math.abs(x1*(y2-y3) + x2*(y3-y1) + x3*(y1-y2));
    }

    private static double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
    }

    // Helper function for LIC4, handles ambiguity and gets correct quadrant
    private static int getQuadrant(int idx) {
        double x = X[idx];  // x-coordinate
        double y = Y[idx];  // y-coordinate

        if (x >= 0 && y >= 0) { return 0; }  // Quadrant 1
        if (x < 0 && y >= 0) { return 1; }  // Quadrant 2
        if (x <= 0 && y < 0) { return 2; }  // Quadrant 3
        return 3;  // Quadrant 4
    }

    // //////////// LIC'S  ///////////

    /*
    This function measures the Euclidean distance between two consecutive points, i.e (X[i],Y[i]) and (X[i+1],Y[i+1]).
    If the distance is greater than LENGHT1, the method returns true. Otherwise, it returns false - even for invalid data (LENGHT1 < 0).
    The function uses a helper method to calculate the Euclidean distance: calculateDistance()
     */
    public static boolean lic0() {
        if(PARAMETERS.LENGTH1 < 0) {
            return false;
        }
        for(int i = 0; i < NUMPOINTS - 1; i++) {
            // consecutive data points e.g. (X[i],Y[i]) and (X[i+1],Y[i+1]) <-- from Glossary in assignment.
            double x1 = X[i];
            double x2 = X[i+1];
            double y1 = Y[i];
            double y2 = Y[i+1];
            
            // Euclidean distance measurement
            double distance = calculateDistance(x1, y1, x2, y2);

            // condition
            if(distance > PARAMETERS.LENGTH1) {
              return true;
            }
        }
        return false;
    }
  
    /*
    This function checks if there are at least one set of three consecutive data points that canotn be contained within or on a circle with RADIUS1.
    If the measured sides of the triangle (of the three data points) are greater than RADIUS1, the method returns true. 
    Otherwise it returns false, even for invalid data (RADIUS1 < 0).
    The function uses a helper method to calculate the Euclidean distance: calculateDistance()
     */
    public static boolean lic1() {
        if(PARAMETERS.LENGTH1 < 0) {
            return false;
        }
        for(int i = 0; i < NUMPOINTS - 2; i++) {
            double x1 = X[i];
            double x2 = X[i + 1];
            double x3 = X[i + 2];
            double y1 = Y[i];
            double y2 = Y[i + 1];
            double y3 = Y[i + 2];

            // Distance between (x1,y1) and (x2,y2)
            double distance_a = calculateDistance(x1, y1, x2, y2);
            // Distance between (x2,y2) and (x3,y3)
            double distance_b = calculateDistance(x2, y2, x3, y3);
            // Distance between (x1,y1) and (x3,y3)
            double distance_c = calculateDistance(x1, y1, x3, y3);

            double radius;

            // Conditions to check pythagorean inequality, with the longest side being the diameter = 2 x radius
            // (also avoids zero division in radius on the else-block)
            if(Math.pow(distance_a, 2) + Math.pow(distance_b, 2) <= Math.pow(distance_c, 2)) {
                radius = distance_c / 2;
            }
            else if(Math.pow(distance_a, 2) + Math.pow(distance_c, 2) <= Math.pow(distance_b, 2)) {
                radius = distance_b / 2;
            }
            else if((Math.pow(distance_b, 2) + Math.pow(distance_c, 2) <= Math.pow(distance_a, 2))) {
                radius = distance_a / 2;
            }
            else {
                double area = 0.5 * Math.abs(x1*(y2-y3) + x2*(y3-y1) + x3*(y1-y2));
                radius = (distance_a * distance_b * distance_c) / (4 * area);
            }

            if(radius > PARAMETERS.RADIUS1) {
              return true;
            }
        }
        return false;
    }

    /*
    This function checks if there are at least one set of three consecutive data points that form an angle such that:
    1. angle < (PI−EPSILON)
    2. angle > (PI + EPSILON)
    The angle is of the secon point (=vertex). If the following requirements of the measured angle are met, the method returns true. 
    Otherwise it returns false, even for invalid data (EPSILON < 0 or EPSILON > PI), and if the rest of the points coincide with vertex.
     */
    public static boolean lic2() {
        if(PARAMETERS.EPSILON < 0 || PARAMETERS.EPSILON > PI) {
            return false;
        }
        for(int i = 0; i < NUMPOINTS - 2; i++) {
            double x1 = X[i], x2 = X[i + 1];
            double x3 = X[i + 2], y1 = Y[i];
            double y2 = Y[i + 1], y3 = Y[i + 2];

            // Handling if points coincide with vertex (=second point)
            if((x1 == x2 && y1 == y2) || (x3 == x2 && y3 == y2)) {
                // skip
                continue;
            }

            // Cosine similarity formula (only measuring vertex)
            double vector_ab_x = x2 - x1, vector_ab_y = y2 - y1;
            double vector_bc_x = x2 - x3, vector_bc_y = y2 - y3;

            double vector_dotProd = (vector_ab_x * vector_bc_x) + (vector_ab_y * vector_bc_y);

            double vector_ab_norm = Math.sqrt(Math.pow(vector_ab_x, 2) + Math.pow(vector_ab_y, 2));
            double vector_bc_norm = Math.sqrt(Math.pow(vector_bc_x, 2) + Math.pow(vector_bc_y, 2));

            double angle = Math.acos(vector_dotProd / (vector_ab_norm * vector_bc_norm));

            if(angle < (PI - PARAMETERS.EPSILON) || angle > (PI + PARAMETERS.EPSILON)) {
                return true;
            }
        }
        return false;
    }

    /*
    This function checks if there are at least one set of three consecutive data points that create a triangle with
    an area greater than AREA1. 
    If the measured area (of the three data points) are greater than AREA1, the method returns true. 
    Otherwise it returns false, even for invalid data (AREA1 < 0).
    The function uses a helper method to calculate the the triangle are: calculateTriangleArea()
     */
    public static boolean lic3() {
        if(PARAMETERS.AREA1 < 0) {
            return false;
        }

        for(int i = 0; i < NUMPOINTS - 2; i++) {
            double x1 = X[i], x2 = X[i + 1];
            double x3 = X[i + 2], y1 = Y[i];
            double y2 = Y[i + 1], y3 = Y[i + 2];

            double area = calculateTriangleArea(x1, y1, x2, y2, x3, y3);

            if(area > PARAMETERS.AREA1) {
                return true;
            }
        }
        return false;
    }

    public static boolean lic4() {
        // If the set size is larger than the number of available points, the LIC is impossible
        if (PARAMETERS.Q_PTS > NUMPOINTS) { return false; }

        // For each possible set of points
        for(int i = 0; i < NUMPOINTS - PARAMETERS.Q_PTS; i++) {
            // Boolean array to track which of the four quadrants are occupied
            boolean[] occupiedQuadrants = new boolean[4];
            int uniqueQuadrants = 0;

            // Check current set of Q_PTS points
            for(int j = 0; j < PARAMETERS.Q_PTS; j++) {
                int pointIdx = i + j;
                int quadrant = getQuadrant(pointIdx);
                // If this quadrant hasn't been seen in this window yet, count it
                if (!occupiedQuadrants[quadrant]) {
                    occupiedQuadrants[quadrant] = true;
                    uniqueQuadrants++;
                }
            }
            // LIC is met if number of unique quadrants exceeds threshold
            if (uniqueQuadrants > PARAMETERS.QUADS) {
                return true;
            }
        }
        return false;
    }

    // Checks if there is at least one point which has moved in
    // the negative x-direction compared to the point before it
    public static boolean lic5() {
        for (int i = 0; i < NUMPOINTS - 1; i++) {
            if (DOUBLECOMPARE(X[i+1] - X[i], 0.0) == CompType.LT) {
                return true;
            }
        }
        return false;
    }

    
    public static boolean lic6() {
        if (NUMPOINTS < 3) { return false; }

        for (int i = 0; i <= NUMPOINTS - PARAMETERS.N_PTS; i++) {
            int startIdx = i;
            int endIdx = i + PARAMETERS.N_PTS - 1;

            // Check if first and last points are identical
            boolean isIdentical =
                    (DOUBLECOMPARE(X[startIdx], X[endIdx]) == CompType.EQ) &&
                    (DOUBLECOMPARE(Y[startIdx], Y[endIdx]) == CompType.EQ);

            // Calculate the length of the line segment
            double lineLength = 0;
            if (!isIdentical) {
                lineLength = calculateDistance(X[startIdx], Y[startIdx], X[endIdx], Y[endIdx]);
            }

            // Iterate through the points in the window (excluding the endpoints)
            for (int j = startIdx + 1; j < endIdx; j++) {
                double distanceToLine;
                if (isIdentical) {
                    distanceToLine = calculateDistance(X[startIdx], Y[startIdx], X[j], Y[j]);
                } else {  // Use triangle geometry to find normal line
                    double area = calculateTriangleArea(X[startIdx], Y[startIdx], X[endIdx], Y[endIdx], X[j], Y[j]);;
                    distanceToLine = (2 * area) / lineLength;
                }
                // LIC met if distance is greater than DIST
                if (DOUBLECOMPARE(distanceToLine, PARAMETERS.DIST) == CompType.GT) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean lic7() {
        if(NUMPOINTS >= 3) {
            for(int i = 0; i < (NUMPOINTS - 1); i++){
                double x1 = X[i];
                double x2 = X[i + PARAMETERS.K_PTS + 1];
                double y1 = Y[i];
                double y2 = Y[i + PARAMETERS.K_PTS + 1];

                double distance = calculateDistance(x1, y1, x2, y2);
                if(distance > PARAMETERS.LENGTH1) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean lic8(){
        if(NUMPOINTS >=5){
            for(int i = 0; i < (NUMPOINTS - PARAMETERS.A_PTS - PARAMETERS.B_PTS - 2); i++){
                double x1 = X[i];
                double x2 = X[i + PARAMETERS.A_PTS + 1];
                double x3 = X[i + PARAMETERS.A_PTS + PARAMETERS.B_PTS + 2];
                double y1 = Y[i];
                double y2 = Y[i + PARAMETERS.A_PTS + 1];
                double y3 = Y[i + PARAMETERS.A_PTS + PARAMETERS.B_PTS + 2];
                
                // FROM LIC 1
                double distance_a = calculateDistance(x1, y1, x2, y2);
                double distance_b = calculateDistance(x2, y2, x3, y3);
                double distance_c = calculateDistance(x1, y1, x3, y3);

                double radius;

                if(Math.pow(distance_a, 2) + Math.pow(distance_b,2) <= Math.pow(distance_c, 2)) {
                    radius = distance_c / 2;
                }
                else if(Math.pow(distance_a, 2) + Math.pow(distance_c, 2) <= Math.pow(distance_b, 2)) {
                    radius = distance_b / 2;
                }
                else if(Math.pow(distance_b, 2) + Math.pow(distance_c, 2) <= Math.pow(distance_a, 2)){
                    radius = distance_a / 2;
                }
                else {
                    double area = 0.5 * Math.abs(x1*(y2-y3) + x2*(y3-y1) + x3*(y1-y2));
                    radius = (distance_a * distance_b * distance_c) / (4 * area);
                }

                if(radius > PARAMETERS.RADIUS1) {
                  return true;
                }
            }
        }
        return false;
    }

    public static boolean lic9() {
        if(NUMPOINTS >= 5) {
            for(int i = 0; i < (NUMPOINTS - PARAMETERS.C_PTS - PARAMETERS.D_PTS - 2); i++) {
                double x1 = X[i];
                double x2 = X[i + PARAMETERS.C_PTS + 1];
                double x3 = X[i + PARAMETERS.C_PTS + PARAMETERS.D_PTS + 2];
                double y1 = Y[i];
                double y2 = Y[i + PARAMETERS.C_PTS + 1];
                double y3 = Y[i + PARAMETERS.C_PTS + PARAMETERS.D_PTS + 2];

                // angle is undefined if either vector has zero length
                if((x1 == x2 && y1 == y2) || (x3 == x2 && y3 == y2)) {
                    continue;
                }

                // computing vectors between the points
                double v1x = (x1 - x2);
                double v1y = (y1 - y2);
                double v2x = (x3 - x2);
                double v2y = (y3 - y2);

                double v_dotProd = (v1x * v2x) + (v1y * v2y);
                double v1_norm = Math.sqrt(Math.pow(v1x, 2) + Math.pow(v1y, 2));
                double v2_norm = Math.sqrt(Math.pow(v2x, 2) + Math.pow(v2y, 2));

                double angle = Math.acos(v_dotProd / (v1_norm * v2_norm));

                if(angle < (PI - PARAMETERS.EPSILON) || angle > (PI + PARAMETERS.EPSILON)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean lic10() {
        if(NUMPOINTS < 5) {
            return false;
        }

        for(int i = 0; i < NUMPOINTS - 2 - PARAMETERS.E_PTS - PARAMETERS.F_PTS; i++) {
            int j = i + PARAMETERS.E_PTS + 1;
            int k = j + PARAMETERS.F_PTS + 1;

            double area = calculateTriangleArea(X[i], Y[i], X[j], Y[j], X[k], Y[k]);

            if(area > PARAMETERS.AREA1) {
                return true;
            }
        }
        return false;
    }

    public static boolean lic11() {
        if(NUMPOINTS < 3) {
            return false;
        }

        for(int i = 0; i < NUMPOINTS - 1 - PARAMETERS.G_PTS; i++) {
            int j = i + PARAMETERS.G_PTS + 1;

            if(X[j] - X[i] < 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean lic12() {
        if(NUMPOINTS < 3) {
            return false;
        }
        boolean condition1 = false; // Distance > LENGTH1
        boolean condition2 = false; // Distance < LENGTH2

        for(int i = 0; i < NUMPOINTS - 1 - PARAMETERS.K_PTS; i++) {
            int j = i + PARAMETERS.K_PTS + 1;
            double distance = calculateDistance(X[i], Y[i], X[j], Y[j]);
            if(distance > PARAMETERS.LENGTH1) {
                condition1 = true;
            }

            if(distance < PARAMETERS.LENGTH2) {
                condition2 = true;
            }

            // Optimization: If both are already found, we can stop early
            if (condition1 && condition2) {
                return true;
            }
        }
        return condition1 && condition2;
    }

    public static boolean lic13(){
        if(NUMPOINTS < 5) return false;
        if(PARAMETERS.RADIUS2 <= 0) return false;
        int minPoints = 3 + PARAMETERS.A_PTS + PARAMETERS.B_PTS;
        if(NUMPOINTS < minPoints) return false;
        boolean condition1 = false;
        boolean condition2 = false;

        for (int i = 0; i <= NUMPOINTS - minPoints; i++) { 
            //points
            double x1 = X[i];
            double x2 = X[i+PARAMETERS.A_PTS+1];
            double x3 = X[i+ PARAMETERS.A_PTS+PARAMETERS.B_PTS+2];
            double y1 = Y[i];
            double y2 = Y[i+PARAMETERS.A_PTS+1];
            double y3 = Y[i+PARAMETERS.A_PTS+PARAMETERS.B_PTS+2];
            double radius;

            //triangle sides
            double a = calculateDistance(x2, y2, x3, y3);
            double b = calculateDistance(x1, y1, x3, y3);
            double c = calculateDistance(x1, y1, x2, y2);

            //calculate area of triangle
            double area = calculateTriangleArea(x1, y1, x2, y2, x3, y3);

            //colinear case
            if (DOUBLECOMPARE(area, 0.0) == CompType.EQ) {
                radius = 0.5 * Math.max(a,Math.max(b,c));
            }
            else{
                //calculate radius of circumscribed circle
                radius = a*b*c / (4*area);
            }
            if(radius > PARAMETERS.RADIUS1){ 
                condition1 = true;
            }
            if(radius <= PARAMETERS.RADIUS2){
                condition2 = true;
            }
        }
        if(condition1 && condition2) return true;
        return false;
    }

    public static boolean lic14(){
        if(NUMPOINTS < 5) return false;
        if(PARAMETERS.AREA2 <= 0) return false;
        int minPoints = 3 + PARAMETERS.E_PTS + PARAMETERS.F_PTS;
        if(NUMPOINTS < minPoints) return false;
        boolean condition1 = false; // area > AREA1
        boolean condition2 = false; // area < AREA2

        for (int i = 0; i <= NUMPOINTS - minPoints; i++) { 

            double x1 = X[i];
            double x2 = X[i+PARAMETERS.E_PTS+1];
            double x3 = X[i+ PARAMETERS.E_PTS+PARAMETERS.F_PTS+2];
            double y1 = Y[i];
            double y2 = Y[i+PARAMETERS.E_PTS+1];
            double y3 = Y[i+PARAMETERS.E_PTS+PARAMETERS.F_PTS+2];

            double area = calculateTriangleArea(x1, y1, x2, y2, x3, y3);

            if(area > PARAMETERS.AREA1){ 
                condition1 = true;
            }
            if(area < PARAMETERS.AREA2){
                condition2 = true;
            }
        }
        return condition1 && condition2;
    }

    // //////////// CMV & PUM & FUV  ///////////

    public static void setCMV(){
        CMV[0] = lic0();
        CMV[1] = lic1();
        CMV[2] = lic2();
        CMV[3] = lic3();
        CMV[4] = lic4();
        CMV[5] = lic5();
        CMV[6] = lic6();
        CMV[7] = lic7();
        CMV[8] = lic8();
        CMV[9] = lic9();
        CMV[10] = lic10();
        CMV[11] = lic11();
        CMV[12] = lic12();
        CMV[13] = lic13();
        CMV[14] = lic14();
    }

    public static void calculatePUM() {
        PUM = new boolean[15][15];

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Connectors operation = LCM[i][j];

                if (operation == Connectors.NOTUSED) {
                    PUM[i][j] = true;
                }
                else if (operation == Connectors.ANDD) {
                    PUM[i][j] = CMV[i] && CMV[j];
                }
                else if (operation == Connectors.ORR) {
                    PUM[i][j] = CMV[i] || CMV[j];
                }
            }
        }
    }
  
    // Generate FUV from PUV and PUM
    public static void computeFUV() {
        // For every LIC represented in PUV
        for (int i = 0; i < 15; i++) {
            // If PUV[i] is false, the i:th LIC is irrelevant, so set FUV[i] to true
            if (!PUV[i]) {
                FUV[i] = true;
            } else {
                boolean entireRowTrue = true;
                for (int j = 0; j < 15; j++) {
                    if (i == j) { continue; }  // Skip diagonal of the PUM
                    // If any element of the PUM row is false, break and set FUV[i] false
                    if (!PUM[i][j]) {
                        entireRowTrue = false;
                        break;
                    }
                }
                FUV[i] = entireRowTrue;
            }
        }
    }

    // //////////// DECIDE  ///////////

    public static void DECIDE() {
        setCMV();
        calculatePUM();
        computeFUV();
        
        int trueFUV = 0;
        for(int i = 0; i < 15; i++){
            if(FUV[i] == true){
                trueFUV++;
            }
            else
            {
                LAUNCH = false;
            }
        }
        if(trueFUV == 15){
            LAUNCH = true;
        }

        if(LAUNCH) {System.out.println("YES");}
        else{System.out.println("NO");}
    }
}
