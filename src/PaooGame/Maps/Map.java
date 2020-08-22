package PaooGame.Maps;

import PaooGame.Input.GameMouseListener;
import PaooGame.Items.Chest;
import PaooGame.Items.Doors.*;
import PaooGame.Items.Hero;
import PaooGame.Items.Item;
import PaooGame.Items.Potions.SpeedPotion;
import PaooGame.Items.RectangleCollisionDetector;
import PaooGame.Items.Traps.HoleTrap;
import PaooGame.Items.Weapons.GoldenSword;
import PaooGame.Items.Weapons.MightySword;
import PaooGame.Maps.Rooms.LevelSpawner;
import PaooGame.Maps.Rooms.Room;
import PaooGame.RefLinks;
import PaooGame.States.MenuState;
import PaooGame.States.State;
import PaooGame.Tiles.Tile;

import java.awt.*;
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

    protected RefLinks refs;

    protected List<Chest> chests;

    protected List<Item> discarded_items;

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

        refs = r;
        levelSpawner = new LevelSpawner(refs);

        currentPosition = new Point(LevelSpawner.DEFAULT_ROOMS_HEIGHT / 2, LevelSpawner.DEFAULT_ROOMS_WIDTH / 2);

        LoadWorld();

        chests = new LinkedList<>();

        discarded_items = new LinkedList<>();
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
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                tiles[x][y] = currentLevelMap(x, y);
                try {
                    solidTiles[x][y] = Tile.tiles[tiles[x][y]].IsSolid();
                } catch (IndexOutOfBoundsException | NullPointerException e) {
                    solidTiles[x][y] = true;
                }
            }
        }
    }


    static public boolean isSolid(int tileID) {
        if (tileID == 99 || Tile.tiles[tileID] == null) return true;
        return Tile.tiles[tileID].IsSolid();
    }

    public boolean isTileSolid(int x, int y) {
        return this.solidTiles[x][y];
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

        int pos_x = (GameMouseListener.getMouseCoordinates().x/Tile.TILE_HEIGHT) *Tile.TILE_HEIGHT;
        int pos_y = (GameMouseListener.getMouseCoordinates().y/Tile.TILE_HEIGHT) *Tile.TILE_WIDTH;

        if (!(State.GetState() instanceof MenuState) && isLeftReleased) {
            Chest temp_chest = new Chest(refs, pos_x, pos_y, 50, 50);
            temp_chest.putItem(new GoldenSword(refs, temp_chest.GetX(), temp_chest.GetY() + 10));
            getRoom().addEntity(temp_chest);
            isLeftReleased = false;
            System.out.println(pos_x + " " + pos_y + " - chest");
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

            getRoom().addEntity(new HoleTrap(refs,pos_x, pos_y,Tile.TILE_WIDTH,Tile.TILE_HEIGHT));
            System.out.println(pos_x + " " + pos_y+ " - SpikeTrap");
        }


        if(refs.GetKeyManager().t) {
            System.out.println("\n\n");
            getRoom().resetItems();
        }

        for(Door door : currentMapLayout[currentPosition.x][currentPosition.y].getDoors()) {
            if (door != null) {
                if (RectangleCollisionDetector.checkIfSecondRectangleContainsFirst(Hero.GetInstance().getNormalBounds(), door.getNormalBounds())) {
                    if (door instanceof EastDoor) {
                        currentPosition.y = door.getJ();
                        Hero.GetInstance().SetX(2 * Tile.TILE_HEIGHT);
                        Hero.GetInstance().SetY(9 * Tile.TILE_WIDTH);
                    }

                    if (door instanceof WestDoor) {
                        currentPosition.y = door.getJ();
                        Hero.GetInstance().SetX(37 * Tile.TILE_HEIGHT);
                        Hero.GetInstance().SetY(9 * Tile.TILE_WIDTH);
                    }

                    if (door instanceof NorthDoor) {
                        currentPosition.x = door.getI();
                        Hero.GetInstance().SetX(19 * Tile.TILE_HEIGHT);
                        Hero.GetInstance().SetY(15 * Tile.TILE_WIDTH);
                    }

                    if (door instanceof SouthDoor) {
                        currentPosition.x = door.getI();
                        Hero.GetInstance().SetX(19 * Tile.TILE_HEIGHT);
                        Hero.GetInstance().SetY(2 * Tile.TILE_WIDTH);
                    }
                    System.out.println(currentPosition.x + " " + currentPosition.y);
                    LoadWorld();
                    Hero.GetInstance().updateObservers();
                    Hero.GetInstance().Update();


                    for (int i = 0; i < currentMapLayout.length; ++i) {
                        for (int j = 0; j < currentMapLayout[0].length; ++j) {
                            if (i == currentPosition.x && j == currentPosition.y)
                                System.out.print("* ");
                            else {
                                if (currentMapLayout[i][j] != null)
                                    System.out.print("# ");
                                else
                                    System.out.print("0 ");
                            }
                        }
                        System.out.println();
                    }
                    System.out.println();


                }
            }
        }

        getRoom().Update();

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

                     //g.drawRect((int)(x * Tile.TILE_HEIGHT - this.refs.GetGame().getCamera().getXOffset()), (int)(y * Tile.TILE_WIDTH - this.refs.GetGame().getCamera().getYOffset()),48,48);
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
        for (Chest chest : chests)
            chest.Draw(g);

        for (Item item : discarded_items) {
            item.Draw(g);
        }


        for(Door door : currentMapLayout[currentPosition.x][currentPosition.y].getDoors())
            if(door!=null)
                door.Draw(g);

        getRoom().Draw(g);
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