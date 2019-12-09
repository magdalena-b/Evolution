package test;

import main.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IntegrationTestLab6 {
    public List<Grass> grasses = new ArrayList<>();
    public IWorldMap map = new GrassField(4);

    @Test
    public void canMoveTo() {
        Animal a1 = new Animal(map, new Vector2d(-4, -5));
        map.place(a1);
        assertFalse(map.canMoveTo(new Vector2d(-4, -5)));
        assertTrue(map.canMoveTo(new Vector2d(1, 1)));
    }
    @Test
    public void place() {
        Animal a1 = new Animal(map, new Vector2d(-4, -5));
        assertTrue(map.place(a1));

    }

    @Test
    public void isOccupied() {
        Animal a1 = new Animal(map, new Vector2d(0, 0));
        map.place(a1);
        assertFalse(map.isOccupied(new Vector2d(1, 1)));
        assertTrue(map.isOccupied(a1.getPosition()));
        MoveDirection[] md = new OptionsParser().parse(new String[]{"f", "b", "f", "f"});


    }

    @Test
    public void objectAt() {
        Animal a1 = new Animal(map, new Vector2d(-4, -5));
        map.place(a1);
        assertEquals(a1, map.objectAt(new Vector2d(-4, -5)));
    }
}
