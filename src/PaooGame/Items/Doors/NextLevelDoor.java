package PaooGame.Items.Doors;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NextLevelDoor extends Door {

    protected static final int DEFAULT_WIDTH = 192;
    protected static final int DEFAULT_HEIGHT = 86;

    protected static final int DEFAULT_BOUNDS_WIDTH = 192;
    protected static final int DEFAULT_BOUNDS_HEIGHT = 170;

    protected BufferedImage image;

    public NextLevelDoor(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        image = Assets.opened_door;


        this.normalBounds = new Rectangle((int)x,(int)y,DEFAULT_BOUNDS_WIDTH,DEFAULT_BOUNDS_HEIGHT);
    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(image, (int) (x - refLink.GetGame().getCamera().getXOffset()), (int) (y - refLink.GetGame().getCamera().getYOffset()),  width, height, null);
        //g.setColor(Color.magenta);
        //g.drawRect((int) (normalBounds.x - refLink.GetGame().getCamera().getXOffset()), (int) (normalBounds.y - refLink.GetGame().getCamera().getYOffset() + collision_offset_y), normalBounds.width, normalBounds.height - collision_offset_y);

    }

    @Override
    public void Update() {

    }
}
