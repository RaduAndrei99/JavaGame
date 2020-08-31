package PaooGame.Items.Weapons;

import PaooGame.Graphics.Assets;
import PaooGame.Items.ItemFactory;
import PaooGame.RefLinks;


public class ShortHammer extends Weapon {

    private final static int DEFAULT_WIDTH = 40;
    private final static int DEFAULT_HEIGHT = 80;

    public ShortHammer(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.image = Assets.short_hammer;
        this.damage = 10;

        this.item_ID = ItemFactory.SHORT_HAMMER;

    }
}
