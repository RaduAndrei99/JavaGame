package PaooGame.States;

import PaooGame.RefLinks;
import PaooGame.UI.Menu.SettingsMenu;
import PaooGame.UI.Menu.Wallpapers.SettingsWallpaper;

import java.awt.*;

/*! \class public class SettingsState extends State
    \brief Implementeaza notiunea de settings pentru joc.

    Aici setarile vor trebui salvate/incarcate intr-un/dintr-un fisier/baza de date sqlite.
 */
public class SettingsState extends State
{
    SettingsMenu settingsMenu;

    SettingsWallpaper wallpaper;


    /*! \fn public SettingsState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public SettingsState(RefLinks refLink)
    {
            ///Apel al construcotrului clasei de baza.
        super(refLink);

        settingsMenu = new SettingsMenu(refLink);

        wallpaper = new SettingsWallpaper();
    }

    /*! \fn public void Update()
        \brief Actualizeaza starea setarilor.
     */
    @Override
    public void Update()
    {
        settingsMenu.Update();
    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran setarile.

        \param g Contextul grafic in care trebuie sa deseneze starea setarilor pe ecran.
     */
    @Override
    public void Draw(Graphics g)
    {
        wallpaper.Draw(g);

        settingsMenu.Draw(g);

    }

    @Override
    public void reset() {

    }
}
