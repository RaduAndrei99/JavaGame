package PaooGame.Maps;

import PaooGame.Items.Chest;
import PaooGame.Items.Enemies.Enemy;
import PaooGame.Items.Item;
import PaooGame.Maps.Rooms.LevelSpawner;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*! \class public class Map
    \brief Implementeaza notiunea de harta a jocului.
 */
public abstract class Map
{
    protected final int width;          /*!< Latimea hartii in numar de dale.*/
    protected final int height;         /*!< Inaltimea hartii in numar de dale.*/
    public static int [][] tiles;     /*!< Referinta catre o matrice cu codurile dalelor ce vor construi harta.*/
    protected boolean [][] solidTiles;

    protected final List<Enemy> enemies;

    protected RefLinks refs;

    protected List<Chest> chests;

    protected List<Item> discarded_items;

    protected LevelSpawner spawner;

    /*! \fn public Map()
        \brief Constructorul de initializare al clasei.

        \param refLink O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public Map(RefLinks r)
    {
        width = 40;
        height = 20;
        enemies = new ArrayList<>();

        refs = r;

        LoadWorld();

        chests = new LinkedList<>();

        discarded_items = new LinkedList<>();
    }

    /*! \fn public  void Update()
        \brief Actualizarea hartii in functie de evenimente (un copac a fost taiat)
     */
    abstract public void Update();


    /*! \fn public void Draw(Graphics g)
        \brief Functia de desenare a hartii.

        \param g Contextl grafi in care se realizeaza desenarea.
     */
    public abstract void Draw(Graphics g);

    /*! \fn public Tile GetTile(int x, int y)
        \brief Intoarce o referinta catre dala aferenta codului din matrice de dale.

        In situatia in care dala nu este gasita datorita unei erori ce tine de cod dala, coordonate gresite etc se
        intoarce o dala predefinita (ex. grassTile, mountainTile)
     */
    public Tile GetTile(int x, int y) {

        if (x < 0 || y < 0 || x >= height || y >= width) {
            return Tile.wallTile;
        }

        int i = tiles[x][y];
        if(i<100)
            return Tile.tiles[tiles[x][y]];
        else
            return null;

    }

    /*! \fn private void LoadWorld()
        \brief Functie de incarcare a hartii jocului.
        Aici se poate genera sau incarca din fisier harta. Momentan este incarcata static.
     */
    private void LoadWorld()
    {
        tiles = new int[height][width];
        solidTiles = new boolean[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x] = currentLevelMap(y, x);
                try {
                    solidTiles[y][x] = Tile.tiles[tiles[y][x]].IsSolid();
                }catch (IndexOutOfBoundsException | NullPointerException e){
                    solidTiles[y][x] = true;
                }
            }
        }
    }

    /*! \fn private int MiddleEastMap(int x ,int y)
        \brief O harta incarcata static.

        \param x linia pe care se afla codul dalei de interes.
        \param y coloana pe care se afla codul dalei de interes.
     */
    abstract int currentLevelMap(int x ,int y);


    static public boolean isSolid(int tileID){
        if(tileID == 99 || Tile.tiles[tileID] == null) return true;
        return Tile.tiles[tileID].IsSolid();
    }

    public List<Enemy> getEnemies()
    {
        return this.enemies;
    }

    public void makeSolid(int x, int y){
        solidTiles[x][y] = true;
    }

    public void makeNonSolid(int x, int y){
        solidTiles[x][y] = false;
    }

    public void resetSolidTiles(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                try {
                    solidTiles[y][x] = Tile.tiles[tiles[y][x]].IsSolid();
                }catch (IndexOutOfBoundsException | NullPointerException e){
                    solidTiles[y][x] = true;
                }
            }
        }
    }

    public boolean isTileSolid(int x, int y){
        return this.solidTiles[x][y];
    }

    public void setTileSolid(int x, int y){
        this.solidTiles[x][y] = true;
    }

    public List<Chest> getChests(){
        return this.chests;
    }

    public void addDiscardedItem(Item i){
        discarded_items.add(i);
    }

    public List<Item> getDiscardedItems(){
        return discarded_items;
    }

    public void removeItemFromDiscarded(Item i){
        this.discarded_items.remove(i);
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

}