package group17;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


import java.lang.Exception;

public class LicAnalyzerTest {

    private static InputHandler input;
    private static LicAnalyzer licAnalyzer;

    @BeforeEach
    public void setUp() {
        input = new InputHandler("sampleData.json");
        licAnalyzer = new LicAnalyzer();
    }

///////////////////////////// Lic 0 /////////////////////////////

    @Test
    public void lic0ReturnsTrueIfConsecutivePositivePointsGreaterThanLENGTH1ApartExists() {
        // Arrange
        input.LENGTH1 = 3.0;
        input.X_COORD = new double[]{1.0, 10.0};
        input.Y_COORD = new double[]{1.0, 10.0};
        input.NUMPOINTS = input.X_COORD.length;

        //Act
        boolean signal = licAnalyzer.lic0(input);

        //Assert
        assertTrue(signal);
    }
    @Test
    public void lic0ReturnsTrueIfConsecutiveNegativePointsGreaterThanLENGTH1ApartExists() {
        // Arrange
        input.LENGTH1 = 3.0;
        input.X_COORD = new double[]{-1.0, -10.0};
        input.Y_COORD = new double[]{-1.0, -10.0};
        input.NUMPOINTS = input.X_COORD.length;

        //Act
        boolean signal = licAnalyzer.lic0(input);

        //Assert
        assertTrue(signal);
    }
    @Test
    public void lic0ReturnsTrueIfConsecutivePointsInQ2AndQ4GreaterThanLENGTH1ApartExists() {
        // Arrange
        input.LENGTH1 = 3.0;
        input.X_COORD = new double[]{1.0, -10.0};
        input.Y_COORD = new double[]{-1.0, 10.0};
        input.NUMPOINTS = input.X_COORD.length;

        //Act
        boolean signal = licAnalyzer.lic0(input);

        //Assert
        assertTrue(signal);
    }
    @Test
    public void lic0ReturnsFalseIfNoConsecutivePointsGreaterThanLENGTH1ApartExists() {
        // Arrange
        input.LENGTH1 = Math.sqrt(2) + 0.00000001;
        input.X_COORD = new double[]{1.0, 2.0};
        input.Y_COORD = new double[]{1.0, 2.0};
        input.NUMPOINTS = input.X_COORD.length;

        //Act
        boolean signal = licAnalyzer.lic0(input);

        //Assert
        assertFalse(signal);
    }

    @Test
    public void lic0ThrowsExceptionIfLENGTH1IsBelowZero() {
        //Arrange
        input.LENGTH1 = -0.00000001;

        //Act, Assert
        assertThrows(Exception.class, () -> licAnalyzer.lic0(input));
    }
    @Test
    public void lic0ThrowsExceptionIfNUMPOINTSIsBelow2() {
        //Arrange
        input.NUMPOINTS = 1;

        //Act, Assert
        assertThrows(Exception.class, () -> licAnalyzer.lic0(input));
    }
    @Test
    public void lic0ThrowsExceptionIfNullPointingCoordinateArray() {
        //Arrange
        input.X_COORD = null;
        input.Y_COORD = null;

        //Act, Assert
        assertThrows(Exception.class, () -> licAnalyzer.lic0(input));
    }
///////////////////////////// Lic 1 /////////////////////////////

    @Test
    public void lic1TestUncontained() {
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{-1.0, 1.0, 3.0, 7.0};
        input.Y_COORD = new double[]{-1.0, 0.0, 3.0, 3.0};
        input.RADIUS1 = 3;
        assertTrue(licAnalyzer.lic1(input));
    }

    public void lic1TestContained() {
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{-1.0, 1.0, 3.0, 5.0};
        input.Y_COORD = new double[]{-1.0, 0.0, 3.0, 3.0};
        input.RADIUS1 = 3;
        assertFalse(licAnalyzer.lic1(input));
    }

    @Test
    public void lic1TestContainedBoundary() {
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{-3.0, 0.0, 3.0, 5.0};
        input.Y_COORD = new double[]{0.0, 3.0, 0.0, 3.0};
        input.RADIUS1 = 3;
        assertFalse(licAnalyzer.lic1(input));
    }

    @Test
    public void lic1TestContained3Points() {
        input.NUMPOINTS = 3;
        input.X_COORD = new double[]{1.0, 0.0, 0.0};
        input.Y_COORD = new double[]{0.0, 1.0, 0.0};
        input.RADIUS1 = 3;
        assertFalse(licAnalyzer.lic1(input));
    }

    @Test
    public void lic1TestUncontained3Points() {
        input.NUMPOINTS = 3;
        input.X_COORD = new double[]{-4.0, 0.0, 4.0};
        input.Y_COORD = new double[]{0.0, 0.0, 0.0};
        input.RADIUS1 = 3;
        assertTrue(licAnalyzer.lic1(input));
    }

