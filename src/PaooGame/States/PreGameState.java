package PaooGame.States;

import PaooGame.RefLinks;
import PaooGame.Sound.Music;
import PaooGame.UI.Menu.PreGameMenu;
import PaooGame.UI.Menu.Wallpapers.PreGameWallpaper;

import java.awt.*;



public class PreGameState extends State
{
    PreGameMenu menu;
    PreGameWallpaper wallpaper;

    long oldTime ;


    public PreGameState(RefLinks refLink) {
        super(refLink);

        wallpaper = new PreGameWallpaper();
        menu = new PreGameMenu(refLink);
    }

    @Override
    public void Update() {
        menu.Update();

        long currentTime =  System.currentTimeMillis()/1000;
        if(reset || currentTime - oldTime > 60){
            reset = false;
            reset();
            oldTime = System.currentTimeMillis()/1000;
        }

    }

    @Override
    public void Draw(Graphics g) {
        wallpaper.Draw(g);
        menu.Draw(g);
    }

    @Override
    public void reset() {
        Music.playSound(Music.menu_music_clip);
    }
}
