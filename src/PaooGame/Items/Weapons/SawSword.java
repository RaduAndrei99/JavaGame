package PaooGame.Items.Weapons;

import PaooGame.Graphics.Assets;
import PaooGame.Items.ItemFactory;
import PaooGame.RefLinks;

public class SawSword extends Weapon {

    private final static int DEFAULT_WIDTH = 40;
    private final static int DEFAULT_HEIGHT = 80;

    public SawSword(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.image = Assets.saw_sword;
        this.damage = 30;

        this.item_ID = ItemFactory.SAW_SWORD;

    }
}
