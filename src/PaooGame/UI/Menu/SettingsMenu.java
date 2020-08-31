package PaooGame.UI.Menu;

import PaooGame.RefLinks;
import PaooGame.UI.Menu.Buttons.*;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class SettingsMenu extends MenuElement {
    protected List<MenuElement> elements;


    public SettingsMenu(RefLinks refLink){
        elements = new LinkedList<>();

        elements.add(new VolumeUpButton(refLink,(int) ( 0.66*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.215*Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.02*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.037*Toolkit.getDefaultToolkit().getScreenSize().height)));
        elements.add(new VolumeDownButton(refLink,(int)(0.7*Toolkit.getDefaultToolkit().getScreenSize().width),(int)(0.23*Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.02*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.01*Toolkit.getDefaultToolkit().getScreenSize().height)));
        elements.add(new VolumeText(refLink, (int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.2182*Toolkit.getDefaultToolkit().getScreenSize().width/2)), (int)(0.2*Toolkit.getDefaultToolkit().getScreenSize().height),(int)(0.2182*Toolkit.getDefaultToolkit().getScreenSize().width),(int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height)));

        elements.add(new SoundFXVolumeUpButton(refLink,(int) ( 0.66*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.37*Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.02*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.037*Toolkit.getDefaultToolkit().getScreenSize().height)));
        elements.add(new SoundFXVolumeDownButton(refLink,(int)(0.7*Toolkit.getDefaultToolkit().getScreenSize().width),(int)(0.385*Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.02*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.01*Toolkit.getDefaultToolkit().getScreenSize().height)));
        elements.add(new SoundText(refLink, (int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.286*Toolkit.getDefaultToolkit().getScreenSize().width/2)), (int)(0.35*Toolkit.getDefaultToolkit().getScreenSize().height),(int)(0.286*Toolkit.getDefaultToolkit().getScreenSize().width),(int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height)));


        elements.add(new EasyButton(refLink,(int) (0.13*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.5*Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.17*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height)));
        elements.add(new MediumButton(refLink,(int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.2859*Toolkit.getDefaultToolkit().getScreenSize().width/2)), (int)(0.5*Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.2859*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height)));
        elements.add(new HardButton(refLink,(int) (0.7*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.5*Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.17*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height)));

        elements.add(new BackButton(refLink,(int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.17*Toolkit.getDefaultToolkit().getScreenSize().width/2)), (int)(0.65*Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.17*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height)));

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
