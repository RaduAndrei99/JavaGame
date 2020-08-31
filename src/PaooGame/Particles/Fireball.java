package PaooGame.Particles;

import PaooGame.Graphics.Assets;

import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Fireball extends Spell {

    public Fireball(RefLinks refLink, float x, float y, Point target) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, target);

        images = new BufferedImage[NO_OF_IMAGES];

        images[0] = Assets.fireBall1;
        images[1] = Assets.fireBall2;
        images[2] = Assets.fireBall3;
        images[3] = Assets.fireBall4;
        images[4] = Assets.fireBall5;

        collision_offset_x = 30;

        this.normalBounds = new Rectangle((int)x,(int)y,DEFAULT_BOUNDS_WIDTH,DEFAULT_BOUNDS_HEIGHT);

        speed = DEFAULT_SPEED;

    }

}
