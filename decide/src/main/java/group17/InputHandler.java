package group17;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;






public class InputHandler {
    public enum CONNECTORS {
        ANDD,
        ORR,
        NOTUSED
    }

    int NUMPOINTS;
    double[] X_COORD;
    double[] Y_COORD;
    double LENGTH1;
    double RADIUS1;
    double EPSILON;
    double AREA1;
    int Q_PTS;
    int QUADS;
    double DIST;
    int N_PTS;
    int K_PTS;
    int A_PTS;
    int B_PTS;
    int C_PTS;
    int D_PTS;
    int E_PTS;
    int F_PTS;
    int G_PTS;
    double LENGTH2;
    double RADIUS2;
    double AREA2;
    CONNECTORS[][] LCM;
    boolean[] PUV;
    public InputHandler(String filepath) {    
        try {
        ObjectMapper objectMapper = new ObjectMapper();

        // Read the JSON file into a JsonNode
        JsonNode jsonNode = objectMapper.readTree(new File(filepath));
        NUMPOINTS = jsonNode.get("NUMPOINTS").asInt();
        X_COORD = jsonNode.get("X_COORD").traverse(objectMapper).readValueAs(double[].class); 
        Y_COORD = jsonNode.get("Y_COORD").traverse(objectMapper).readValueAs(double[].class); 
        LENGTH1 = jsonNode.get("LENGTH1").asDouble();
        RADIUS1 = jsonNode.get("RADIUS1").asDouble();
        EPSILON = jsonNode.get("EPSILON").asDouble();
        AREA1 = jsonNode.get("AREA1").asDouble();
        Q_PTS = jsonNode.get("Q_PTS").asInt();
        QUADS = jsonNode.get("QUADS").asInt();
        DIST = jsonNode.get("DIST").asDouble();
        N_PTS = jsonNode.get("N_PTS").asInt();
        K_PTS = jsonNode.get("K_PTS").asInt();
        A_PTS = jsonNode.get("A_PTS").asInt();
        B_PTS = jsonNode.get("B_PTS").asInt();
        C_PTS = jsonNode.get("C_PTS").asInt();
        D_PTS = jsonNode.get("D_PTS").asInt();
        E_PTS = jsonNode.get("E_PTS").asInt();
        F_PTS = jsonNode.get("F_PTS").asInt();
        G_PTS = jsonNode.get("G_PTS").asInt();
        RADIUS2 = jsonNode.get("RADIUS2").asDouble();
        AREA1 = jsonNode.get("AREA1").asDouble();
        AREA2 = jsonNode.get("AREA2").asDouble();
        LCM = jsonNode.get("LCM").traverse(objectMapper).readValueAs(CONNECTORS[][].class); 
        PUV = jsonNode.get("PUV").traverse(objectMapper).readValueAs(boolean[].class); 

        } catch (FileNotFoundException e) {
            System.err.println("Input file not found. Check that file exists and path is correct.");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
