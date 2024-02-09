package group17;
import java.lang.Math;
import java.util.ArrayList;

public class LicAnalyzer {

    GeometryUtils geoUtils = new GeometryUtils();


    /*
    * Evaluates if set of two consecutive points greater than LENGTH1 apart exists.
    *
    * @param  input  object whose members hold data for the problem; relevant 
    *                fields for lic0 are: NUMPOINTS, X_COORD, Y_COORD, LENGTH1
    * @return        true if set exists, false otherwise
    */
    public boolean lic0(InputHandler input) {
        if (input.NUMPOINTS < 2 || input.NUMPOINTS > 100) {
            throw new IllegalArgumentException("Exception thrown from: LIC 0. Reason: NUMPOINTS outside range [2, 100].");
        } else if (input.LENGTH1 < 0.0) {
           throw new IllegalArgumentException("Exception thrown from: LIC 0. Reason: LENGTH1 < 0");
        } else if (input.X_COORD == null || input.Y_COORD == null) {
            throw new IllegalArgumentException("Exception thrown from: LIC 0. Reason: X_COORD or Y_COORD array points to null!");
        }

        for (int i = 1; i < input.NUMPOINTS; ++i) {
            double xCoordinate2 = input.X_COORD[i];
            double yCoordinate2 = input.Y_COORD[i];

            double xCoordinate1 = input.X_COORD[i - 1];
            double yCoordinate1 = input.Y_COORD[i - 1];

            double distance = geoUtils.dist(xCoordinate1, yCoordinate1, xCoordinate2, yCoordinate2);

            if (distance > input.LENGTH1) {
                return true;
            }
        }
        return false;
    }
    
    /** 
     * This function calculates Launch Interceptor Condition (LIC) number 1
     * 
     * @param input
     * @return true if at least one set of three consecutive data points that cannot all be contained within or on a circle of radius RADIUS1 exists.
     */
    public boolean lic1(InputHandler input) {
        double threshold_radius = input.RADIUS1;
        // Number of sets to check enclosing radius for.
        int n = input.NUMPOINTS - 2;
        double[] X = input.X_COORD;
        double[] Y = input.Y_COORD;
        
        if (input.NUMPOINTS < 2 || input.NUMPOINTS > 100) {
            throw new IllegalArgumentException("Exception thrown from: LIC 1. Reason: NUMPOINTS outside range [2, 100].");
        }
        if (threshold_radius < 0) {
            throw new IllegalArgumentException("Exception thrown from: LIC 1. Reason: RADIUS1 cannot be strictly less than 0.");
        }
        if (input.NUMPOINTS == 2) {
            // No set of 3 points exists, so criterion cannot be fulfilled.
            return false;
        }

        for (int i = 0; i < n; i++) {
            double radius = geoUtils.minimumEnclosingRadius(X[i],Y[i],X[i+1],Y[i+1],X[i+2],Y[i+2]);
            if (radius > threshold_radius) {
                // The three points cannot fit within threshold radius.
                return true;
            }
        }

        return false;
    }

    /**
     * This function calculates Launch Interceptor Condition (LIC) number 2
     *  
     * @param input.NUMPOINTS,EPSILON,X_COORD,Y_COORD
     * @return true if any 3 consecutive points make an angle outside of the EPSILON radius around PI/180 deg
     */
    public boolean lic2(InputHandler input) {

        if (input.NUMPOINTS < 2 || input.NUMPOINTS > 100) {
            throw new IllegalArgumentException("NUMPOINTS must be inside range [2, 100]");
        }

        if (input.NUMPOINTS == 2) {
            return false;
        }

        if (input.EPSILON < 0 || input.EPSILON >= Math.PI) {
            throw new IllegalArgumentException("EPSILON must be between 0 (inclusive) and PI (non-inclusive");
        }

        for (int i = 0; i < input.NUMPOINTS - 2; i++) {
            double x1 = input.X_COORD[i], y1 = input.Y_COORD[i];
            double x2 = input.X_COORD[i+1], y2 = input.Y_COORD[i+1];
            double x3 = input.X_COORD[i+2], y3 = input.Y_COORD[i+2];

            if (x2 == x1 && y2 == y1 || x2 == x3 && y2 == y3) {
                continue;
            }

            double angle = geoUtils.calcAngle(x1, y1, x2, y2, x3, y3);

            if (angle < (Math.PI - input.EPSILON) || angle > (Math.PI + input.EPSILON)) {
                return true;
            }
        }

        return false;
    }

