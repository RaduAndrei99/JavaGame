package PaooGame.UI.Menu.Buttons;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SettingsButton extends AbstractButton {

    List<AbstractButton> elements;
    private boolean drawElements = false;

    public SettingsButton(RefLinks refs,int x, int y, int w, int h){
        super(refs,x,y,w,h);
        this.static_image = Assets.settingsButton_static;
        this.mouse_over = Assets.settingsButton_mouseOver;

        current_image = static_image;

        elements = new ArrayList<>();
    }

    @Override
    public void Draw(Graphics g) {
        if(!drawElements)
            g.drawImage(current_image,x,y,w,h ,null);
        else
            for(AbstractButton element : elements)
                element.Draw(g);

    }

    @Override
    void isClicked() {
        refs.GetGame().SetSettingsState();
    }

    public void addElement(AbstractButton b){
        this.elements.add(b);
    }
}