    @Test
    public void lic1TestContainedZeroRadius() {
        input.NUMPOINTS = 3;
        input.X_COORD = new double[]{1.0, 1.0, 1.0};
        input.Y_COORD = new double[]{1.0, 1.0, 1.0};
        input.RADIUS1 = 0;
        assertFalse(licAnalyzer.lic1(input));
    }

    @Test
    public void lic1TestUncontainedZeroRadius() {
        input.NUMPOINTS = 3;
        input.X_COORD = new double[]{1.0, 0.0, 1.0};
        input.Y_COORD = new double[]{1.0, 1.0, 1.0};
        input.RADIUS1 = 0;
        assertTrue(licAnalyzer.lic1(input));
    }

    @Test
    public void lic1TestUncontainedZeroRadius2() {
        input.NUMPOINTS = 3;
        input.X_COORD = new double[]{1.0, 1.0, 1.0};
        input.Y_COORD = new double[]{1.0, 0.0, 1.0};
        input.RADIUS1 = 0;
        assertTrue(licAnalyzer.lic1(input));
    }

    @Test
    public void lic1TestTooFewPoints() {
        input.NUMPOINTS = 1;
        input.X_COORD = new double[]{-4.0};
        input.Y_COORD = new double[]{0.0};
        input.RADIUS1 = 3;
        assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic1(input));
    }

    @Test
    public void lic1TestManyPoints() {
        input.NUMPOINTS = 101;
        input.RADIUS1 = 3;
        assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic1(input));
    }

    @Test
    public void lic1Test2Points() {
        input.NUMPOINTS = 2;
        input.X_COORD = new double[]{-10.0, 0.0};
        input.Y_COORD = new double[]{0.0, 0.0};
        input.RADIUS1 = 3;
        assertFalse(licAnalyzer.lic1(input));
    }

    @Test
    public void lic1TestNegativeRadius() {
        input.NUMPOINTS = 3;
        input.X_COORD = new double[]{-4.0, 0.0, 4.0};
        input.Y_COORD = new double[]{0.0, 0.0, 0.0};
        input.RADIUS1 = -1;
        assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic1(input));
    }

///////////////////////////// Lic 2 /////////////////////////////

    @Test
    public void lic2AngleOutsideEPSILONRadius() {
        input.NUMPOINTS = 3;
        input.X_COORD = new double[]{-1.0, 3.0, 3.0};
        input.Y_COORD = new double[]{1.0, 1.0, 5.0};
        input.EPSILON = Math.PI / 4;

        assertTrue(licAnalyzer.lic2(input));
    }

    @Test
    public void lic2AngleInsideEPSILONRadius() {
        input.NUMPOINTS = 3;
        input.X_COORD = new double[]{-1.0, 3.0, 7.0};
        input.Y_COORD = new double[]{1.0, 1.0, 1.0};
        input.EPSILON = Math.PI / 4;

        assertFalse(licAnalyzer.lic2(input));
    }

    @Test
    public void lic2TestInvalidEPSILON() {
        input.NUMPOINTS = 3;
        input.X_COORD = new double[]{0.0, 0.0, 0.0};
        input.Y_COORD = new double[]{0.0, 0.0, 0.0};
        input.EPSILON = 2 * Math.PI;

        assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic2(input));
    }

    @Test
    public void lic2TestInvalidNUMPOINTS() {
        input.NUMPOINTS = 1;
        input.X_COORD = new double[]{0.0, 0.0};
        input.Y_COORD = new double[]{0.0, 0.0};
        input.EPSILON = Math.PI / 4;

        assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic2(input));
    }

///////////////////////////// Lic 3 /////////////////////////////

    @Test
    public void lic3TestTriangleAreaGreaterThanAREA1() {
        input.NUMPOINTS = 3;
        input.X_COORD = new double[]{0.0, 0.0, 1.0};
        input.Y_COORD = new double[]{1.0, 0.0, 0.0};
        input.AREA1 = 0.25;

        assertTrue(licAnalyzer.lic3(input));
    }

    @Test
    public void lic3TestTriangleAreaSmallerThanAREA1() {
        input.NUMPOINTS = 3;
        input.X_COORD = new double[]{0.0, 0.0, 1.0};
        input.Y_COORD = new double[]{1.0, 0.0, 0.0};
        input.AREA1 = 1.0;

        assertFalse(licAnalyzer.lic3(input));
    }

    @Test
    public void lic3TestInvalidAREA1() {
        input.AREA1 = -1.0;

        assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic3(input));
    }

    @Test
    public void lic3TestInvalidNUMPOINTS() {
        input.NUMPOINTS = 2;

        assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic3(input));
    }

