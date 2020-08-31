package PaooGame.Items.Doors;

import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

public class SouthDoor extends Door {

    public SouthDoor(RefLinks refLink) {
        super(refLink,19* Tile.TILE_WIDTH, 17*Tile.TILE_HEIGHT, 2 * Tile.TILE_WIDTH, 3 * Tile.TILE_HEIGHT);

        this.door_tiles = new int[][]{
                {2,2},
                {2,2},
                {2,2},
        };
    }

}
