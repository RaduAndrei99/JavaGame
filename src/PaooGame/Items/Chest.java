package PaooGame.Items;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Chest extends Item {
    protected BufferedImage image;

    protected Item stored_item;
    protected boolean isOpened;

    protected Rectangle stored_item_bounds;

    public Chest(RefLinks refLink, float x, float y, int width, int height) {
        super(refLink, x, y, width, height);
        image = Assets.chest_closed;
        isOpened = false;

    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(image,(int)(this.x - refLink.GetGame().getCamera().getXOffset()),(int)(this.y - refLink.GetGame().getCamera().getYOffset()), this.width, this.height, null);
        //g.drawRect((int)(x - refLink.GetGame().getCamera().getXOffset()),(int)(y- refLink.GetGame().getCamera().getYOffset()),width,height);
        //g.drawRect((int)(stored_item_bounds.x - refLink.GetGame().getCamera().getXOffset()),(int)(stored_item_bounds.y- refLink.GetGame().getCamera().getYOffset()),stored_item_bounds.width,stored_item_bounds.height);
    }

    public Rectangle getNormalBounds(){
        return this.normalBounds;
    }

    public void openChest(){
        image = Assets.chest_opened;
        isOpened = true;

        refLink.GetMap().addDiscardedItem(stored_item);
    }

    public void putItem(Item item){
        stored_item = item;
    }

    public boolean isChestOpened(){
        return isOpened;
    }

    public Rectangle getStoredItemBounds(){
        return stored_item_bounds;
    }
}
