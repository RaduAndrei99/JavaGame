package PaooGame.Menu;

import PaooGame.Graphics.Assets;
import PaooGame.Input.GameMouseListener;
import PaooGame.RefLinks;
import PaooGame.Sound.Sound;

import java.awt.*;

public class PlayButton extends AbstractButton {

    public PlayButton(RefLinks r,int x, int y, int w, int h){
        super(r,x,y,w,h);
        this.static_image = Assets.playButton_static;
        this.mouse_over = Assets.playButton_mouseOver;

        this.x = Toolkit.getDefaultToolkit().getScreenSize().width/2 - Assets.playButton_static.getWidth()/2;
        this.y = 400;

        current_image = static_image;
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

        if(GameMouseListener.isLeftMousePressed && MouseOver()){
            this.isClicked();
        }
    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(current_image, x,y, null);
    }

    @Override
    void isClicked() {
      refs.GetGame().SetPlayState();
    }

    @Override
    boolean MouseOver() {
        int mX = GameMouseListener.getMouseCoordinates().x;
        int mY = GameMouseListener.getMouseCoordinates().y;

        return (this.x < mX) && (mX < (this.x + this.w)) && (this.y < mY) && (mY < (this.y + this.h));
    }
}
