package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Chest;
import PaooGame.Menu.PlayButton;
import PaooGame.Menu.QuitButton;
import PaooGame.Menu.SettingsButton;
import PaooGame.RefLinks;
import PaooGame.Sound.Music;
import PaooGame.Sound.Sound;

import java.awt.*;

/*! \class public class MenuState extends State
    \brief Implementeaza notiunea de menu pentru joc.
 */
public class MenuState extends State
{
    PlayButton playButton;
    SettingsButton settingsButton;
    QuitButton quitButton;
    long oldTime ;

    public MenuState(RefLinks refLink)
    {
            ///Apel al constructorului clasei de baza.
        super(refLink);
        playButton = new PlayButton(refLink,750, 400, 333, 72);
        settingsButton = new SettingsButton(refLink,600,600, 636, 72);
        quitButton = new QuitButton(refLink,770,800,289 , 72);
    }
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniului.
     */
    @Override
    public void Update()
    {
        playButton.Update();
        settingsButton.Update();
        quitButton.Update();

        long currentTime =  System.currentTimeMillis()/1000;
        //System.out.println(curentTime - oldTime);
        if(reset || currentTime - oldTime > 60){
            reset = false;
            reset();
            oldTime = System.currentTimeMillis()/1000;
        }
    }

    public void reset(){
        Music.playSound(Music.menu_music);
    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a meniului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g)
    {
        g.drawImage(Assets.mainMenuWallpaper,0,0,null);
        g.drawImage(Assets.mainMenuTitle, Toolkit.getDefaultToolkit().getScreenSize().width/2 - Assets.mainMenuTitle.getWidth()/2,100, null);
        playButton.Draw(g);
        settingsButton.Draw(g);
        quitButton.Draw(g);

        //g.drawImage(Assets.inventory_cell_selected,500,500,50,50,null);
       // g.setColor(Color.GREEN);
       // g.drawRect(500,500,50,50);
    }
}
