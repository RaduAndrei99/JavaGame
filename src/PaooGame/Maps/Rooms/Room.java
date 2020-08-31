package PaooGame.Maps.Rooms;

import PaooGame.GameDifficulty;
import PaooGame.Items.*;
import PaooGame.Items.Doors.*;
import PaooGame.Items.Enemies.*;
import PaooGame.RefLinks;

import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class Room {
    protected final int NO_OF_TILES_WIDTH = 40;
    protected final int NO_OF_TILES_HEIGHT = 20;

    protected int model;

    protected Door[] doors;
    protected List<Item> entities;

    protected int[][] room_layout;


    protected LinkedList<Enemy> enemies;

    protected int dead_enemies = 0;

    RefLinks refs;

    NextLevelDoor nextLevelDoor;

    public Room(RefLinks refs, boolean createDoors) {
        Random rand = new Random();
        doors = new Door[4];
        boolean door_created = false;
        entities = new LinkedList<>();

        this.refs = refs;

        enemies = new LinkedList<>();

        if (createDoors) {
            while (!door_created) {
                for (int i = 0; i < 4; ++i) {
                    if (rand.nextInt(2) == 1) {//50% sansa sa apara o usa in oricare directie
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
        if(k >=0 && k <4){
            doors[k] = null;
        }
    }

    public int[][] getLayout() {
        return this.room_layout;
    }

    public void addEntity(Item item) {
        entities.add(item);
    }

    public List<Item> getItemList() {
        return entities;
    }

    public void resetItems() {
        this.entities = new ArrayList<>();
    }

    public void Draw(Graphics g) {
        if (!entities.isEmpty())
            for (int i = 0; i < entities.size(); ++i)
                if(entities.get(i) != null)
                    entities.get(i).Draw(g);

        if (!enemies.isEmpty())
            if (enemies.size() == 1) {
                enemies.get(0).Draw(g);
            } else {
                for (int i = 0; i < GameDifficulty.current_no_of_enemies - dead_enemies; ++i)
                    enemies.get(i).Draw(g);
            }

        for (Door door : doors) {
            if (door != null)
                door.Draw(g);
        }

        if (nextLevelDoor != null)
            nextLevelDoor.Draw(g);

    }

    public void Update() {
        if (enemies.size() == 1) {
            enemies.get(0).Update();
        } else {
            if (!enemies.isEmpty())
                for (int i = 0; i < GameDifficulty.current_no_of_enemies - dead_enemies; ++i)
                    enemies.get(i).Update();
        }
        if (!entities.isEmpty()) {
            for (int i = 0; i < entities.size(); ++i)
                entities.get(i).Update();
        }


    }

    public List<Enemy> getEnemies() {
        List<Enemy> tempList = new LinkedList<>();
        if (!enemies.isEmpty()) {
            if (enemies.size() == 1) return enemies;
            // System.out.println(GameDifficulty.current_no_of_enemies - dead_enemies + " " + enemies.size());
            for (int i = 0; i < GameDifficulty.current_no_of_enemies - dead_enemies; ++i)
                tempList.add(enemies.get(i));
        }
        return tempList;
    }

    public void removeEnemy(Enemy e) {
        this.enemies.remove(e);
        dead_enemies++;
    }

    public boolean isContainingEnemies() {
        return !enemies.isEmpty();
    }

    public void removeItem(Item i) {
        entities.remove(i);
    }

    public int getRoomModel() {
        return model;
    }

    public void addBoss(Enemy boss) {
        enemies.add(0, boss);
        dead_enemies--;
    }

    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    public void setDead_enemies(int value) {
        dead_enemies = value;
    }

    public void resetEnemies() {
        this.enemies = new LinkedList<>();
    }

    public Door getNextLevelDoor() {
        return this.nextLevelDoor;
    }

    public abstract void createNextLevelDoor();
}