package PaooGame.UI.Menu;

import PaooGame.Graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MainMenuWallpaper extends MenuElement{
    protected BufferedImage image;


    public MainMenuWallpaper(){
        image = Assets.mainMenuWallpaper;
    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(image,0,0,Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height,null);
    }
}
