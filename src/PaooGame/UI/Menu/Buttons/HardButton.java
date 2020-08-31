package PaooGame.UI.Menu.Buttons;

import PaooGame.GameDifficulty;
import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

import java.awt.*;

public class HardButton extends AbstractButton {

    public HardButton(RefLinks r,int x, int y, int w, int h){
        super(r,x,y,w,h);
        this.static_image = Assets.hard_static;
        this.mouse_over = Assets.hard_mouseOver;

        current_image = static_image;
    }



    @Override
    public void Draw(Graphics g) {
        g.drawImage(current_image, x,y, w,h,null);
    }

    @Override
    void isClicked(){
        GameDifficulty.setDifficultyHard();
        refs.GetGame().getDatabase().saveSettings();
    }

}
