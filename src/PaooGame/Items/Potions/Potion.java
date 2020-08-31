package PaooGame.Items.Potions;

import PaooGame.Graphics.Assets;
import PaooGame.Input.GameMouseListener;
import PaooGame.Items.Item;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class Potion extends Item {
    public Potion(RefLinks refLink, float x, float y, int width, int height) {
        super(refLink, x, y, width, height);
        droopedBounds = new Rectangle((int)x,(int)y, width, height);
    }


    protected void updateBounds(){
        this.droopedBounds.x = (int) x;
        this.droopedBounds.y = (int) y;
    }

    @Override
    public void Update() {
        updateBounds();
    }

    @Override
    public void Draw(Graphics g) {
        if(!isDrooped) {/*
            float angle = (float) Math.toDegrees(Math.atan2(GameMouseListener.getMouseCoordinates().y - y, GameMouseListener.getMouseCoordinates().x - x));

            AffineTransform at = AffineTransform.getTranslateInstance(x - refLink.GetGame().getCamera().getXOffset(), y - refLink.GetGame().getCamera().getYOffset());
            at.rotate(Math.toRadians(angle + 90), (float) this.width / 2, (float) this.height / 2);
            at.scale(3, 3);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(Color.RED);

            g2d.drawImage(image, at, null);*/
            g.drawImage(image,(int)(x - refLink.GetGame().getCamera().getXOffset()), (int)(y - refLink.GetGame().getCamera().getYOffset()),width,height, null);
        }
        else {
            AffineTransform at = AffineTransform.getTranslateInstance(x - refLink.GetGame().getCamera().getXOffset(), y - refLink.GetGame().getCamera().getYOffset());
            at.scale(3, 3);
            Graphics2D g2d = (Graphics2D) g;

            g2d.drawImage(image, at, null);

            g2d.drawRect((int) (this.droopedBounds.x - refLink.GetGame().getCamera().getXOffset()), (int) (this.droopedBounds.y - refLink.GetGame().getCamera().getYOffset()), this.droopedBounds.width,droopedBounds.height);

        }
    }

    public abstract void drinkThisPotion();
}
