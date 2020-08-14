package PaooGame.Maps;

import PaooGame.Input.GameMouseListener;
import PaooGame.Items.Chest;
import PaooGame.Items.Enemies.BigDemon;
import PaooGame.Items.Enemies.Enemy;
import PaooGame.Items.Item;
import PaooGame.Items.Weapons.BasicSword;
import PaooGame.Items.Weapons.GoldenSword;
import PaooGame.Items.Weapons.MightySword;
import PaooGame.Maps.Rooms.LevelSpawner;
import PaooGame.Maps.Rooms.Room;
import PaooGame.RefLinks;
import PaooGame.States.MenuState;
import PaooGame.States.State;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*! \class public class Map
    \brief Implementeaza notiunea de harta a jocului.
 */
public class Map {
    protected final int width;          /*!< Latimea hartii in numar de dale.*/
    protected final int height;         /*!< Inaltimea hartii in numar de dale.*/
    public static int[][] tiles;     /*!< Referinta catre o matrice cu codurile dalelor ce vor construi harta.*/
    protected boolean[][] solidTiles;

    Room[][] currentMapLayout;

    protected boolean firstTime = true;

    protected final List<Enemy> enemies;

    protected RefLinks refs;

    protected List<Chest> chests;

    protected List<Item> discarded_items;

    protected LevelSpawner spawner;

    protected LevelSpawner levelSpawner;

    protected Point currentPosition;

    private Boolean isLeftClicked = false;
    private Boolean isLeftReleased = false;
    private Boolean isRightClicked = false;
    private Boolean isRightReleased = false;

