package PaooGame.Items.Enemies;

import PaooGame.Graphics.Assets;

import PaooGame.RefLinks;
import PaooGame.Sound.Sound;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class BigOrc extends Enemy {

    public static int DEFAULT_SPRITE_WIDTH = 32*4;
    public static int DEFAULT_SPRITE_HEIGHT = 36*4;
    public static int DEFAULT_SPEED = 2;

    public static int DEFAULT_BOUNDS_WIDTH = 60;
    public static int DEFAULT_BOUNDS_HEIGHT = 90;


    public BigOrc(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_SPRITE_WIDTH, DEFAULT_SPRITE_HEIGHT);

        image = new BufferedImage[4];

        image[0] = Assets.bigOrc1;
        image[1] = Assets.bigOrc2;
        image[2] = Assets.bigOrc3;
        image[3] = Assets.bigOrc4;

        this.life = 1000;
        this.damage = 3;

        collision_offset_y = 40;
        collision_offset_x = 40;

        this.normalBounds = new Rectangle((int)x , (int)y , DEFAULT_BOUNDS_WIDTH,DEFAULT_BOUNDS_HEIGHT);
        this.speed = DEFAULT_SPEED;

        moans = new File[2];

        moans[0] = Sound.death_big_orc;
        moans[1] = Sound.orc_sound1;

    }

/*
    void colorTiles(Graphics g){
        g.setColor(Color.RED);
         if(this.path!=null) {
             for (int tile : this.path) {
                 int c = tile / 32;
                 int r = tile % 32;
                 g.drawRect((int)(r * 48 - refLink.GetGame().getCamera().getXOffset()) , (int)(c * 48 - refLink.GetGame().getCamera().getYOffset()), 48, 48);
             }
         }
    }
*/
}
