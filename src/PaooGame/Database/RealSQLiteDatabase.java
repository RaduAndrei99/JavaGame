package PaooGame.Database;

import PaooGame.ErrorHandler.ErrorScreenPrinter;
import PaooGame.Game;
import PaooGame.GameDifficulty;
import PaooGame.Items.Chest;
import PaooGame.Items.Enemies.Enemy;
import PaooGame.Items.Hero;
import PaooGame.Items.Item;
import PaooGame.Items.Traps.Trap;
import PaooGame.Maps.Rooms.LevelSpawner;
import PaooGame.RefLinks;
import PaooGame.Sound.Music;
import PaooGame.Sound.Sound;
import PaooGame.States.PlayState;
import PaooGame.UI.Inventory;
import PaooGame.UI.Scoreboard;
import javafx.util.Pair;

import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

class RealSQLiteDatabase extends GameDatabase {


    public RealSQLiteDatabase(String databaseName, RefLinks r) {
        super(r);
        fileName = databaseName;
        refs = r;
        url = "jdbc:sqlite:src/PaooGame/Database/";
        databaseFolder = "src/PaooGame/Database/";

        File tempFile = new File(databaseFolder + fileName);
        if (!tempFile.exists()) {
            createDatabase();
        }
    }

    @Override
    public void saveGame(Game game) {
        Connection connection;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url + fileName);

            int [][]tempRooms = new int[LevelSpawner.DEFAULT_ROOMS_HEIGHT][LevelSpawner.DEFAULT_ROOMS_WIDTH];//ID -1
            boolean [][][] tempDoors = new boolean[LevelSpawner.DEFAULT_ROOMS_HEIGHT][LevelSpawner.DEFAULT_ROOMS_WIDTH][4];//ID - 2
            LinkedList<Pair<Integer, Integer>> [][]tempOpenedChests = new LinkedList[LevelSpawner.DEFAULT_ROOMS_HEIGHT][LevelSpawner.DEFAULT_ROOMS_WIDTH];//ID - 3
            LinkedList<Pair<Pair<Integer, Integer>, Integer >> [][]tempUnopenedChests = new LinkedList[LevelSpawner.DEFAULT_ROOMS_HEIGHT][LevelSpawner.DEFAULT_ROOMS_WIDTH];//ID - 4
            LinkedList<Pair<Pair<Integer, Integer>, Integer >> [][]tempTraps = new LinkedList[LevelSpawner.DEFAULT_ROOMS_HEIGHT][LevelSpawner.DEFAULT_ROOMS_WIDTH];//ID -5
            int []tempGameStats = new int[12];//ID -6
            LinkedList<Pair<Pair<Integer, Integer>, Integer >> [][]tempEnemies = new LinkedList[LevelSpawner.DEFAULT_ROOMS_HEIGHT][LevelSpawner.DEFAULT_ROOMS_WIDTH];//ID - 7
            boolean [][]tempMiniMap = new boolean[LevelSpawner.DEFAULT_ROOMS_HEIGHT][LevelSpawner.DEFAULT_ROOMS_WIDTH];//ID - 8

