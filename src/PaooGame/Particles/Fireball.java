package PaooGame.Particles;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Item;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Fireball extends Item {

    public static final int DEFAULT_WIDTH = 80;
    public static final int DEFAULT_HEIGHT = 50;

    public static final int DEFAULT_BOUNDS_WIDTH = 40;
    public static final int DEFAULT_BOUNDS_HEIGHT = 40;

    protected final int NO_OF_IMAGES = 5;

    protected int wait;
    protected int currentPos = 0;

    protected BufferedImage []images;

    public Fireball(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        images = new BufferedImage[NO_OF_IMAGES];

        images[0] = Assets.fireBall1;
        images[1] = Assets.fireBall2;
        images[2] = Assets.fireBall3;
        images[3] = Assets.fireBall4;
        images[4] = Assets.fireBall5;

        collision_offset_x = 30;

        this.normalBounds = new Rectangle((int)x,(int)y,DEFAULT_BOUNDS_WIDTH,DEFAULT_BOUNDS_HEIGHT);

    }

    @Override
    public void Update() {
        if(this.x >= 1.5*Toolkit.getDefaultToolkit().getScreenSize().width  || this. x < 0)
            refLink.GetMap().getRoom().removeItem(this);
        else{
            this.x += 5;
            UpdateBoundsRectangle();
        }
    }

    @Override
    public void Draw(Graphics g) {
        g.drawRect((int) (normalBounds.x - refLink.GetGame().getCamera().getXOffset() + collision_offset_x ), (int) (normalBounds.y - refLink.GetGame().getCamera().getYOffset() + collision_offset_y ), normalBounds.width , normalBounds.height );

        g.drawImage(images[nextPos()], (int) (x - refLink.GetGame().getCamera().getXOffset() + x_mirror_offset), (int) (y - refLink.GetGame().getCamera().getYOffset()), position * width, height, null);
    }

    protected int nextPos(){
        wait++;
        if(wait > 5) {
            wait = 0;
            if (currentPos < images.length - 1)
                return currentPos++;
            else
                return currentPos = 0;
        }
        return  currentPos;
    }
}
