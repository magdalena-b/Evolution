package Game;

public enum MapDirection {
    NORTH("Polnoc", new Vector2d(0, 1)),
    SOUTH("Poludnie", new Vector2d(0, -1)),
    WEST("Zachod", new Vector2d(-1, 0)),
    EAST("Wschod", new Vector2d(1, 0)),
    NORTHWEST("Północny zachód",new Vector2d(-1, 1)),
    NORTHEAST("Północny wschód", new Vector2d(1,1)),
    SOUTHWEST("Południowy zachód", new Vector2d(-1, -1)),
    SOUTHEAST("Południowy wschód", new Vector2d(1, -1));


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
            case NORTH: return NORTHEAST;
            case NORTHEAST: return EAST;
            case EAST: return SOUTHEAST;
            case SOUTHEAST: return SOUTH;
            case SOUTH: return SOUTHWEST;
            case SOUTHWEST: return WEST;
            case WEST: return NORTHWEST;
            case NORTHWEST: return NORTH;
        }
        return null;
    }
    public MapDirection previous(){
        switch (this){
            case NORTH: return NORTHWEST;
            case NORTHWEST: return WEST;
            case WEST: return SOUTHWEST;
            case SOUTHWEST: return SOUTH;
            case SOUTH: return SOUTHEAST;
            case SOUTHEAST: return EAST;
            case EAST: return NORTHEAST;
            case NORTHEAST: return NORTH;
        }
        return null;
    }
    public Vector2d toUnitVector(){
        return this.unitV;
    }
}
