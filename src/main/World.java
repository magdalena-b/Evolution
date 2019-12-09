package main;

import static java.lang.System.out;

public class World {
    public static void main(String[] args) {
        try{

            /*
            MoveDirection[] directions = new OptionsParser().parse(args);
            IWorldMap map = new RectangularMap(10, 5);
            Animal animal1 = new Animal(map,new Vector2d(2,2));
            Animal animal2= new Animal(map,new Vector2d(4,3));

            map.place(animal1);
            map.place(animal2);
            animal1.move(MoveDirection.LEFT);
            animal1.move(MoveDirection.FORWARD);
            out.println(map);
            MoveDirection[] md = new OptionsParser().parse(new String[]{"f", "b", "r", "l"});

//            out.println(map);

             */

            IWorldMap unbMap = new GrassField(4);
            unbMap.place(new Animal(unbMap,new Vector2d(2,2)));
            unbMap.place(new Animal(unbMap,new Vector2d(5,10)));

//            unbMap.place(new Animal(unbMap,new Vector2d(2,2)));
            out.println(unbMap);

        }catch(IllegalArgumentException e){
            out.println(e);
        }

    }
}
