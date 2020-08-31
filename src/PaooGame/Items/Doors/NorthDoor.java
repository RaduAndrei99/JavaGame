package PaooGame.Items.Doors;

import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

public class NorthDoor extends Door {

    public NorthDoor(RefLinks refLink) {
        super(refLink,19* Tile.TILE_WIDTH, 0, 2 * Tile.TILE_WIDTH, 4 * Tile.TILE_HEIGHT);

        this.door_tiles = new int[][]{
                {2,2},
                {2,2}
        };

    }


}
