package PaooGame.Items.Weapons;

import PaooGame.Input.GameMouseListener;
import PaooGame.Items.Item;
import PaooGame.RefLinks;
import PaooGame.Sound.Sound;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class Weapon extends Item {
    public static int DEFAULT_DAMAGE = 10;
    protected final int DAMAGE_OFFSET = 30;


    public boolean attackMode = false;
    public boolean damageGiven = true;

    protected int damage;
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected Rectangle boundLeft,boundsRight,boundsUp,boundsDown;

    public Weapon(RefLinks refLink, float x, float y, int width, int height){
        super(refLink, x, y, width, height);
        damage = DEFAULT_DAMAGE;
        this.x = (int)x;
        this.y = (int)y;
        this.width = width;
        this.height = height;

        boundsUp = new Rectangle((int)x,(int)y,width,height);
        boundsDown = new Rectangle((int)x,(int)y,width,height);
        boundLeft = new Rectangle((int)x - (height-width), (int)y,height,width);
        boundsRight = new Rectangle((int)x, (int)y,height,width);

        droopedBounds = new Rectangle((int)x,(int)y, width, height);
    }

    void setDamage(int damage){
        this.damage = damage;
    }

    public void setX(int x){
        this.x = x;
        this.droopedBounds.x = x;
    }

    public void setY(int y){
        this.y = y;
        this.droopedBounds.y = y;

    }

    protected void updateBounds(){
        //this.normalBounds.x = (int)(x );
        //this.normalBounds.y = (int)(y );

        boundsUp.x = x;
        boundsUp.y = y;

        boundsDown.x = x;
        boundsDown.y = y;

        boundsRight.x = x;
        boundsRight.y = y;

        boundLeft.x = x - (height-width);
        boundLeft.y = y;

    }

    public void Update() {

        updateBounds();


        float angle = (float)Math.toDegrees(Math.atan2(GameMouseListener.getMouseCoordinates().y - y ,GameMouseListener.getMouseCoordinates().x - x ));

        if(-135<angle && angle < -45){
            this.normalBounds = boundsUp;
            if(GameMouseListener.isLeftMousePressed) {
                this.y -= DAMAGE_OFFSET;
                attackMode = true;
            }
            else {
                damageGiven = false;
                attackMode = false;
            }
        }else{
            if(-45<angle && angle<45){
                this.normalBounds = boundsRight;
                if(GameMouseListener.isLeftMousePressed) {
                    this.x += DAMAGE_OFFSET;
                    attackMode = true;
                }
                else {
                    damageGiven = false;
                    attackMode = false;
                }
            }else{
                if(45<angle && angle<135){
                    this.normalBounds = boundsDown;
                    if(GameMouseListener.isLeftMousePressed) {
                        this.y += DAMAGE_OFFSET;
                        attackMode = true;
                    }
                    else {
                        damageGiven = false;
                        attackMode = false;
                    }
                }else {
                    this.normalBounds = boundLeft;
                    if(GameMouseListener.isLeftMousePressed) {
                        this.x -= DAMAGE_OFFSET;
                        attackMode = true;
                    }
                    else {
                        damageGiven = false;
                        attackMode = false;
                    }
                }
            }
        }

        updateBounds();

    }

    public void Draw(Graphics g) {
        if(!isDrooped) {
            float angle = (float) Math.toDegrees(Math.atan2(GameMouseListener.getMouseCoordinates().y - y, GameMouseListener.getMouseCoordinates().x - x));

            AffineTransform at = AffineTransform.getTranslateInstance(x - refLink.GetGame().getCamera().getXOffset(), y - refLink.GetGame().getCamera().getYOffset());
            at.rotate(Math.toRadians(angle + 90), (float) this.width / 2, (float) this.height / 2);
            at.scale(3, 3);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(Color.RED);

            g2d.drawImage(image, at, null);
            g2d.drawRect((int) (normalBounds.x - refLink.GetGame().getCamera().getXOffset()), (int) (normalBounds.y - refLink.GetGame().getCamera().getYOffset()), normalBounds.width, normalBounds.height);
            // System.out.println(normalBounds.x + " " + normalBounds.y);
        }
        else {
            AffineTransform at = AffineTransform.getTranslateInstance(x - refLink.GetGame().getCamera().getXOffset(), y - refLink.GetGame().getCamera().getYOffset());
            at.scale(3, 3);
            Graphics2D g2d = (Graphics2D) g;

            g2d.drawImage(image, at, null);


            g2d.drawRect((int) (this.droopedBounds.x - refLink.GetGame().getCamera().getXOffset()), (int) (this.droopedBounds.y - refLink.GetGame().getCamera().getYOffset()), this.droopedBounds.width,droopedBounds.height);

        }
    }

    void setAttackMode(boolean v){
        this.attackMode = v;
    }

    public void signalDamage(){
        this.damageGiven = true;
    }

    public int getSwordDamage(){
        return this.damage;
    }
    public boolean isInAttackMode(){
        return this.attackMode;
    }

    public boolean damageAlreadyGiven(){
        return this.damageGiven;
    }

}
