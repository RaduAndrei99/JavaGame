package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Hero;
import PaooGame.Items.Weapons.Excalibur;
import PaooGame.RefLinks;
import PaooGame.Maps.Map;
import PaooGame.Sound.Music;
import PaooGame.UI.Scoreboard;

import java.awt.*;
import java.awt.image.BufferedImage;

/*! \class public class PlayState extends State
    \brief Implementeaza/controleaza jocul.
 */
public class PlayState extends State
{
    private final static int NO_OF_LEVELS = 4;


    private final Hero hero;  /*!< Referinta catre obiectul animat erou (controlat de utilizator).*/
    private final Map currentMap;    /*!< Referinta catre harta curenta.*/
    private final Scoreboard scoreboard;

    private final BufferedImage []levelStartText;

    protected long timeElapsed; //variabila ce ajuta la masurarea timpului scurs

    static boolean levelHasStarted = true;

    public static int current_level = 1;
    /*! \fn public PlayState(RefLinks refLink)
        \brief Constructorul de initializare al clasei

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */

    public PlayState(RefLinks refLink)
    {
            ///Apel al constructorului clasei de baza
        super(refLink);
            ///Construieste harta jocului
        currentMap = new Map(refLink);
            ///Referinta catre harta construita este setata si in obiectul shortcut pentru a fi accesibila si in alte clase ale programului.
        refLink.SetMap(currentMap);
            ///Construieste eroul
        hero = Hero.GetInstance();
        Hero.GetInstance().SetRefLink(refLink);
        Hero.GetInstance().init();
        hero.RegisterObserver(refLink.GetGame().getPathFinder());

        scoreboard = new Scoreboard(refLink, 1600,20);
        levelStartText = new BufferedImage[NO_OF_LEVELS];

        levelStartText[0] = Assets.level1_text;
        levelStartText[1] = Assets.level2_text;
        levelStartText[2] = Assets.level3_text;
        levelStartText[3] = Assets.level4_text;
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

        if(Hero.GetInstance().getHeldItem() instanceof Excalibur){
            refLink.GetGame().setEndState();
            return;
        }

        if(Hero.GetInstance().isHeroDead() && refLink.GetKeyManager().r) {
            Hero.GetInstance().resetOnDeath();
            refLink.GetMap().resetRoom();
            Music.stopLastSong();
            Music.playSound(Music.level1_clip);
            levelHasStarted = true;
            return;
        }

        currentMap.Update();

        hero.Update();

        if (refLink.GetKeyManager().esc) {
            refLink.GetGame().SetPreGameState();
        }

        if(levelHasStarted){
            timeElapsed = System.currentTimeMillis()/1000;
            levelHasStarted = false;
        }


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

        scoreboard.Draw(g);

        if(Hero.GetInstance().isHeroDead())
            g.drawImage(Assets.game_over, refLink.GetGame().GetWidth()/2 - Assets.game_over.getWidth()/2, refLink.GetGame().GetHeight()/2 - Assets.game_over.getHeight()/2,null);

        if(System.currentTimeMillis()/1000 - timeElapsed < 5)
            g.drawImage(levelStartText[current_level - 1],Toolkit.getDefaultToolkit().getScreenSize().width/2 - levelStartText[current_level - 1].getWidth()/2,
                    Toolkit.getDefaultToolkit().getScreenSize().height/2 - levelStartText[current_level- 1].getHeight()/2,
                        levelStartText[current_level - 1].getWidth(),levelStartText[current_level - 1].getHeight(), null );
    }

    @Override
    public void reset() {
        if(current_level + 1 <= NO_OF_LEVELS ) {
            Music.stopLastSong();
            Music.playSound(Music.level1_clip);
            levelHasStarted = true;
            current_level = 1;
        }
    }

    public static void signalLevelHasStarted(){
        levelHasStarted = true;
    }

    public static void selectNextLevel(){
        if(current_level < NO_OF_LEVELS){
            current_level++;
        }
    }

}
