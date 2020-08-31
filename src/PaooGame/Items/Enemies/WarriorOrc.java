package PaooGame.Items.Enemies;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import java.awt.*;
import java.awt.image.BufferedImage;

public class WarriorOrc extends Enemy {

    public static int DEFAULT_WIDTH = 16*3;
    public static int DEFAULT_HEIGHT = 16*4;
    public static int DEFAULT_SPEED = 1;

    public static int DEFAULT_BOUNDS_WIDTH = 25;
    public static int DEFAULT_BOUNDS_HEIGHT = 40;

    public WarriorOrc(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        image = new BufferedImage[4];

        image[0] = Assets.middleOrc1;
        image[1] = Assets.middleOrc2;
        image[2] = Assets.middleOrc3;
        image[3] = Assets.middleOrc4;

        this.life = 200;
        this.damage = 1;
        this.speed = 3;

        collision_offset_y = 20;
        collision_offset_x = 10;

        this.normalBounds = new Rectangle((int)x , (int)y , DEFAULT_BOUNDS_WIDTH,DEFAULT_BOUNDS_HEIGHT);
        this.speed = DEFAULT_SPEED;

        coins_dropped = 3;

        enemy_id = EnemiesFactory.WARRIOR_ORC;


    }



}
