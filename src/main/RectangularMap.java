package main;

public class RectangularMap extends AbstractWorldMap {

    private Vector2d upperRight;
    private Vector2d lowerLeft;

    public RectangularMap(int width, int height){
        upperRight = new Vector2d(width, height);
        lowerLeft = new Vector2d(0,0);
    }

    @Override
    public boolean canMoveTo(Vector2d vector2d){
        if(vector2d.precedes(upperRight) && vector2d.follows(lowerLeft)) {
            return !isOccupied(vector2d);
        }
        else return false;
    }

    @Override
    public String toString() {
        MapVisualizer mv = new MapVisualizer(this);
        return mv.draw(lowerLeft, upperRight);
    }

    public Vector2d[] getBounds(){
        return new Vector2d[]{lowerLeft, upperRight};
    }

}
