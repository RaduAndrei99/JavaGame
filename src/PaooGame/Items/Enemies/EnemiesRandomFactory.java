package PaooGame.Items.Enemies;

import PaooGame.RefLinks;

import java.util.Random;

public class EnemiesRandomFactory {
    public static final int NO_OF_ENEMIES = 7;

    public static Enemy getEnemy(RefLinks r, int x, int y){
        Random rand = new Random();

        switch(rand.nextInt(NO_OF_ENEMIES) + 1){
            case 1:
                return new LittleOrc(r, x, y);

            case 2:
                return new LittleDemon(r, x, y);

            case 3:
                return new LittleSkeleton(r, x, y);

            case 4:
                return new MaskedOrc(r, x, y);

            case 5:
                return new WarriorOrc(r, x, y);

            case 6:
                return new LittleWizard(r, x, y);

            case 7:
                return new OldWizard(r, x, y);

            default:
                return new LittleOrc(r, x, y);

        }
    }

}
