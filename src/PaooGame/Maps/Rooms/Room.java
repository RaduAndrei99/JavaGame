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

        while(!door_created) {
            for (int i = 0; i < 4; ++i) {
                if (rand.nextInt(2) == 1) {
                    switch (i) {
                        case 0://North
                            doors[0] = new Door(null, NO_OF_TILES_WIDTH / 2 - 2, 0, 4 * TILE_SIZE, 2 * TILE_SIZE);
                            doors[0].setI(-1);
                            doors[0].setJ(0);
                            break;

                        case 1://East
                            doors[1] = new Door(null, 1920 - 2 * TILE_SIZE, NO_OF_TILES_HEIGHT / 2 - 2, 2 * TILE_SIZE, 4 * TILE_SIZE);
                            doors[1].setI(0);
                            doors[1].setJ(1);
                            break;

                        case 2://South
                            doors[2] = new Door(null, 1920 - 2 * TILE_SIZE, 1080 - 2 / TILE_SIZE, 4 * TILE_SIZE, 2 * TILE_SIZE);
                            doors[2].setI(1);
                            doors[2].setJ(0);
                            break;

                        case 3://West
                            doors[3] = new Door(null, 0, NO_OF_TILES_HEIGHT / 2 - 2, 4 * TILE_SIZE, 2 * TILE_SIZE);
                            doors[3].setI(0);
                            doors[3].setJ(-1);
                            break;
                    }
                    door_created = true;
                }
            }
        }

    }

    public Door[] getDoors(){
        return this.doors;
    }

    @Override
    public String toString(){
        String north = "";
        String east = "";
        String south = "";
        String west = "";

        if(doors[0] != null)
            north += " |\n";
        else
            north +=" \n";

        if(doors[3] != null)
            west = "-";
        else
            west = " ";

        if(doors[1] != null)
            east += "-\n";
        else
            east +=" \n";

        if(doors[2] != null)
            south += " |";
        else
            south +=" ";

        return north + west + "*" + east + south;
    }

    public void openNorthDoorTo(int i, int j){
        if(this.doors[0] == null)
            this.doors[0] = new Door(null, NO_OF_TILES_WIDTH / 2 - 2, 0, 4 * TILE_SIZE, 2 * TILE_SIZE);

        this.doors[0].setI(i);
        this.doors[0].setJ(j);
    }

    public void openEastDoorTo(int i, int j){
        if(this.doors[1] == null)
            this.doors[1] = new Door(null, 1920 - 2 * TILE_SIZE, NO_OF_TILES_HEIGHT / 2 - 2, 2 * TILE_SIZE, 4 * TILE_SIZE);

        this.doors[1].setI(i);
        this.doors[1].setJ(j);
    }

    public void openSouthDoorTo(int i, int j){
        if(this.doors[2] == null)
            this.doors[2] = new Door(null, 1920 - 2 * TILE_SIZE, 1080 - 2 / TILE_SIZE, 4 * TILE_SIZE, 2 * TILE_SIZE);

        this.doors[2].setI(i);
        this.doors[2].setJ(j);
    }

    public void openWestDoorTo(int i, int j){

        if(this.doors[3] == null)
            this.doors[3] = new Door(null, 0, NO_OF_TILES_HEIGHT / 2 - 2, 4 * TILE_SIZE, 2 * TILE_SIZE);

        this.doors[3].setI(i);
        this.doors[3].setJ(j);
    }

    public void closeDoor(int k){
        switch(k){
            case 0:
                this.doors[0] = null;
                break;

            case 1:
                this.doors[1] = null;
                break;
            case 2:
                this.doors[2] = null;
                break;
            case 3:
                this.doors[3] = null;
                break;
        }
    }

    public int [][] getLayout(){
        return this.room_layout;
    }
}
