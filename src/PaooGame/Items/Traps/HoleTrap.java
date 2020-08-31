package PaooGame.Items.Traps;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HoleTrap extends Trap{
    protected BufferedImage sprite;

    public static int DEFAULT_WIDTH = Tile.TILE_WIDTH;
    public static int DEFAULT_HEIGHT = Tile.TILE_HEIGHT;



    public HoleTrap(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        sprite = Assets.floorHole;

        normalBounds = new Rectangle((int)(x - width/2.0), (int)(y - height), 2*width, 2*height);
    }

    @Override
    public void activate() {

    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw(Graphics g) {
       // g.drawRect((int) (getNormalBounds().x - refLink.GetGame().getCamera().getXOffset()), (int) (getNormalBounds().y - refLink.GetGame().getCamera().getYOffset() ) , getNormalBounds().width , getNormalBounds().height );

        g.drawImage(sprite,(int)(x - refLink.GetGame().getCamera().getXOffset()),(int)(y - refLink.GetGame().getCamera().getYOffset()),width,height,null);
    }

    @Override
    public boolean isGivingDamage() {
        return isGivingDamage;
    }

}
