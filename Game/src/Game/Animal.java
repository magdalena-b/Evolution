package Game;

import java.awt.*;

public class Animal implements IMapElement {

    Vector2d position;
    Graphics g;

    public Animal(Graphics g){
        position = new Vector2d(100, 100);
        this.g = g;

    }

    public void tick(){

    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(position.x, position.y, 32, 32);
    }




}
