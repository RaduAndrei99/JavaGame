package PaooGame.UI.Menu.Buttons;

import PaooGame.GameDifficulty;
import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

import java.awt.*;

public class BackToGameButton extends AbstractButton {

    public BackToGameButton(RefLinks r,int x, int y, int w, int h){
        super(r,x,y,w,h);
        this.static_image = Assets.backToGame_static;
        this.mouse_over = Assets.backToGame_mouseOver;
        this.invalid = Assets.backToGame_invalid;

        current_image = invalid;

        this.isButtonValid = false;

    }



    @Override
    public void Draw(Graphics g) {
        g.drawImage(current_image, x,y, w,h,null);
    }

    @Override
    void isClicked(){
        refs.GetGame().SetPlayState();
    }

}