///////////////////////////// Lic 4 /////////////////////////////

    @Test
    public void lic4TestQptsDoNotCover() {
        input.Q_PTS = 3;
        input.QUADS = 2;
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{0, 2, -4, 1};
        input.Y_COORD = new double[]{1, 3, -5, 4};
        assertFalse(licAnalyzer.lic4(input));
    }
    @Test
    public void lic4TestQptsDoCover() {
        input.Q_PTS = 3;
        input.QUADS = 2;
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{0, -2, -4, 1};
        input.Y_COORD = new double[]{1, 3, -5, 4};
        assertTrue(licAnalyzer.lic4(input));
    }
    @Test
    public void lic4TestQptsEqualNumPoints() {
        input.Q_PTS = 4;
        input.QUADS = 3;
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{0, -2, -4, 1};
        input.Y_COORD = new double[]{1, 3, -5, -4};
        assertTrue(licAnalyzer.lic4(input));
    }
    @Test
    public void lic4TestQuadsHigerOrEqualThanQptsCoverage() {
        input.Q_PTS = 3;
        input.QUADS = 3;
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{0, -2, -4, 1};
        input.Y_COORD = new double[]{1, 3, -5, 4};
        assertFalse(licAnalyzer.lic4(input));
    }
    @Test
    public void lic4TestInvalidInputQpts() {
        input.Q_PTS = 1;
        input.QUADS = 3;
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{0, -2, -4, 1};
        input.Y_COORD = new double[]{1, 3, -5, 4};
        assertThrows(IllegalArgumentException.class, () ->  licAnalyzer.lic4(input));
    }
    @Test
    public void lic4TestInvalidInputQuads() {
        input.Q_PTS = 3;
        input.QUADS = 4;
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{0, -2, -4, 1};
        input.Y_COORD = new double[]{1, 3, -5, 4};
        assertThrows(IllegalArgumentException.class, () ->  licAnalyzer.lic4(input));
    }

///////////////////////////// Lic 5 /////////////////////////////

    @Test
    public void lic5ReturnsTrueIfConsecutivePointsWhereLaterXCoordinateIsLowerThanFirstExistsAndBothAreNegativeNumbers() {
        //Arrange
        input.X_COORD = new double[]{-1.0, -1.0000001};
        input.NUMPOINTS = input.X_COORD.length;

        //Act
        boolean signal = licAnalyzer.lic5(input);

        //Assertions
        assertTrue(signal);
    }
    @Test
    public void lic5ReturnsTrueIfConsecutivePointsWhereLaterXCoordinateIsLowerThanFirstExistsAndLaterIsNegativeNumber() {
        //Arrange
        input.X_COORD = new double[]{2.0, -1.0};
        input.NUMPOINTS = input.X_COORD.length;

        //Act
        boolean signal = licAnalyzer.lic5(input);

        //Assert
        assertTrue(signal);
    }
    @Test
    public void lic5ReturnsFalseIfNoConsecutivePointsWhereLaterIsGreaterExistsBothNumberPositive() {
        //Arrange
        input.X_COORD = new double[]{2.0, 2.1};
        input.NUMPOINTS = input.X_COORD.length;

        //Act
        boolean signal = licAnalyzer.lic5(input);

        //Assert
        assertFalse(signal);
    }
    @Test
    public void lic5ReturnsFalseIfNoConsecutivePointsWhereLaterIsGreaterExistsBothNumberNegative() {
        //Arrange
        input.X_COORD = new double[]{-2.1, -2.0};
        input.NUMPOINTS = input.X_COORD.length;

        //Act
        boolean signal = licAnalyzer.lic5(input);

        //Assert
        assertFalse(signal);
    }

    @Test
    public void lic5ThrowsIllegalArgumentExceptionIfNUMPOINTSIsNegative() {
        //Arrange
        input.NUMPOINTS = -1;

        //Act, Assert
        assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic5(input));
    }

    @Test
    public void lic5ThrowsIllegalArgumentExceptionIfInvalidArraysForPointsAreProvided() {
        //Arrange
        input.X_COORD = null;
        input.Y_COORD = null;

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic5(input));

    }

