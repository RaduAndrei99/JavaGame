package PaooGame.Maps.Rooms;

import PaooGame.Items.Door;

import java.util.List;
import java.util.Random;

public class LevelSpawner {

    private Random rand;

    public static final int DEFAULT_ROOMS_WIDTH = 5;
    public static final int DEFAULT_ROOMS_HEIGHT= 5;

    public Room [][]rooms_position;

    protected final int MAX_ROOM_NUMBER = 20;

    protected int no_of_rooms;


    public LevelSpawner(){
        rand = new Random();

        no_of_rooms = 0;

        rooms_position = new Room[DEFAULT_ROOMS_WIDTH][DEFAULT_ROOMS_HEIGHT];
    }

    public void createLevel(int i, int j){

        try {
            if(no_of_rooms < MAX_ROOM_NUMBER) {
                Room room = RoomFactory.getRoom(rand.nextInt(3) + 1);

                rooms_position[i][j] = room;
                no_of_rooms++;

                for (int k = 0; k < 4; ++k) { // 0 - North, 1 - East, 3 - South, 4 - West
                    if (room.getDoors()[k] != null) {
                        int new_i = i + room.getDoors()[k].getI();
                        int new_j = j + room.getDoors()[k].getJ();

                        room.getDoors()[k].setI(new_i);
                        room.getDoors()[k].setJ(new_j);

                        createLevel(new_i,new_j);
                    }
                }

                for (int l = 0; l < 4; ++l)
                    System.out.print(room.getDoors()[l] + " ");
                System.out.println();
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }

    }

    public static void main(String []args){
        LevelSpawner a = new LevelSpawner();

        a.createLevel(a.DEFAULT_ROOMS_WIDTH/2, DEFAULT_ROOMS_HEIGHT/2);

        for(int i=0;i<a.rooms_position.length;++i){
            for(int j=0;j<a.rooms_position[0].length;++j){
                if(a.rooms_position[i][j] != null)
                    System.out.print("# ");
                else
                    System.out.print("0 ");

            }
            System.out.println();
        }
    }

}