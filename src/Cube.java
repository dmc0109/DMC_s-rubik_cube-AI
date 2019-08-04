import com.sun.org.apache.xpath.internal.operations.String;

/**
 * @Author: Changhai Man
 * @Date: 2019-08-01 10:27
 */

public class Cube {
    public static final int UP = 0, DOWN = 1, NORTH = 2, EAST = 3, WEST = 4, SOUTH = 5;
    private final int sideLength;
    private CubeFace[] faces = new CubeFace[6];

    public Cube(final int level) {
        initCube(level);
        sideLength = level;
    }

    public Cube() {
        this(3);
    }

    private void initCube(final int level) {
        for (int i = 0; i < faces.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < level * level; j++)
                stringBuilder.append(Color.values()[i].name().toLowerCase().charAt(0));
            faces[i] = new CubeFace(level, stringBuilder.toString());
        }
    }

    private void rotate(int surface, int layer, boolean clockwise) {
        Color[] down=null, left=null, up=null, right=null, tmp=null;
        switch (surface) {
            case UP:
                down = CubeFace.sInverseColor(this.faces[SOUTH].getLine(layer, true));
                left = CubeFace.sInverseColor(this.faces[WEST].getLine(this.sideLength - layer - 1, false));
                up = this.faces[NORTH].getLine(this.sideLength - layer - 1, true);
                right = this.faces[EAST].getLine(layer, false);
                break;
            case DOWN:
                down = CubeFace.sInverseColor(this.faces[NORTH].getLine(layer, true));
                left = this.faces[WEST].getLine(layer, false);
                up = this.faces[SOUTH].getLine(this.sideLength-layer-1, true);
                right = CubeFace.sInverseColor(this.faces[EAST].getLine(this.sideLength-layer-1, false));
                break;
            case NORTH:
                down = CubeFace.sInverseColor(this.faces[UP].getLine(layer, true));
                left = CubeFace.sInverseColor(this.faces[WEST].getLine(layer, true));
                up = CubeFace.sInverseColor(this.faces[DOWN].getLine(layer, true));
                right = CubeFace.sInverseColor(this.faces[EAST].getLine(layer, true));
                break;
            case SOUTH:
                down = this.faces[DOWN].getLine(this.sideLength-layer-1, true);
                left = this.faces[WEST].getLine(this.sideLength-layer-1, true);
                up = this.faces[UP].getLine(this.sideLength-layer-1, true);
                right = this.faces[EAST].getLine(this.sideLength-layer-1, true);
                break;
        }
        assert(down!=null);
        assert(up!=null);
        assert(right!=null);
        assert(left!=null);
        if (clockwise) {
            tmp = down;
            down = right;
            right = up;
            up = left;
            left = tmp;
        } else {
            tmp = down;
            down = left;
            left = up;
            up = right;
            right = tmp;
        }
        switch (surface){
            case UP:
                this.faces[SOUTH].setLine(CubeFace.sInverseColor(down), layer, true);
                this.faces[WEST].setLine(CubeFace.sInverseColor(left), this.sideLength-layer-1, false);
                this.faces[NORTH].setLine(up, this.sideLength-layer-1, true);
                this.faces[EAST].setLine(right, layer, false);
                break;
            case DOWN:
                this.faces[NORTH].setLine(CubeFace.sInverseColor(down), layer, true);
                this.faces[WEST].setLine(left, layer, false);
                this.faces[SOUTH].setLine(up, this.sideLength-layer-1, true);
                this.faces[EAST].setLine(CubeFace.sInverseColor(right), this.sideLength-layer-1, false);
                break;
            case NORTH:
                this.faces[UP].setLine(CubeFace.sInverseColor(down), layer, true);
                this.faces[WEST].setLine(CubeFace.sInverseColor(left), layer, true);
                this.faces[DOWN].setLine(CubeFace.sInverseColor(up), layer, true);
                this.faces[EAST].setLine(CubeFace.sInverseColor(right), layer, true);
                break;
            case SOUTH:
                this.faces[DOWN].setLine(down, this.sideLength-layer-1, true);
                this.faces[WEST].setLine(left, this.sideLength-layer-1, true);
                this.faces[UP].setLine(up, this.sideLength-layer-1, true);
                this.faces[EAST].setLine(right, this.sideLength-layer-1, true);
                break;
        }
    }

}

