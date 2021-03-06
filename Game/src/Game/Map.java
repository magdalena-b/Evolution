package Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.abs;

public class Map {

    int width;
    int height;
    double jungleRatio;
    int grassProfit = 10;

    public ArrayList<Animal> animals = new ArrayList<Animal>();
    public ArrayList<Plant> plants = new ArrayList<Plant>();
    public HashMap<Vector2d, ArrayList<Animal>> animalsMap = new HashMap<>();
    public HashMap<Vector2d, Plant> plantsMap = new HashMap<>();



    public Map(int width, int height, double jungleRatio) {
        this.width = width;
        this.height = height;
        this.jungleRatio = jungleRatio;
    }

    public void tick(int width, int height){
        for (int i = 0; i < animals.size(); i++) {
            animals.get(i).rotate(width, height);
            animals.get(i).move(MoveDirection.BACKWARD, width, height);
            eating(animals.get(i));
            animalsCollision(animals.get(i));
        }
        removeDeadAnimals();
    }

    public void render(Graphics g) {


        for (int i = 0; i < plants.size(); i++) {
            plants.get(i).render(g);
        }

        for (int i = 0; i < animals.size(); i++) {
            animals.get(i).render(g);
        }


    }

    public void addAnimal(Animal animal) {

        animals.add(animal);
        if(animalsMap.get(animal.position) == null){
            ArrayList<Animal> tmp = new ArrayList<Animal>();
            tmp.add(animal);
            animalsMap.put(animal.position, tmp);
        }
        else{
            ArrayList<Animal> list = animalsMap.get(animal.position);
            list.add(animal);
        }

    }

    public void addPlant(Plant plant) {

        plants.add(plant);
        plantsMap.put(plant.position, plant);
    }

    public void removeDeadAnimals() {
        for (int i = 0; i < animals.size(); i++) {
            if(animals.get(i).isDead()) {
                animalsMap.remove(animals.get(i).position);
                animals.remove(i);
            }
        }
    }

    public Vector2d getRandomLocation() {

        int positionX = (int)(Math.random() * width);
        int positionY = (int)(Math.random() * height);
        return new Vector2d(positionX, positionY);

    }


    public Animal animalAt(Animal animal) {
        for (int i = 0; i < animals.size(); i++) {
            Animal other = animals.get(i);
            if (other.equals(animal)) {
                continue;
            }
            Vector2d tmp = animal.position.subtract(other.position);
            if ( abs(tmp.x) < animal.animalSize / 2 &&  abs(tmp.y) < animal.animalSize / 2) {
                return other;
            }
        }
        return null;
    }

    public Plant plantAt(Vector2d position) {
        for (int i = 0; i < plants.size(); i++) {
            Vector2d tmp = plants.get(i).position.subtract(position);
            if ( abs(tmp.x) < plants.get(i).plantSize / 2 &&  abs(tmp.y) < plants.get(i).plantSize / 2 ) {
                return plants.get(i);
            }
        }
        return null;
    }


    public void createJungleAndSahannah(){

        int plantPositionX = 0;
        int plantPositionY = 0;

        int plantCounter = width / 10;

        while(plantCounter > 0) {
            plantPositionX = (int)(Math.random() * width);
            plantPositionY = (int)(Math.random() * height);
            addPlant(new Plant(new Vector2d(plantPositionX, plantPositionY)));
            plantCounter--;
        }


        int jungleWidth = (int) (width * jungleRatio);
        int jungleHeight = (int) (height * jungleRatio);

        int center_w = width / 2;
        int center_h = height / 2;

        Vector2d leftLowerJungle = new Vector2d(center_w - jungleWidth/2, center_h - jungleHeight/2);
        Vector2d rightLowerJungle = new Vector2d(center_w + jungleWidth/2, center_h - jungleHeight/2);
        Vector2d leftUpperJungle = new Vector2d(center_w - jungleWidth/2, center_h + jungleHeight/2);
        Vector2d rightUpperJungle = new Vector2d(center_w + jungleWidth/2, center_h + jungleHeight/2);

        int plantCounterJungle = jungleWidth / 2;

        while (plantCounterJungle > 0) {
            plantPositionX = (int)(Math.random() * jungleWidth) + leftLowerJungle.x;
            plantPositionY = (int)(Math.random() * jungleHeight) + leftLowerJungle.y;
            addPlant(new Plant(new Vector2d(plantPositionX, plantPositionY)));
            plantCounterJungle--;
        }

    }



    public void eating (Animal animal) {
        if (plantAt(animal.position) != null && !(plantAt(animal.position).wasEaten)) {
            Plant food = plantAt(animal.position);
            animal.changeEnergy(grassProfit);
            food.wasEaten = true;
        }
    }

    public void animalsCollision(Animal animal) {
        Animal date = animalAt(animal);
        if (date != null) {
            createLife(animal, date);
        }
    }


    public Animal createLife(Animal parent1, Animal parent2) {
        if(parent1.energy >= parent1.minEnergyToReproduce && parent2.energy >= parent2.minEnergyToReproduce){
            int energy = parent1.energy / 4 + parent2.energy / 4;
            Animal child = new Animal(new Vector2d(parent1.position.x, parent2.position.y));
            child.generateInheritedGenes(parent1, parent2);
            child.startEnergy = (parent1.energy / 4) + (parent2.energy / 4);
            parent1.changeEnergy( - parent1.energy / 4);
            parent2.changeEnergy(- parent2.energy / 4);
            System.out.println("Creating child");
            addAnimal(child);
            return child;
        }

        return null;
    }



}