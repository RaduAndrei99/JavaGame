package PaooGame.UI;

import PaooGame.Graphics.Assets;
import PaooGame.Input.GameMouseListener;
import PaooGame.Items.Item;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class InventoryCell extends UIElement {

    public static int CELL_HEIGHT = 60;
    public static int CELL_WIDTH = 60;

    protected boolean isOccupied;

    protected BufferedImage cell_image;

    protected Item stored_item;

    public InventoryCell(RefLinks refs, float x, float y) {
        super(refs, x, y);
        isOccupied = false;
        cell_image = Assets.inventory_cell;
    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(cell_image, (int) (x), (int) (y), CELL_WIDTH, CELL_HEIGHT, null);
        if(stored_item!=null) {
            if (isOccupied) {
                //g.drawImage(stored_item.getImage(), (int) (this.x + 5), (int) (this.y + 5), 50, 50, null);

                float angle = 45;

                AffineTransform at = AffineTransform.getTranslateInstance(x + 16, y + 18);
                at.rotate(Math.toRadians(angle), 25, 25);
                at.scale(2, 2);

                Graphics2D g2d = (Graphics2D) g;


                g2d.drawImage(stored_item.getImage(), at,null);

            }
        }
    }

    @Override
    public void Update() {
    }

    public void putItemInSlot(Item item){
        this.stored_item = item;
        isOccupied = true;
    }

    public Item getItem(){
        return stored_item;
    }

    public boolean isSlotOccupied(){
        return this.isOccupied;
    }

    public void setCellDiscarded(){
        this.cell_image = Assets.inventory_cell;
    }

    public void setCellSelected(){
        this.cell_image = Assets.inventory_cell_selected;
    }

    public Item removeStoredItem(){
        Item temp = stored_item;
        isOccupied = false;
        stored_item = null;

        return temp;
    }


}
