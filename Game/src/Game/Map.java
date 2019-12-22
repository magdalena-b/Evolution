package Game;

import java.awt.*;
import java.util.LinkedList;

public class Map {

    int width;
    int height;
    double jungleRatio;

    LinkedList<Animal> animals = new LinkedList<Animal>();
    LinkedList<Plant> plants = new LinkedList<Plant>();

    public Map(int width, int height, double jungleRatio) {
        this.width = width;
        this.height = height;
        this.jungleRatio = jungleRatio;
    }

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

        int jungleWidth = (int) (width * jungleRatio);
        int jungleHeight = (int) (height * jungleRatio);

        System.out.println("jungle width: " + jungleWidth + ", jungle height: " + jungleHeight);

        int center_w = width / 2;
        int center_h = height / 2;

        Vector2d leftLowerJungle = new Vector2d(center_w - jungleWidth/2, center_h - jungleHeight/2);
        Vector2d rightLowerJungle = new Vector2d(center_w + jungleWidth/2, center_h - jungleHeight/2);
        Vector2d leftUpperJungle = new Vector2d(center_w - jungleWidth/2, center_h + jungleHeight/2);
        Vector2d rightUpperJungle = new Vector2d(center_w + jungleWidth/2, center_h + jungleHeight/2);

        System.out.println("left lower: " + leftLowerJungle.toString());
        System.out.println("right lower: " + rightLowerJungle.toString());
        System.out.println("left upper: " + leftUpperJungle.toString());
        System.out.println("right upper: " + rightUpperJungle.toString());


        int plantCounter = jungleWidth / 2;

        while (plantCounter > 0) {
            int plantPositionX = (int)(Math.random() * jungleWidth) + leftLowerJungle.x;
            int plantPositionY = (int)(Math.random() * jungleHeight) + leftLowerJungle.y;
            addPlant(new Plant(new Vector2d(plantPositionX, plantPositionY)));
            plantCounter--;
        }

    }




}