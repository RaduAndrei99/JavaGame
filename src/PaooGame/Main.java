package PaooGame;

import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        Game paooGame = new Game("The Mighty Dungeon",Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        paooGame.StartGame();
    }
}
