package PaooGame.UI.Menu;

import PaooGame.RefLinks;
import PaooGame.UI.Menu.Buttons.*;
import PaooGame.UI.Menu.Wallpapers.SettingsWallpaper;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class PreGameMenu extends MenuElement {
    protected List<MenuElement> elements;


    public PreGameMenu(RefLinks refLink){
        elements = new LinkedList<>();

        elements.add(new NewGameButton(refLink, (int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.286*Toolkit.getDefaultToolkit().getScreenSize().width/2)), (int)(0.15*Toolkit.getDefaultToolkit().getScreenSize().height),(int)(0.286*Toolkit.getDefaultToolkit().getScreenSize().width),(int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height)));

        elements.add(new SaveGameButton(refLink, (int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.3989*Toolkit.getDefaultToolkit().getScreenSize().width/2)), (int)(0.30*Toolkit.getDefaultToolkit().getScreenSize().height),(int)(0.3989*Toolkit.getDefaultToolkit().getScreenSize().width),(int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height)));

        elements.add(new LoadLastSaveButton(refLink,(int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.579*Toolkit.getDefaultToolkit().getScreenSize().width/2)), (int)(0.45*Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.579*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height)));

        elements.add(new MainMenuButton(refLink,(int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.364*Toolkit.getDefaultToolkit().getScreenSize().width/2)), (int)(0.60*Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.364*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height)));

        elements.add(new BackButton(refLink,(int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.17*Toolkit.getDefaultToolkit().getScreenSize().width/2)), (int)(0.75*Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.17*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height)));

    }


    @Override
    public void Update() {
        for(MenuElement element : elements)
            element.Update();
    }

    @Override
    public void Draw(Graphics g) {
        for(MenuElement element : elements)
            element.Draw(g);
    }
}
