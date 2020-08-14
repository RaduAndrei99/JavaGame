package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.UI.Menu.*;
import PaooGame.RefLinks;
import PaooGame.Sound.Music;

import java.awt.*;

/*! \class public class MenuState extends State
    \brief Implementeaza notiunea de menu pentru joc.
 */
public class MenuState extends State
{
    MenuElement mainMenu;

    MainMenuWallpaper wallpaper;
    MainMenuTitle title;

    long oldTime ;

    public MenuState(RefLinks refLink) {
        ///Apel al constructorului clasei de baza.
        super(refLink);

        mainMenu = new MainMenu(refLink);

        wallpaper = new MainMenuWallpaper();

        title = new MainMenuTitle();

        //   g.drawImage(Assets.mainMenuTitle, (int) (Toolkit.getDefaultToolkit().getScreenSize().width/2 - (0.78*Toolkit.getDefaultToolkit().getScreenSize().width/2)),(int)(0.0925*Toolkit.getDefaultToolkit().getScreenSize().height),(int)(0.78*Toolkit.getDefaultToolkit().getScreenSize().width),(int)(0.1111*Toolkit.getDefaultToolkit().getScreenSize().height), null);
    }
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniului.
     */
    @Override
    public void Update()
    {
        mainMenu.Update();

        long currentTime =  System.currentTimeMillis()/1000;
        //System.out.println(currentTime - oldTime);
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
        wallpaper.Draw(g);
        title.Draw(g);
        mainMenu.Draw(g);

        //g.drawImage(Assets.minusButton,500,500,20,20,null);
        //g.setColor(Color.GREEN);
        //g.drawRect(500,500,20,20);
    }

    public void setSettingsMenu(){

    }
}
