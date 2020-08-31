package PaooGame.UI.Menu.Buttons;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

import java.awt.*;
import java.util.Vector;

public class SaveGameButton extends AbstractButton {


    public SaveGameButton(RefLinks r,int x, int y, int w, int h){
        super(r,x,y,w,h);
        this.static_image = Assets.saveGame_static;
        this.mouse_over = Assets.saveGame_mouseOver;
        this.invalid = Assets.saveGame_invalid;

        current_image = invalid;

        this.isButtonValid = false;
    }



    @Override
    public void Draw(Graphics g) {
        g.drawImage(current_image, x,y, w,h,null);
    }

    @Override
    void isClicked() {
        refs.GetGame().getDatabase().saveGame(refs.GetGame());
    }

}