    /**
     * This function calculates Launch Interceptor Condition (LIC) number 3
     *  
     * @param input.NUMPOINTS,AREA1,X_COORD,Y_COORD
     * @return true if any 3 consecutive points make a triangle with area greater than AREA1
     */
    public boolean lic3(InputHandler input) {

        if (input.NUMPOINTS < 3 || input.NUMPOINTS > 100) {
            throw new IllegalArgumentException("NUMPOINTS must be between 3 (inclusive) and 100 (inclusive)");
        }

        if (input.AREA1 < 0) {
            throw new IllegalArgumentException("AREA1 must be greater than 0");
        }

        for (int i = 0; i < input.NUMPOINTS - 2; i++) {
            double x1 = input.X_COORD[i], y1 = input.Y_COORD[i];
            double x2 = input.X_COORD[i+1], y2 = input.Y_COORD[i+1];
            double x3 = input.X_COORD[i+2], y3 = input.Y_COORD[i+2];

            double triangleArea = geoUtils.calcTriangleArea(x1, y1, x2, y2, x3, y3);
            if (triangleArea > input.AREA1) {
                return true;
            }
        }

        return false;
    }

    /**
     *  Checks if Q_PTS consecutive data points lie in more than QUADS quadrants.
     *  @returns true, false, or @throws IllegalArgumentException
     *  @param input.NUMPOINTS,QUADS,Q_PTS,X_COORD,Y_COORD
    */
    public boolean lic4(InputHandler input) throws IllegalArgumentException{
        //Note: 
        //  q1 = {x = [0, inf), y = [0, inf)},
        //  q2 = {x = (0, -inf), y = [0, inf)},
        //  q3 = {x = [0, -inf), y = (0, -inf)},
        //  q4 = {x = (0, inf), y = (0, -inf)}
        
        //Initialize
        ArrayList<Integer> numOfQuadrantsCovered = new ArrayList<Integer>();
        int currentPointQuadrant = 0;

        //Exeception handling
        if (input.NUMPOINTS  <= 1 || input.NUMPOINTS > 100){
            throw new IllegalArgumentException("Exception thrown from: LIC 4. Reason: NUMPOINTS outside range [2, 100].");    
        }
        else if (input.QUADS > 3 || input.QUADS <= 0){
            throw new IllegalArgumentException("Exception thrown from: LIC 4. Reason: QUADS outside range [1, 3].");    
        }
        else if (input.Q_PTS > input.NUMPOINTS || input.Q_PTS <= 1){
            throw new IllegalArgumentException("Exception thrown from: LIC 4. Reason: Q_PTS outside range [2, NUMPOINTS].");    
        }
        
        //Valid input edge case that must be false
        if (input.Q_PTS  <= input.QUADS){
            return false;    
        }

        //Edge-case check if all points lie in more than QUADS quadrants.
        if(input.NUMPOINTS == input.Q_PTS){
                for(int i = 0; i < input.Q_PTS; i++){
                    if(input.X_COORD[i] >= 0 && input.Y_COORD[i] >= 0)
                        currentPointQuadrant = 1;
                    else if(input.X_COORD[i] < 0 && input.Y_COORD[i] >= 0)
                        currentPointQuadrant = 2;
                    else if(input.X_COORD[i] <= 0 && input.Y_COORD[i] < 0)
                        currentPointQuadrant = 3;
                    else if(input.X_COORD[i] > 0 && input.Y_COORD[i] < 0)
                        currentPointQuadrant = 4;
    
                    //If quadrant is not already covered, increase coverage.
                    if(!numOfQuadrantsCovered.contains(currentPointQuadrant)){
                        numOfQuadrantsCovered.add(currentPointQuadrant);
                        //Seen points covers more than QUADS
                        if(numOfQuadrantsCovered.size() > input.QUADS)
                            return true;
                    }
                }
                return false;
        }
        else {
            //Create sliding window of size Q_PTS to check
            // if points in window lie in more than QUADS quadrants.
            for (int i = 0; i < input.NUMPOINTS - input.Q_PTS; i++){
                for(int j = i; j < input.Q_PTS + i; j++){
                    if(input.X_COORD[j] >= 0 && input.Y_COORD[j] >= 0)
                        currentPointQuadrant = 1;
                    else if(input.X_COORD[j] < 0 && input.Y_COORD[j] >= 0)
                        currentPointQuadrant = 2;
                    else if(input.X_COORD[j] <= 0 && input.Y_COORD[j] < 0)
                        currentPointQuadrant = 3;
                    else if(input.X_COORD[j] > 0 && input.Y_COORD[j] < 0)
                        currentPointQuadrant = 4;
    
                    //If quadrant is not already covered, increase coverage.
                    if(!numOfQuadrantsCovered.contains(currentPointQuadrant)){
                        numOfQuadrantsCovered.add(currentPointQuadrant);
                        //Seen points covers more than QUADS
                        if(numOfQuadrantsCovered.size() > input.QUADS)
                            return true;
                    }
                }
                //If points in tested window failed, restart coverage check n move window.
                numOfQuadrantsCovered.clear();
            }
        }
        return false;
    }

