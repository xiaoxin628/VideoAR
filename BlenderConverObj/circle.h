/*
created with obj2opengl.pl

source file    : ./circle.obj
vertices       : 32
faces          : 30
normals        : 0
texture coords : 0


// include generated arrays
#import "./circle.h"

// set input data to arrays
glVertexPointer(3, GL_FLOAT, 0, circleVerts);

// draw data
glDrawArrays(GL_TRIANGLES, 0, circleNumVerts);
*/

unsigned int circleNumVerts = 90;

float circleVerts [] = {
  // f 17 25 9
  1.24999999979742e-07f, -0.5000001875f, 0f,
  0.500000125f, -1.87499999996718e-07f, 0f,
  -0.499999875f, -1.87499999996718e-07f, 0f,
  // f 1 2 5
  1.24999999979742e-07f, 0.4999998125f, 0f,
  -0.097545875f, 0.4903918125f, 0f,
  -0.353553875f, 0.3535538125f, 0f,
  // f 3 4 5
  -0.191341875f, 0.4619398125f, 0f,
  -0.277785875f, 0.4157338125f, 0f,
  -0.353553875f, 0.3535538125f, 0f,
  // f 5 6 7
  -0.353553875f, 0.3535538125f, 0f,
  -0.415733875f, 0.2777858125f, 0f,
  -0.461939875f, 0.1913418125f, 0f,
  // f 7 8 5
  -0.461939875f, 0.1913418125f, 0f,
  -0.490391875f, 0.0975458125f, 0f,
  -0.353553875f, 0.3535538125f, 0f,
  // f 9 10 11
  -0.499999875f, -1.87499999996718e-07f, 0f,
  -0.490391875f, -0.0975461875f, 0f,
  -0.461939875f, -0.1913421875f, 0f,
  // f 11 12 13
  -0.461939875f, -0.1913421875f, 0f,
  -0.415733875f, -0.2777861875f, 0f,
  -0.353553875f, -0.3535541875f, 0f,
  // f 13 14 15
  -0.353553875f, -0.3535541875f, 0f,
  -0.277785875f, -0.4157341875f, 0f,
  -0.191341875f, -0.4619401875f, 0f,
  // f 15 16 13
  -0.191341875f, -0.4619401875f, 0f,
  -0.097545875f, -0.4903921875f, 0f,
  -0.353553875f, -0.3535541875f, 0f,
  // f 17 18 19
  1.24999999979742e-07f, -0.5000001875f, 0f,
  0.097546125f, -0.4903921875f, 0f,
  0.191342125f, -0.4619401875f, 0f,
  // f 19 20 21
  0.191342125f, -0.4619401875f, 0f,
  0.277786125f, -0.4157341875f, 0f,
  0.353554125f, -0.3535541875f, 0f,
  // f 21 22 23
  0.353554125f, -0.3535541875f, 0f,
  0.415736125f, -0.2777841875f, 0f,
  0.461940125f, -0.1913421875f, 0f,
  // f 23 24 21
  0.461940125f, -0.1913421875f, 0f,
  0.490392125f, -0.0975441875f, 0f,
  0.353554125f, -0.3535541875f, 0f,
  // f 25 26 27
  0.500000125f, -1.87499999996718e-07f, 0f,
  0.490392125f, 0.0975458125f, 0f,
  0.461940125f, 0.1913418125f, 0f,
  // f 27 28 29
  0.461940125f, 0.1913418125f, 0f,
  0.415734125f, 0.2777858125f, 0f,
  0.353552125f, 0.3535538125f, 0f,
  // f 29 30 31
  0.353552125f, 0.3535538125f, 0f,
  0.277784125f, 0.4157358125f, 0f,
  0.191342125f, 0.4619398125f, 0f,
  // f 31 32 29
  0.191342125f, 0.4619398125f, 0f,
  0.097544125f, 0.4903918125f, 0f,
  0.353552125f, 0.3535538125f, 0f,
  // f 2 3 5
  -0.097545875f, 0.4903918125f, 0f,
  -0.191341875f, 0.4619398125f, 0f,
  -0.353553875f, 0.3535538125f, 0f,
  // f 5 8 9
  -0.353553875f, 0.3535538125f, 0f,
  -0.490391875f, 0.0975458125f, 0f,
  -0.499999875f, -1.87499999996718e-07f, 0f,
  // f 9 11 17
  -0.499999875f, -1.87499999996718e-07f, 0f,
  -0.461939875f, -0.1913421875f, 0f,
  1.24999999979742e-07f, -0.5000001875f, 0f,
  // f 13 16 17
  -0.353553875f, -0.3535541875f, 0f,
  -0.097545875f, -0.4903921875f, 0f,
  1.24999999979742e-07f, -0.5000001875f, 0f,
  // f 17 19 25
  1.24999999979742e-07f, -0.5000001875f, 0f,
  0.191342125f, -0.4619401875f, 0f,
  0.500000125f, -1.87499999996718e-07f, 0f,
  // f 21 24 25
  0.353554125f, -0.3535541875f, 0f,
  0.490392125f, -0.0975441875f, 0f,
  0.500000125f, -1.87499999996718e-07f, 0f,
  // f 25 27 29
  0.500000125f, -1.87499999996718e-07f, 0f,
  0.461940125f, 0.1913418125f, 0f,
  0.353552125f, 0.3535538125f, 0f,
  // f 29 32 1
  0.353552125f, 0.3535538125f, 0f,
  0.097544125f, 0.4903918125f, 0f,
  1.24999999979742e-07f, 0.4999998125f, 0f,
  // f 1 5 9
  1.24999999979742e-07f, 0.4999998125f, 0f,
  -0.353553875f, 0.3535538125f, 0f,
  -0.499999875f, -1.87499999996718e-07f, 0f,
  // f 11 13 17
  -0.461939875f, -0.1913421875f, 0f,
  -0.353553875f, -0.3535541875f, 0f,
  1.24999999979742e-07f, -0.5000001875f, 0f,
  // f 19 21 25
  0.191342125f, -0.4619401875f, 0f,
  0.353554125f, -0.3535541875f, 0f,
  0.500000125f, -1.87499999996718e-07f, 0f,
  // f 25 29 1
  0.500000125f, -1.87499999996718e-07f, 0f,
  0.353552125f, 0.3535538125f, 0f,
  1.24999999979742e-07f, 0.4999998125f, 0f,
  // f 1 9 25
  1.24999999979742e-07f, 0.4999998125f, 0f,
  -0.499999875f, -1.87499999996718e-07f, 0f,
  0.500000125f, -1.87499999996718e-07f, 0f,
};

float circleIndex [] = {
17,,25,,9,,
1,,2,,5,,
3,,4,,5,,
5,,6,,7,,
7,,8,,5,,
9,,10,,11,,
11,,12,,13,,
13,,14,,15,,
15,,16,,13,,
17,,18,,19,,
19,,20,,21,,
21,,22,,23,,
23,,24,,21,,
25,,26,,27,,
27,,28,,29,,
29,,30,,31,,
31,,32,,29,,
2,,3,,5,,
5,,8,,9,,
9,,11,,17,,
13,,16,,17,,
17,,19,,25,,
21,,24,,25,,
25,,27,,29,,
29,,32,,1,,
1,,5,,9,,
11,,13,,17,,
19,,21,,25,,
25,,29,,1,,
1,,9,,25,,
};

