package PaooGame.Items.Doors;

import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

public class NorthDoor extends Door {

    public NorthDoor(RefLinks refLink) {
        super(refLink,18* Tile.TILE_WIDTH, Tile.TILE_WIDTH, 4 * Tile.TILE_WIDTH, 3 * Tile.TILE_HEIGHT);
    }

}
