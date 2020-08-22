package PaooGame.UI.Menu.Buttons;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

import java.awt.*;

public class MainMenuButton extends AbstractButton {

    public MainMenuButton(RefLinks r,int x, int y, int w, int h){
        super(r,x,y,w,h);
        this.static_image = Assets.mainMenu_static;
        this.mouse_over = Assets.mainMenuMouseOver;

        current_image = static_image;
    }



    @Override
    public void Draw(Graphics g) {
        g.drawImage(current_image, x,y, w,h,null);
    }

    @Override
    void isClicked() {
        try {
            refs.GetGame().SetMenuState();
        }
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

}
