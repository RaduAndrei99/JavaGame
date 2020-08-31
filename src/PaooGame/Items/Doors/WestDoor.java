package PaooGame.Items.Doors;

import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

public class WestDoor extends Door {

    public WestDoor(RefLinks refLink) {
        super(refLink,0, 10*Tile.TILE_HEIGHT, (int)(3 * Tile.TILE_WIDTH), 2 * Tile.TILE_HEIGHT);

        this.door_tiles = new int[][]{
                {2,0,0},
                {2,0,0},

        };
    }

}