     /*
     * Evaluates if a set of two consecutive points exists where latter point has a lower value on its x-coordinate,
     * more specifically, if it exists points such that X[i] < X[j], where j = i - 1 and X is a container storing
     * every point's x-coordinate.
     *
     * @param  input  object whose members hold data for the problem; relevant 
                      fields for lic5 are: NUMPOINTS, X_COORD, Y_COORD
     * @return        true if set exists, false otherwise
     */
    public boolean lic5(InputHandler input) throws IllegalArgumentException {
        if (input.NUMPOINTS < 2 || input.NUMPOINTS > 100) {
            throw new IllegalArgumentException("Exception thrown from: LIC 5. Reason: NUMPOINTS outside range [2, 100].");
        } else if (input.X_COORD == null || input.Y_COORD == null) {
            throw new IllegalArgumentException("Exception thrown from: LIC 5. X_COORD or Y_COORD array points to null!");
        }
        for (int i = 1; i < input.NUMPOINTS; ++i) {
            double xCoordinate2 = input.X_COORD[i];
            double xCoordinate1 = input.X_COORD[i - 1];

            if (xCoordinate2 < xCoordinate1) {
                return true;
            }
        }
        return false;
    }

    
    
    /** 
     * This function calculates Launch Interceptor Condition (LIC) number 6.
     * @param input
     * @return boolean
     */
    public boolean lic6(InputHandler input) {
        int n_pts = input.N_PTS;
        double dist = input.DIST;
        int n = input.NUMPOINTS;
        double[] X = input.X_COORD;
        double[] Y = input.Y_COORD;

        if (dist < 0) {
            throw new IllegalArgumentException("Exception thrown from: LIC 6. DIST must be greater than or equal to 0.");
        } else if (n_pts < 3) {
            throw new IllegalArgumentException("Exception thrown from: LIC 6. N_PTS must be greater than or equal to 3.");
        } else if (n < n_pts) {
            throw new IllegalArgumentException("Exception thrown from: LIC 6. NUMPOINTS must be greater than or equal to N_PTS.");
        }

        for (int i = 0; i <= n - n_pts; i++) {
            for (int j = i + 1; j < i + n_pts - 1; j++) {
                // Strictly greater to satisfy condition.
                if (geoUtils.distanceFromPointToLine(X[i],Y[i],X[i+n_pts-1],Y[i+n_pts-1],X[j],Y[j]) > dist) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * This function calculates Launch Interceptor Condition (LIC) number 7
     *
     * @param input.NUMPOINTS,LENGTH1,X_COORD,Y_COORD,K_PTS
     * @return true if the set of two data points separated by input.K_PTS point's distance is greater than LENGTH1
     */
    public boolean lic7(InputHandler input) {

        if (input.NUMPOINTS < 2 || input.NUMPOINTS > 100) {
            throw new IllegalArgumentException("NUMPOINTS must be inside range [2, 100]");
        }

        if (input.NUMPOINTS == 2) {
            return false;
        }

        if (input.K_PTS < 1 || input.K_PTS > input.NUMPOINTS-2) {
            throw new IllegalArgumentException("K_PTS must be between 1 (inclusive) and NUMPOINTS-2 (inclusive)");
        }

        if (input.LENGTH1 < 0) {
            throw new IllegalArgumentException("LENGTH1 must be a positive number");
        }

        for (int i = 0; i < input.NUMPOINTS - input.K_PTS - 1; i++) {
            double x1 = input.X_COORD[i], y1 = input.Y_COORD[i];
            double x2 = input.X_COORD[i + input.K_PTS + 1], y2 = input.Y_COORD[i + input.K_PTS + 1];

            if (geoUtils.dist(x1, y1, x2, y2) > input.LENGTH1) {
                return true;
            }
        }
        return false;
    }

    /**
     * This function calculates Launch Interceptor Condition (LIC) number 8
     * Utilizes helper methods in GeometryUtils.java to calculate the smallest possible radius between 3 points.
     * The first two points are separated by A_PTS.
     * The last two points are separated by B_PTS.
     *
     * @param input.NUMPOINTS,RADIUS1,X_COORD,Y_COORD,A_PTS,B_PTS
     * @return true if three points, separated by A_PTS and B_PTS cannot be contained on or within a circle of radius RADIUS1
     */
    public boolean lic8(InputHandler input) {

        double[] X = input.X_COORD;
        double[] Y = input.Y_COORD;
        int A = input.A_PTS;
        int B = input.B_PTS;
        
        if (input.NUMPOINTS < 2 || input.NUMPOINTS > 100) {
            throw new IllegalArgumentException("NUMPOINTS must be between 2 and 100.");
        }
        if (input.RADIUS1 < 0) {
            throw new IllegalArgumentException("RADIUS1 cannot be lower than 0.");
        }
        if (A < 1) {
            throw new IllegalArgumentException("A_PTS must be greater than or equal to 1.");
        }
        if (B < 1) {
            throw new IllegalArgumentException("A_PTS must be greater than or equal to 1.");
        }
        if (A + B > (input.NUMPOINTS - 3)) {
            throw new IllegalArgumentException("A_PTS + B_PTS must be less than or equal to NUMPOINTS - 3.");
        }
        if (input.NUMPOINTS < 5) {
            return false;
        }

        for (int i = 0; i < input.NUMPOINTS - A - B - 2; i++) {
            double radius = geoUtils.minimumEnclosingRadius(X[i],Y[i],X[i+1+A],Y[i+1+A],X[i+2+A+B],Y[i+2+A+B]);
            if (radius > input.RADIUS1) {
                return true;
            }
        }

        return false;
    }

    /**
     *  Checks if a set of three points is separated by C_PTS and D_PTS, and if angle condition is met.
     *  @returns true, false, or @throws IllegalArgumentException
     *  @param input.NUMPOINTS,C_PTS,D_PTS,EPSILON,X_COORD,Y_COORD
    */
    public boolean lic9(InputHandler input) throws IllegalArgumentException {

        if(input.NUMPOINTS < 5 || input.NUMPOINTS > 100)
            throw new IllegalArgumentException("Exception thrown from: LIC 9. Reason: NUMPOINTS < 5 or NUMPOINTS > 100.");
        else if(input.C_PTS < 1 || input.D_PTS < 1 )
            throw new IllegalArgumentException("Exception thrown from: LIC 9. Reason: C_PTS < 1 or D_PTS < 1.");
        else if(input.C_PTS + input.D_PTS > input.NUMPOINTS - 3)
            throw new IllegalArgumentException("Exception thrown from: LIC 9. Reason: C_PTS + D_PTS > NUMPOINTS - 3");
        double x1,x2,x3,y1,y2,y3 = 0;
        int gap1 = input.C_PTS;
        int gap2 = input.D_PTS;
        for(int i = 0; i < input.NUMPOINTS - (gap1 + gap2); i++)
        {
            x1 = input.X_COORD[i];
            x2 = input.X_COORD[i+gap1+1];
            x3 = input.X_COORD[i+gap1+1+gap2+1];

            y1 = input.Y_COORD[i];
            y2 = input.Y_COORD[i+gap1+1];
            y3 = input.Y_COORD[i+gap1+1+gap2+1];

            //coinciding first or last point is undefined -> false
            if(geoUtils.sameCoordinate(x1, x2) && geoUtils.sameCoordinate(y1, y2) || geoUtils.sameCoordinate(x3, x2) && geoUtils.sameCoordinate(y3, y2))
                return false;
            double angle = geoUtils.calcAngle(x1, y1, x2, y2, x3, y3);
            if(angle < (Math.PI - input.EPSILON) || angle > (Math.PI + input.EPSILON))
                return true;

            x1 = input.X_COORD[i];
            x2 = input.X_COORD[i+gap2+1];
            x3 = input.X_COORD[i+gap1+1+gap2+1];

            y1 = input.Y_COORD[i];
            y2 = input.Y_COORD[i+gap2+1];
            y3 = input.Y_COORD[i+gap1+1+gap2+1];  
            
            angle = geoUtils.calcAngle(x1, y1, x2, y2, x3, y3);
            if(angle < (Math.PI - input.EPSILON) || angle > (Math.PI + input.EPSILON))
                return true;
        }
        return false;
    }

    /*
     * Evaluate if three points exists such that point 1 and 2 are seperated by quantity1 and point 2 and point 3 are
     * seperated by quantity2 with area greater than AREA1.
     *
     * @param quantity1 Number of consecutive intervening points between point 1 and point 2
     * @param quantity2 Number of consecutive intervening points between point 2 and point 3
     * @param input Class holding relevant data
     *
     * @return boolean True if such points exists, false otherwise
     */
    private boolean helperLic10ThreePointsSeperatedBy(int quantity1, int quantity2, InputHandler input)
    {
        for (int i = quantity1 + quantity2 + 2; i < input.NUMPOINTS; ++i) {
            int vertex3 = i;
            int vertex2 = i - quantity1 - 1;
            int vertex1 = i - quantity1 - quantity2 - 2;

            //Calculate triangle area from three points using shoelace formula
            double xCoordinate1 = input.X_COORD[vertex1];
            double yCoordinate1 = input.Y_COORD[vertex1];

            double xCoordinate2 = input.X_COORD[vertex2];
            double yCoordinate2 = input.Y_COORD[vertex2];

            double xCoordinate3 = input.X_COORD[vertex3];
            double yCoordinate3 = input.Y_COORD[vertex3];

            double triangleArea = 0.5 * Math.abs( (xCoordinate1 - xCoordinate3) * (yCoordinate2 - yCoordinate1)
                                                 -(xCoordinate1 - xCoordinate2) * (yCoordinate3 - yCoordinate1)
                                                );
            if (triangleArea > input.AREA1) {
                return true;
            }
        }

        return false;
    }

    /*
     * Evaluates existence of a set of three points seperated by E_PTS and F_PTS
     * respectively that together has area greater than AREA1.
     *
     * @param  input  object whose members hold data for the problem; relevant 
                      fields for lic10 are: NUMPOINTS, E_PTS, F_PTS, AREA1, X_COORD, Y_COORD
     * @return        true if set exists, false otherwise
     */
    public boolean lic10(InputHandler input) {
        if (input.NUMPOINTS < 5 || input.NUMPOINTS > 100) {
            throw new IllegalArgumentException("Exception thrown from: LIC 10. Reason: NUMPOINTS outside range [5, 100].");
        } else if (input.E_PTS < 1 || input.F_PTS < 1) {
            throw new IllegalArgumentException("Exception thrown from: LIC 10. Reason: E_PTS < 1 or F_PTS < 1.");
        } else if (input.NUMPOINTS < input.E_PTS + input.F_PTS + 3) {
            throw new IllegalArgumentException("Exception thrown from: LIC 10. Reason: NUMPOINTS < E_PTS + F_PTS + 3.");
        }

        return helperLic10ThreePointsSeperatedBy(input.F_PTS, input.E_PTS, input)
            || helperLic10ThreePointsSeperatedBy(input.E_PTS, input.F_PTS, input);

    }


    
    /** 
     * This function calculates Launch Interceptor Condition (LIC) number 11
     * 
     * @param input
     * @return boolean
     */
    public boolean lic11(InputHandler input) {
        int gpts = input.G_PTS;
        int numpoints = input.NUMPOINTS;
        double[] X = input.X_COORD;

        if (input.NUMPOINTS < 2 || input.NUMPOINTS > 100) {
            throw new IllegalArgumentException("Exception thrown from: LIC 11. Reason: NUMPOINTS must be between 2 (inclusive) and 100 (inclusive)");
        } else if (numpoints == 2) {
            return false;
        } else if (gpts < 1) {
            throw new IllegalArgumentException("Exception thrown from: LIC 11. Reason: G_PTS must be greater than 1.");
        } else if (gpts > numpoints - 2) {
            throw new IllegalArgumentException("Exception thrown from: LIC 11. Reason: G_PTS must be less than NUMPOINTS - 2.");
        }

        for (int i = 0; i < numpoints - gpts - 1; i++) {
            if (X[i + gpts + 1] < X[i]) {
                return true;
            }
        }

        return false;
    }

    /**
     * This function calculates Launch Interceptor Condition (LIC) number 12
     *
     * @param input.NUMPOINTS,LENGTH1,LENGTH2,X_COORD,Y_COORD,K_PTS
     * @return true if there are at least two sets of two data points separated by input.K_PTS data points which distance is greater than LENGTH1 and less than LENGTH2 respectively
     */
    public boolean lic12(InputHandler input) {

        if (input.NUMPOINTS < 2 || input.NUMPOINTS > 100) {
            throw new IllegalArgumentException("NUMPOINTS must be inside the range [2, 100]");
        }

        if (input.NUMPOINTS == 2) {
            return false;
        }

        if (input.K_PTS < 1 || input.K_PTS > input.NUMPOINTS-2) {
            throw new IllegalArgumentException("K_PTS must be between 1 (inclusive) and NUMPOINTS-2 (inclusive)");
        }

        if (input.LENGTH1 < 0) {
            throw new IllegalArgumentException("LENGTH1 must be a positive number");
        }

        if (input.LENGTH2 < 0) {
            throw new IllegalArgumentException("LENGTH2 must be a positive number");
        }

        boolean distGreaterThanLENGTH1 = false;
        boolean distLessThanLENGTH2 = false;

        for (int i = 0; i < input.NUMPOINTS - input.K_PTS - 1; i++) {
            double x1 = input.X_COORD[i], y1 = input.Y_COORD[i];
            double x2 = input.X_COORD[i + input.K_PTS + 1], y2 = input.Y_COORD[i + input.K_PTS + 1];

            if (geoUtils.dist(x1, y1, x2, y2) > input.LENGTH1) {
                distGreaterThanLENGTH1 = true;
            }

            if (geoUtils.dist(x1 , y1, x2, y2) < input.LENGTH2) {
                distLessThanLENGTH2 = true;
            }
        }

        return distGreaterThanLENGTH1 && distLessThanLENGTH2;
    }

    /*
     * Evaluate if circle can hold set of three points seperated by gap1 and gap2 
     * points.
     *
     * @param gap1     Number of intervening points between point 1 and 2.
     * @param gap2     Number of intervening points between point 2 and 3.
     * @param radius   Radius of circle
     * @param input    Class holding relevant data.
     *
     * @return         True if set exists, false otherwise.
     */
    private boolean CircleContainsSetOfThreePoints(int gap1, int gap2, double radius, 
                                                   InputHandler input)
    {
        for (int i = gap1 + gap2 + 2; i < input.NUMPOINTS; ++i) {

            int vertex3 = i;
            int vertex2 = i - gap1 - 1;
            int vertex1 = i - gap1 - gap2 - 2;

            double xCoordinate1 = input.X_COORD[vertex1];
            double yCoordinate1 = input.Y_COORD[vertex1];

            double xCoordinate2 = input.X_COORD[vertex2];
            double yCoordinate2 = input.Y_COORD[vertex2];

            double xCoordinate3 = input.X_COORD[vertex3];
            double yCoordinate3 = input.Y_COORD[vertex3];

            boolean point1Found = geoUtils.pointInsideCircle(xCoordinate1, yCoordinate1, radius);

            boolean point2Found = geoUtils.pointInsideCircle(xCoordinate2, yCoordinate2, radius);

            boolean point3Found = geoUtils.pointInsideCircle(xCoordinate3, yCoordinate3, radius);

            if (point1Found && point2Found && point3Found) {
                return true;
            }

            // Only middle point is affected by gap order, check reverse if worthwhile.
            // I.e. {point1 ----- points2 - points3} vs {point1 - points2 ----- points3}
            if (!point2Found && point1Found && point3Found) {
                vertex2 = i - gap2 - 1;

                xCoordinate2 = input.X_COORD[vertex2];
                yCoordinate2 = input.Y_COORD[vertex2];

                point2Found = geoUtils.pointInsideCircle(xCoordinate2, yCoordinate2, radius);

                if (point2Found) {
                    return true;
                }
            }


        }
        return false;
    }

    /*
     * Evaluates existence of a set of three points, where point 1 and 2 are 
     * seperated by either A_PTS or B_PTS and point 2 and 3 seperated by the other, that can 
     * be contained in a circle with radius RADIUS2 but not inside a circle with RADIUS1. 
     *
     * @param  input  object whose members hold data for the problem.
     *                Lic13 uses: NUMPOINTS, A_PTS, B_PTS, RADIUS1, RADIUS2, X_COORD, Y_COORD
     *
     * @return        true if both requirements are met, false otherwise
     */
    public boolean lic13(InputHandler input) {
        if (input.RADIUS2 < 0.0) {
            throw new IllegalArgumentException("Exception thrown from: LIC 13. Reason: RADIUS2 must be 0 or greater.");
        }
        if (input.NUMPOINTS > 1 && input.NUMPOINTS < 5) {
            return false;
        }

        boolean wholeSetWithinRadius1 = CircleContainsSetOfThreePoints(input.A_PTS, input.B_PTS, 
                                                                       input.RADIUS1, input);

        boolean wholeSetWithinRadius2 = CircleContainsSetOfThreePoints(input.A_PTS, input.B_PTS, 
                                                                       input.RADIUS2, input);

        return !wholeSetWithinRadius1 && wholeSetWithinRadius2;
    }

    /**
     *  Checks if a set of three points is separated by E_PTS and F_PTS, and if formed triangle meets condition.
     *  cond1 is x1,x2,x3,y1,y2,y3 provides area strictly greater than AREA1
     *  cond2 is x1,x2,x3,y1,y2,y3 provides area stricly less than AREA2
     *  @returns true, false, or @throws IllegalArgumentException
     *  @param input.NUMPOINTS,E_PTS,F_PTS,AREA1,AREA2,X_COORD,Y_COORD
    */
    public boolean lic14(InputHandler input) throws IllegalArgumentException {
        //Initialize
        double x1,x2,x3,y1,y2,y3 = 0;
        double area1 = input.AREA1;
        double area2 = input.AREA2;
        int gapE = input.E_PTS;
        int gapF = input.F_PTS;
        int numPts = input.NUMPOINTS;
        double triangle1, triangle2 = 0;
        
        //Exception handling
        if(numPts < 5 || numPts > 100)
            throw new IllegalArgumentException("Exception thrown from: LIC 14. Reason: NUMPOINTS < 5 or NUMPOINTS > 100.");
        else if(gapE < 1 || gapF < 1 )
            throw new IllegalArgumentException("Exception thrown from: LIC 14. Reason: E_PTS < 1 or F_PTS < 1.");
        else if(gapE + gapF > numPts - 3)
            throw new IllegalArgumentException("Exception thrown from: LIC 14. Reason: E_PTS + F_PTS > NUMPOINTS - 3");
        else if(area2 < 0)
            throw new IllegalArgumentException("Exception thrown from: LIC 14. Reason: AREA2 < 0");
        
        //Check all data points
        for(int i = 0; i < numPts - (gapE + gapF + 2); i++) {
            x1 = input.X_COORD[i];
            x2 = input.X_COORD[i+gapE+1];
            x3 = input.X_COORD[i+gapE+1+gapF+1];

            y1 = input.Y_COORD[i];
            y2 = input.Y_COORD[i+gapE+1];
            y3 = input.Y_COORD[i+gapE+1+gapF+1];

            //Found points satifies cond1 -> find second triangle
            triangle1 = geoUtils.calcTriangleArea(x1, y1, x2, y2, x3, y3);
            if(triangle1 > area1)
            {
                //The same points satisfy cond2
                if(triangle1 < area2)
                    return true;
                //Iterate all points to find points that satify cond2
                else {
                    for(int j = 0; j < numPts - (gapE + gapF + 2); j++)
                    {
                        x1 = input.X_COORD[j];
                        x2 = input.X_COORD[j+gapE+1];
                        x3 = input.X_COORD[j+gapE+1+gapF+1];

                        y1 = input.Y_COORD[j];
                        y2 = input.Y_COORD[j+gapE+1];
                        y3 = input.Y_COORD[j+gapE+1+gapF+1];
                        
                        triangle2 = geoUtils.calcTriangleArea(x1, y1, x2, y2, x3, y3);
                        if(triangle2 < area2)
                            return true;
                    }
                }
                //cond2 cannot be satisfied.
                return false;
            }
        }
        return false;
    }


}
