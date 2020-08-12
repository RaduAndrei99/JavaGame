package PaooGame.Tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

/*! \class public class Tile
    \brief Retine toate dalele intr-un vector si ofera posibilitatea regasirii dupa un id.
 */
public class Tile
{
    private static final int NO_TILES   = 100;
    public static Tile[] tiles          = new Tile[NO_TILES];

        /// De remarcat ca urmatoarele dale sunt statice si publice. Acest lucru imi permite sa le am incarcate
        /// o singura data in memorie
    public static Tile floorTile     = new FloorTile(2);

    public static Tile wallTile        = new WallTile(10);
    public static Tile wallTopTile        = new WallTopTile(11);
    public static Tile wallTopReversedTile        = new WallTopReversedTile(12);
    public static Tile wallRightSideTile   = new WallRightSideTile(13);
    public static Tile wallLeftSideTile   = new WallLeftSideTile(14);
    public static Tile wallTopRightCrossTile   = new WallTopRightCrossTile(15);
    public static Tile wallTopRightCrossReversedTile   = new WallTopRightCrossReversedTile(16);
    public static Tile wallBottomCrossReversed   = new WallBottomCrossReversed(17);
    public static Tile wallBottomCross   = new WallBottomCross(18);
    public static Tile wallTopRightTopCornerTile   = new WallRightTopCorner(19);
    public static Tile wallTopRightTopReverseCornerTile   = new WallRightTopReversedCorner(20);
    public static Tile wallLeftTopCornerTile   = new WallLeftTopCornerTile(21);
    public static Tile wallLeftTopReversedCornerTile   = new WallLeftTopReversedCorner(22);
    public static Tile floorWallTopTile   = new FloorWallTopTile(23);
    public static Tile wallWithBrickTop = new WallWithBrickTop(24);
    public static Tile fountainTop = new FountainTop(25);
    public static Tile lavaFountainMid1 = new LavaFountainMid1(26);
    public static Tile lavaFountainFloor1 = new LavaFountainFloor1(27);
    public static Tile wallSideRight = new WallSideRight(28);
    public static Tile wallSideLeft = new WallSideLeft(29);
    public static Tile wallWithCross = new WallWithCross(30);
    public static Tile wallWithCrossReversed = new WallWithCrossReversed(31);
    public static Tile pillarTop = new PillarTop(32);
    public static Tile pillarMid = new PillarMid(33);
    public static Tile pillarFloor = new PillarFloor(34);
    public static Tile slimeMid = new SlimeMid(35);
    public static Tile slimeFloor = new SlimeFloor(36);



    public static Tile floorLittleCrackTile   = new FloorLittleCrackTile(3);



    public static final int TILE_WIDTH  = 48;                       /*!< Latimea unei dale.*/
    public static final int TILE_HEIGHT = 48;                       /*!< Inaltimea unei dale.*/

    protected BufferedImage img;                                    /*!< Imaginea aferenta tipului de dala.*/
    protected final int id;                                         /*!< Id-ul unic aferent tipului de dala.*/

    boolean isSolid = false;

    /*! \fn public Tile(BufferedImage texture, int id)
        \brief Constructorul aferent clasei.

        \param image Imaginea corespunzatoare dalei.
        \param id Id-ul dalei.
     */
    public Tile(BufferedImage image, int idd)
    {
        img = image;
        id = idd;

        tiles[id] = this;
    }

    /*! \fn public void Update()
        \brief Actualizeaza proprietatile dalei.
     */
    public void Update()
    {

    }

    /*! \fn public void Draw(Graphics g, int x, int y)
        \brief Deseneaza in fereastra dala.

        \param g Contextul grafic in care sa se realizeze desenarea
        \param x Coordonata x in cadrul ferestrei unde sa fie desenata dala
        \param y Coordonata y in cadrul ferestrei unde sa fie desenata dala
     */
    public void Draw(Graphics g, int x, int y)
    {
            /// Desenare dala
        g.drawImage(img, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    /*! \fn public boolean IsSolid()
        \brief Returneaza proprietatea de dala solida (supusa coliziunilor) sau nu.
     */
    public boolean IsSolid()
    {
        return this.isSolid;
    }

    /*! \fn public int GetId()
        \brief Returneaza id-ul dalei.
     */
    public int GetId()
    {
        return id;
    }
}