            //construiesc structurile necesare pentru salvarea hartii si ale obiectelor de pe harta
            for(int i=0;i<LevelSpawner.DEFAULT_ROOMS_HEIGHT;++i){
                for(int j=0;j<LevelSpawner.DEFAULT_ROOMS_WIDTH;++j){
                    for(int k=0;k<4;++k){
                        if(refs.GetMap().getCurrentMapLayout()[i][j] != null && refs.GetMap().getCurrentMapLayout()[i][j].getDoors()[k] != null){
                            tempDoors[i][j][k] = true;
                        }
                        else {
                            tempDoors[i][j][k] = false;
                        }
                    }

                    if(refs.GetMap().getCurrentMapLayout()[i][j] != null) {
                        tempRooms[i][j] = refs.GetMap().getCurrentMapLayout()[i][j].getRoomModel();

                        tempOpenedChests[i][j] = new LinkedList<>();
                        tempUnopenedChests[i][j] = new LinkedList<>();
                        tempTraps[i][j] = new LinkedList<>();

                        List<Item> tempList = refs.GetMap().getCurrentMapLayout()[i][j].getItemList();
                        for(int k=0;k<tempList.size();++k){
                            if(tempList.get(k) instanceof Chest){
                                Chest tempChest =(Chest) tempList.get(k);
                                if(tempChest.isChestOpened()) {
                                    tempOpenedChests[i][j].add(new Pair<>(tempChest.getNormalBounds().x, tempChest.getNormalBounds().y));
                                }else{
                                    tempUnopenedChests[i][j].add(new Pair<>(new Pair<>(tempChest.getNormalBounds().x, tempChest.getNormalBounds().y), tempChest.getStored_item().item_ID));
                                }
                            }else{
                                if(tempList.get(k) instanceof Trap) {
                                    Trap tempTrap = (Trap) tempList.get(k);
                                    tempTraps[i][j].add(new Pair<>(new Pair<>(tempTrap.getNormalBounds().x, tempTrap.getNormalBounds().y), tempTrap.getTrap_id()));


                                }
                            }
                        }

                        if(refs.GetMap().getCurrentMapLayout()[i][j].getEnemies()!= null){
                            tempEnemies[i][j] = new LinkedList<>();

                            for(Enemy enemy :refs.GetMap().getCurrentMapLayout()[i][j].getEnemies() )
                                tempEnemies[i][j].add(new Pair<>(new Pair<>(enemy.getNormalBounds().x, enemy.getNormalBounds().y), enemy.getEnemy_id()));
                        }
                    }
                    else {
                        tempRooms[i][j] = 0;
                    }

                }
            }

            //salvez intr-un vector stat-urile curente ale jocului(camera curenta, viata, scorul, inventarul)
            tempGameStats[0] = Hero.GetInstance().getCurrentLife();// 0 - viata
            tempGameStats[1] = Scoreboard.getCurrentScore(); // 1 - scor
            tempGameStats[2] = refs.GetMap().getCurrentPosition().x; // 2 - primul index al matricei de camere
            tempGameStats[3] = refs.GetMap().getCurrentPosition().y; // 3 - al doilea index al matricei de camere

            //salvez id-urile item-elor din inventar. Daca nu exista, salvez ca -1
            for(int i = 4;i<9;++i){
                Item tempItem = Hero.GetInstance().getInventory().getInventorySlots()[i - 4].getItem();
                if(tempItem != null){
                    tempGameStats[i] = tempItem.getItem_ID();
                }else{
                    tempGameStats[i] = -1;
                }
            }
            tempGameStats[9] = (int)Hero.GetInstance().GetX(); // 9 - pozitia pe x a eroului
            tempGameStats[10] = (int)Hero.GetInstance().GetY(); // 10 - pozitia pe y a eroului
            tempGameStats[11] = PlayState.current_level; // 10 - pozitia pe y a eroului


            tempMiniMap = refs.GetMap().getMiniMap().getMap_rooms();

            //salvez matricea de camere
            PreparedStatement statement = connection.prepareStatement("UPDATE SaveGame SET id = 1, valid = 'true', object_name = ?, serialized_object = ? WHERE id = 1");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(tempRooms);
            oos.flush();

            statement.setString(1, tempRooms.getClass().getName());
            statement.setObject(2, bos.toByteArray());
            statement.executeUpdate();

            //salvez lista de usi
            statement = connection.prepareStatement("UPDATE SaveGame SET id = 2, valid = 'true', object_name = ?, serialized_object = ? WHERE id = 2 ");
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(tempDoors);
            oos.flush();

            statement.setString(1, tempDoors.getClass().getName());
            statement.setObject(2, bos.toByteArray());
            statement.executeUpdate();

            //salvez chesturile deschise
            statement = connection.prepareStatement("UPDATE SaveGame SET id = 3, valid = 'true', object_name = ?, serialized_object = ? WHERE id = 3 ");
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(tempOpenedChests);
            oos.flush();

            statement.setString(1, tempOpenedChests.getClass().getName());
            statement.setObject(2, bos.toByteArray());
            statement.executeUpdate();


            //salvez chesturile nedeschise
            statement = connection.prepareStatement("UPDATE SaveGame SET id = 4, valid = 'true', object_name = ?, serialized_object = ? WHERE id = 4 ");
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(tempUnopenedChests);
            oos.flush();

