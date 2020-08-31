package PaooGame.Items;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.Sound.Sound;
import PaooGame.UI.Scoreboard;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Vector;

public class Coin extends Item{

    public static int DEFAULT_WIDTH = 24;
    public static int DEFAULT_HEIGHT = 24;

    protected static int VALUE = 10;

    protected boolean first_time_draw = true;
    protected int wait = 0;
    protected int currentPos = 0;

    protected BufferedImage []images;

    public Coin(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        images = new BufferedImage[4];
        images[0] = Assets.coin1;
        images[1] = Assets.coin2;
        images[2] = Assets.coin3;
        images[3] = Assets.coin4;

    }

    @Override
    public void Update() {
        if(CollisionDetector.checkRectanglesCollision(getNormalBounds(),Hero.GetInstance().getNormalBounds())){
            Scoreboard.updateScore(VALUE);
            Sound.playSound(Sound.coin_sound);
            refLink.GetMap().getRoom().removeItem(this);
        }
    }

    @Override
    public void Draw(Graphics g) {
        //g.drawRect((int) (normalBounds.x - refLink.GetGame().getCamera().getXOffset() + collision_offset_x ), (int) (normalBounds.y - refLink.GetGame().getCamera().getYOffset() + collision_offset_y ), normalBounds.width , normalBounds.height );
        g.drawImage(images[nextPos()], (int) (x - refLink.GetGame().getCamera().getXOffset() + x_mirror_offset), (int) (y - refLink.GetGame().getCamera().getYOffset()), position * width, height, null);
    }

    protected int nextPos(){
        if(first_time_draw){
            Random rand = new Random();
            currentPos = rand.nextInt(images.length);
            first_time_draw = false;
        }else {
            wait++;
            if (wait > 5) {
                wait = 0;
                if (currentPos < images.length - 1)
                    return currentPos++;
                else
                    return currentPos = 0;
            }
        }
        return  currentPos;
    }
}
