package PaooGame.UI.Menu;

import PaooGame.RefLinks;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class MainMenu extends MenuElement {
    protected List<MenuElement> elements;

    public MainMenu(RefLinks refLink){
        elements = new LinkedList<>();

        elements.add(new PlayButton(refLink,(int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.17*Toolkit.getDefaultToolkit().getScreenSize().width/2)), (int)(0.37*Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.17*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height)));
        elements.add(new SettingsButton(refLink, (int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.3312*Toolkit.getDefaultToolkit().getScreenSize().width/2)),(int)(0.5555*Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.3312*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height)));
        elements.add(new QuitButton(refLink, (int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.15*Toolkit.getDefaultToolkit().getScreenSize().width/2)),(int)(0.74*Toolkit.getDefaultToolkit().getScreenSize().height),(int)(0.15*Toolkit.getDefaultToolkit().getScreenSize().width) , (int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height)));
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
