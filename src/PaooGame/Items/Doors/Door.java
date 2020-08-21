package PaooGame.Items.Doors;

import PaooGame.Items.Item;
import PaooGame.RefLinks;
import org.omg.CORBA.BAD_POLICY_VALUE;

import java.awt.*;

public  class Door extends Item {

    protected int i; /*!< retin pozitia camerei din matrice pe y*/
    protected int j;  /*!< retin pozitia camerei din matrice pe x*/



    public boolean linked = false;

    public Door(RefLinks refLink, float x, float y, int width, int height) {
        super(refLink, x, y, width, height);
    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw(Graphics g) {
        g.setColor(Color.magenta);
        g.drawRect((int) (normalBounds.x - refLink.GetGame().getCamera().getXOffset()), (int) (normalBounds.y - refLink.GetGame().getCamera().getYOffset() + collision_offset_y), normalBounds.width, normalBounds.height - collision_offset_y);

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
