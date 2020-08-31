package PaooGame.Items.Potions;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Hero;
import PaooGame.Items.ItemFactory;
import PaooGame.RefLinks;

public class LifePotion extends Potion {

    protected final int HEALTH_POINTS_RESTORED = 1;

    private final static int DEFAULT_WIDTH = 40;
    private final static int DEFAULT_HEIGHT = 40;

    public LifePotion(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.image = Assets.lifePotion;

        this.item_ID = ItemFactory.LIFE_POTION_ID;

    }

    @Override
    public void drinkThisPotion() {
        Hero.GetInstance().heal(HEALTH_POINTS_RESTORED);
    }
}
