package PaooGame.UI.Menu.Buttons;

import PaooGame.RefLinks;
import PaooGame.Sound.Sound;

public class SoundFXVolumeDownButton extends VolumeDownButton {
    public SoundFXVolumeDownButton(RefLinks r, int x, int y, int w, int h) {
        super(r, x, y, w, h);
    }

    @Override
    void isClicked() {
        Sound.getVolumeDown();
        refs.GetGame().getDatabase().saveSettings();
    }
}
