package PaooGame.Items.Enemies;

import PaooGame.Items.Character;
import PaooGame.RefLinks;
import PaooGame.Sound.Sound;

public abstract class  Enemy extends Character {
    protected final int BASE_DAMAGE = 10;

    protected int damage;
    public Enemy(RefLinks refLink, float x, float y, int width, int height) {
        super(refLink, x, y, width, height);

        this.damage = BASE_DAMAGE;
    }

    protected void getHit(){
        Sound.playSound(Sound.stab);
    }

    public int  getDamage(){
        return this.damage;
    }
}
