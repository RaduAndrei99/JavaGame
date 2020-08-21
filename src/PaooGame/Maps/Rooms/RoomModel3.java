package PaooGame.Maps.Rooms;

import PaooGame.RefLinks;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RoomModel3 extends Room {

    public RoomModel3(RefLinks refs){
        super(refs);
        potentialEntities = new ArrayList<>();

        room_layout = new int[NO_OF_TILES_HEIGHT][NO_OF_TILES_WIDTH];
        try {
            File myObj = new File("res/Rooms/room_3.txt");
            Scanner myReader = new Scanner(myObj);

            for(int i=0;i<NO_OF_TILES_HEIGHT;++i){
                for(int j=0;j<NO_OF_TILES_WIDTH;++j){
                    room_layout[i][j] = myReader.nextInt();
                }
            }


            int read_x = myReader.nextInt();
            int read_y = myReader.nextInt();

            while(read_x != -1 || read_y != -1 ){
                potentialEntities.add(new Pair("chest", new Pair(read_x,read_y)));

                read_x = myReader.nextInt();
                read_y = myReader.nextInt();
            }

            read_x = myReader.nextInt();
            read_y = myReader.nextInt();

            while(read_x != -1 || read_y != -1){
                potentialEntities.add(new Pair("spike", new Pair(read_x,read_y)));

                read_x = myReader.nextInt();
                read_y = myReader.nextInt();
            }

        }catch (FileNotFoundException e){
            System.out.println("There is no file for room_model_3 ");
            e.printStackTrace();
        }catch (NoSuchElementException e){
            System.out.println("The file room_model_3 is corrupt!");
        }
    }
}
