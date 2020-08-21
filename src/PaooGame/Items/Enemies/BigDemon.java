package PaooGame.Items.Enemies;

import PaooGame.Graphics.Assets;
import PaooGame.Items.*;
import PaooGame.Items.Weapons.Weapon;
import PaooGame.RefLinks;
import PaooGame.Sound.Sound;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class BigDemon extends Enemy {

    public static int DEFAULT_SPRITE_WIDTH = 32*4;
    public static int DEFAULT_SPRITE_HEIGHT = 36*4;
    public static int DEFAULT_SPEED = 2;

    public static int DEFAULT_BOUNDS_WIDTH = 60;
    public static int DEFAULT_BOUNDS_HEIGHT = 80;


    public BigDemon(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_SPRITE_WIDTH, DEFAULT_SPRITE_HEIGHT);

        image = new BufferedImage[4];

        image[0] = Assets.bigDemon1;
        image[1] = Assets.bigDemon2;
        image[2] = Assets.bigDemon3;
        image[3] = Assets.bigDemon4;

        this.life = 200;
        this.damage = 2;

        collision_offset_y = 50;
        collision_offset_x = 40;

        this.normalBounds = new Rectangle((int)x , (int)y , DEFAULT_BOUNDS_WIDTH,DEFAULT_BOUNDS_HEIGHT);
        this.speed = DEFAULT_SPEED;

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
