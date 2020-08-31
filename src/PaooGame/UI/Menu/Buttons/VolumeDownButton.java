package PaooGame.UI.Menu.Buttons;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.Sound.Music;

import java.awt.*;

public class VolumeDownButton extends AbstractButton {
    public VolumeDownButton(RefLinks r, int x, int y, int w, int h) {
        super(r,x,y,w,h);
        this.static_image = Assets.minusButton_static;
        this.mouse_over = Assets.minusButton_mouseOver;

        current_image = static_image;
    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(current_image, x,y, w,h,null);
    }

    @Override
    void isClicked() {
        Music.getVolumeDown();
        refs.GetGame().getDatabase().saveSettings();

    }

}
