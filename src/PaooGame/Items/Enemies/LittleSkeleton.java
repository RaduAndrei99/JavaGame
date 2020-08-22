package PaooGame.Items.Enemies;
import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LittleSkeleton extends Enemy {

    public static int DEFAULT_WIDTH = 16*4;
    public static int DEFAULT_HEIGHT = 16*4;
    public static int DEFAULT_SPEED = 4;

    public static int DEFAULT_BOUNDS_WIDTH = 40;
    public static int DEFAULT_BOUNDS_HEIGHT = 40;


    public LittleSkeleton(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        image = new BufferedImage[4];

        image[0] = Assets.littleSkeleton1;
        image[1] = Assets.littleSkeleton2;
        image[2] = Assets.littleSkeleton3;
        image[3] = Assets.littleSkeleton4;

        this.life = 80;
        this.damage = 1;

        speed = DEFAULT_SPEED;

        collision_offset_y = 15;
        collision_offset_x = 15;

        this.normalBounds = new Rectangle((int)x , (int)y , DEFAULT_BOUNDS_WIDTH,DEFAULT_BOUNDS_HEIGHT);
        this.speed = DEFAULT_SPEED;

    }


}
