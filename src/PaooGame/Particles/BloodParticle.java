package PaooGame.Particles;

import PaooGame.Graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BloodParticle {
    int wait = 0;
    int currentPos;

    public static int MAX_POS;

    private final BufferedImage []image;
    public BloodParticle(){
        image = new BufferedImage[12];


        image[0] = Assets.blood0;
        image[1] = Assets.blood1;
        image[2] = Assets.blood2;
        image[3] = Assets.blood3;
        image[4] = Assets.blood4;
        image[5] = Assets.blood5;
        image[6] = Assets.blood6;
        image[7] = Assets.blood7;
        image[8] = Assets.blood8;
        image[9] = Assets.blood9;
        image[10] = Assets.blood10;
        image[11] = Assets.blood11;
        currentPos = image.length - 1;
        MAX_POS = image.length - 1;
    }

    public void Draw(Graphics g, int x, int y, int w, int h){
        if(currentPos != image.length - 1) {
            g.drawImage(image[nextPos()], x, y, w, h, null);
        }

    }

    protected int nextPos(){
        wait++;
        if(wait > 2) {
            wait = 0;
            if (currentPos < image.length - 1)
                return currentPos++;
            else
                return currentPos = 0;
        }

        return currentPos;
    }

    public void resetAnimation(){
        this.currentPos = 0;
    }
}
