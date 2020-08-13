package PaooGame;


import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        System.out.println(width);
        System.out.println(height);
        Game paooGame = new Game("PaooGame", (int)width, (int)height);
        paooGame.StartGame();
    }
}
