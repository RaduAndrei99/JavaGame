package PaooGame.Items.Weapons;

import PaooGame.Graphics.Assets;
import PaooGame.Items.ItemFactory;
import PaooGame.RefLinks;


public class Mace extends Weapon {

    private final static int DEFAULT_WIDTH = 40;
    private final static int DEFAULT_HEIGHT = 80;

    public Mace(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.image = Assets.mace;
        this.damage = 25;

        this.item_ID = ItemFactory.MACE_ID;

    }
}
