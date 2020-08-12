package PaooGame.Items.Weapons;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

public class BigHammer extends Weapon {

    private final static int DEFAULT_WIDTH = 40;
    private final static int DEFAULT_HEIGHT = 100;

    public BigHammer(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.image = Assets.big_hammer;
        this.damage = 10;
    }

}
