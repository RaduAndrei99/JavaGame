package PaooGame.Items.Enemies;

import PaooGame.RefLinks;

public class EnemiesFactory {

    public static final int LITTLE_ORC = 1;
    public static final int LITTLE_DEMON = 2;
    public static final int LITTLE_SKELETON = 3;
    public static final int MASKED_ORC = 4;
    public static final int WARRIOR_ORC = 5;
    public static final int LITTLE_WIZARD = 6;
    public static final int OLD_WIZARD = 7;

    public static final int BIG_DEMON = 8;
    public static final int BIG_ORC = 9;
    public static final int BIG_GNOLL = 10;
    public static final int BIG_GUARDIAN = 11;


    public static Enemy getEnemy(RefLinks r, int enemy, int x, int y){

        switch(enemy){
            case LITTLE_ORC:
                return new LittleOrc(r, x, y);

            case LITTLE_DEMON:
                return new LittleDemon(r, x, y);

            case LITTLE_SKELETON:
                return new LittleSkeleton(r, x, y);

            case MASKED_ORC:
                return new MaskedOrc(r, x, y);

            case WARRIOR_ORC:
                return new WarriorOrc(r, x, y);

            case LITTLE_WIZARD:
                return new LittleWizard(r, x, y);

            case OLD_WIZARD:
                return new OldWizard(r, x, y);

            case BIG_ORC:
                return new BigOrc(r, x, y);

            case BIG_GNOLL:
                return new BigGnoll(r, x, y);

            case BIG_DEMON:
                return new BigDemon(r, x, y);

            case BIG_GUARDIAN:
                return new BigGuardian(r, x, y);

            default:
                return new LittleOrc(r, x, y);

        }
    }

    public static boolean isThisEnemyABoss(int enemy){
        return enemy == BIG_DEMON || enemy == BIG_ORC || enemy == BIG_GNOLL || enemy == BIG_GUARDIAN;
    }

}
