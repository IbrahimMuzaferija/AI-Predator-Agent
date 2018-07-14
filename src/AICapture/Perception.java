package AICapture;

public class Perception {

    private Cell east, south, north, west;

    public Perception(Cell east, Cell south, Cell north, Cell west){
        this.east = east;
        this.south = south;
        this.north = north;
        this.west = west;
    }

    public Cell getEast() {
        return east;
    }

    public Cell getNorth() {
        return north;
    }

    public Cell getSouth() {
        return south;
    }

    public Cell getWest() {
        return west;
    }
}
