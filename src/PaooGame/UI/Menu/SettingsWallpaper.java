package PaooGame.UI.Menu;

import PaooGame.Graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SettingsWallpaper extends MenuElement{
    protected BufferedImage image;


    public SettingsWallpaper(){
        image = Assets.settingsWallpaper;
    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(image,-1,0,Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height,null);
    }
}
