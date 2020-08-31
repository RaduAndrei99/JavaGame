package PaooGame.Items.Doors;

import PaooGame.Items.Item;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;
import org.omg.CORBA.BAD_POLICY_VALUE;

import java.awt.*;

public abstract class Door extends Item {

    protected int i; /*!< retin pozitia camerei din matrice pe y*/
    protected int j;  /*!< retin pozitia camerei din matrice pe x*/



    protected boolean linked = false;

    protected int [][]door_tiles;

    public Door(RefLinks refLink, float x, float y, int width, int height) {
        super(refLink, x, y, width, height);
    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw(Graphics g) {
       // g.setColor(Color.magenta);
        //g.drawRect((int) (normalBounds.x - refLink.GetGame().getCamera().getXOffset()), (int) (normalBounds.y - refLink.GetGame().getCamera().getYOffset() + collision_offset_y), normalBounds.width, normalBounds.height - collision_offset_y);

        if(door_tiles!=null) {
            for (int i = 0; i < this.door_tiles.length; ++i) {
                for (int j = 0; j < door_tiles[0].length; ++j) {
                    if(door_tiles[i][j] != 0)
                        Tile.tiles[door_tiles[i][j]].Draw(g,(int)(this.x + j*Tile.TILE_WIDTH - refLink.GetGame().getCamera().getXOffset()), (int)(this.y + i*Tile.TILE_HEIGHT - refLink.GetGame().getCamera().getYOffset()));
                }
            }
        }
    }

    public void setI(int val){
        i = val;
    }

    public void setJ(int val){
        j = val;
    }

    public int getI(){
        return i;
    }

    public int getJ(){
        return j;
    }

    public void openDoor(){
        this.linked = true;
    }


    public void closeDoor(){
        this.linked = false;
    }

    public boolean isLinked(){
        return this.linked;
    }

}
