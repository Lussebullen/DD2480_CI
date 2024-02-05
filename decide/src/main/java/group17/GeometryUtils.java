package group17;

/**
 * This class contains all the geometry utility functions needed to solve the LICs
 * 
 * @author Group17
 * @verison 1.0
 */
public class GeometryUtils {

    /**
     * The method calcAngle calculates the angle between the two vectors that go from (x2,y2) to (x1,y1) and (x3,y3) respectively 
     * 
     * @param x1 The x coodinate of the first point
     * @param y1 The y coodinate of the first point
     * @param x2 The x coodinate of the second point where the angle is formed
     * @param y2 The y coodinate of the second point where the angle is formed
     * @param x3 The x coodinate of the third point
     * @param y3 The y coodinate of the third point
     * @return The angle between two vectors that go from (x2,y2) to (x1,y1) and (x3,y3) respectively 
     */
    public double calcAngle(double x1, double y1, double x2, double y2, double x3, double y3) {
        double uX = x2 - x1;
        double uY = y2 - y1;
        double vX = x2 - x3;
        double vY = y2 - y3;
        double uv_dotProduct = (uX * vX) + (uY * vY);

        double uMagnitude = Math.sqrt(Math.pow(uX, 2) + Math.pow(uY, 2));
        double vMagnitude = Math.sqrt(Math.pow(vX, 2) + Math.pow(vY, 2));

        return Math.acos(uv_dotProduct / (uMagnitude * vMagnitude));
    }

    /**
     * Calculates and returns the distance between the points (x1,y1), (x2,y2).
     * @param x1 X coordinate of first point.
     * @param y1 Y coordinate of first point.
     * @param x2 X coordinate of second point.
     * @param y2 Y coordinate of second point.
     * @return double
     */
    public double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
    }

    /*
     * A equality comparator of two doubles using a threshold
     * @param a, is the first double
     * @param b, is the second double
     * @return true, if equal, false otherwise
     */ 
    public boolean sameCoordinate(double a, double b) {
        final double epsilon = 1e-9;    // The acceptable threeshold
        return Math.abs(a - b) < epsilon;
    }

     /**
     * Calculates the area of the triangle between the points (x1, y1), (x2, y2) and (x3, y3).
     * The distance between the first two points is treated as the length of the hypotenuse.
     * The angle is formed at point (x2, y2).
     * 
     * @param x1 The x coodinate of the first point
     * @param y1 The y coodinate of the first point
     * @param x2 The x coodinate of the second point where the angle is formed
     * @param y2 The y coodinate of the second point where the angle is formed
     * @param x3 The x coodinate of the third point
     * @param y3 The y coodinate of the third point
     * @return The area of the triangle defined by the points.
     */
    public double calcTriangleArea(double x1, double y1, double x2, double y2, double x3, double y3) {
        double angle = calcAngle(x1, y1, x2, y2, x3, y3);
        double hypotenuse = dist(x1, y1, x2, y2);
        double height = Math.sin(angle) * hypotenuse;
        double base = dist(x2, y2, x3, y3);

        double triangleArea = height * base / 2;
        return triangleArea;
    }

    /*
    * Returns true if a point lies on circle with specified radius. Uses circle 
    * equation relative to origin. "Lies on" means inside circle or on its 
    * boundry formed by its radius.
    * 
    * @param x             Point's x-coordinate
    * @param y             Point's y-coordinate
    * @param circleRadius  Radius for circle of interest
    *
    * @return              True if circle contains point, false otherwise.
    */
    public boolean pointInsideCircle(double x, double y, double circleRadius) {
        final double epsilon             = 1e-9;
        final double squaredRadiusPoint  = Math.pow(x - 0.0, 2) + Math.pow(y - 0.0, 2);
        final double squaredRadiusCircle = Math.pow(circleRadius, 2);
                                                                                  
        if (Math.abs(squaredRadiusPoint - squaredRadiusCircle) < epsilon) {
            return true;
        } else {
            return dist(x, y, 0, 0) < circleRadius;
        }
    }

    /** 
     * Calculates and returns the circumradius of the points (x1,y1), (x2,y2), (x3,y3).
     * @param x1 X coordinate of first point.
     * @param y1 Y coordinate of first point.
     * @param x2 X coordinate of second point.
     * @param y2 Y coordinate of second point.
     * @param x3 X coordinate of third point.
     * @param y3 Y coordinate of third point.
     * @return double
     */
    public double circumRadius(double x1, double y1, double x2, double y2, double x3, double y3) {
        double a = dist(x1, y1, x2, y2);
        double b = dist(x2, y2, x3, y3);
        double c = dist(x3, y3, x1, y1);
        double s = (a + b + c) / 2;

        if (s <= 0) {
            // Degenerate triangle, 3 coinciding points.
            return 0.0;
        }
        
        return a*b*c / (4*Math.sqrt(s*(s-a)*(s-b)*(s-c)));
    }

    /** 
     * Calculates and returns the radius of the smallest enclosing circle for the points (x1,y1), (x2,y2), (x3,y3).
     * @param x1 X coordinate of first point.
     * @param y1 Y coordinate of first point.
     * @param x2 X coordinate of second point.
     * @param y2 Y coordinate of second point.
     * @param x3 X coordinate of third point.
     * @param y3 Y coordinate of third point.
     * @return double
     */
    public double minimumEnclosingRadius(double x1, double y1, double x2, double y2, double x3, double y3) {
        double radius = circumRadius(x1, y1, x2, y2, x3, y3);
        double[] X = new double[] {x1, x2, x3};
        double[] Y = new double[] {y1, y2, y3};
        for (int i=0; i<3; i++) {
            double midX = (X[(i+1) % 3] + X[i]) / 2;
            double midY = (Y[(i+1) % 3] + Y[i]) / 2;
            double radius_temp = dist(X[i], Y[i], X[(i+1) % 3], Y[(i+1) % 3]) / 2;
            // If last point contained, update radius.
            if (dist(midX, midY, X[(i+2) % 3], Y[(i+2) % 3]) <= radius_temp) {
                radius = radius_temp;
            }
        }
        return radius;
    }

    /** 
     * Calculates and returns the distance from (x3,y3) to the line formed by (x1,y1), (x2,y2).
     * @param x1 X coordinate of first point.
     * @param y1 Y coordinate of first point.
     * @param x2 X coordinate of second point.
     * @param y2 Y coordinate of second point.
     * @param x3 X coordinate of third point.
     * @param y3 Y coordinate of third point.
     * @return double
     */
    public double distanceFromPointToLine(double x1, double y1, double x2, double y2, double x3, double y3) {
        // Calculate the numerator of the distance formula
        double numerator = Math.abs((x2 - x1) * (y1 - y3) - (x1 - x3) * (y2 - y1));
    
        // Calculate the denominator of the distance formula
        double denominator = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

        // If first and last coincident, distance to coincident point instead of line.
        if (dist(x1,y1,x2,y2) <= 0) {
            return dist(x1,y1,x3,y3);
        }
    
        // Return the distance
        return numerator / denominator;
    }
    
}
