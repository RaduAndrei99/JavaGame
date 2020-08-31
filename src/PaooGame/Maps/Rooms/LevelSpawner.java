package PaooGame.Maps.Rooms;

import PaooGame.Items.Enemies.EnemyBossFactory;
import PaooGame.RefLinks;

import java.awt.*;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class LevelSpawner {
    private final Random rand;

    public static final int DEFAULT_ROOMS_WIDTH = 7;
    public static final int DEFAULT_ROOMS_HEIGHT= 7;

    public Room [][]rooms_position;

    public static final int MAX_ROOM_NUMBER = 20;
    public static final int MIN_ROOMS_NUMBER = 15;


    protected int no_of_rooms;
    protected RefLinks refs;

    protected static Stack<Room> roomsStack;

    public LevelSpawner(RefLinks refs){
        rand = new Random();

        this.refs = refs;

        no_of_rooms = 0;

        rooms_position = new Room[DEFAULT_ROOMS_WIDTH][DEFAULT_ROOMS_HEIGHT];
        roomsStack = new Stack<>();
    }

    public void createLevel(int i, int j, int came_from)  {
        try {

            Room room = RoomFactory.getRoom(rand.nextInt(RoomFactory.ROOM_TYPES) + 1, true, refs);

            if(room!=null) {
                switch (came_from) {
                    case 0:
                        room.openSouthDoorTo(i + 1, j);
                        break;

                    case 1:
                        room.openWestDoorTo(i, j - 1);
                        break;

                    case 2:
                        room.openNorthDoorTo(i - 1, j);
                        break;

                    case 3:
                        room.openEastDoorTo(i, j + 1);
                        break;

                    default:
                        break;
                }
            }

            rooms_position[i][j] = room;
            no_of_rooms++;
            roomsStack.add(room);

            for (int k = 0; k < 4; ++k) { // 0 - North, 1 - East, 3 - South, 4 - West
                if(no_of_rooms < MAX_ROOM_NUMBER) {
                    if (room != null && room.getDoors()[k] != null && Math.abs(came_from - k) != 2) {
                        int new_i = i + room.getDoors()[k].getI();
                        int new_j = j + room.getDoors()[k].getJ();
                        if( !(room.getDoors()[k].getI() >= -1 &&  room.getDoors()[k].getI() <=1 &&  room.getDoors()[k].getJ() >=-1 &&  room.getDoors()[k].getJ() <= 1)) {
                               continue;
                        }

                        if(isPositionValid(new_i,new_j)){
                            room.getDoors()[k].setI(new_i);
                            room.getDoors()[k].setJ(new_j);
                            if(Math.abs( i -new_i) > 1 || Math.abs(j-new_j) > 1)
                                System.out.println(i + " " + j + " to " + new_i + " " + new_j + " trough " + k + ", came from " + came_from);

                        }
                        else
                            continue;


                        room.getDoors()[k].openDoor();

                        if( rooms_position[new_i][new_j] != null) {
                            switch (k) {
                                    case 0:
                                        rooms_position[new_i][new_j].openSouthDoorTo(i, j);
                                        break;
                                    case 1:
                                        rooms_position[new_i][new_j].openWestDoorTo(i, j);
                                        break;

                                    case 2:
                                        rooms_position[new_i][new_j].openNorthDoorTo(i, j);
                                        break;

                                    case 3:
                                        rooms_position[new_i][new_j].openEastDoorTo(i, j);
                            }

                            continue;
                        }

                        createLevel(new_i, new_j, k);

                        }
                }
                else
                    return;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean isPositionValid(int i, int j){
        return i > 0 && i < DEFAULT_ROOMS_HEIGHT && j > 0 && j < DEFAULT_ROOMS_WIDTH;
    }

    public void rearrangeTheDoors(){
        for (int i=0;i<this.rooms_position.length;++i) {
            for (int j = 0; j < this.rooms_position[0].length; ++j) {
                if (rooms_position[i][j] != null) {
                    for (int k = 0; k < 4; ++k) {
                        if (rooms_position[i][j].getDoors()[k] != null && !rooms_position[i][j].getDoors()[k].isLinked())
                            rooms_position[i][j].closeDoor(k);
                    }
                }
            }
        }
    }

    public static void main(String []args) {
        LevelSpawner a = new LevelSpawner(null);
        a.createLevel(DEFAULT_ROOMS_WIDTH/2, DEFAULT_ROOMS_HEIGHT/2, -1);
        a.rearrangeTheDoors();

        for(int i=0;i<a.rooms_position.length;++i){
            for(int j=0;j<a.rooms_position[0].length;++j){

                if(a.rooms_position[i][j] != null)
                    System.out.print("# ");
                else
                    System.out.print("0 ");
            }
            System.out.println();
        }

        Scanner in = new Scanner(System.in);
        int i,j;

        do{
            System.out.println("i: ");
            i = in.nextInt();
            System.out.println("j: ");
            j = in.nextInt();

            System.out.println(a.rooms_position[i][j]);

            for (int l = 0; l < 4; ++l) {
                System.out.print(a.rooms_position[i][j].getDoors()[l] + " ");
                if(a.rooms_position[i][j].getDoors()[l] != null)
                    System.out.print("- " + a.rooms_position[i][j].getDoors()[l].isLinked() + " ");
            }
            System.out.println();

            for(int l=0;l<a.rooms_position.length;++l){
                for(int m=0;m<a.rooms_position[0].length;++m){

                    if(a.rooms_position[l][m] != null)
                        System.out.print("# ");
                    else
                        System.out.print("0 ");
                }
                System.out.println();
            }

        }while(true);
    }

    public Room[][] getLevel(){
        while(no_of_rooms < MIN_ROOMS_NUMBER) {
            no_of_rooms = 0;
            rooms_position = new Room[DEFAULT_ROOMS_WIDTH][DEFAULT_ROOMS_HEIGHT];
            roomsStack = new Stack<>();
            this.createLevel(DEFAULT_ROOMS_WIDTH / 2, DEFAULT_ROOMS_HEIGHT / 2, -1);
        }
        rearrangeTheDoors();

        Room tempRoom = roomsStack.peek();
        tempRoom.addBoss(EnemyBossFactory.getBoss(refs,Toolkit.getDefaultToolkit().getScreenSize().width/2-100,Toolkit.getDefaultToolkit().getScreenSize().height/2-100));
        roomsStack.firstElement().createNextLevelDoor();


        no_of_rooms = 0;
        return this.rooms_position;
    }

}