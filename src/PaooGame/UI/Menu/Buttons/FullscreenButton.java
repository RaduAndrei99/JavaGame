package PaooGame.UI.Menu.Buttons;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

import java.awt.*;

public class FullscreenButton extends AbstractButton {

    protected boolean isFullscreen = true;

    public FullscreenButton(RefLinks r,int x, int y, int w, int h){
        super(r,x,y,w,h);
        this.static_image = Assets.fullscreenOn_static;
        this.mouse_over = Assets.fullscreenOn_mouseOver;

        //this.x = Toolkit.getDefaultToolkit().getScreenSize().width/2 - Assets.playButton_static.getWidth()/2;
        // this.y = 400;

        current_image = static_image;
    }



    @Override
    public void Draw(Graphics g) {
        g.drawImage(current_image, x,y, w,h,null);
    }

    @Override
    void isClicked() {
        if(isFullscreen) {
            isFullscreen = false;
            static_image = Assets.fullscreenOff_static;
            mouse_over = Assets.fullscreenOff_mouseOver;

        }else {
            isFullscreen = true;
            static_image = Assets.fullscreenOn_static;
            mouse_over = Assets.fullscreenOn_mouseOver;

        }
    }



}
