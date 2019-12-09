package main;

public enum MapDirection {
    NORTH("Polnoc", new Vector2d(0, 1)),
    SOUTH("Poludnie", new Vector2d(0, -1)),
    WEST("Zachod", new Vector2d(-1, 0)),
    EAST("Wschod", new Vector2d(1, 0));

    private String translation;
    private Vector2d unitV;

    MapDirection(String translation, Vector2d unitV){
        this.translation = translation;
        this.unitV = unitV;
    }
    public String toString(){
        return this.translation;
    }
    public MapDirection next(){
        switch (this){
            case EAST: return SOUTH;
            case WEST: return  NORTH;
            case NORTH: return EAST;
            case SOUTH: return WEST;
        }
        return null;
    }
    public MapDirection previous(){
        switch (this){
            case EAST: return NORTH;
            case WEST: return  SOUTH;
            case NORTH: return WEST;
            case SOUTH: return EAST;
        }
        return null;
    }
    public Vector2d toUnitVector(){
        return this.unitV;
    }
}
