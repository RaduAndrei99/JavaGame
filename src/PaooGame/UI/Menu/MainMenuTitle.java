package PaooGame.UI.Menu;

import PaooGame.Graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MainMenuTitle extends MenuElement{

    protected BufferedImage image;


    public MainMenuTitle(){
        image = Assets.mainMenuTitle;
    }
    @Override
    public void Update() {

    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(Assets.mainMenuTitle, (int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.78*Toolkit.getDefaultToolkit().getScreenSize().width/2)),(int)(0.0925*Toolkit.getDefaultToolkit().getScreenSize().height),(int)(0.78*Toolkit.getDefaultToolkit().getScreenSize().width),(int)(0.1111*Toolkit.getDefaultToolkit().getScreenSize().height), null);
    }
}
