package PaooGame.Items.Traps;

import PaooGame.RefLinks;

public class TrapsFactory {
    public static final int SPIKE_TRAP = 1;
    public static final int HOLE_TRAP = 2;

    public static Trap getTrap(RefLinks refs,int trap, int x, int y){
        switch (trap){
            case SPIKE_TRAP:
                return new SpikeTrap(refs,x,y);

            case HOLE_TRAP:
                return new HoleTrap(refs,x,y);


            default:
                return new SpikeTrap(refs,x,y);
        }

    }
}
