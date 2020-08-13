package PaooGame.Maps.Rooms;

import java.util.Random;
import java.util.Scanner;

public class LevelSpawner {

    private Random rand;

    public static final int DEFAULT_ROOMS_WIDTH = 7;
    public static final int DEFAULT_ROOMS_HEIGHT= 7;

    public Room [][]rooms_position;

    protected final int MAX_ROOM_NUMBER = 20;

    protected int no_of_rooms;


    public LevelSpawner(){
        rand = new Random();

        no_of_rooms = 0;

        rooms_position = new Room[DEFAULT_ROOMS_WIDTH][DEFAULT_ROOMS_HEIGHT];
    }

    public void createLevel(int i, int j, int came_from)  {
        try {

            Room room = RoomFactory.getRoom(rand.nextInt(3) + 1);

            switch (came_from){
                case 0:
                    room.openSouthDoorTo(i+1,j);
                    room.getDoors()[2].openDoor();
                    break;
                case 1:
                    room.openWestDoorTo(i,j-1);
                    room.getDoors()[3].openDoor();
                    break;

                case 2:
                    room.openNorthDoorTo(i-1,j);
                    room.getDoors()[0].openDoor();
                    break;

                case 3:
                    room.openEastDoorTo(i,j+1);
                    room.getDoors()[1].openDoor();
            }



            rooms_position[i][j] = room;
            no_of_rooms++;

            for (int k = 0; k < 4; ++k) { // 0 - North, 1 - East, 3 - South, 4 - West
                if(no_of_rooms < MAX_ROOM_NUMBER) {
                    if (room.getDoors()[k] != null && Math.abs(came_from - k) != 2) {
                        int new_i = i + room.getDoors()[k].getI();
                        int new_j = j + room.getDoors()[k].getJ();

                        if(0<new_i && new_i < DEFAULT_ROOMS_HEIGHT)
                            room.getDoors()[k].setI(new_i);
                        else
                            continue;

                        if(0< new_j && new_j < DEFAULT_ROOMS_WIDTH)
                            room.getDoors()[k].setJ(new_j);
                        else
                            continue;

                        room.getDoors()[k].openDoor();

                        if( rooms_position[new_i][new_j] != null) {

                            switch (k) {
                                    case 0:
                                        rooms_position[new_i][new_j].openSouthDoorTo(i, j);
                                        rooms_position[new_i][new_j].getDoors()[2].openDoor();
                                        break;
                                    case 1:
                                        rooms_position[new_i][new_j].openWestDoorTo(i, j);
                                        rooms_position[new_i][new_j].getDoors()[3].openDoor();
                                        break;

                                    case 2:
                                        rooms_position[new_i][new_j].openNorthDoorTo(i, j);
                                        rooms_position[new_i][new_j].getDoors()[0].openDoor();
                                        break;

                                    case 3:
                                        rooms_position[new_i][new_j].openEastDoorTo(i, j);
                                        rooms_position[new_i][new_j].getDoors()[1].openDoor();
                            }

                            continue;
                        }

                            createLevel(new_i, new_j, k);
                        }
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean isPositionValid(int i, int j){
        return i > 0 && i < DEFAULT_ROOMS_HEIGHT && j > 0 && j < DEFAULT_ROOMS_WIDTH;
    }

    public void rearrangeTheDoors(){
        for(int i = 0; i<this.rooms_position.length;++i){
            for(int j = 0; j<this.rooms_position[0].length;++j){
                if(rooms_position[i][j] != null){
                    for(int k = 0; k < 4; ++k){
                        if(rooms_position[i][j].getDoors()[k] != null && !rooms_position[i][j].getDoors()[k].isLinked())
                            rooms_position[i][j].closeDoor(k);
/*
                        if(rooms_position[i][j].getDoors()[k] != null){
                            if(isPositionValid(i-1,j)){
                                if(rooms_position[i-1][j] != null && rooms_position[i][j].getDoors()[0] == null){
                                    rooms_position[i][j].openNorthDoorTo(i-1,j);
                                    rooms_position[i][j].getDoors()[0].openDoor();

                                    rooms_position[i-1][j].openSouthDoorTo(i,j);
                                    rooms_position[i-1][j].getDoors()[2].openDoor();
                                }
                            }

                            if(isPositionValid(i,j + 1)){
                                if(rooms_position[i][j+1] != null && rooms_position[i][j].getDoors()[1] == null){
                                    rooms_position[i][j].openEastDoorTo(i,j + 1);
                                    rooms_position[i][j].getDoors()[1].openDoor();

                                    rooms_position[i][j+1].openWestDoorTo(i,j);
                                    rooms_position[i][j+1].getDoors()[3].openDoor();
                                }
                            }


                            if(isPositionValid(i + 1,j)){
                                if(rooms_position[i+1][j] != null && rooms_position[i][j].getDoors()[2] == null){
                                    rooms_position[i][j].openSouthDoorTo(i + 1,j );
                                    rooms_position[i][j].getDoors()[2].openDoor();

                                    rooms_position[i+1][j].openNorthDoorTo(i,j);
                                    rooms_position[i+1][j].getDoors()[0].openDoor();
                                }
                            }

                            if(isPositionValid(i ,j-1)){
                                if(rooms_position[i][j-1] != null && rooms_position[i][j].getDoors()[3] == null){
                                    rooms_position[i][j].openWestDoorTo(i ,j-1 );
                                    rooms_position[i][j].getDoors()[3].openDoor();

                                    rooms_position[i][j-1].openEastDoorTo(i,j);
                                    rooms_position[i][j-1].getDoors()[1].openDoor();
                                }
                            }

                        }*/


                    }
                }
            }
        }
    }

    public static void main(String []args) throws  Exception{
        LevelSpawner a = new LevelSpawner();
        a.createLevel(a.DEFAULT_ROOMS_WIDTH/2, DEFAULT_ROOMS_HEIGHT/2, -1);
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
        this.createLevel(DEFAULT_ROOMS_WIDTH/2, DEFAULT_ROOMS_HEIGHT/2, -1);

        return this.rooms_position;
    }
}