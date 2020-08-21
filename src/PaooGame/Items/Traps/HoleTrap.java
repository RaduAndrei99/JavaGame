package PaooGame.Items.Traps;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HoleTrap extends Trap{
    protected BufferedImage sprite;


    public HoleTrap(RefLinks refLink, float x, float y, int width, int height) {
        super(refLink, x, y, width, height);
        sprite = Assets.floorHole;
    }

    @Override
    public void activate() {

    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(sprite,(int)(x - refLink.GetGame().getCamera().getXOffset()),(int)(y - refLink.GetGame().getCamera().getYOffset()),width,height,null);
    }

    @Override
    public boolean isGivingDamage() {
        return isGivingDamage;
    }

}
