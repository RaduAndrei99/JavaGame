package PaooGame.Maps.Rooms;

import PaooGame.Items.*;
import PaooGame.Items.Doors.*;
import PaooGame.Items.Enemies.Enemy;
import PaooGame.RefLinks;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Room {
    protected final int NO_OF_TILES_WIDTH = 40;
    protected final int NO_OF_TILES_HEIGHT = 20;

    protected final int TILE_SIZE = 48;

    protected Door[] doors;
    protected ArrayList<Item> entities;
    Random rand;

    protected ArrayList<Pair<String ,Pair<Float, Float>>> potentialEntities;

    protected int[][] room_layout;

    protected final List<Enemy> enemies;


    RefLinks refs;

    public Room(RefLinks refs) {
        rand = new Random();
        doors = new Door[4];
        boolean door_created = false;
        entities = new ArrayList<>();

        this.refs = refs;

        enemies = new ArrayList<>();
        while (!door_created) {
            for (int i = 0; i < 4; ++i) {
                if (rand.nextInt(2) == 1) {
                    switch (i) {
                        case 0://North
                            doors[0] = new NorthDoor(refs);
                            doors[0].setI(-1);
                            doors[0].setJ(0);
                            break;

                        case 1://East
                            doors[1] = new EastDoor(refs);
                            doors[1].setI(0);
                            doors[1].setJ(1);
                            break;

                        case 2://South
                            doors[2] = new SouthDoor(refs);
                            doors[2].setI(1);
                            doors[2].setJ(0);
                            break;

                        case 3://West
                            doors[3] = new WestDoor(refs);
                            doors[3].setI(0);
                            doors[3].setJ(-1);
                            break;
                    }
                    door_created = true;
                }
            }
        }

    }

    public Door[] getDoors() {
        return this.doors;
    }

    @Override
    public String toString() {
        String north = "";
        String east = "";
        String south = "";
        String west = "";

        if (doors[0] != null)
            north += " |\n";
        else
            north += " \n";

        if (doors[3] != null)
            west = "-";
        else
            west = " ";

        if (doors[1] != null)
            east += "-\n";
        else
            east += " \n";

        if (doors[2] != null)
            south += " |";
        else
            south += " ";

        return north + west + "*" + east + south;
    }

    public void openNorthDoorTo(int i, int j) {
        if (this.doors[0] == null)
            this.doors[0] = new NorthDoor(refs);

        this.doors[0].setI(i);
        this.doors[0].setJ(j);
        this.doors[0].openDoor();
    }

    public void openEastDoorTo(int i, int j) {
        if (this.doors[1] == null)
            this.doors[1] = new EastDoor(refs);

        this.doors[1].setI(i);
        this.doors[1].setJ(j);
        this.doors[1].openDoor();

    }

    public void openSouthDoorTo(int i, int j) {
        if (this.doors[2] == null)
            this.doors[2] = new SouthDoor(refs);

        this.doors[2].setI(i);
        this.doors[2].setJ(j);
        this.doors[2].openDoor();

    }

    public void openWestDoorTo(int i, int j) {

        if (this.doors[3] == null)
            this.doors[3] = new WestDoor(this.refs);

        this.doors[3].setI(i);
        this.doors[3].setJ(j);
        this.doors[3].openDoor();

    }

    public void closeDoor(int k) {
        switch (k) {
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

    public int[][] getLayout() {
        return this.room_layout;
    }

    public void addEntity(Item item) {
        entities.add(item);
    }

    public List<Item> getItemList(){
        return entities;
    }

    public void resetItems(){
        this.entities = new ArrayList<>();
    }

    public void Draw(Graphics g){
        for(Enemy enemy : enemies)
            enemy.Draw(g);
    }

    public void Update() {

        for(int i = 0; i<enemies.size();++i)
            enemies.get(i).Update();

        for(int i = 0; i<entities.size();++i)
            entities.get(i).Update();
    }

    public List<Enemy> getEnemies(){
        return enemies;
    }

    public void removeEnemy(Enemy e){
        this.enemies.remove(e);
    }

    public boolean isContainingEnemies(){
        return !enemies.isEmpty();
    }

    public void removeItem(Item i){
        entities.remove(i);
    }
}
