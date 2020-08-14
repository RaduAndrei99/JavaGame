package PaooGame.UI.Menu;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

public class VolumeText extends AbstractButton {

    public BufferedImage static_image;


    public VolumeText(RefLinks refs, int x, int y, int w, int h){
        super(refs,x,y,w,h);
        this.static_image = Assets.volumeButton_static;
    }

    @Override
    public void Update() {

    }

    @Override
    void isClicked() {

    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(static_image,x,y,w,h,null);
    }
}
