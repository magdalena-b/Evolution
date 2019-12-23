package Game;

import java.awt.*;

public class Plant {

    public Vector2d position;

    public Plant(Vector2d position) {
        this.position = position;
    }

    public void render(Graphics g) {
        g.setColor(this.toColor());
        g.fillRect(position.x, position.y, 16, 16);
    }

    public Color toColor() {
        return new Color(28, 105, 35);
    }


}
