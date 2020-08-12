package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Hero;
import PaooGame.Maps.LevelFactory;
import PaooGame.RefLinks;
import PaooGame.Maps.Map;
import PaooGame.Sound.Music;
import PaooGame.Sound.Sound;

import java.awt.*;
import java.awt.event.KeyEvent;

/*! \class public class PlayState extends State
    \brief Implementeaza/controleaza jocul.
 */
public class PlayState extends State
{
    private Hero hero;  /*!< Referinta catre obiectul animat erou (controlat de utilizator).*/
    private Map currentMap;    /*!< Referinta catre harta curenta.*/
    private boolean isHeroDead = false;

    /*! \fn public PlayState(RefLinks refLink)
        \brief Constructorul de initializare al clasei

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public PlayState(RefLinks refLink)
    {
            ///Apel al constructorului clasei de baza
        super(refLink);
            ///Construieste harta jocului
        currentMap = LevelFactory.getMap(2,refLink);
            ///Referinta catre harta construita este setata si in obiectul shortcut pentru a fi accesibila si in alte clase ale programului.
        refLink.SetMap(currentMap);
            ///Construieste eroul
        hero = Hero.GetInstance();
        Hero.GetInstance().SetRefLink(refLink);
        Hero.GetInstance().init();
        hero.RegisterObserver(refLink.GetGame().getPathFinder());

    }

    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a jocului.
     */
    @Override
    public void Update()
    {
        if(reset){
            reset = false;
            reset();
        }


        if(isHeroDead && refLink.GetKeyManager().r) {
            isHeroDead = false;
            Hero.GetInstance().reset();
            refLink.GetGame().resetPlayState();
            refLink.GetGame().SetPlayState();
            refLink.GetGame().getPathFinder().reset();
            return;
        }

        currentMap.Update();

        hero.Update();

    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a jocului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g)
    {
        currentMap.Draw(g);
        hero.Draw(g);
        if(isHeroDead)
            g.drawImage(Assets.game_over,(int)(refLink.GetGame().GetWidth()/2 - Assets.game_over.getWidth()/2),(int)(refLink.GetGame().GetHeight()/2 - Assets.game_over.getHeight()/2),null);

    }

    @Override
    public void reset() {
        Music.stopLastSong();
        Music.playSound(Music.dungeaon_cave);
    }

    public void setHeroDead(boolean v){
        this.isHeroDead = v;
    }
}