///////////////////////////// Lic 6 /////////////////////////////

    @Test
    public void lic6PointsWithinDist() {
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{0.0, 1.0, 2.0, 3.0};
        input.Y_COORD = new double[]{1.0, 0.0, 2.0, 1.0};
        input.N_PTS = 3;
        input.DIST = 1.5;

        assertFalse(licAnalyzer.lic6(input));
    }

    @Test
    public void lic6Dist0PointsOnLine() {
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{0.0, 0.0, 4.0, 8.0};
        input.Y_COORD = new double[]{-1.0, 2.0, 0.0, 2.0};
        input.N_PTS = 3;
        input.DIST = 0;
 
        assertTrue(licAnalyzer.lic6(input));
    }

    @Test
    public void lic6PointsOutsideDist() {
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{0.0, 1.0, 2.0, 3.0};
        input.Y_COORD = new double[]{1.0, 0.0, 2.0, 1.0};
        input.N_PTS = 3;
        input.DIST = 1.3;

        assertTrue(licAnalyzer.lic6(input));
    }

    @Test
    public void lic6PointsExactDist() {
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{0.0, 1.0, 2.0, 3.0};
        input.Y_COORD = new double[]{0.0, 1.0, 2.0, 1.0};
        input.N_PTS = 3;
        input.DIST = 1;

        assertFalse(licAnalyzer.lic6(input));
    }

    @Test
    public void lic6CoincideOutsideDist() {
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{0.0, 0.0, 0.0, 3.0};
        input.Y_COORD = new double[]{1.0, 4.0, 1.0, 1.0};
        input.N_PTS = 3;
        input.DIST = 2.9;

        assertTrue(licAnalyzer.lic6(input));
    }

    @Test
    public void lic6CoincideInsideDist() {
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{0.0, 0.0, 0.0, 3.0};
        input.Y_COORD = new double[]{1.0, 4.0, 1.0, 1.0};
        input.N_PTS = 3;
        input.DIST = 3.1;

        assertFalse(licAnalyzer.lic6(input));
    }

    @Test
    public void lic6CoincidentNumpointsEqualsNptsInside() {
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{0.0, 0.0, 0.0, 0.0};
        input.Y_COORD = new double[]{1.0, 0.5, 0.9, 1.0};
        input.N_PTS = 4;
        input.DIST = 0.6;

        assertFalse(licAnalyzer.lic6(input));
    }

    @Test
    public void lic6CoincidentNumpointsEqualsNptsOutside() {
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{0.0, 0.0, 0.0, 0.0};
        input.Y_COORD = new double[]{1.0, 0.5, 2.0, 1.0};
        input.N_PTS = 4;
        input.DIST = 0.9;

        assertTrue(licAnalyzer.lic6(input));
    }

    @Test
    public void lic6NumpointsEqualsNptsInside() {
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{0.0, 0.0, 0.0, 8.0};
        input.Y_COORD = new double[]{1.0, 0.5, 0.9, 1.0};
        input.N_PTS = 4;
        input.DIST = 0.6;

        assertFalse(licAnalyzer.lic6(input));
    }

    @Test
    public void lic6NumpointsEqualsNptsOutside() {
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{0.0, 0.0, 0.0, 8.0};
        input.Y_COORD = new double[]{1.0, 0.5, 2.0, 1.0};
        input.N_PTS = 4;
        input.DIST = 0.9;

        assertTrue(licAnalyzer.lic6(input));
    }
    

    @Test
    public void lic6LessThan3Points() {
        input.NUMPOINTS = 2;
        input.X_COORD = new double[]{0.0, 1.0};
        input.Y_COORD = new double[]{1.0, 0.0};
        input.N_PTS = 2;
        input.DIST = 1.3;

        Assertions.assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic6(input));
    }

    @Test
    public void lic6DistLessThan0() {
        input.NUMPOINTS = 3;
        input.X_COORD = new double[]{0.0, 1.0};
        input.Y_COORD = new double[]{1.0, 0.0};
        input.N_PTS = 2;
        input.DIST = -1;

        Assertions.assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic6(input));
    }

    @Test
    public void lic6NptsLessThan3() {
        input.NUMPOINTS = 3;
        input.X_COORD = new double[]{0.0, 1.0, 1.0};
        input.Y_COORD = new double[]{1.0, 0.0, 1.0};
        input.N_PTS = 2;
        input.DIST = 1;

        Assertions.assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic6(input));
    }

    @Test
    public void lic6NpointsLessThanNpts() {
        input.NUMPOINTS = 3;
        input.X_COORD = new double[]{0.0, 1.0, 1.0};
        input.Y_COORD = new double[]{1.0, 0.0, 1.0};
        input.N_PTS = 4;
        input.DIST = 1;

        Assertions.assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic6(input));
    }

