package group17;

import group17.InputHandler.CONNECTORS;
import java.util.Arrays;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest 
{
    private static App missileSystem;
    private static InputHandler input;
    private static LicAnalyzer licAnalyzer;

    ////
    /*
     * Following posts on Stack Overflow was used to handle assertion on text 
     * written to stdout.
     * https://stackoverflow.com/questions/1119385/junit-test-for-missileSystem-out-println
     */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }
    
    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }
    ////
    
    @BeforeAll
    public static void init()
    {
        missileSystem  = new App();
        input          = new InputHandler("sampleData.json");
        licAnalyzer    = new LicAnalyzer();

        for (int i = 0; i < input.LCM.length; ++i) {
            input.PUV[i] = true;
            Arrays.fill(input.LCM[i], CONNECTORS.ANDD);
        }
    }

    @Test
    public void decidePrintsYesWhenGivenBasicDataWhereAllLicsReturnTrue()
    {
        input.X_COORD   = new double[]{ -0.5, 0.3, 1.0, 0.4, 2.8 , 1.5 , 3.0, -3.0, 21.0, 22.2, 19.0};
        input.Y_COORD   = new double[]{ -0.5, 0.0, 1.0, 0.1, 0.1 , 0.0 , 3.0, -3.0, 21.0, 22.2, 19.0};
        input.NUMPOINTS = input.X_COORD.length;
        input.N_PTS     = input.X_COORD.length - 2;
        input.K_PTS     = 1;
        input.A_PTS     = 1;
        input.B_PTS     = 1;
        input.C_PTS     = 1;
        input.D_PTS     = 1;
        input.E_PTS     = 1;
        input.F_PTS     = 1;
        input.G_PTS     = 1;
        input.Q_PTS     = 2;
        input.QUADS     = 1;
        input.LENGTH1   = 0.9;
        input.RADIUS1   = 0.9;
        input.EPSILON   = 0.0;
        input.AREA1     = 1.0;
        input.LENGTH2   = 1.0;
        input.RADIUS2   = 10.0;
        input.AREA2     = 10.0;
        input.DIST      = 0.05;

        boolean[] CMV = {
            licAnalyzer.lic0(input),
            licAnalyzer.lic1(input),
            licAnalyzer.lic2(input),
            licAnalyzer.lic3(input),
            licAnalyzer.lic4(input),
            licAnalyzer.lic5(input),
            licAnalyzer.lic6(input),
            licAnalyzer.lic7(input),
            licAnalyzer.lic8(input),
            licAnalyzer.lic9(input),
            licAnalyzer.lic10(input),
            licAnalyzer.lic11(input),
            licAnalyzer.lic12(input),
            licAnalyzer.lic13(input),
            licAnalyzer.lic14(input)
        };
        boolean[] allSignalsTrue = new boolean[15];
        Arrays.fill(allSignalsTrue, true);

        //Act
        missileSystem.decide(input);

        //Assert
        Assertions.assertArrayEquals(CMV, allSignalsTrue);
        assertEquals("YES\n", outContent.toString());
    }

    @Test
    public void decidePrintsNoWhenCalledOnAssignmentsExampleArrays()
    {
        // Arrange

        // Relevant for LIC 0 - 3
        input.X_COORD   = new double[]{ -5.0, 0.0, 10.0, 3.0, 2.8 , -3.0 };
        input.Y_COORD   = new double[]{ -5.0, 0.0, 10.0, 3.0, 0.1 , -3.0 };
        input.NUMPOINTS = input.X_COORD.length;
        input.Q_PTS     = 2;
        input.QUADS     = 2;
        input.LENGTH1   = 100.0;
        input.RADIUS1   = 0.0;
        input.AREA1     = 1.0;

        //Irrelevant for this test other than not throwing exception anywhere
        input.N_PTS     = input.X_COORD.length - 2;
        input.K_PTS     = 1;
        input.A_PTS     = 1;
        input.B_PTS     = 1;
        input.C_PTS     = 1;
        input.D_PTS     = 1;
        input.E_PTS     = 1;
        input.F_PTS     = 1;
        input.G_PTS     = 1;
        input.EPSILON   = 0.0;
        input.LENGTH2   = 1.0;
        input.RADIUS2   = 10.0;
        input.AREA2     = 10.0;
        input.DIST      = 0.05;

        //Mimic LCM
        for (int i = 0; i < input.LCM.length; ++i) {
            Arrays.fill(input.LCM[i], CONNECTORS.NOTUSED);
        }
        input.LCM[0][0] = CONNECTORS.ANDD; 
        input.LCM[0][1] = CONNECTORS.ANDD; 
        input.LCM[0][2] = CONNECTORS.ORR; 
        input.LCM[0][3] = CONNECTORS.ANDD; 

        input.LCM[1][0] = CONNECTORS.ANDD; 
        input.LCM[1][1] = CONNECTORS.ANDD; 
        input.LCM[1][2] = CONNECTORS.ORR; 
        input.LCM[1][3] = CONNECTORS.ORR; 
        
        input.LCM[2][0] = CONNECTORS.ORR; 
        input.LCM[2][1] = CONNECTORS.ORR; 
        input.LCM[2][2] = CONNECTORS.ANDD; 
        input.LCM[2][3] = CONNECTORS.ANDD; 

        input.LCM[3][0] = CONNECTORS.ANDD; 
        input.LCM[3][1] = CONNECTORS.ORR; 
        input.LCM[3][2] = CONNECTORS.ANDD; 
        input.LCM[3][3] = CONNECTORS.ANDD; 

        boolean[] CMVZeroToThree = {
            licAnalyzer.lic0(input),
            licAnalyzer.lic1(input),
            licAnalyzer.lic2(input),
            licAnalyzer.lic3(input),
        };
        boolean[] expectedSignals = {false, true, true, true};

        //Act
        missileSystem.decide(input);
        //Assert
        Assertions.assertArrayEquals(CMVZeroToThree, expectedSignals);
        assertEquals("NO\n", outContent.toString());
    }

    @Test
    public void decidePrintsYesWhenOnlyCaringAboutLic3() {
        for (int i = 0; i < input.LCM.length; ++i) {
            Arrays.fill(input.LCM[i], CONNECTORS.NOTUSED);
        }
        for (int i = 0; i < input.PUV.length; ++i) {
            input.LCM[i][3] = CONNECTORS.ORR;
            input.LCM[3][i] = CONNECTORS.ORR;
        }

        // Relevant data for LIC3, assume sampleData.json enough for rest
        input.X_COORD   = new double[]{ 0.0, 5.0, 10.0, -0.5, 0.3, 1.0, 0.4, 2.8 , 1.5 };
        input.Y_COORD   = new double[]{ 0.0, 5.0, 0.0, -0.5, 0.0, 1.0, 0.1, 0.1 , 0.0 };
        input.NUMPOINTS = input.X_COORD.length;
        input.AREA1     = 24.0;

        //Act
        missileSystem.decide(input);
        //Assert
        assertTrue(licAnalyzer.lic3(input)); 
        assertEquals("YES\n", outContent.toString());
    }
}
