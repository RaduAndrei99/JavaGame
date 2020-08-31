package PaooGame.Particles;

import PaooGame.Items.Hero;
import PaooGame.Items.Item;
import PaooGame.RefLinks;
import PaooGame.Sound.Sound;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class Spell extends Item {

    public static final int DEFAULT_WIDTH = 20;
    public static final int DEFAULT_HEIGHT = 10;

    public static final int DAMAGE = 1;

    public static final float DEFAULT_SPEED = 10.0f;


    public static final int DEFAULT_BOUNDS_WIDTH = 40;
    public static final int DEFAULT_BOUNDS_HEIGHT = 20;

    protected final int NO_OF_IMAGES = 5;

    protected int wait;
    protected int currentPos = 0;

    protected BufferedImage[]images;


    protected Point target;

    protected boolean firstTimeDraw = true;
    protected boolean firstTimeUpdate = true;

    float drawAngle;
    float updateAngle;

    float speed;

    public Spell(RefLinks refLink, float x, float y, int width, int height, Point target) {
        super(refLink, x, y, width, height);
        this.target = target;
    }

    @Override
    public void Update() {
        if(refLink.GetMap().isTileSolid(normalBounds.y / Tile.TILE_WIDTH,(normalBounds.x + normalBounds.width)/Tile.TILE_HEIGHT)) {
            refLink.GetMap().getRoom().removeItem(this);
            if(this instanceof Fireball)
                Sound.playSound(Sound.fireBallExplosion);
            else
                Sound.playSound(Sound.blueSpellExplosion);
        }
        else{
            if(firstTimeUpdate){
                firstTimeUpdate = false;
                updateAngle = (float) Math.atan2(target.y - y, target.x - x);
            }

            x += Math.cos(updateAngle) * speed;
            y += Math.sin(updateAngle) * speed;

            normalBounds.x = (int)x;
            normalBounds.y = (int)y;
        }

    }

    @Override
    public void Draw(Graphics g) {

        AffineTransform at = AffineTransform.getTranslateInstance(x - refLink.GetGame().getCamera().getXOffset(), y - refLink.GetGame().getCamera().getYOffset());

        if(firstTimeDraw) {
            drawAngle = (float) Math.atan2(target.y - y,target.x - x);
            firstTimeDraw = false;
        }


        at.rotate(drawAngle, (float) this.images[currentPos].getWidth() / 2, (float) this.images[currentPos].getHeight() / 2);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);

        g2d.drawImage(images[nextPos()], at, null);

        //g.drawRect((int) (normalBounds.x - refLink.GetGame().getCamera().getXOffset() + collision_offset_x), (int) (normalBounds.y - refLink.GetGame().getCamera().getYOffset() + collision_offset_y), normalBounds.width, normalBounds.height);

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