//public class Cube {
//    public static final int UP = 0, DOWN = 1, NORTH = 2, EAST = 3, WEST = 4, SOUTH = 5;
//    private final int sideLength;
//    private Color[][][] cube = new Color[6][][];
//
//    public Cube(final int level) {
//        initCube(level);
//        sideLength = level;
//    }
//
//    public Cube() {
//        this(3);
//    }
//
//
//    private void initCube(final int level) {
//        this.cube = new Color[6][][];
//        for (int i = 0; i < cube.length; i++) {
//            Color[][] surface = new Color[level][];
//            for (int j = 0; j < surface.length; j++) {
//                surface[j] = new Color[level];
//                for (int k = 0; k < surface[j].length; k++)
//                    surface[j][k] = Color.values()[i];
//            }
//            cube[i] = surface;
//        }
//    }
//
//    private void rotateSurface(int surface, boolean clockwise) {
//        final int sideLength = cube[surface].length;
//        Color[][] oriSurface = cube[surface];
//        Color[][] newSurface = new Color[oriSurface.length][];
//        for (int i = 0; i < newSurface.length; i++)
//            newSurface[i] = new Color[oriSurface[i].length];
//        for (int i = 0; i < newSurface.length; i++)
//            for (int j = 0; j < newSurface[i].length; j++) {
//                if (!clockwise)
//                    newSurface[sideLength - j - 1][i] = oriSurface[i][j];
//                else
//                    newSurface[j][sideLength - i - 1] = oriSurface[i][j];
//            }
//        cube[surface] = newSurface;
//    }
//
//    private void rotateLayer(int surface, int layer, boolean clockwise) {
//        if (surface == UP) {
//            Color[] a, b, c, d;
//            a = new Color[sideLength];
//            for (int i = 0; i < cube[WEST][sideLength - layer - 1].length; i++)
//                a[i] = cube[WEST][sideLength - layer - 1][sideLength - i - 1];
//            b = cube[NORTH][sideLength - layer - 1];
//            c = new Color[sideLength];
//            for (int i = 0; i < cube[EAST][layer].length; i++)
//                c[i] = cube[EAST][layer][i];
//            d = new Color[sideLength];
//            for (int i = 0; i < cube[SOUTH][layer].length; i++)
//                d[i] = cube[SOUTH][layer][sideLength - i - 1];
//            if (clockwise) {
//                Color[] tmp;
//                tmp = d;
//                d = c;
//                c = b;
//                b = a;
//                a = tmp;
//            } else {
//                Color[] tmp;
//                tmp = a;
//                a = b;
//                b = c;
//                c = d;
//                d = tmp;
//            }
//            for (int i = 0; i < cube[WEST][sideLength - layer - 1].length; i++)
//                cube[WEST][sideLength - layer - 1][sideLength - i - 1] = a[i];
//            cube[NORTH][sideLength - layer - 1] = b;
//            for (int i = 0; i < cube[EAST][layer].length; i++)
//                cube[EAST][layer][i] = c[i];
//            for (int i = 0; i < cube[SOUTH][layer].length; i++)
//                cube[SOUTH][layer][sideLength - i - 1] = d[i];
//        }
//        //else if (surface ==)
//
//    }
//
//    public static void testRotate(String[] args) {
//        final int length = 5;
//        Cube container = new Cube(length);
//        Color[][] ori = new Color[length][length];
//        int counter = 0;
//        for (int i = 0; i < ori.length; i++) {
//            Color[] side = new Color[length];
//            for (int j = 0; j < side.length; j++) {
//                side[j] = Color.values()[counter % 6];
//                counter++;
//            }
//            ori[i] = side;
//        }
//        for (int i = 0; i < ori.length; i++) {
//            for (int j = 0; j < ori[i].length; j++)
//                System.out.print(ori[i][j].name().charAt(0) + "   ");
//            System.out.println();
//        }
//        container.cube[0] = ori;
//        System.out.println();
//
//        container.rotateSurface(0, true);
//        Color[][] _new = container.cube[0];
//        for (int i = 0; i < _new.length; i++) {
//            for (int j = 0; j < _new[i].length; j++)
//                System.out.print(_new[i][j].name().charAt(0) + "   ");
//            System.out.println();
//        }
//
//        System.out.println();
//        container.rotateSurface(0, false);
//        _new = container.cube[0];
//        for (int i = 0; i < _new.length; i++) {
//            for (int j = 0; j < _new[i].length; j++)
//                System.out.print(_new[i][j].name().charAt(0) + "   ");
//            System.out.println();
//        }
//
//        System.out.println();
//        container.rotateSurface(0, false);
//        _new = container.cube[0];
//        for (int i = 0; i < _new.length; i++) {
//            for (int j = 0; j < _new[i].length; j++)
//                System.out.print(_new[i][j].name().charAt(0) + "   ");
//            System.out.println();
//        }
//        //container.cube[0] = new
//    }
//
//
//}
