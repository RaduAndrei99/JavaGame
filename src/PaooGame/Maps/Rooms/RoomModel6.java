package PaooGame.Maps.Rooms;

import PaooGame.ErrorHandler.ErrorScreenPrinter;
import PaooGame.Items.Chest;
import PaooGame.Items.Doors.NextLevelDoor;
import PaooGame.Items.ItemGenerator;
import PaooGame.Items.Traps.HoleTrap;
import PaooGame.Items.Traps.SpikeTrap;
import PaooGame.RefLinks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class RoomModel6 extends Room {

    public RoomModel6(RefLinks refs, boolean createDoors) {
        super(refs, createDoors);

        model = 6;

        //enemies.add(new LittleWizard(refs,200,200));
        //enemies.add(new OldWizard(refs,300,200));
        //enemies.add(new BigZombie(refs,300,400));


        room_layout = new int[NO_OF_TILES_HEIGHT][NO_OF_TILES_WIDTH];
        try {
            File myObj = new File("res/Rooms/room_6.txt");
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
                if (rand.nextInt(5) + 1 == 1) {
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
                    if(rand.nextInt(5) + 1 == 1)
                        entities.add(new HoleTrap(refs,read_x,read_y));
                    else
                        entities.add(new SpikeTrap(refs,read_x,read_y));
                }
                read_x = myReader.nextInt();
                read_y = myReader.nextInt();
            }
            Collections.shuffle(enemies);


        }catch (FileNotFoundException e){
            ErrorScreenPrinter.addErrorMessage("There is no file for room_model_6. - " + e.getMessage());
            e.printStackTrace();
        }catch (NoSuchElementException e){
            ErrorScreenPrinter.addErrorMessage("The file room_model_6 is corrupt! - " + e.getMessage());
        }

/*
        for(int i =0;i<this.potentialEntities.size();++i){
            System.out.println(potentialEntities.get(i).getKey() + " " + potentialEntities.get(i).getValue());
        }*/
    }

    @Override
    public void createNextLevelDoor(){
        nextLevelDoor  = new NextLevelDoor(refs,1440, 6*48 );
    }
}