///////////////////////////// Lic 7 /////////////////////////////

    @Test
    public void lic7DistanceLongerThanLength() {
        input.NUMPOINTS = 5;
        input.X_COORD = new double[]{0.0, 3.0, 1.0, 2.0, 7.0};
        input.Y_COORD = new double[]{0.0, 1.0, 1.0, 0.0, 5.0};
        input.K_PTS= 2;
        input.LENGTH1 = 1;

        assertTrue(licAnalyzer.lic7(input));
    }
    @Test

    public void lic7DistanceLongerThanLength2() {
        input.NUMPOINTS = 3;
        input.X_COORD = new double[]{0.0, 3.0, 1.0};
        input.Y_COORD = new double[]{0.0, 1.0, 1.0};
        input.K_PTS= 1;
        input.LENGTH1 = 1;

        assertTrue(licAnalyzer.lic7(input));
    }

    @Test
    public void lic7DistanceShorterThanLength() {
        input.NUMPOINTS = 3;
        input.X_COORD = new double[]{0.0, 3.0, 1.0};
        input.Y_COORD = new double[]{0.0, 1.0, 1.0};
        input.K_PTS= 1;
        input.LENGTH1 = 2;

        assertFalse(licAnalyzer.lic7(input));
    }

    @Test
    public void lic7InvalidLENGTH1() {
        input.NUMPOINTS = 3;
        input.X_COORD = new double[]{0.0, 3.0, 1.0};
        input.Y_COORD = new double[]{0.0, 1.0, 1.0};
        input.K_PTS= 1;
        input.LENGTH1 = -1;

        assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic7(input));
    }

    @Test
    public void lic7InvalidK_PTS() {
        input.NUMPOINTS = 5;
        input.X_COORD = new double[]{0.0, 0.0, 0.0, 3.0, 1.0};
        input.Y_COORD = new double[]{0.0, 0.0, 0.0, 1.0, 1.0};
        input.K_PTS= 4;
        input.LENGTH1 = 2;

        assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic7(input));
    }

///////////////////////////// Lic 8 /////////////////////////////

@Test
public void lic8TestUncontained() {
    input.NUMPOINTS = 5;
    input.A_PTS = 1;
    input.B_PTS = 1;
    input.X_COORD = new double[]{-2.0, 13.37, 0.0, 13.37, 2.0};
    input.Y_COORD = new double[]{0.0, 13.37, 0.0, 13.37, 0.0};
    input.RADIUS1 = 1.5;

    assertTrue(licAnalyzer.lic8(input));
}

@Test
public void lic8TestUncontainedOffset1() {
    input.NUMPOINTS = 6;
    input.A_PTS = 1;
    input.B_PTS = 1;
    input.X_COORD = new double[]{13.37, -2.0, 13.37, 0.0, 13.37, 2.0};
    input.Y_COORD = new double[]{13.37, 0.0, 13.37, 0.0, 13.37, 0.0};
    input.RADIUS1 = 1.5;

    assertTrue(licAnalyzer.lic8(input));
}

public void lic8TestContained() {
    input.NUMPOINTS = 6;
    input.A_PTS = 1;
    input.B_PTS = 1;
    input.X_COORD = new double[]{13.37, -2.0, 13.37, 0.0, 13.37, 2.0};
    input.Y_COORD = new double[]{13.37, 0.0, 13.37, 0.0, 13.37, 0.0};
    input.RADIUS1 = 3;

    assertFalse(licAnalyzer.lic8(input));
}

public void lic8TestContainedOffset1() {
    input.NUMPOINTS = 6;
    input.A_PTS = 1;
    input.B_PTS = 1;
    input.X_COORD = new double[]{13.37, -2.0, 13.37, 0.0, 13.37, 2.0};
    input.Y_COORD = new double[]{13.37, 0.0, 13.37, 0.0, 13.37, 0.0};
    input.RADIUS1 = 3;

    assertFalse(licAnalyzer.lic8(input));
}

@Test
public void lic8TestContainedBoundary() {
    input.NUMPOINTS = 5;
    input.A_PTS = 1;
    input.B_PTS = 1;
    input.X_COORD = new double[]{-2.0, 13.37, 0.0, 13.37, 2.0};
    input.Y_COORD = new double[]{0.0, 13.37, 2.0, 13.37, 0.0};
    input.RADIUS1 = 2;

    assertFalse(licAnalyzer.lic8(input));
}

@Test
public void lic8TestContainedBoundaryOffset1() {
    input.NUMPOINTS = 6;
    input.A_PTS = 1;
    input.B_PTS = 1;
    input.X_COORD = new double[]{13.37, -2.0, 13.37, 0.0, 13.37, 2.0};
    input.Y_COORD = new double[]{13.37, 0.0, 13.37, 2.0, 13.37, 0.0};
    input.RADIUS1 = 2;

    assertFalse(licAnalyzer.lic8(input));
}

@Test
public void lic8TestContainedZeroRadius() {
    input.NUMPOINTS = 5;
    input.A_PTS = 1;
    input.B_PTS = 1;
    input.X_COORD = new double[]{1.0, 13.37, 1.0, 13.37, 1.0};
    input.Y_COORD = new double[]{1.0, 13.37, 1.0, 13.37, 1.0};
    input.RADIUS1 = 0;

    assertFalse(licAnalyzer.lic8(input));
}

