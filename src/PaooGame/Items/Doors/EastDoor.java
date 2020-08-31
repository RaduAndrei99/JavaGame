package PaooGame.Items.Doors;

import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

public class EastDoor extends Door {

    public EastDoor(RefLinks refLink) {
        super(refLink,(int)(37* Tile.TILE_WIDTH), 10*Tile.TILE_HEIGHT, (int)(2 * Tile.TILE_WIDTH), 2 * Tile.TILE_HEIGHT);

        this.door_tiles = new int[][]{
                {0,0,2},
                {0,0,2},

        };
    }

}
