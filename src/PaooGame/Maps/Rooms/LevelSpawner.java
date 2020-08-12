package PaooGame.Maps.Rooms;

import java.util.List;
import java.util.Random;

public class LevelSpawner {

    private Random rand;

    protected List<Room> rooms;

    protected final int DEFAULT_ROOM_NUMBER = 10;

    protected int no_of_rooms;

    public LevelSpawner(){
        rand = new Random();

        no_of_rooms = DEFAULT_ROOM_NUMBER;

    }

    public void createLevel(){



        for(int i=0;i<no_of_rooms;++i){

        }
    }

}