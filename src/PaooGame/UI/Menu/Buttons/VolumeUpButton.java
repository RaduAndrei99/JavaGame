package PaooGame.UI.Menu.Buttons;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.Sound.Music;

import java.awt.*;

public class VolumeUpButton extends AbstractButton {
    public VolumeUpButton(RefLinks r, int x, int y, int w, int h) {
        super(r,x,y,w,h);
        this.static_image = Assets.plusButton_static;
        this.mouse_over = Assets.plusButton_mouseOver;


        current_image = static_image;
    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(current_image, x,y, w,h,null);
    }

    @Override
    void isClicked() {
        Music.getVolumeUp();
    }

}
