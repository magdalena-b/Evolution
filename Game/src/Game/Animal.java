package Game;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Animal {

    public Vector2d position;
    public MapDirection direction;
    public int energy = 150;
    public int startEnergy = 10;
    int genes[];
    int numberOfGenes = 32;
    int genesLength = 7;
    int bornEpoch = -1;
    int deathEpoch = -1;
    int velX = 64;
    int velY = 64;


    public Animal(Vector2d position){
        this.position = position;
        this.direction = MapDirection.NORTH;

        this.generateRandomGenes();
    }

    public void render(Graphics g) {
        if(!isDead()) {
            g.setColor(this.toColor());
            g.fillRect(position.x, position.y, 16, 16);
        }
    }

    public void generateRandomGenes(){ // first generation

        Random generator = new Random();

        this.genes = new int[numberOfGenes];
        int allGenes[] = new int[genesLength]; // wartosci: indeksy pierwszych unikalnych genow
        Arrays.fill(allGenes, 0, 7, -1);

        for (int i = 0; i < numberOfGenes; i++) {
            int x = generator.nextInt(genesLength);
            this.genes[i] = x;
            if(allGenes[x] == -1) allGenes[x] = i; // wypelniamy randomowo genom
        }

        for (int i = 0; i < genesLength; i++) { // sprawdzamy czy sa wszystkie geny
            if (allGenes[i] == -1) {
                boolean flag = true;
                int x;
                do {
                    x = generator.nextInt(numberOfGenes); // indeks na ktory wstawimy brakujacy
                    for(int j = 0; j < genesLength; i++) {
                        if (x == allGenes[i]){
                            flag = false;
                            break;
                        }
                    }
                } while (flag);
                this.genes[x] = i;
            }
        }

    }

    public int[] generateInheritedGenes(Animal parent1, Animal parent2) { // inherited from parents

        Random generator = new Random();
        int x = generator.nextInt(numberOfGenes); //first random index to divide genom
        int y = generator.nextInt(numberOfGenes); // second random index to divide genom
        if(x > y) {
            int tmp = x;
            x = y;
            y = tmp;
        }
        genes = new int[numberOfGenes];
        for (int i = 0; i < x; i++) {
            genes[i] = parent1.genes[i];
        }
        for (int i = x; i < y; i++) {
            genes[i] = parent2.genes[i];
        }
        for (int i = y; i < numberOfGenes; i++) {
            genes[i] = parent1.genes[i];
        }
        return genes;
    }

    public boolean isDead() {
        return this.energy <= 0;
    }

    public void changeEnergy(int x) {
        this.energy = this.energy + x;
    }

    public Animal createLife(Animal parent1, Animal parent2) {
        int energy = parent1.energy / 4 + parent2.energy / 4;
        //Animal child = new Animal(energy, generateInheritedGenes(parent1, parent2));
        parent1.changeEnergy( - parent1.energy / 4);
        parent2.changeEnergy(- parent2.energy / 4);
        Animal child =  new Animal(parent1.position);
        child.generateInheritedGenes(parent1, parent2);
        return child;

    }

    public void move(MoveDirection direction, int width, int height){

        Vector2d oldPosition = this.position;
        Vector2d newPosition = new Vector2d(0,0);

        switch(direction){
            case FORWARD:
                newPosition = this.position.add(this.direction.toUnitVector());
                if(newPosition.y < height && newPosition.y > 0) this.position.y = newPosition.y;
                if(newPosition.x < width && newPosition.x > 0) this.position.x = newPosition.x;
                this.energy--;
                break;
            case BACKWARD:
                newPosition = this.position.subtract(this.direction.toUnitVector());
                //if(map.canMoveTo(newPosition)) {
                if(newPosition.y < height && newPosition.y > 0) this.position.y = newPosition.y;
                if(newPosition.x < width && newPosition.x > 0) this.position.x = newPosition.x;
                this.energy--;
                //}
                break;
            case LEFT:
                this.direction.previous();
                break;
            case RIGHT:
                this.direction = this.direction.next();
                break;
        }
    }

    public void rotate(int width, int height) {
        Random generator = new Random();
        int index = generator.nextInt(numberOfGenes);
        int rotation = this.genes[index];

        for (int i = 0; i < rotation; i++) {
            this.move(MoveDirection.RIGHT, width, height);
        }
    }

    public Color toColor() {
        if(energy < 0.5 * startEnergy) return new Color(255, 0, 0);
        if(energy < startEnergy) return new Color(255, 255, 0);
        return new Color(0, 255, 0);
    }




}
