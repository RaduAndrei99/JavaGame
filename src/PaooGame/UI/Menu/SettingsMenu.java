package PaooGame.UI.Menu;

import PaooGame.RefLinks;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class SettingsMenu extends MenuElement {
    protected List<MenuElement> elements;

    SettingsWallpaper wallpaper;

    public SettingsMenu(RefLinks refLink){
        elements = new LinkedList<>();

        elements.add(new VolumeUpButton(refLink,(int) ( 0.65*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.38*Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.026*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.046*Toolkit.getDefaultToolkit().getScreenSize().height)));
        elements.add(new VolumeDownButton(refLink,(int)(0.7*Toolkit.getDefaultToolkit().getScreenSize().width),(int)(0.38*Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.026*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.046*Toolkit.getDefaultToolkit().getScreenSize().height)));
        elements.add(new VolumeText(refLink, (int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.286*Toolkit.getDefaultToolkit().getScreenSize().width/2)), (int)(0.37*Toolkit.getDefaultToolkit().getScreenSize().height),(int)(0.286*Toolkit.getDefaultToolkit().getScreenSize().width),(int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height)));

        elements.add(new FullscreenButton(refLink, (int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.6*Toolkit.getDefaultToolkit().getScreenSize().width/2)), (int)(0.55*Toolkit.getDefaultToolkit().getScreenSize().height),(int)(0.6*Toolkit.getDefaultToolkit().getScreenSize().width),(int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height)));

        elements.add(new BackButton(refLink,(int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.17*Toolkit.getDefaultToolkit().getScreenSize().width/2)), (int)(0.7*Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.17*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height)));

        wallpaper = new SettingsWallpaper();
    }


    @Override
    public void Update() {
        for(MenuElement element : elements)
            element.Update();
    }

    @Override
    public void Draw(Graphics g) {
        wallpaper.Draw(g);

        for(MenuElement element : elements)
            element.Draw(g);
    }
}
