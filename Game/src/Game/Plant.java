package Game;

import java.awt.*;

public class Plant {

    public Vector2d position;
    public boolean wasEaten = false;
    int plantSize = 16;

    public Plant(Vector2d position) {
        this.position = position;
    }

    public void render(Graphics g) {
        if(!wasEaten) {
            g.setColor(this.toColor());
            g.fillRect(position.x, position.y, plantSize, plantSize);
        }
        else{
            g.setColor(new Color(0, 0, 0));
            g.fillRect(position.x, position.y, plantSize, plantSize);
        }
    }


    public Color toColor() {
        return new Color(28, 105, 35);
    }


}
