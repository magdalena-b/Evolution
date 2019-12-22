package Game;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Map {

    int width;
    int height;
    int jungleRatio;

    LinkedList<Animal> animals = new LinkedList<Animal>();
    LinkedList<Plant> plants = new LinkedList<Plant>();

    public void tick(int width, int height){
        for (int i = 0; i < animals.size(); i++) {
            //animals.get(i).tick(width, height);
            animals.get(i).rotate(width, height);
            animals.get(i).move(MoveDirection.BACKWARD, width, height);
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < animals.size(); i++) {
            animals.get(i).render(g);
        }

        for (int i = 0; i < plants.size(); i++) {
            plants.get(i).render(g);
        }

    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    public boolean isOccupied(Vector2d vector2d){

        return (objectAt(vector2d) != null);
    }

    public Object objectAt(Vector2d position){

        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).position.equals(position)){
                return animals.get(i);
            }
        }

        return null;

        // return animalMap.get(position);
    }

    public void createJungleAndSahannah(){
        int jungleWidth = width * jungleRatio;
        int jungleHeight = height * jungleRatio;

        int center_w = width / 2;
        int center_h = height / 2;

        Vector2d leftLowerJungle = new Vector2d(center_w - jungleWidth/2, center_h - jungleHeight/2);
        Vector2d rightLowerJungle = new Vector2d(center_w + jungleWidth/2, center_h - jungleHeight/2);
        Vector2d leftUpperJungle = new Vector2d(center_w - jungleWidth/2, center_h + jungleHeight/2);
        Vector2d rightUpperJungle = new Vector2d(center_w + jungleWidth/2, center_h + jungleHeight/2);

    }



    private void generatePlantsInJungle (int n) {

        int sqrt_n = (int)Math.sqrt(n*10);
        Random generator = new Random();

        for (int i = 0; i < sqrt_n; i++) {

            int x = generator.nextInt(sqrt_n);
            int y = generator.nextInt(sqrt_n);
            Vector2d p = new Vector2d(x, y);
            Plant plant = new Plant(p);

            while (isOccupied(p)) {
                x = generator.nextInt(sqrt_n);
                y = generator.nextInt(sqrt_n);
                p = new Vector2d(x, y);
            }
            if(!isOccupied(p)){
                //this.grassMap.put(p, grass);
                addPlant(plant);
            }
        }

    }

    private void generatePlantsInSavannah() {

    }

}