@Test
public void lic8TestUncontainedZeroRadius() {
    input.NUMPOINTS = 5;
    input.A_PTS = 1;
    input.B_PTS = 1;
    input.X_COORD = new double[]{1.0, 13.37, 0.0, 13.37, 1.0};
    input.Y_COORD = new double[]{1.0, 13.37, 1.0, 13.37, 1.0};
    input.RADIUS1 = 0;

    assertTrue(licAnalyzer.lic8(input));
}

@Test
public void lic8TestTooFewPoints() {
    input.NUMPOINTS = 1;

    assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic8(input));
}

@Test
public void lic8TestManyPoints() {
    input.NUMPOINTS = 101;

    assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic8(input));
}

@Test
public void lic8Test2Points() {
    input.NUMPOINTS = 2;
    //Since A_PTS, B_PTS >= 1 and A_PTS + B_PTS <= NUMPOINTS - 3 this should throw invalid argument exception 
    assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic8(input));
}

@Test
public void lic8Test4Points() {
    input.NUMPOINTS = 4;
    //Since A_PTS, B_PTS >= 1 and A_PTS + B_PTS <= NUMPOINTS - 3 this should throw invalid argument exception 
    assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic8(input));
}

@Test
public void lic8TestNegativeRadius() {
    input.RADIUS1 = -1;

    assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic8(input));
}

@Test
public void lic8TestA_PTSTooSmall() {
    input.A_PTS = 0;

    assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic8(input));
}

@Test
public void lic8TestB_PTSTooSmall() {
    input.B_PTS = 0;

    assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic8(input));
}

///////////////////////////// Lic 9 /////////////////////////////

    @Test
    public void lic9TestAngleBiggerOrSmaller() {
        input.D_PTS = 1;
        input.C_PTS = 1;
        input.NUMPOINTS = 5;
        input.EPSILON = 1;

        //26degree/0.451rad angle
        input.X_COORD = new double[]{5, 0, 0, 0, 5};
        input.Y_COORD = new double[]{0, 0, 0, 0, 2.453}; 
        assertTrue(licAnalyzer.lic9(input));
    }
    @Test
    public void lic9TestCoincidingPoints() {
        input.D_PTS = 1;
        input.C_PTS = 1;
        input.NUMPOINTS = 5;
        input.EPSILON = 1;

        input.X_COORD = new double[]{0, 0, 0, 0, 0};
        input.Y_COORD = new double[]{0, 0, 0, 0, 0}; 
        assertFalse(licAnalyzer.lic9(input));
    }
    @Test
    public void lic9TestInvalidNumpoints() {
        input.D_PTS = 1;
        input.C_PTS = 1;
        input.NUMPOINTS = 3;
        input.EPSILON = 1;

        input.X_COORD = new double[]{0, 0, 0};
        input.Y_COORD = new double[]{0, 0, 0}; 
        assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic9(input));
    }
    @Test
    public void lic9TestInvalidCpts() {
        input.D_PTS = 1;
        input.C_PTS = 0;
        input.NUMPOINTS = 5;
        input.EPSILON = 1;

        input.X_COORD = new double[]{0, 0, 0, 0, 0};
        input.Y_COORD = new double[]{0, 0, 0, 0, 0}; 
        assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic9(input));
    }

    ///////////////////////////// Lic 10 /////////////////////////////
    
    @Test
    public void lic10IsTrueIfThreePointsSeperatedByTwoAndFourPointsExistsWithAreaGreaterThan49() {
        //Arrange
        input.X_COORD = new double[]{1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 11.0};
        input.Y_COORD = new double[]{1.0, 0.0, 0.0, 11.0, 0.0, 0.0, 0.0, 0.0, 1.0};

        input.NUMPOINTS = input.X_COORD.length;
        input.E_PTS = 2;
        input.F_PTS = 4;
        input.AREA1 = 49.0;

        //Act
        boolean signal = licAnalyzer.lic10(input);

        // Assert
        assertTrue(signal);
    }

    @Test
    public void lic10ThrowsExceptionOnInvalidF_PTS() {
        //Arrange
        input.F_PTS = -1;

        //Act, Assert
        assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic10(input));
    }
    @Test
    public void lic10ThrowsExceptionOnInvalidE_PTS() {
        //Arrange
        input.E_PTS = -1;

        //Act, Assert
        assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic10(input));
    }
    @Test
    public void lic10ThrowsExceptionOnInvalidNUMPOINTS() {
        //Arrange
        input.NUMPOINTS = 5;
        input.E_PTS = 10;
        input.F_PTS = 10;

        //Act, Assert
        assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic10(input));
    }

