package PaooGame.UI.Menu.Buttons;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.UI.Menu.Buttons.AbstractButton;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SoundText extends AbstractButton {

    public BufferedImage static_image;


    public SoundText(RefLinks refs, int x, int y, int w, int h){
        super(refs,x,y,w,h);
        this.static_image = Assets.soundButton_static;
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
