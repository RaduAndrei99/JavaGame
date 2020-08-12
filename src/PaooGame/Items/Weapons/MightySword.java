package PaooGame.Items.Weapons;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

public class MightySword extends Weapon {

    private final static int DEFAULT_WIDTH = 40;
    private final static int DEFAULT_HEIGHT = 100;

    public MightySword(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.image = Assets.mighty_sword;
        this.damage = 100;
    }

}