///////////////////////////// Lic 11 /////////////////////////////

    @Test
    public void lic11SepBoundPos() {
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{0.0, 1.0, 10.0, -1.0};
        input.Y_COORD = new double[]{1.0, 0.0, 2.0, 0.0};
        input.G_PTS = 2;

        assertTrue(licAnalyzer.lic11(input));
    }

    @Test
    public void lic11SepBoundNeg() {
        input.NUMPOINTS = 4;
        input.X_COORD = new double[]{0.0, 1.0, 10.0, 1.0};
        input.Y_COORD = new double[]{1.0, 0.0, 2.0, 0.0};
        input.G_PTS = 2;

        assertFalse(licAnalyzer.lic11(input));
    }

    @Test
    public void lic11SepPos() {
        input.NUMPOINTS = 5;
        input.X_COORD = new double[]{0.0, 1.0, 2.0, 0.0, 4.0};
        input.Y_COORD = new double[]{1.0, 2.0, 3.0, 4.0, 5.0};
        input.G_PTS = 1;

        assertTrue(licAnalyzer.lic11(input));
    }

    @Test
    public void lic11SepNeg() {
        input.NUMPOINTS = 5;
        input.X_COORD = new double[]{0.0, 1.0, 2.0, 2.0, 4.0};
        input.Y_COORD = new double[]{5.0, 4.0, 3.0, 2.0, 1.0};
        input.G_PTS = 1;

        assertFalse(licAnalyzer.lic11(input));
    }

    @Test
    public void lic11BoundNeg() {

        input.NUMPOINTS = 4;
        input.G_PTS = 2;
        input.X_COORD = new double[]{1.0, 2.0, 3.0, 4.0};
        input.Y_COORD = new double[]{0.0, 0.0, 0.0, 0.0};

        assertFalse(licAnalyzer.lic11(input));
    }

    @Test
    public void lic11BoundPos() {

        input.NUMPOINTS = 4;
        input.G_PTS = 2;
        input.X_COORD = new double[]{1.0, 2.0, 3.0, 0.0};
        input.Y_COORD = new double[]{0.0, 0.0, 0.0, 0.0};

        assertTrue(licAnalyzer.lic11(input));
    }

    @Test
    public void lic11Numpoints2() {

        input.NUMPOINTS = 2;

        assertFalse(licAnalyzer.lic11(input));
    }

    @Test
    public void lic11GptsLessThan1() {
        input.G_PTS = 0;

        Assertions.assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic11(input));
    }

    @Test
    public void lic11GptsBiggerThanNumpointsMinus2() {
        input.NUMPOINTS = 5;
        input.G_PTS = 4;

        Assertions.assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic11(input));
    }

    @Test
    public void lic11NumpointsLessThan2() {
        input.NUMPOINTS = 1;
        input.G_PTS = 1;

        Assertions.assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic11(input));
    }

///////////////////////////// Lic 12 /////////////////////////////

    @Test
    public void lic12LENGTH1LessThanDistAndLENGTH2GreaterThanDist() {
        input.NUMPOINTS = 3;
        input.X_COORD = new double[]{0.0, 0.0, 1.0};
        input.Y_COORD = new double[]{0.0, 0.0, 1.0};
        input.K_PTS= 1;
        input.LENGTH1 = 1;
        input.LENGTH2 = 3;

        assertTrue(licAnalyzer.lic12(input));
    }
    
    @Test
    public void lic12LENGTH1LessThanDistAndLENGTH2NotGreaterThanDist() {
        input.NUMPOINTS = 3;
        input.X_COORD = new double[]{0.0, 3.0, 1.0};
        input.Y_COORD = new double[]{0.0, 1.0, 1.0};
        input.K_PTS= 1;
        input.LENGTH1 = 1;

        assertFalse(licAnalyzer.lic12(input));
    }

    @Test
    public void lic12InvalidLENGTH2() {
        input.NUMPOINTS = 3;
        input.X_COORD = new double[]{0.0, 3.0, 1.0};
        input.Y_COORD = new double[]{0.0, 1.0, 1.0};
        input.K_PTS= 1;
        input.LENGTH1 = 1;
        input.LENGTH2 = -1;

        assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic12(input));
    }

