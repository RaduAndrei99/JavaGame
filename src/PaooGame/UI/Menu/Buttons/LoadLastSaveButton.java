package PaooGame.UI.Menu.Buttons;

import PaooGame.Game;
import PaooGame.Graphics.Assets;
import PaooGame.Items.Hero;
import PaooGame.RefLinks;

import java.awt.*;

public class LoadLastSaveButton extends AbstractButton {

    public LoadLastSaveButton(RefLinks r,int x, int y, int w, int h){
        super(r,x,y,w,h);
        this.static_image = Assets.loadLastSave_static;
        this.mouse_over = Assets.loadLastSave_mouseOver;
        this.invalid = Assets.loadLastSave_invalid;
        current_image = invalid;

        this.isButtonValid = false;
    }



    @Override
    public void Draw(Graphics g) {
        g.drawImage(current_image, x,y, w,h,null);
    }

    @Override
    void isClicked() {
        refs.GetGame().getDatabase().loadGame();
        Hero.GetInstance().notifyObservers();
        Hero.GetInstance().Update();
        refs.GetGame().SetPlayState();
    }

}
