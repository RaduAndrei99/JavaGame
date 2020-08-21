package PaooGame.Items.Doors;

import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

public class SouthDoor extends Door {

    public SouthDoor(RefLinks refLink) {
        super(refLink,18* Tile.TILE_WIDTH, 17*Tile.TILE_HEIGHT, 4 * Tile.TILE_WIDTH, 3 * Tile.TILE_HEIGHT);
    }

}
