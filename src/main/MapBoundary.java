package main;

import java.util.Comparator;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {

    private TreeSet<Vector2d> x_axis = new TreeSet<>(Comparator.comparingInt(v -> v.x));
    private TreeSet<Vector2d> y_axis = new TreeSet<>(Comparator.comparingInt(o -> o.y));

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        // remove old element from the tree and add new one
        x_axis.remove(oldPosition);
        y_axis.remove(oldPosition);
        x_axis.add(newPosition);
        y_axis.add(newPosition);
    }

    public void addPosition(Vector2d position) {
        x_axis.add(position);
        y_axis.add(position);
    }
    public void addAnimal(Animal animal){
        animal.addObserver(this);
    }

    public void removePosition(Vector2d position) {
        x_axis.remove(position);
        y_axis.remove(position);
    }

    public Vector2d getLowerLeft() {
        return x_axis.first().lowerLeft(y_axis.first());
    }

    public Vector2d getUpperRight() {
        return x_axis.last().upperRight(y_axis.last());
    }

}
