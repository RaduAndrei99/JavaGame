package PaooGame.Items;

import PaooGame.Items.Potions.LifePotion;
import PaooGame.Items.Potions.SpeedPotion;
import PaooGame.Items.Weapons.*;
import PaooGame.RefLinks;

public class ItemFactory {

    public static final int AXE_ID = 1;
    public static final int BASIC_SWORD_ID = 2;
    public static final int BIG_HAMMER_ID = 3;
    public static final int GOLDEN_SWORD_ID= 4;
    public static final int HEAVY_SWORD_ID = 5;
    public static final int KNIGHT_SWORD_ID = 6;
    public static final int MACE_ID = 7;
    public static final int EXCALIBUR_ID = 8;
    public static final int RUSTY_SWORD = 9;
    public static final int SAW_SWORD = 10;
    public static final int SHORT_HAMMER = 11;
    public static final int SPEED_POTION_ID = 12;
    public static final int LIFE_POTION_ID = 13;

    public static final int KEY_ID = 14;



    public static Item getItem(RefLinks refs,int item, int x, int y) {
        switch (item){
            case AXE_ID:
                return new Axe(refs,x,y);
            case BASIC_SWORD_ID:
                return new BasicSword(refs,x,y);
            case BIG_HAMMER_ID:
                return new BigHammer(refs,x,y);
            case GOLDEN_SWORD_ID:
                return new GoldenSword(refs,x,y);
            case HEAVY_SWORD_ID:
                return new HeavySword(refs,x,y);
            case KNIGHT_SWORD_ID:
                return new KnightSword(refs,x,y);
            case MACE_ID:
                return new Mace(refs,x,y);
            case EXCALIBUR_ID:
                return new Excalibur(refs,x,y);
            case RUSTY_SWORD:
                return new RustySword(refs,x,y);
            case SAW_SWORD:
                return new SawSword(refs,x,y);
            case SHORT_HAMMER:
                return new ShortHammer(refs,x,y);
            case SPEED_POTION_ID:
                return new SpeedPotion(refs,x,y);
            case LIFE_POTION_ID:
                return new LifePotion(refs,x,y);
            case KEY_ID:
                return new Key(refs,x,y);
            default:
                return null;
        }
    }
}
