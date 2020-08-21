package PaooGame.Items.Enemies;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Enemies.Enemy;
import PaooGame.Items.Hero;
import PaooGame.Items.Item;
import PaooGame.Items.RectangleCollisionDetector;
import PaooGame.Items.Weapons.Weapon;
import PaooGame.RefLinks;
import PaooGame.Sound.Sound;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class BigZombie extends Enemy {

    public static int DEFAULT_WIDTH = 32*4;
    public static int DEFAULT_HEIGHT = 36*4;
    public static int DEFAULT_SPEED = 1;

    public static int DEFAULT_BOUNDS_WIDTH = 60;
    public static int DEFAULT_BOUNDS_HEIGHT = 80;


    public BigZombie(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        image = new BufferedImage[4];

        image[0] = Assets.bigZombie1;
        image[1] = Assets.bigZombie2;
        image[2] = Assets.bigZombie3;
        image[3] = Assets.bigZombie4;

        this.life = 300;
        this.damage = 1;

        collision_offset_y = 50;
        collision_offset_x = 40;

        this.normalBounds = new Rectangle((int)x , (int)y , DEFAULT_BOUNDS_WIDTH,DEFAULT_BOUNDS_HEIGHT);
        this.speed = DEFAULT_SPEED;
    }

}
