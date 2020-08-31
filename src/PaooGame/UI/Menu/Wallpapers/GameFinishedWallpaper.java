package PaooGame.UI.Menu.Wallpapers;

import PaooGame.Graphics.Assets;
import PaooGame.UI.Menu.MenuElement;

import java.awt.*;
import java.awt.image.BufferedImage;


public class GameFinishedWallpaper extends MenuElement {
    protected BufferedImage image;


    public GameFinishedWallpaper(){
        image = Assets.game_finished_wallpaper;
    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw(Graphics g) {


        g.drawImage(image,0,0,Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height,null);
    }
}
