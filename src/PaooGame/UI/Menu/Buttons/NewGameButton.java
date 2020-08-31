package PaooGame.UI.Menu.Buttons;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Hero;
import PaooGame.RefLinks;
import PaooGame.States.State;
import PaooGame.UI.Scoreboard;

import java.awt.*;

public class NewGameButton extends AbstractButton {

    public NewGameButton(RefLinks r,int x, int y, int w, int h){
        super(r,x,y,w,h);
        this.static_image = Assets.newGame_static;
        this.mouse_over = Assets.newGame_mouseOver;

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
        refs.GetMap().resetMap();
        Scoreboard.resetScore();
        refs.GetGame().SetPlayState();
        State.GetState().reset();
        Hero.GetInstance().reset();

    }

}
