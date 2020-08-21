package PaooGame.UI;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Hero;
import PaooGame.Items.Item;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LifeBar extends Item {

    int numberOfLives;


    BufferedImage fullHeart;
    BufferedImage emptyHeart;

    public LifeBar(int x, int y,int w,int h, int l){
        super(null,x,y,w,h);

        numberOfLives = l;

        fullHeart = Assets.full_heart;
        emptyHeart = Assets.empty_heart;

    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw(Graphics g) {
        int j = 0;
        for (int i = 0; i < Hero.GetInstance().GetCurrentLife(); ++i) {
            g.drawImage(fullHeart, (int) x + (i + 1) * width, (int) y, width, height, null);
            j = i + 1;
        }

        for (int i = j; i < numberOfLives; ++i) {
            g.drawImage(emptyHeart, (int) x + (i + 1) * width, (int) y, width, height, null);
        }
    }

}
