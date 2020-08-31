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

public class BlueSpell extends Spell {



    public BlueSpell(RefLinks refLink, float x, float y, Point target) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, target);

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
}
