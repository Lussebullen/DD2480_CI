package group17;

import group17.InputHandler.CONNECTORS;

public class App 
{
    private boolean[] evaluateLics(final InputHandler input) {
        LicAnalyzer licAnalyzer = new LicAnalyzer();
        final boolean[] CMV = {
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
        return CMV;
    }

    private boolean[][] calculatePUM(final boolean[] CMV, 
                                     final CONNECTORS[][] LCM) 
    {
        final int signals = CMV.length;
        boolean[][] PUM = new boolean[signals][signals];
        for (int i = 0; i < signals; ++i) {
            for (int j = 0; j < signals; ++j) {

                CONNECTORS operation = LCM[i][j];
                switch (operation) {
                    case ANDD:
                        PUM[i][j] = CMV[i] && CMV[j];
                        break;
                    case ORR:
                        PUM[i][j] = CMV[i] || CMV[j];
                        break;
                    case NOTUSED:
                        PUM[i][j] = true;
                        break;
                }
            }
        }
        return PUM;
    }

    private boolean[] calculateFUV(final boolean[][] PUM,
                                   final boolean[] PUV)
    {
        final int signals = PUV.length;
        boolean[] FUV = new boolean[signals];
        for (int i = 0; i < signals; ++i) {
            if (PUV[i] == false) {
                FUV[i] = true;
                continue;
            }
            for (int j = 0; j < signals; ++j) {
                boolean enabledSignal = PUM[i][j];
                if (!enabledSignal && i != j) {
                    return FUV;
                }
            }
            FUV[i] = true;
        }
        return FUV;
    }

    public void decide(final InputHandler input) {
        final boolean[]   CMV = evaluateLics(input);
        final boolean[][] PUM = calculatePUM(CMV, input.LCM);
        final boolean[]   FUV = calculateFUV(PUM, input.PUV);

        for (boolean enabledSignal : FUV) {
            if (!enabledSignal) {
                System.out.println("NO");
                return;
            } 
        }
        System.out.println("YES");
    }

    public static void main( String[] args ) {
        try {
            InputHandler input = new InputHandler("sampleData.json");
            App missileSystem = new App();
            missileSystem.decide(input);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());  
        }
    }
}
