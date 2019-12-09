package main;
import java.util.Arrays;

public class OptionsParser {

    public MoveDirection[] parse(String t[]) {
        MoveDirection[] directions = new MoveDirection[t.length];
        int j = 0;
        for (int i = 0; i < t.length; i++) {
            if (t[i].equals("f") || t[i].equals("forward")) {
                directions[j++] = MoveDirection.FORWARD;
            }
            else if (t[i].equals("b") || t[i].equals("backward")) {
                directions[j++] = MoveDirection.BACKWARD;
            }
            else if (t[i].equals("l") || t[i].equals("left")) {
                directions[j++] = MoveDirection.LEFT;
            }
            else if (t[i].equals("r") || t[i].equals("right")) {
                directions[j++] = MoveDirection.RIGHT;
            }
            else {
                throw new IllegalArgumentException(t[i] + " nie okresla ruchu");
            }
        }

        return Arrays.copyOf(directions, j);

    }


}
