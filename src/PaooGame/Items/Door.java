package PaooGame.Items;

import PaooGame.Items.Item;
import PaooGame.RefLinks;
import org.omg.CORBA.BAD_POLICY_VALUE;

import java.awt.*;

public class Door extends Item {

    protected int i; /*!< retin pozitia camerei din matrice pe y*/
    protected int j;  /*!< retin pozitia camerei din matrice pe x*/

    public Door(RefLinks refLink, float x, float y, int width, int height) {
        super(refLink, x, y, width, height);
    }

    @Override
    public void Update() {
        
    }

    @Override
    public void Draw(Graphics g) {

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

}
