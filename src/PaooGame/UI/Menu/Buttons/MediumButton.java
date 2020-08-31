package PaooGame.UI.Menu.Buttons;

import PaooGame.GameDifficulty;
import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

import java.awt.*;

public class MediumButton extends AbstractButton {

    public MediumButton(RefLinks r,int x, int y, int w, int h){
        super(r,x,y,w,h);
        this.static_image = Assets.medium_static;
        this.mouse_over = Assets.medium_mouseOver;

        current_image = static_image;
    }


    @Override
    public void Draw(Graphics g) {
        g.drawImage(current_image, x,y, w,h,null);
    }

    @Override
    void isClicked(){
        GameDifficulty.setDifficultyMedium();
        refs.GetGame().getDatabase().saveSettings();
    }

}
