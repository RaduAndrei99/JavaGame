package PaooGame.Menu;

import PaooGame.RefLinks;

import javax.swing.*;
import java.awt.*;

public class VolumeSlider extends AbstractButton{

    JSlider slider = new JSlider(JSlider.HORIZONTAL);

    VolumeSlider(RefLinks r, int x, int y, int w, int h) {
        super(r, x, y, w, h);
    }

    @Override
    void Update() {

    }

    @Override
    void Draw(Graphics g) {
    }

    @Override
    void isClicked() {

    }

    @Override
    boolean MouseOver() {
        return false;
    }
}
