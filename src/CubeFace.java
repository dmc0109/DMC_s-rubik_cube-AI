
import java.io.Serializable;

/**
 * @Author: Changhai Man
 * @Date: 2019-08-04 7:46
 */
public class CubeFace implements Serializable {
    private Color[][] face;

    public CubeFace(int level, String colors) {
        this.face = new Color[level][];
        for (int i = 0; i < face.length; i++)
            face[i] = new Color[level];
        this.setFaceByStr(colors);
    }

    public CubeFace(int level) {
        this(level, "wwwwwwwww");
    }

    public CubeFace(String colors) {
        int length = (int) (Math.sqrt(colors.length()) + 0.0001);
        colors = colors.substring(0, length * length);
        this.face = new Color[length][];
        for (int i = 0; i < face.length; i++)
            face[i] = new Color[length];
        this.setFaceByStr(colors);
    }

    public CubeFace() {
        this(3);
    }

    private boolean setFaceByStr(String color) {
        color = color.toLowerCase().replaceAll("[^wyrobg]", "");
        if (color.length() < this.face.length * this.face[0].length)
            return false;
        int pt = 0;
        boolean success = true;
        for (int i = 0; i < face.length; i++)
            for (int j = 0; j < face[i].length; j++) {
                Color tmp;
                switch (color.charAt(pt)) {
                    case 'w':
                        tmp = Color.WIGHT;
                        break;
                    case 'y':
                        tmp = Color.YELLOW;
                        break;
                    case 'r':
                        tmp = Color.RED;
                        break;
                    case 'o':
                        tmp = Color.ORANGE;
                        break;
                    case 'g':
                        tmp = Color.GREEN;
                        break;
                    case 'b':
                        tmp = Color.BLUE;
                        break;
                    default:
                        tmp = null;
                        success = false;
                        break;
                }
                face[i][j] = tmp;
                pt++;
            }
        return success;
    }

    private boolean setFaceByColor(Color[] color) {
        if (color.length < this.face.length * this.face[0].length)
            return false;
        int pt = 0;
        boolean success = true;
        for (int i = 0; i < face.length; i++)
            for (int j = 0; j < face[i].length; j++) {
                if (color[pt] == null)
                    success = false;
                face[i][j] = color[pt];
                pt++;
            }
        return success;
    }

    public Color[] getLine(int idx, boolean horizon) throws ArrayIndexOutOfBoundsException{
        Color[] ans = new Color[face.length];
        if (horizon)
            ans = face[idx];
        else
            for (int i = 0; i < face.length; i++)
                ans[i] = face[i][idx];
        return ans;
    }

    public boolean setLine(Color[] line, int idx, boolean horizon) {
        if (line.length < face.length)
            return false;
        else if (line.length > face.length) {
            Color[] tmp = new Color[face.length];
            for (int i = 0; i < tmp.length; i++)
                tmp[i] = line[i];
            line = tmp;
        }
        for (Color e : line)
            if (e == null)
                return false;
        if (horizon)
            face[idx] = line;
        else
            for (int i = 0; i < face.length; i++)
                face[i][idx] = line[i];
        return true;
    }

    public String getFaceStr() {
        StringBuilder ansBuilder = new StringBuilder();
        for (Color[] line : face)
            for (Color c : line)
                ansBuilder.append(c.name().toLowerCase().charAt(0));
        return ansBuilder.toString();
    }

    public Color[] getFaceColor() {
        Color[] ans = new Color[face.length * face.length];
        int pt = 0;
        for (Color[] line : face)
            for (Color c : line) {
                ans[pt] = c;
                pt++;
            }
        return ans;
    }

    public Color[] inverseColor(Color[] ori) {
        return CubeFace.sInverseColor(ori);
    }

    public static Color[] sInverseColor(Color[] ori) {
        Color[] ans = new Color[ori.length];
        for (int i = 0; i < ans.length; i++)
            ans[i] = ori[ans.length - i - 1];
        return ans;
    }

    public boolean rotateFace(boolean clockWise) {
        Color[][] newFace = new Color[face[0].length][face.length];
        if (clockWise)
            for (int i = 0; i < face.length; i++)
                for (int j = 0; j < face[0].length; j++)
                    newFace[j][face.length - i - 1] = face[i][j];
        else
            for (int i = 0; i < face.length; i++)
                for (int j = 0; j < face[0].length; j++)
                    newFace[face.length - j - 1][i] = face[i][j];
        this.face = newFace;
        return true;
    }

    @Override
    public String toString(){
        StringBuilder ansBuilder = new StringBuilder();
        for(Color[] line:face){
            for(Color c:line)
                ansBuilder.append(c.name().charAt(0)+"   ");
            ansBuilder.append("\r\n");
        }
        return ansBuilder.toString();
    }

}
