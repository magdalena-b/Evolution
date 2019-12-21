package Game;

import java.util.Objects;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public Vector2d upperRight(Vector2d v) {
        return new Vector2d(max(v.x, this.x), max(v.y, this.y));
    }

    public Vector2d lowerLeft(Vector2d v) {
        return new Vector2d(min(v.x, this.x), min(v.y, this.y));
    }

    public Vector2d add(Vector2d v) {
        return new Vector2d(v.x + this.x, v.y + this.y);
    }

    public Vector2d subtract(Vector2d v) {
        return new Vector2d(-v.x + this.x, -v.y + this.y);
    }

    public boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public boolean equals(Object v) {
        if (this == v)
            return true;
        if (!(v instanceof Vector2d))
            return false;
        Vector2d u = (Vector2d) v;
        return (u.x == this.x) && (u.y == this.y);
    }

    public Vector2d opposite() {
        return new Vector2d((-1) * this.x, (-1) * this.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
