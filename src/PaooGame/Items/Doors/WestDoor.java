package PaooGame.Items.Doors;

import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

public class WestDoor extends Door {

    public WestDoor(RefLinks refLink) {
        super(refLink,0, 8*Tile.TILE_HEIGHT, (int)(2.5 * Tile.TILE_WIDTH), 4 * Tile.TILE_HEIGHT);
    }

}