            statement.setString(1, tempUnopenedChests.getClass().getName());
            statement.setObject(2, bos.toByteArray());
            statement.executeUpdate();


            //salvez capcanele
            statement = connection.prepareStatement("UPDATE SaveGame SET id = 5, valid = 'true', object_name = ?, serialized_object = ? WHERE id = 5 ");
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(tempTraps);
            oos.flush();

            statement.setString(1, tempTraps.getClass().getName());
            statement.setObject(2, bos.toByteArray());
            statement.executeUpdate();



            //salvez vectorul de stats-uri
            statement = connection.prepareStatement("UPDATE SaveGame SET id = 6, valid = 'true', object_name = ?, serialized_object = ? WHERE id = 6 ");
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(tempGameStats);
            oos.flush();

            statement.setString(1, tempGameStats.getClass().getName());
            statement.setObject(2, bos.toByteArray());
            statement.executeUpdate();


            //salvez inamicii
            statement = connection.prepareStatement("UPDATE SaveGame SET id = 7, valid = 'true', object_name = ?, serialized_object = ? WHERE id = 7 ");
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(tempEnemies);
            oos.flush();

            statement.setString(1, tempEnemies.getClass().getName());
            statement.setObject(2, bos.toByteArray());
            statement.executeUpdate();

            //salvez matricea de camere pentru minimap
            statement = connection.prepareStatement("UPDATE SaveGame SET id = 8, valid = 'true', object_name = ?, serialized_object = ? WHERE id = 8 ");
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(tempMiniMap);
            oos.flush();

            statement.setString(1, tempMiniMap.getClass().getName());
            statement.setObject(2, bos.toByteArray());
            statement.executeUpdate();



