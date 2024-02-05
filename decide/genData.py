import numpy as np
import random as rd
import json

def genData(npoints):
    dic = {"NUMPOINTS":npoints}
    pointsX = (np.random.rand(100)*100).tolist()
    pointsY = (np.random.rand(100)*100).tolist()


    # PARAMETERS
    dic["X_COORD"] = pointsX
    dic["Y_COORD"] = pointsY
    dic["LENGTH1"] = 20.2
    dic["RADIUS1"] = 2.9
    dic["RADIUS2"] = 4.1
    dic["EPSILON"] = 2.1
    dic["AREA1"] = 40.7
    dic["Q_PTS"] = 2
    dic["QUADS"] = 3
    dic["DIST"] = 10.7
    dic["N_PTS"] = 3
    dic["K_PTS"] = 3
    dic["A_PTS"] = 3
    dic["B_PTS"] = 3
    dic["C_PTS"] = 3
    dic["D_PTS"] = 3
    dic["E_PTS"] = 3
    dic["F_PTS"] = 3
    dic["G_PTS"] = 3
    dic["LENGTH2"] = 11.2
    dic["AREA2"] = 22.2

    vals = ["ANDD","ORR","NOTUSED"]
    dic["LCM"] = [[rd.choice(vals) for i in range(15)] for j in range(15)]
    dic["PUV"] = [rd.choice([1, 0]) for i in range(15)]

    return dic

with open("sampleData.json","w") as f:
    json.dump(genData(30),f)