///////////////////////////// Lic 13 /////////////////////////////

    @Test
    public void lic13ThrowsIfRADIUS2IsLessThanZero() {
        // Arrange
        input.RADIUS2 = -1e-9;

        // Act, Assert
        assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic13(input));
    }

    @Test
    public void lic13IsFalseIfNumpointsIsLessThanFiveButMoreThanOne() {
        //Arrange
        input.X_COORD   = new double[]{ 0.0, 0.0 };
        input.Y_COORD   = new double[]{ 0.0, 0.0 };
        input.NUMPOINTS = input.X_COORD.length;

        //Act
        boolean signal  = licAnalyzer.lic13(input);

        //Assert
        assertFalse(signal);
    }
    @Test
    public void lic13ReturnsTrueWhenRADIUS1CanNotContainAllPointsButRadius2Can() {
        //Arrange
        input.RADIUS1   = 1.0;
        input.RADIUS2   = 3.0;
        input.A_PTS     = 1;
        input.B_PTS     = 1;
        input.X_COORD   = new double[]{ 0.5, 9.9, 1.7, 9.9, 1.9 };
        input.Y_COORD   = new double[]{ 0.5, 9.9, 1.7, 9.9, 1.9 };
        input.NUMPOINTS = input.X_COORD.length;

        //Act
        boolean signal  = licAnalyzer.lic13(input);

        //Assert
        assertTrue(signal);
    }

    @Test
    public void lic13ReturnsTrueWhenSetLiesOnRadius2Exactly() {
        //Arrange
        input.RADIUS1   = 1.0;
        input.RADIUS2   = 3.0;
        input.A_PTS     = 1;
        input.B_PTS     = 1;
        input.X_COORD   = new double[]{ 3.0, 9.9, 0.0, 9.9, -3.0 };
        input.Y_COORD   = new double[]{ 0.0, 9.9, 3.0, 9.9, 0.0 };
        input.NUMPOINTS = input.X_COORD.length;

        //Act
        boolean signal  = licAnalyzer.lic13(input);

        //Assert
        assertTrue(signal);
    }

    @Test
    public void lic13ReturnsFalseIfOnePointLiesJustOutsideRadius2() {
        //Arrange
        input.RADIUS1   = 1.0;
        input.RADIUS2   = 3.0;
        input.A_PTS     = 1;
        input.B_PTS     = 1;
        input.X_COORD   = new double[]{ 3.000, 9.9,  0.0,   9.9, -3.0001 };
        input.Y_COORD   = new double[]{ 0.0,   9.9,  3.000, 9.9,  0.0 };
        input.NUMPOINTS = input.X_COORD.length;

        //Act
        boolean signal  = licAnalyzer.lic13(input);

        //Assert
        assertFalse(signal);

    }

    @Test
    public void Lic13ReturnsFalseIfAllPointsAreOutsideRadius2() {
        //Arrange
        input.RADIUS1   = 1.0;
        input.RADIUS2   = 3.0;
        input.A_PTS     = 1;
        input.B_PTS     = 1;
        input.X_COORD   = new double[]{ 5.0, 9.9, 5.25, 9.9, 5.5 };
        input.Y_COORD   = new double[]{ 5.0, 9.9, 5.25, 9.9, 5.5 };
        input.NUMPOINTS = input.X_COORD.length;

        //Act
        boolean signal  = licAnalyzer.lic13(input);

        //Assert
        assertFalse(signal);
    }

    @Test
    public void Lic13ReturnsFalseIfAllPointsFitInsideRadius1() {
        //Arrange
        input.RADIUS1   = 1.0;
        input.RADIUS2   = 3.0;
        input.A_PTS     = 1;
        input.B_PTS     = 1;
        input.X_COORD   = new double[]{ 0.2, 9.9, 0.1, 9.9, 0.3 };
        input.Y_COORD   = new double[]{ 0.2, 9.9, 0.1, 9.9, 0.3 };
        input.NUMPOINTS = input.X_COORD.length;

        //Act
        boolean signal  = licAnalyzer.lic13(input);

        //Assert
        assertFalse(signal);
    }

///////////////////////////// Lic 14 /////////////////////////////

@Test
public void lic14BothTriangleCondsAreMet() {
    input.NUMPOINTS = 7;
    input.E_PTS = 1;
    input.F_PTS = 2;
    input.AREA1 = 7;
    input.AREA2 = 3;
    input.X_COORD = new double[]{0.0, 0.0, 5.0, 2.0, 99.99, 5.0, 2.0};
    input.Y_COORD = new double[]{0.0, 0.0, 0.0, 0.0, 99.99, 4.0, 2.0};
    assertTrue(licAnalyzer.lic14(input));
}

@Test
public void lic14TooFewPoints() {
    input.NUMPOINTS = 4;
    input.E_PTS = 1;
    input.F_PTS = 2;
    input.AREA1 = 7;
    input.AREA2 = 0.5;
    input.X_COORD = new double[]{0.0, 99.99, 5.0, -99.99};
    input.Y_COORD = new double[]{0.0, 0.0,   0.0,  0.0};
    assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic14(input));
}
@Test
public void lic14Area2IsNegative() {
    input.NUMPOINTS = 7;
    input.E_PTS = 1;
    input.F_PTS = 2;
    input.AREA1 = 7;
    input.AREA2 = -3;
    input.X_COORD = new double[]{0.0, 0.0, 5.0, 2.0, 99.99, 5.0, 2.0};
    input.Y_COORD = new double[]{0.0, 0.0, 0.0, 0.0, 99.99, 4.0, 2.0};
    assertThrows(IllegalArgumentException.class, () -> licAnalyzer.lic14(input));
}

}
