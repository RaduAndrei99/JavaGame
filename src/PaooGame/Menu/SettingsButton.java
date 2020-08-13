package PaooGame.Menu;

import PaooGame.Graphics.Assets;
import PaooGame.Input.GameMouseListener;
import PaooGame.RefLinks;
import PaooGame.Sound.Sound;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SettingsButton extends AbstractButton {

    List<AbstractButton> elements;
    private boolean drawElements = false;

    public SettingsButton(RefLinks refs,int x, int y, int w, int h){
        super(refs,x,y,w,h);
        this.static_image = Assets.settingsButton_static;
        this.mouse_over = Assets.settingsButton_mouseOver;

        current_image = static_image;

        elements = new ArrayList<>();
    }

    @Override
    public void Update() {
        if(MouseOver()) {
            this.current_image = mouse_over;
            if(!sound_played) {
                Sound.playSound(Sound.mouse_over);
                sound_played = true;
            }
        }
        else {
            this.current_image = static_image;
            sound_played = false;
        }

        if(GameMouseListener.isLeftMousePressed && MouseOver())
            drawElements = true;
    }

    @Override
    public void Draw(Graphics g) {
        if(!drawElements)
            g.drawImage(current_image,x,y,w,h ,null);
        else
            for(AbstractButton element : elements)
                element.Draw(g);

    }

    @Override
    void isClicked() {

    }

    @Override
    boolean MouseOver() {
        int mX = GameMouseListener.getMouseCoordinates().x;
        int mY = GameMouseListener.getMouseCoordinates().y;

        return (this.x < mX) && (mX < (this.x + this.w)) && (this.y < mY) && (mY < (this.y + this.h));
    }

    public void addElement(AbstractButton b){
        this.elements.add(b);
    }
}
