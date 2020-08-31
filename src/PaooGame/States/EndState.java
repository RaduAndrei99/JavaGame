package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Hero;
import PaooGame.RefLinks;
import PaooGame.UI.Menu.Wallpapers.GameFinishedWallpaper;
import PaooGame.UI.Scoreboard;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EndState extends State {

    private GameFinishedWallpaper wallpaper;
    private Scoreboard scoreboard;
    private BufferedImage end_text;


    public EndState(RefLinks refLink) {
        super(refLink);
        scoreboard = new Scoreboard(refLink,600,585);
        end_text = Assets.end_text;
        wallpaper = new GameFinishedWallpaper();
    }

    @Override
    public void Update() {
        if(refLink.GetGame().GetKeyManager().r){
            refLink.GetMap().resetMap();
            Scoreboard.resetScore();
            Hero.GetInstance().reset();
            refLink.GetGame().SetMenuState();;
        }
    }

    @Override
    public void Draw(Graphics g) {
        wallpaper.Draw(g);
        g.drawImage(end_text, Toolkit.getDefaultToolkit().getScreenSize().width/2 - (int)(0.7567*Toolkit.getDefaultToolkit().getScreenSize().width)/2,
                Toolkit.getDefaultToolkit().getScreenSize().height/2 - (int)(0.1574*Toolkit.getDefaultToolkit().getScreenSize().height)/2,
                (int)(0.7567*Toolkit.getDefaultToolkit().getScreenSize().width),(int)(0.1574*Toolkit.getDefaultToolkit().getScreenSize().height), null);
        scoreboard.Draw(g);

    }

    @Override
    public void reset() {

    }
}
