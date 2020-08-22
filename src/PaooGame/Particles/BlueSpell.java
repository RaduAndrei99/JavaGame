package PaooGame.Particles;

import PaooGame.Graphics.Assets;
import PaooGame.Input.GameMouseListener;
import PaooGame.Items.Hero;
import PaooGame.Items.Item;
import PaooGame.Main;
import PaooGame.RefLinks;
import PaooGame.Sound.Sound;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class BlueSpell extends Item {

    public static final int DEFAULT_WIDTH = 20;
    public static final int DEFAULT_HEIGHT = 10;

    public static final int DAMAGE = 1;

    public static final float DEFAULT_SPEED = 10.0f;


    public static final int DEFAULT_BOUNDS_WIDTH = 40;
    public static final int DEFAULT_BOUNDS_HEIGHT = 20;

    protected final int NO_OF_IMAGES = 5;

    protected int wait;
    protected int currentPos = 0;

    protected BufferedImage []images;


    protected boolean firstTimeDraw = true;
    protected boolean firstTimeUpdate = true;

    float drawAngle;
    float updateAngle;

    float speed;

    public BlueSpell(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        images = new BufferedImage[NO_OF_IMAGES];

        images[0] = Assets.blueSpell1;
        images[1] = Assets.blueSpell2;
        images[2] = Assets.blueSpell3;
        images[3] = Assets.blueSpell4;
        images[4] = Assets.blueSpell5;

        collision_offset_x = 10;

        this.normalBounds = new Rectangle((int)x,(int)y,DEFAULT_BOUNDS_WIDTH,DEFAULT_BOUNDS_HEIGHT);

        speed = DEFAULT_SPEED;

    }

    @Override
    public void Update() {
        if(refLink.GetMap().isTileSolid(normalBounds.y / Tile.TILE_WIDTH,(normalBounds.x + normalBounds.width)/Tile.TILE_HEIGHT)) {
            refLink.GetMap().getRoom().removeItem(this);
            Sound.playSound(Sound.blueSpellExplosion);
        }
        else{
            if(firstTimeUpdate){
                firstTimeUpdate = false;
                updateAngle = (float) Math.atan2(Hero.GetInstance().getNormalBounds().y + Hero.GetInstance().getNormalBounds().height / 2.0 - y, Hero.GetInstance().getNormalBounds().x + Hero.GetInstance().getNormalBounds().width / 2.0 - x);
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
            drawAngle = (float) Math.atan2(Hero.GetInstance().getNormalBounds().y + Hero.GetInstance().getNormalBounds().height / 2.0 - y, Hero.GetInstance().getNormalBounds().x + Hero.GetInstance().getNormalBounds().width / 2.0 - x);
            firstTimeDraw = false;
        }


        at.rotate(drawAngle, (float) this.images[currentPos].getWidth() / 2, (float) this.images[currentPos].getHeight() / 2);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);

        g2d.drawImage(images[nextPos()], at, null);

        g.drawRect((int) (normalBounds.x - refLink.GetGame().getCamera().getXOffset() + collision_offset_x), (int) (normalBounds.y - refLink.GetGame().getCamera().getYOffset() + collision_offset_y), normalBounds.width, normalBounds.height);

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
