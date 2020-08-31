package PaooGame.UI.Menu;

import PaooGame.RefLinks;
import PaooGame.States.PlayState;
import PaooGame.States.State;
import PaooGame.UI.Menu.Buttons.*;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class PreGameMenu extends MenuElement {
    protected List<MenuElement> elements;

    protected SaveGameButton saveButton;
    protected LoadLastSaveButton loadButton;
    protected BackToGameButton backToGameButton;


    protected RefLinks refs;

    public PreGameMenu(RefLinks refLink){
        refs = refLink;

        elements = new LinkedList<>();

        elements.add(new NewGameButton(refLink, (int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.286*Toolkit.getDefaultToolkit().getScreenSize().width/2)), (int)(0.15*Toolkit.getDefaultToolkit().getScreenSize().height),(int)(0.286*Toolkit.getDefaultToolkit().getScreenSize().width),(int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height)));

        saveButton = new SaveGameButton(refLink, (int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.3989*Toolkit.getDefaultToolkit().getScreenSize().width/2)), (int)(0.30*Toolkit.getDefaultToolkit().getScreenSize().height),(int)(0.3989*Toolkit.getDefaultToolkit().getScreenSize().width),(int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height));
        elements.add(saveButton);

        loadButton = new LoadLastSaveButton(refLink,(int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.579*Toolkit.getDefaultToolkit().getScreenSize().width/2)), (int)(0.43*Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.579*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height));
        elements.add(loadButton);

        elements.add(new SettingsButton(refLink,(int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.364*Toolkit.getDefaultToolkit().getScreenSize().width/2)), (int)(0.58*Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.364*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height)));

        elements.add(new MainMenuButton(refLink,(int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.364*Toolkit.getDefaultToolkit().getScreenSize().width/2)), (int)(0.72*Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.364*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height)));

        backToGameButton = new BackToGameButton(refLink,(int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.512*Toolkit.getDefaultToolkit().getScreenSize().width/2)), (int)(0.85*Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.512*Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.07*Toolkit.getDefaultToolkit().getScreenSize().height));
        elements.add(backToGameButton);
    }


    @Override
    public void Update() {
        for(MenuElement element : elements)
            element.Update();

        if(State.GetPreviousState() instanceof PlayState){
            backToGameButton.makeValid();
        }

        if(refs.GetGame().getDatabase().isSaveAvailable() && State.GetPreviousState() instanceof PlayState) {
            this.saveButton.makeValid();
        }
        else
            this.saveButton.makeInvalid();

        if(refs.GetGame().getDatabase().isLoadAvailable())
            this.loadButton.makeValid();

    }

    @Override
    public void Draw(Graphics g) {
        for(MenuElement element : elements)
            element.Draw(g);
    }
}
