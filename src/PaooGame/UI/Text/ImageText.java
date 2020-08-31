package PaooGame.UI.Text;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ImageText {

    protected int x;
    protected int y;

    protected int width;
    protected int height;

    protected BufferedImage text_image;

    public ImageText(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }


    public void Draw(Graphics g){
        g.drawImage(text_image,x,y,width,height,null);
    }
}
