package PaooGame.Tiles;

import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;

/*! \class public class MountainTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip munte sau piatra.
 */
public class FloorCrack4 extends Tile {

    /*! \fn public MountainTile(int id)
       \brief Constructorul de initializare al clasei

       \param id Id-ul dalei util in desenarea hartii.
    */
    public FloorCrack4(int id)
    {
        /// Apel al constructorului clasei de baza
        super(Assets.floorBigCrack4, id);
        this.isSolid = false;
    }

    /*! \fn public boolean IsSolid()
        \brief Suprascrie metoda IsSolid() din clasa de baza in sensul ca va fi luat in calcul in caz de coliziune.
     */
    @Override
    public boolean IsSolid()
    {
        return isSolid;
    }
}
