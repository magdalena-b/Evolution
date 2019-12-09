package main;

import java.util.HashSet;
import java.util.Set;

public class Animal {
    private MapDirection orientation;
    private Vector2d position;
    private IWorldMap map;
    private Set<IPositionChangeObserver> observers = new HashSet<>();

    public Animal() {
        this.orientation = MapDirection.NORTH;
        this.position = new Vector2d(2, 2);
    }

    public Animal(IWorldMap map) {
        this.orientation = MapDirection.NORTH;
        this.position = new Vector2d(2, 2);
        this.map = map;
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.orientation = MapDirection.NORTH;
        this.map = map;
        this.position = initialPosition;
        addObserver(map);
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver observer : observers){
            observer.positionChanged(oldPosition, newPosition);
        }
    }

    public void move(MoveDirection direction){

        // f, b, r, l
        // jesli f, b -> zmiana polozenia; metoda add, subtract w Vector2d
        // jesli r, l -> zmiana orientacji; metoda next, previous w MapDirection

        Vector2d oldPosition = this.position;
        Vector2d newPosition = new Vector2d(0,0);

        switch(direction){
            case FORWARD:
                newPosition = this.position.add(this.orientation.toUnitVector());
                if(map.canMoveTo(newPosition)) {
                    this.position = newPosition;
                    this.positionChanged(oldPosition, newPosition);
                }
                break;
            case BACKWARD:
                newPosition = this.position.subtract(this.orientation.toUnitVector());
                if(map.canMoveTo(newPosition)) {
                    this.position = newPosition;
                    this.positionChanged(oldPosition, newPosition);
                }
                break;
            case LEFT:
                this.orientation = this.orientation.previous();
                break;
            case RIGHT:
                this.orientation = this.orientation.next();
                break;
        }
    }


    public MapDirection getOrientation() {
        return this.orientation;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public String toString(){
        String result = "";
        switch(this.orientation){
            case NORTH: result =  "N"; break;
            case WEST: result = "W"; break;
            case EAST: result = "E"; break;
            case SOUTH: result = "S"; break;
        }
        return result;
    }

}
