package PaooGame.Menu;

import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

abstract class AbstractButton {

    RefLinks refs;

    public boolean sound_played = false;

    protected int x;
    protected int y;
    protected int w;
    protected int h;

    AbstractButton(RefLinks r,int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        refs = r;
    }

    protected BufferedImage static_image;
    protected BufferedImage mouse_over;

    protected BufferedImage current_image;

    abstract void Update();
    abstract void Draw(Graphics g);
    abstract void isClicked();

    abstract boolean MouseOver();
}