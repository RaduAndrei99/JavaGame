package PaooGame.Maps.Rooms;

import PaooGame.Items.Door;

import java.util.Random;

public abstract class Room {
    protected final int NO_OF_TILES_WIDTH = 40;
    protected final int NO_OF_TILES_HEIGHT = 20;

    protected final int TILE_SIZE = 48;

    protected Door []doors;

    Random rand;

    protected int [][]room_layout;

    public Room(){
        rand = new Random();
        doors = new Door[4];

        boolean door_created = false;

        while(!door_created)
            for(int i = 0; i<4; ++i) {
                if (rand.nextInt(2) == 1) {
                    switch (i){
                        case 0://North
                            doors[0] = new Door(null,NO_OF_TILES_WIDTH/2-2,0,4*TILE_SIZE,2*TILE_SIZE);
                            doors[0].setI(-1);
                            doors[0].setJ(0);
                            break;

                        case 1://East
                            doors[1] = new Door(null,1920 - 2*TILE_SIZE,NO_OF_TILES_HEIGHT/2-2,2*TILE_SIZE,4*TILE_SIZE);
                            doors[0].setI(0);
                            doors[0].setJ(1);
                            break;

                        case 2://South
                            doors[2] = new Door(null,1920 - 2*TILE_SIZE,1080 - 2/TILE_SIZE,4*TILE_SIZE,2*TILE_SIZE);
                            doors[0].setI(1);
                            doors[0].setJ(0);
                            break;

                        case 3://West
                            doors[3] = new Door(null,0,NO_OF_TILES_HEIGHT/2-2,4*TILE_SIZE,2*TILE_SIZE);
                            doors[0].setI(0);
                            doors[0].setJ(-1);
                            break;
                    }
                    door_created = true;
                }
            }

    }

    public Door[] getDoors(){
        return this.doors;
    }
}
