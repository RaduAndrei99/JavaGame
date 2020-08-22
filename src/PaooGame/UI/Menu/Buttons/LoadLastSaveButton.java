package PaooGame.UI.Menu.Buttons;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.UI.Menu.Buttons.AbstractButton;

import java.awt.*;

public class LoadLastSaveButton extends AbstractButton {

    public LoadLastSaveButton(RefLinks r,int x, int y, int w, int h){
        super(r,x,y,w,h);
        this.static_image = Assets.loadLastSave_static;
        this.mouse_over = Assets.loadLastSave_mouseOver;

        current_image = static_image;
    }



    @Override
    public void Draw(Graphics g) {
        g.drawImage(current_image, x,y, w,h,null);
    }

    @Override
    void isClicked() {

    }

}
