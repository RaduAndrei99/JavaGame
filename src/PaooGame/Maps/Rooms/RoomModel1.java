package PaooGame.Maps.Rooms;

import PaooGame.ErrorHandler.ErrorScreenPrinter;
import PaooGame.Items.Chest;
import PaooGame.Items.Doors.NextLevelDoor;
import PaooGame.Items.Enemies.*;
import PaooGame.Items.ItemGenerator;
import PaooGame.Items.Traps.HoleTrap;
import PaooGame.Items.Traps.SpikeTrap;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class RoomModel1 extends Room {


    public RoomModel1(RefLinks refs, boolean createDoors) {
        super(refs, createDoors);

        model = 1;

        room_layout = new int[NO_OF_TILES_HEIGHT][NO_OF_TILES_WIDTH];
        try {
            File myObj = new File("res/Rooms/room_1.txt");
            Scanner myReader = new Scanner(myObj);

            for(int i=0;i<NO_OF_TILES_HEIGHT;++i){
                for(int j=0;j<NO_OF_TILES_WIDTH;++j){
                    room_layout[i][j] = myReader.nextInt();
                }
            }

            Random rand = new Random();

            int read_x = myReader.nextInt();
            int read_y = myReader.nextInt();

            while(read_x != -1 || read_y != -1) {
                if (rand.nextInt(10) + 1 == 1) {
                    Chest tempChest = new Chest(refs, read_x, read_y);
                    tempChest.putItem(ItemGenerator.createItem(refs, (int) tempChest.GetX(), (int) tempChest.GetY()));
                    entities.add(tempChest);
                }
                read_x = myReader.nextInt();
                read_y = myReader.nextInt();
            }

            read_x = myReader.nextInt();
            read_y = myReader.nextInt();

            while(read_x != -1 || read_y != -1){
                if(rand.nextInt(3) + 1 == 1){
                    if(rand.nextInt(10) + 1 == 1)
                        entities.add(new HoleTrap(refs,read_x,read_y));
                    else
                        entities.add(new SpikeTrap(refs,read_x,read_y));
                }
                read_x = myReader.nextInt();
                read_y = myReader.nextInt();
            }


            read_x = myReader.nextInt();
            read_y = myReader.nextInt();

            while(read_x != -1 || read_y != -1){
                enemies.add(EnemiesFactory.getEnemy(refs,1,read_x,read_y));
                read_x = myReader.nextInt();
                read_y = myReader.nextInt();
            }
            Collections.shuffle(enemies);


        }catch (FileNotFoundException e){
            ErrorScreenPrinter.addErrorMessage("There is no file for room_model_1. - " + e.getMessage());
            e.printStackTrace();
        }catch (NoSuchElementException e){
            ErrorScreenPrinter.addErrorMessage("The file room_model_1 is corrupt! - " + e.getMessage());
        }


    }

    @Override
    public void createNextLevelDoor(){
        nextLevelDoor  = new NextLevelDoor(refs,1440, 0 );
    }
}