    /*! \fn public Map()
        \brief Constructorul de initializare al clasei.

        \param refLink O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public Map(RefLinks r) {
        width = 40;
        height = 20;
        enemies = new ArrayList<>();
        //enemies.add(new BigDemon(r,500,500));

        levelSpawner = new LevelSpawner();

        levelSpawner = new LevelSpawner();

        refs = r;

        currentPosition = new Point(LevelSpawner.DEFAULT_ROOMS_HEIGHT / 2, LevelSpawner.DEFAULT_ROOMS_WIDTH / 2);

        LoadWorld();

        chests = new LinkedList<>();

        discarded_items = new LinkedList<>();
/*
        Chest temp_chest = new Chest(r,1100,3*48,50,50);
        temp_chest.putItem(new BasicSword(r,temp_chest.GetX(),temp_chest.GetY() + 10));
        chests.add(temp_chest);

        temp_chest = new Chest(r,1200,3*48,50,50);
        temp_chest.putItem(new GoldenSword(r,temp_chest.GetX(),temp_chest.GetY() + 10));
        chests.add(temp_chest);

        temp_chest = new Chest(r,1300,3*48,50,50);
        temp_chest.putItem(new MightySword(r,temp_chest.GetX(),temp_chest.GetY() + 10));
        chests.add(temp_chest);
*/
    }


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
        if (i < 100)
            return Tile.tiles[tiles[x][y]];
        else
            return null;
    }

    /*! \fn private void LoadWorld()
        \brief Functie de incarcare a hartii jocului.
        Aici se poate genera sau incarca din fisier harta. Momentan este incarcata static.
     */
    private void LoadWorld() {
        tiles = new int[height][width];
        solidTiles = new boolean[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x] = currentLevelMap(y, x);
                try {
                    solidTiles[y][x] = Tile.tiles[tiles[y][x]].IsSolid();
                } catch (IndexOutOfBoundsException | NullPointerException e) {
                    solidTiles[y][x] = true;
                }
            }
        }
    }


    static public boolean isSolid(int tileID) {
        if (tileID == 99 || Tile.tiles[tileID] == null) return true;
        return Tile.tiles[tileID].IsSolid();
    }

    public List<Enemy> getEnemies() {
        return this.enemies;
    }

    public void makeSolid(int x, int y) {
        solidTiles[x][y] = true;
    }

    public void makeNonSolid(int x, int y) {
        solidTiles[x][y] = false;
    }

    public void resetSolidTiles() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                try {
                    solidTiles[y][x] = Tile.tiles[tiles[y][x]].IsSolid();
                } catch (IndexOutOfBoundsException | NullPointerException e) {
                    solidTiles[y][x] = true;
                }
            }
        }
    }

    public boolean isTileSolid(int x, int y) {
        return this.solidTiles[x][y];
    }

    public void setTileSolid(int x, int y) {
        this.solidTiles[x][y] = true;
    }

    public List<Chest> getChests() {
        return this.chests;
    }

    public void addDiscardedItem(Item i) {
        discarded_items.add(i);
    }

    public List<Item> getDiscardedItems() {
        return discarded_items;
    }

    public void removeItemFromDiscarded(Item i) {
        this.discarded_items.remove(i);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public void Update() {
        if (GameMouseListener.isLeftMousePressed) {
            isLeftClicked = true;
            isLeftReleased = false;
        }
        if (GameMouseListener.isLeftMouseReleased && isLeftClicked) {
            isLeftClicked = false;
            isLeftReleased = true;
        }
        if (!(State.GetState() instanceof MenuState) && isLeftReleased) {
            Chest temp_chest = new Chest(refs, GameMouseListener.getMouseCoordinates().x, GameMouseListener.getMouseCoordinates().y, 50, 50);
            temp_chest.putItem(new MightySword(refs, temp_chest.GetX(), temp_chest.GetY() + 10));
            getRoom().addChest(temp_chest);
            isLeftReleased = false;
        }
        if (GameMouseListener.isRightMousePressed) {
            isRightClicked = true;
            isRightReleased = false;
        }
        if (GameMouseListener.isRightMouseReleased && isRightClicked) {
            isRightClicked = false;
            isRightReleased = true;
        }
        if (!(State.GetState() instanceof MenuState) && isRightReleased) {
            
            isRightReleased = false;
        }

        resetSolidTiles();
        for (Enemy enemy : enemies)
            enemy.Update();
        for (Item item : getRoom().getItemList()) {
            item.Update();
        }
    }

    public void Draw(Graphics g) {
        Tile t;

        int xStart = (int) Math.max(0, refs.GetGame().getCamera().getXOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (refs.GetGame().getCamera().getXOffset() + refs.GetGame().GetWidth()) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, refs.GetGame().getCamera().getYOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (refs.GetGame().getCamera().getYOffset() + refs.GetGame().GetHeight()) / Tile.TILE_WIDTH + 1);

        ///Se parcurge matricea de dale (codurile aferente) si se deseneaza harta respectiva
        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                t = GetTile(y, x);
                if (t != null) {
                    t.Draw(g, (int) (x * Tile.TILE_HEIGHT - this.refs.GetGame().getCamera().getXOffset()), (int) (y * Tile.TILE_WIDTH - this.refs.GetGame().getCamera().getYOffset()));
                    if (refs.GetMap().isTileSolid(y, x)) {
                        g.setColor(Color.BLUE);

                    } else {
                        g.setColor(Color.GREEN);
                    }

                    // g.drawRect((int)(x * Tile.TILE_HEIGHT - this.refs.GetGame().getCamera().getXOffset()), (int)(y * Tile.TILE_WIDTH - this.refs.GetGame().getCamera().getYOffset()),48,48);
                }
                //g.setColor(Color.GREEN);
                //g.drawString(String.valueOf(y*this.width+x), (int)(x *Tile.TILE_HEIGHT - this.refs.GetGame().getCamera().getXOffset()), (int)((y+1) * Tile.TILE_WIDTH  - this.refs.GetGame().getCamera().getYOffset()));
            }
        }
        for (Item item : getRoom().getItemList()) {
            //Chest chest = (Chest)item;
            if (item != null) {
                item.Draw(g);
            }

        }
        for (Item item : discarded_items) {
            float angle = 90;

            if (item != null) {
                AffineTransform at = AffineTransform.getTranslateInstance(item.GetX() - refs.GetGame().getCamera().getXOffset(), item.GetY() - refs.GetGame().getCamera().getYOffset() + 50);
                at.rotate(Math.toRadians(angle), (float) this.width / 2, this.height / 2);
                at.scale(3, 3);
                Graphics2D g2d = (Graphics2D) g;

                g2d.drawImage(item.getImage(), at, null);
                g2d.drawRect((int) (item.getNormalBounds().x - refs.GetGame().getCamera().getXOffset()), (int) (item.getNormalBounds().y - refs.GetGame().getCamera().getYOffset()), item.getNormalBounds().width, item.getNormalBounds().height);
            }
        }

        for (Chest chest : chests)
            chest.Draw(g);

        for (Enemy enemy : enemies)
            enemy.Draw(g);
    }

    int currentLevelMap(int x, int y) {
        if (firstTime) {
            this.currentMapLayout = levelSpawner.getLevel();
            firstTime = false;
        }
        return currentMapLayout[currentPosition.x][currentPosition.y].getLayout()[x][y];
    }

    public Room getRoom() {
        return currentMapLayout[currentPosition.x][currentPosition.y];
    }

}