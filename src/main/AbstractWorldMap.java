package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected Map<Vector2d, Animal> animalMap= new HashMap<>();
    protected List<Animal> animals = new ArrayList<>();

    public boolean place(Animal animal) throws IllegalArgumentException{
        if (this.isOccupied(animal.getPosition())){
            throw new IllegalArgumentException("Pole " + animal.getPosition() + " jest juz zajete");
        }
        if(!this.canMoveTo(animal.getPosition())) return false;
        animals.add(animal);
        animalMap.put(animal.getPosition(), animal);
        return true;
    }

    public boolean canMoveTo(Vector2d vector2d){
        return !this.isOccupied(vector2d);
    }

    public boolean isOccupied(Vector2d vector2d){
        return (objectAt(vector2d) != null);
    }

    public void run(MoveDirection[] directions){
        int i = 0;
        int length = animals.size();
        for(MoveDirection direction : directions){
            Animal animal = animals.get(i);

            animalMap.remove(animal.getPosition());
            animal.move(direction);
            animalMap.put(animal.getPosition(), animal);

            i = (i + 1) % length;
        }
    }

    public Object objectAt(Vector2d position){
        return animalMap.get(position);
    }

    @Override
    public String toString() {
        MapVisualizer mv = new MapVisualizer(this);
        Vector2d[] bounds = getBounds();
        return mv.draw(bounds[0], bounds[1]);

    }
    public abstract Vector2d[] getBounds();

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        Animal animal = animalMap.get(oldPosition);
        animalMap.remove(oldPosition);
        animalMap.put(newPosition, animal);
    }

}
