package lab1;
import java.util.Arrays;

public class Main {

    private static void loadTestData() {
        System.out.println("Loading simulated data");

        // Assigning new values for parameters 
        Decide.PARAMETERS = new Decide.ParametersT();
        Decide.LCM = new Decide.Connectors[15][15];
        Decide.PUV = new boolean[15];
        Decide.X = new double[100];
        Decide.Y = new double[100];

        // Dimensions from Table 2,3 and 4 from paper
        Decide.CMV = new boolean[15];
        Decide.PUM = new boolean[15][15];
        Decide.FUV = new boolean[15];

        // Setting parameters
        Decide.PARAMETERS.LENGTH1 = 10.0;     
        Decide.PARAMETERS.RADIUS1 = 4.0;     
        Decide.PARAMETERS.EPSILON = 2.0;     
        Decide.PARAMETERS.AREA1 = 50.0;       
        Decide.PARAMETERS.Q_PTS = 3;          
        Decide.PARAMETERS.QUADS = 2;          
        Decide.PARAMETERS.DIST = 6.0;        
        Decide.PARAMETERS.N_PTS = 9;          
        Decide.PARAMETERS.K_PTS = 4;          
        Decide.PARAMETERS.A_PTS = 2;         
        Decide.PARAMETERS.B_PTS = 3;          
        Decide.PARAMETERS.C_PTS = 1;          
        Decide.PARAMETERS.D_PTS = 4;          
        Decide.PARAMETERS.E_PTS = 5;          
        Decide.PARAMETERS.F_PTS = 1;         
        Decide.PARAMETERS.G_PTS = 3;          
        Decide.PARAMETERS.LENGTH2 = 13.0;    
        Decide.PARAMETERS.RADIUS2 = 5.0;     
        Decide.PARAMETERS.AREA2 = 34.0;  
        
        // Setting coordinates
        Decide.NUMPOINTS = 10;
        for (int i = 0; i < Decide.NUMPOINTS; i++) {
            Decide.X[i] = i;
            Decide.Y[i] = i;
        }

        // LCM at default
        for (int i = 0; i < 15; i++) {
            // Filling all spaces in the matrix as NOTUSED (default)
            Arrays.fill(Decide.LCM[i], Decide.Connectors.NOTUSED);
        }

        // Changing the shown rows as in Table 1
        // Row 0
        Decide.LCM[0][0] = Decide.Connectors.ANDD;
        Decide.LCM[0][1] = Decide.Connectors.ANDD;
        Decide.LCM[0][2] = Decide.Connectors.ORR;
        Decide.LCM[0][3] = Decide.Connectors.ANDD;
        // Row 1
        Decide.LCM[1][0] = Decide.Connectors.ANDD;
        Decide.LCM[1][1] = Decide.Connectors.ANDD;
        Decide.LCM[1][2] = Decide.Connectors.ORR;
        Decide.LCM[1][3] = Decide.Connectors.ORR;
        // Row 2
        Decide.LCM[2][0] = Decide.Connectors.ORR;
        Decide.LCM[2][1] = Decide.Connectors.ORR;
        Decide.LCM[2][2] = Decide.Connectors.ANDD;
        Decide.LCM[2][3] = Decide.Connectors.ANDD;
        // Row 3
        Decide.LCM[3][0] = Decide.Connectors.ANDD;
        Decide.LCM[3][1] = Decide.Connectors.ORR;
        Decide.LCM[3][2] = Decide.Connectors.ANDD;
        Decide.LCM[3][3] = Decide.Connectors.ANDD;

        // Filling all spaces the vector as as false (default)
        Arrays.fill(Decide.PUV, false);
        // Changing according to stated boolean values in 2.3 FUV in paper
        Decide.PUV[0] = true;
        Decide.PUV[1] = false;
        Decide.PUV[2] = true;
    }
    
    public static void main(String[] args) {
        System.out.println("System Initiating");

        // Calling helper method to load data
        loadTestData();

        System.out.println("Result: ");
        Decide.DECIDE();

        System.out.println("System Complete");
    }
}
