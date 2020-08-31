package PaooGame.Items;

import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class Item {
    protected float x;                  /*!< Pozitia pe axa X a "tablei" de joc a imaginii entitatii.*/
    protected float y;                  /*!< Pozitia pe axa Y a "tablei" de joc a imaginii entitatii.*/
    protected int width;                /*!< Latimea imaginii entitatii.*/
    protected int height;               /*!< Inaltimea imaginii entitatii.*/
    protected Rectangle normalBounds;   /*!< Dreptunghiul de coliziune aferent starii obisnuite(spatiul ocupat de entitate in mod normal), poate fi mai mic sau mai mare decat dimensiunea imaginii sale.*/
    protected Rectangle droopedBounds;
    protected RefLinks refLink;         /*!< O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.*/

    protected int collision_offset_x = 0;
    protected int collision_offset_y = 0;

    protected boolean isDrooped = true;

    protected BufferedImage image;

    protected int position = 1;
    protected int x_mirror_offset;


    public int item_ID;

    public Item(RefLinks refLink, float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.refLink = refLink;

        normalBounds = new Rectangle((int) x, (int) y, width, height);

    }

    public abstract void Update();

    public abstract void Draw(Graphics g);

    public float GetX() {
        return x;
    }

    public float GetY() {
        return y;
    }

    public int GetWidth() {
        return width;
    }

    public int GetHeight() {
        return height;
    }

    public void SetX(float x) {
        this.x = x;
    }

    public void SetY(float y) {
        this.y = y;
    }

    public void SetWidth(int width) {
        this.width = width;
    }

    public void SetHeight(int height) {
        this.height = height;
    }

    protected void UpdateBoundsRectangle() {
        normalBounds.x = (int) x;
        normalBounds.y = (int) y;
    }

    public Rectangle getNormalBounds() {
        return new Rectangle(this.normalBounds.x + collision_offset_x,this.normalBounds.y + collision_offset_y,
                this.normalBounds.width,this.normalBounds.height);
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public Rectangle getDroppedBounds(){
        return droopedBounds;
    }

    public void setDroppedBoundsX(int x){
        this.droopedBounds.x = x;
    }

    public void setDroppedBoundsY(int y){
        this.droopedBounds.y = y;
    }

    public void dropItem(){
        isDrooped = true;
    }

    public void pickItem(){
        isDrooped = false;
    }

    public int getItem_ID(){
        return item_ID;
    }

}
