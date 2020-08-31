package PaooGame.Items;

import PaooGame.Items.Potions.LifePotion;
import PaooGame.Items.Potions.SpeedPotion;
import PaooGame.Items.Weapons.*;
import PaooGame.RefLinks;

import java.util.Random;

public class ItemGenerator {
    
    public static Item createItem(RefLinks refs, int x, int y){
        Random rand = new Random();
        if(rand.nextInt(2) + 1 == 1) {
            float probability = rand.nextFloat();

            if (probability < 0.01)
                return new GoldenSword(refs,x, y + 10);
            else if (probability < 0.03)
                return new HeavySword(refs,x, y + 10);
            else if (probability < 0.07)
                return new SawSword(refs,x, y + 10);
            else if (probability < 0.13)
                return new Axe(refs,x, y + 10);
            else if (probability < 0.19)
                return new Mace(refs,x, y + 10);
            else if (probability < 0.32)
                return new KnightSword(refs,x, y + 10);
            else if (probability < 0.45)
                return new BigHammer(refs,x, y + 10);
            else if (probability < 0.62)
                return new BasicSword(refs,x, y + 10);
            else if (probability < 0.8)
                return new ShortHammer(refs,x, y + 10);
            else
                return new RustySword(refs,x, y + 10);

        } else{
            if(rand.nextInt(2) + 1 == 1) {
                return new SpeedPotion(refs,x, y + 10);
            }else{
                return new LifePotion(refs,x, y + 10);
            }
        }
    }
}
