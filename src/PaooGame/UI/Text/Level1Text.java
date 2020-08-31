package PaooGame.UI.Text;

import PaooGame.Graphics.Assets;

public class Level1Text extends ImageText {
    protected static final int DEFAULT_WIDTH = 889;
    protected static final int DEFAULT_HEIGHT = 174;

    public Level1Text(int x, int y){
        super(x,y,DEFAULT_WIDTH,DEFAULT_WIDTH);
        this.text_image = Assets.level1_text;
    }
}
