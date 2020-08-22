package PaooGame.UI.Menu.Buttons;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

import java.awt.*;

public class QuitButton extends AbstractButton {

    public QuitButton(RefLinks r,int x, int y, int w, int h){
        super(r,x,y,w,h);
        this.static_image = Assets.quitButton_static;
        this.mouse_over = Assets.quitButton_mouseOver;


        current_image = static_image;
    }


    @Override
    public void Draw(Graphics g) {
        g.drawImage(current_image, x,y, w,h,null);
    }

    @Override
    void isClicked() {
        refs.GetGame().StopGame();
    }

}
