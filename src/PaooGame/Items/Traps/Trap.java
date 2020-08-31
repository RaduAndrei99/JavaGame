package PaooGame.Items.Traps;

import PaooGame.Items.Item;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;


public abstract class Trap extends Item {

    protected final int DEFAULT_DAMAGE_GIVEN = 1;

    protected boolean isActivated = false;
    protected boolean isGivingDamage = false;

    protected int damage_given;

    protected int trap_id;

    public Trap(RefLinks refLink, float x, float y, int width, int height) {
        super(refLink, x, y, width, height);
        damage_given = DEFAULT_DAMAGE_GIVEN;
    }

    public abstract void activate();

    public abstract boolean isGivingDamage();

    public int getDamage(){
        return damage_given;
    }

    public int getTrap_id(){
        return trap_id;
    }
}
