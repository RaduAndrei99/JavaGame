package PaooGame.Items.Potions;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Hero;
import PaooGame.RefLinks;

public class SpeedPotion extends Potion{
    protected static final float BONUS_SPEED = 5.0f;
    protected static long SPEED_DURATION_TIME = 30;

    private final static int DEFAULT_WIDTH = 40;
    private final static int DEFAULT_HEIGHT = 40;

    public SpeedPotion(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.image = Assets.speedPotion;

    }

    @Override
    public void drinkThisPotion() {
        Hero.GetInstance().takeSpeedBurst(BONUS_SPEED);
    }

    public static float getBonusSpeed(){
        return BONUS_SPEED;
    }

    public static long getSpeedDurationTime(){
        return SPEED_DURATION_TIME;
    }
}
