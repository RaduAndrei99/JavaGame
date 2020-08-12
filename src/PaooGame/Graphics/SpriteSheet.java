package PaooGame.Graphics;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/*! \class public class SpriteSheet
    \brief Clasa retine o referinta catre o imagine formata din dale (sprite sheet)

    Metoda crop() returneaza o dala de dimensiuni fixe (o subimagine) din sprite sheet
    de la adresa (x * latimeDala, y * inaltimeDala)
 */
public class SpriteSheet
{
    private BufferedImage       spriteSheet;        /*!< Referinta catre obiectul BufferedImage ce contine sprite sheet-ul.*/

    /*! \fn public SpriteSheet(BufferedImage sheet)
        \brief Constructor, initializeaza spriteSheet.

        \param buffImg Un obiect BufferedImage valid.
     */
    public SpriteSheet(BufferedImage buffImg)
    {
            /// Retine referinta catre BufferedImage object.
        spriteSheet = buffImg;
    }

    /*! \fn public BufferedImage crop(int x, int y)
        \brief Returneaza un obiect BufferedImage ce contine o subimage (dala).

        Subimaginea este localizata avand ca referinta punctul din stanga sus.

        \param x numarul dalei din sprite sheet pe axa x.
        \param y numarul dalei din sprite sheet pe axa y.
     */
    public BufferedImage crop(int x, int y, int w, int h)
    {
            /// Subimaginea (dala) este regasita in sprite sheet specificad coltul stanga sus
            /// al imaginii si apoi latimea si inaltimea (totul in pixeli). Coltul din stanga sus al imaginii
            /// se obtine inmultind numarul de ordine al dalei cu dimensiunea in pixeli a unei dale.
        return spriteSheet.getSubimage(x , y , w, h);
    }

    public static BufferedImage rotate(BufferedImage img, double angle) {

        int w = img.getWidth();
        int h = img.getHeight();

        BufferedImage rotated = new BufferedImage(w, h, img.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.rotate(Math.toRadians(angle), h/2, h/2);
        graphic.drawImage(img, null, 0, 0);
        graphic.dispose();



        return rotated;
    }
}
