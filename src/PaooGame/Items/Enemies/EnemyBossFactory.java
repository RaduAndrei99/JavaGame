package PaooGame.Items.Enemies;

import PaooGame.RefLinks;
import PaooGame.States.PlayState;

public class EnemyBossFactory {

    public static Enemy getBoss(RefLinks refs, int x, int y){
        switch (PlayState.current_level){
            case 1:
                return new BigOrc(refs,x,y);//level 3
            case 2:
                return new BigDemon(refs,x,y);//level 2
            case 3:
                return new BigGnoll(refs,x,y);//level 3
            case 4:
                return new BigGuardian(refs,x,y);//level 4
            default:
                return null;
        }
    }
}
