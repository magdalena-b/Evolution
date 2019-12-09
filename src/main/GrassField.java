package main;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GrassField extends AbstractWorldMap{
    private Map<Vector2d, Grass> grassMap = new HashMap<>();
    private MapBoundary mapBoundary;
    private void generateGrass (int n) {

        int sqrt_n = (int)Math.sqrt(n*10);
        Random generator = new Random();

        for (int i = 0; i < sqrt_n; i++) {

            int x = generator.nextInt(sqrt_n);
            int y = generator.nextInt(sqrt_n);
            Vector2d p = new Vector2d(x, y);
            Grass grass = new Grass(p);

            while (isOccupied(p)) {
                x = generator.nextInt(sqrt_n);
                y = generator.nextInt(sqrt_n);
                p = new Vector2d(x, y);
            }
            if(!isOccupied(p)){
                this.grassMap.put(p, grass);
            }
        }

    }
    public GrassField(int n){
        this.mapBoundary = new MapBoundary();
        generateGrass(n);
    }

    public GrassField(int n, MapBoundary mapBoundary){
        this.mapBoundary = mapBoundary;
        generateGrass(n);
    }

    @Override
    public Object objectAt(Vector2d vector2d){
        Object animal = super.objectAt(vector2d);
        if(animal != null) return animal;
        return grassMap.get(vector2d);
    }
    @Override
    public boolean isOccupied(Vector2d vector2d){
        return animalMap.containsKey(vector2d);
    }

    @Override
    public boolean place(Animal animal) throws IllegalArgumentException {
        if (super.place(animal)) {
            mapBoundary.addPosition(animal.getPosition());
            animal.addObserver(mapBoundary);
//             mapBoundary.addAnimal(animal);
        }
        return true;
    }

    public Vector2d[] getBounds(){
        Vector2d[] bounds = new Vector2d[2];
        bounds[0] = mapBoundary.getLowerLeft();
        bounds[1] = mapBoundary.getUpperRight();
        return bounds;
    }

}
