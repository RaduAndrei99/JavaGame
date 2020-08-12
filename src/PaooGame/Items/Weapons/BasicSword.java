package PaooGame.Items.Weapons;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

public class BasicSword extends Weapon {

    private final static int DEFAULT_WIDTH = 40;
    private final static int DEFAULT_HEIGHT = 100;

    public BasicSword(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.image = Assets.basic_sword;
        this.damage = 10;
    }
}
