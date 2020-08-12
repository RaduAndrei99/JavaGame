package PaooGame.Maps;

import PaooGame.RefLinks;

public class LevelFactory {
    public static Map getMap(int level, RefLinks r){
        switch (level){
            case 1:
                return new Level1(r);

            case 2:
                return new Level2(r);

            case 3:
                return new Level3(r);

            case 4:
                return new Level4(r);

            case 5:
                return new Level5(r);


            default:
                return null;
        }
    }
}
