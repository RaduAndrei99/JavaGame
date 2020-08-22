package PaooGame.UI.Menu.Wallpapers;

import PaooGame.Graphics.Assets;
import PaooGame.UI.Menu.MenuElement;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PreGameWallpaper extends MenuElement {
    protected BufferedImage image;


    public PreGameWallpaper(){
        image = Assets.preGameWallpaper;
    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(image,-1,0,Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height,null);
    }
}
