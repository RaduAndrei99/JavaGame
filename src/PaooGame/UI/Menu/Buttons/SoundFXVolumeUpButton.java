package PaooGame.UI.Menu.Buttons;

import PaooGame.RefLinks;
import PaooGame.Sound.Sound;

public class SoundFXVolumeUpButton extends VolumeUpButton {
    public SoundFXVolumeUpButton(RefLinks r, int x, int y, int w, int h) {
        super(r, x, y, w, h);
    }

    @Override
    void isClicked() {
        Sound.getVolumeUp();
        refs.GetGame().getDatabase().saveSettings();

    }
}