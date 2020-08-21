package PaooGame.Items.Doors;

import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

public class EastDoor extends Door {

    public EastDoor(RefLinks refLink) {
        super(refLink,(int)(37.5* Tile.TILE_WIDTH), 8*Tile.TILE_HEIGHT, (int)(2.5 * Tile.TILE_WIDTH), 4 * Tile.TILE_HEIGHT);
    }

}
