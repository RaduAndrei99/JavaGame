package PaooGame.UI.Menu;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.States.State;

import java.awt.*;

class BackButton extends AbstractButton {

    public BackButton(RefLinks r,int x, int y, int w, int h){
        super(r,x,y,w,h);
        this.static_image = Assets.backButton_static;
        this.mouse_over = Assets.backButton_mouseOver;

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
        State.setPreviousState();
    }

}
