package PaooGame.UI;

import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class UIElement {
    protected float x, y;

    protected RefLinks refs;

    public UIElement(RefLinks refs, float x, float y) {
        this.x = x;
        this.y = y;
        this.refs = refs;
    }

    public abstract void Draw(Graphics g);

    public abstract void Update();

}
