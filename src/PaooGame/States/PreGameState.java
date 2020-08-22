package PaooGame.States;

import PaooGame.RefLinks;
import PaooGame.UI.Menu.PreGameMenu;
import PaooGame.UI.Menu.Wallpapers.PreGameWallpaper;

import java.awt.*;



public class PreGameState extends State
{
    PreGameMenu menu;
    PreGameWallpaper wallpaper;

    public PreGameState(RefLinks refLink) {
        super(refLink);

        wallpaper = new PreGameWallpaper();
        menu = new PreGameMenu(refLink);
    }

    @Override
    public void Update() {
        menu.Update();
    }

    @Override
    public void Draw(Graphics g) {
        wallpaper.Draw(g);
        menu.Draw(g);
    }

    @Override
    public void reset() {

    }
}