            statement.close();
            connection.close();
            System.out.println("Game saved successfully");
            canYouLoadGame = true;
        } catch (Exception e){
            ErrorScreenPrinter.addErrorMessage("Can't save the game! - " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void loadGame() {
        Connection connection;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url + fileName);
            PreparedStatement pstmt = connection .prepareStatement("SELECT serialized_object FROM SaveGame WHERE id = 1");
            ResultSet rs = pstmt.executeQuery();
            rs.next();

            //incarc matricea de camere din baza de date
            byte[] buf = rs.getBytes(1);
            ObjectInputStream objectIn = null;
            if (buf != null)
                objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));

            Object deSerializedObject = null;
            if(objectIn != null) {
                deSerializedObject = objectIn.readObject();
            }

            int [][]tempMap = (int[][])deSerializedObject;



            //incarc array-ul de usi din baza de date
            pstmt = connection.prepareStatement("SELECT serialized_object FROM SaveGame where id = 2");
            rs = pstmt.executeQuery();
            rs.next();

            buf = rs.getBytes(1);
            objectIn = null;
            if (buf != null)
                objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));

            deSerializedObject = null;
            if(objectIn != null) {
                deSerializedObject = objectIn.readObject();
            }

            boolean [][][]tempDoors = (boolean[][][]) deSerializedObject;

            refs.GetMap().loadMapFromDatabase(tempMap, tempDoors);


            //incarc chesturile deschise
            pstmt = connection.prepareStatement("SELECT serialized_object FROM SaveGame where id = 3");
            rs = pstmt.executeQuery();
            rs.next();

            buf = rs.getBytes(1);
            objectIn = null;
            if (buf != null)
                objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));

            deSerializedObject = null;
            if(objectIn != null) {
                deSerializedObject = objectIn.readObject();
            }

            LinkedList<Pair<Integer, Integer>> [][]tempOpenedChests= (LinkedList<Pair<Integer, Integer>>[][]) deSerializedObject;


            //incarc chesturile nedeschise
            pstmt = connection.prepareStatement("SELECT serialized_object FROM SaveGame where id = 4");
            rs = pstmt.executeQuery();
            rs.next();

            buf = rs.getBytes(1);
            objectIn = null;
            if (buf != null)
                objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));

            deSerializedObject = null;
            if(objectIn != null) {
                deSerializedObject = objectIn.readObject();
            }

            LinkedList<Pair<Pair<Integer, Integer>, Integer >> [][]tempUnopenedChests = (LinkedList<Pair<Pair<Integer, Integer>, Integer >> [][]) deSerializedObject;

            refs.GetMap().loadItemsFromDatabase(tempUnopenedChests, tempOpenedChests);


            //incarc capcanele
            pstmt = connection.prepareStatement("SELECT serialized_object FROM SaveGame where id = 5");
            rs = pstmt.executeQuery();
            rs.next();

            buf = rs.getBytes(1);
            objectIn = null;
            if (buf != null)
                objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));

            deSerializedObject = null;
            if(objectIn != null) {
                deSerializedObject = objectIn.readObject();
            }

            LinkedList<Pair<Pair<Integer, Integer>, Integer >> [][]tempTraps= (LinkedList<Pair<Pair<Integer, Integer>, Integer >> [][]) deSerializedObject;

            refs.GetMap().loadTrapsFromDatabase(tempTraps);



            //incarc game stats
            pstmt = connection.prepareStatement("SELECT serialized_object FROM SaveGame where id = 6");
            rs = pstmt.executeQuery();
            rs.next();

            buf = rs.getBytes(1);
            objectIn = null;
            if (buf != null)
                objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));

            deSerializedObject = null;
            if(objectIn != null) {
                deSerializedObject = objectIn.readObject();
            }

            int []tempGameStats = (int[]) deSerializedObject;

            Hero.GetInstance().setLife(tempGameStats[0]);
            Scoreboard.setScore(tempGameStats[1]);
            refs.GetMap().setCurrentPosition(new Point(tempGameStats[2], tempGameStats[3]));

            int []tempInventoryItemsIDs = new int [Inventory.NO_OF_SLOTS];

            if (Inventory.NO_OF_SLOTS >= 0)
                System.arraycopy(tempGameStats, 4, tempInventoryItemsIDs, 0, Inventory.NO_OF_SLOTS);

            Hero.GetInstance().getInventory().loadInventory(tempInventoryItemsIDs);
            Hero.GetInstance().SetX(tempGameStats[9]);
            Hero.GetInstance().SetY(tempGameStats[10]);
            PlayState.current_level = tempGameStats[11];


            //incarc inamicii
            pstmt = connection.prepareStatement("SELECT serialized_object FROM SaveGame where id = 7");
            rs = pstmt.executeQuery();
            rs.next();

            buf = rs.getBytes(1);
            objectIn = null;
            if (buf != null)
                objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));

            deSerializedObject = null;
            if(objectIn != null) {
                deSerializedObject = objectIn.readObject();
            }

            LinkedList<Pair<Pair<Integer, Integer>, Integer >> [][]tempEnemies= (LinkedList<Pair<Pair<Integer, Integer>, Integer >> [][]) deSerializedObject;

            refs.GetMap().loadEnemiesFromDatabase(tempEnemies);



            //incarc minimpa-ul
            pstmt = connection.prepareStatement("SELECT serialized_object FROM SaveGame where id = 8");
            rs = pstmt.executeQuery();
            rs.next();

            buf = rs.getBytes(1);
            objectIn = null;
            if (buf != null)
                objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));

            deSerializedObject = null;
            if(objectIn != null) {
                deSerializedObject = objectIn.readObject();
            }

            boolean [][]tempMiniMap =  (boolean[][]) deSerializedObject;

            refs.GetMap().loadMiniMapFromDatabase(tempMiniMap);


            rs.close();
            pstmt.close();
            connection.close();
            System.out.println("Game loaded successfully");
        } catch ( Exception e ) {
            ErrorScreenPrinter.addErrorMessage("Can't load the game! - " + e.getMessage());
        }
    }

    @Override
    public void saveSettings() {
        Connection connection;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url + fileName);

            float [] tempSettings = new float[3]; //vectorul in care retin setarile

            tempSettings[0] = Music.getCurrentVolume();//volumul muzicii
            tempSettings[1] = Sound.getCurrentVolume();//volumul sunetelor
            tempSettings[2] = GameDifficulty.current_no_of_enemies;//dificultatea curenta

            //salvez setarile
            PreparedStatement statement = connection.prepareStatement("UPDATE Settings SET id = 1, valid = 'true', object_name = ?, serialized_object = ? WHERE id = 1");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(tempSettings);
            oos.flush();

            statement.setString(1, tempSettings.getClass().getName());
            statement.setObject(2, bos.toByteArray());
            statement.executeUpdate();

            statement.close();
            connection.close();
            System.out.println("Settings saved successfully");
        } catch ( Exception e ) {
            ErrorScreenPrinter.addErrorMessage("Can't save settings! - " + e.getMessage() );
            e.printStackTrace();
        }
    }

    @Override
    public void loadSettings() {
        Connection connection;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url + fileName);
            PreparedStatement pstmt = connection .prepareStatement("SELECT serialized_object FROM Settings WHERE id = 1");
            ResultSet rs = pstmt.executeQuery();
            rs.next();

            //incarc matricea de camere din baza de date
            byte[] buf = rs.getBytes(1);
            ObjectInputStream objectIn = null;
            if (buf != null)
                objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));

            Object deSerializedObject = null;
            if(objectIn != null) {
                deSerializedObject = objectIn.readObject();
            }

            float []tempSettings = (float[])deSerializedObject;

            Music.setCurrentVolume(tempSettings[0]);
            Sound.setCurrentVolume(tempSettings[1]);
            GameDifficulty.setCurrentDifficulty((int)tempSettings[2]);

            rs.close();
            pstmt.close();
            connection.close();
            System.out.println("Settings loaded successfully!");
        } catch ( Exception e ) {
            ErrorScreenPrinter.addErrorMessage("Can't load settings! - " + e.getMessage() );
        }
    }

    @Override
    public void loadDatabase() {

    }


    /* Creez baza de date si initial introduc linii nevalide ca ulterior, prin operatie de saveGame, doar sa fac update
        Prima linie: salvez sub forma de BLOB matricea de camere
        A doua linie: salvez sub forma de BLOB pozitia usilor
        A treia linie: salvez sub forma de BLOB lista de chest-uri de pe harta
     */
    public void createDatabase(){
        Connection c ;
        Statement stmt ;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url + fileName);
            stmt = c.createStatement();
            c.setAutoCommit(false);

            String sql = "CREATE TABLE IF NOT EXISTS SaveGame(id INT PRIMARY KEY NOT NULL, valid, object_name, serialized_object);";

            stmt.executeUpdate(sql);
            c.commit();

            sql = "INSERT INTO SaveGame VALUES(1, 'false', 'Here belongs the Rooms', NULL);";

            stmt.executeUpdate(sql);
            c.commit();


            sql = "INSERT INTO SaveGame VALUES(2, 'false', 'Here belongs the Doors', NULL);";

            stmt.executeUpdate(sql);
            c.commit();

            sql = "INSERT INTO SaveGame VALUES(3, 'false', 'Here belongs the opened Chests', NULL);";

            stmt.executeUpdate(sql);
            c.commit();


            sql = "INSERT INTO SaveGame VALUES(4, 'false', 'Here belongs the unopened Chests', NULL);";

            stmt.executeUpdate(sql);
            c.commit();

            sql = "INSERT INTO SaveGame VALUES(5, 'false', 'Here belongs the Traps', NULL);";

            stmt.executeUpdate(sql);
            c.commit();

            sql = "INSERT INTO SaveGame VALUES(6, 'false', 'Here belongs the Hero stats', NULL);";

            stmt.executeUpdate(sql);
            c.commit();

            sql = "INSERT INTO SaveGame VALUES(7, 'false', 'Here belongs the Enemies', NULL);";

            stmt.executeUpdate(sql);
            c.commit();

            sql = "INSERT INTO SaveGame VALUES(8, 'false', 'Here belongs the Minimap', NULL);";

            stmt.executeUpdate(sql);
            c.commit();


            sql = "CREATE TABLE IF NOT EXISTS Settings(id INT PRIMARY KEY NOT NULL, valid, object_name, serialized_object);";

            stmt.executeUpdate(sql);
            c.commit();

            sql = "INSERT INTO Settings VALUES(1, 'false', 'Here belongs the Settings', NULL);";

            stmt.executeUpdate(sql);
            c.commit();


            stmt.close();
            c.close();
            System.out.println("Created database successfully");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            canYouSaveGame = false;
        }
    }



}
