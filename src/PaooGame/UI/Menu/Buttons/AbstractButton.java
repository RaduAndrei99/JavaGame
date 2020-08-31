package PaooGame.UI.Menu.Buttons;

import PaooGame.Input.GameMouseListener;
import PaooGame.RefLinks;
import PaooGame.Sound.Sound;
import PaooGame.UI.Menu.MenuElement;

import java.awt.*;
import java.awt.image.BufferedImage;

abstract class AbstractButton extends MenuElement {

    RefLinks refs;

    public boolean sound_played = false;

    protected int x;
    protected int y;
    protected int w;
    protected int h;

    boolean isClicked = false;
    boolean isReleased = false;

    protected BufferedImage static_image;
    protected BufferedImage mouse_over;
    protected BufferedImage invalid;


    protected BufferedImage current_image;

    protected boolean isButtonValid = true;

    AbstractButton(RefLinks r,int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        refs = r;
    }

    public void Update() {
        if(isButtonValid) {
            if (isMouseOver()) {
                if (GameMouseListener.isLeftMousePressed) {
                    isClicked = true;
                    isReleased = false;
                }
                if (GameMouseListener.isLeftMouseReleased && isClicked) {
                    isClicked = false;
                    isReleased = true;
                }
            }

            if (isMouseOver()) {
                this.current_image = mouse_over;
                if (!sound_played) {
                    Sound.playSound(Sound.mouse_over);
                    sound_played = true;
                }
            } else {
                this.current_image = static_image;
                sound_played = false;
            }

            if (isReleased && isMouseOver()) {
                this.isClicked();
                isReleased = false;
            }
        }
        else
            this.current_image = invalid;
    }
    abstract void isClicked();

    boolean isMouseOver() {
        int mX = GameMouseListener.getMouseCoordinates().x;
        int mY = GameMouseListener.getMouseCoordinates().y;

        return (this.x < mX) && (mX < (this.x + this.w)) && (this.y < mY) && (mY < (this.y + this.h));
    }


    public void makeValid(){
        this.isButtonValid = true;
    }

    public void makeInvalid(){
        this.isButtonValid = false;
    }

}