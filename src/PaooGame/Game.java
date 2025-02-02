package PaooGame;

import PaooGame.Database.GameDatabase;
import PaooGame.Database.ProxySQLiteDatabase;
import PaooGame.ErrorHandler.ErrorScreenPrinter;
import PaooGame.GameWindow.Camera;
import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Assets;
import PaooGame.Input.GameMouseListener;
import PaooGame.Input.KeyManager;
import PaooGame.Maps.PathFinderBFS;
import PaooGame.Sound.Music;
import PaooGame.Sound.Sound;
import PaooGame.States.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.*;

/*! \class Game
    \brief Clasa principala a intregului proiect. Implementeaza Game - Loop (Update -> Draw)

                ------------
                |           |
                |     ------------
    60 times/s  |     |  Update  |  -->{ actualizeaza variabile, stari, pozitii ale elementelor grafice etc.
        =       |     ------------
     16.7 ms    |           |
                |     ------------
                |     |   Draw   |  -->{ deseneaza totul pe ecran
                |     ------------
                |           |
                -------------
    Implementeaza interfata Runnable:

        public interface Runnable {
            public void run();
        }

    Interfata este utilizata pentru a crea un nou fir de executie avand ca argument clasa Game.
    Clasa Game trebuie sa aiba definita metoda "public void run()", metoda ce va fi apelata
    in noul thread(fir de executie).

    In mod obisnuit aceasta clasa trebuie sa contina urmatoarele:
        - public Game();            //constructor
        - private void init();      //metoda privata de initializare
        - private void update();    //metoda privata de actualizare a elementelor jocului
        - private void draw();      //metoda privata de desenare a tablei de joc
        - public run();             //metoda publica ce va fi apelata de noul fir de executie
        - public synchronized void start(); //metoda publica de pornire a jocului
        - public synchronized void stop()   //metoda publica de oprire a jocului
 */
public class Game implements Runnable {
    private GameWindow wnd;        /*!< Fereastra in care se va desena tabla jocului*/
    private boolean runState;   /*!< Flag ce starea firului de executie.*/
    private Thread gameThread; /*!< Referinta catre thread-ul de update si draw al ferestrei*/
    private BufferStrategy bs;         /*!< Referinta catre un mecanism cu care se organizeaza memoria complexa pentru un canvas.*/
    private Camera camera;
    /// Sunt cateva tipuri de "complex buffer strategies", scopul fiind acela de a elimina fenomenul de
    /// flickering (palpaire) a ferestrei.
    /// Modul in care va fi implementata aceasta strategie in cadrul proiectului curent va fi triplu buffer-at

    ///                         |------------------------------------------------>|
    ///                         |                                                 |
    ///                 ****************          *****************        ***************
    ///                 *              *   Show   *               *        *             *
    /// [ Ecran ] <---- * Front Buffer *  <------ * Middle Buffer * <----- * Back Buffer * <---- Draw()
    ///                 *              *          *               *        *             *
    ///                 ****************          *****************        ***************

    private Graphics g;          /*!< Referinta catre un context grafic.*/

    ///Available states
    private State playState;            /*!< Referinta catre joc.*/
    private State preGameState;         /*!< Referinta catre partea de new/load game.*/
    private State menuState;            /*!< Referinta catre menu.*/
    private State settingsState;        /*!< Referinta catre setari.*/
    private State endState;        /*!< Referinta catre end.*/
    private State aboutState;           /*!< Referinta catre about.*/

    GameDatabase database;

    private KeyManager keyManager;      /*!< Referinta catre obiectul care gestioneaza intrarile din partea utilizatorului.*/
    private GameMouseListener mouseManager;
    private RefLinks refLink;            /*!< Referinta catre un obiect a carui sarcina este doar de a retine diverse referinte pentru a fi usor accesibile.*/

    private PathFinderBFS pathFinder;

    private ErrorScreenPrinter errorPrinter;

    /*! \fn public Game(String title, int width, int height)
        \brief Constructor de initializare al clasei Game.

        Acest constructor primeste ca parametri titlul ferestrei, latimea si inaltimea
        acesteia avand in vedere ca fereastra va fi construita/creata in cadrul clasei Game.

        \param title Titlul ferestrei.
        \param width Latimea ferestrei in pixeli.
        \param height Inaltimea ferestrei in pixeli.
     */



    public Game(String title, int width, int height) {
        /// Obiectul GameWindow este creat insa fereastra nu este construita
        /// Acest lucru va fi realizat in metoda init() prin apelul
        /// functiei BuildGameWindow();
        wnd = new GameWindow(title, width, height);
        /// Resetarea flagului runState ce indica starea firului de executie (started/stoped)
        runState = false;
        ///Construirea obiectului de gestiune a evenimentelor de tastatura
        keyManager = new KeyManager();
    }

    /*! \fn private void init()
        \brief  Metoda construieste fereastra jocului, initializeaza aseturile, listenerul de tastatura etc.

        Fereastra jocului va fi construita prin apelul functiei BuildGameWindow();
        Sunt construite elementele grafice (assets): dale, player, elemente active si pasive.

     */
    private void InitGame() {
        errorPrinter = new ErrorScreenPrinter();
        /// Este construita fereastra grafica.
        wnd.BuildGameWindow();
        ///Sa ataseaza ferestrei managerul de tastatura pentru a primi evenimentele furnizate de fereastra.
        ///Se incarca toate elementele grafice (dale)
        Assets.Init();
        Sound.Init();
        Music.Init();
        ///Se construieste obiectul de tip shortcut ce va retine o serie de referinte catre elementele importante din program.
        refLink = new RefLinks(this);
        mouseManager = new GameMouseListener(refLink);

        wnd.GetCanvas().addKeyListener(keyManager);
        wnd.GetCanvas().addMouseListener(mouseManager);
        wnd.GetCanvas().addMouseWheelListener(mouseManager);


        camera = new Camera(this.refLink, (int)(0.11 *Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.22 * Toolkit.getDefaultToolkit().getScreenSize().height), (int)(0.78 *  Toolkit.getDefaultToolkit().getScreenSize().width), (int)(0.5 *  Toolkit.getDefaultToolkit().getScreenSize().height));

        pathFinder = new PathFinderBFS(refLink);

        ///Definirea starilor programului
        playState = new PlayState(refLink);
        menuState = new MenuState(refLink);
        settingsState = new SettingsState(refLink);
        aboutState = new AboutState(refLink);
        preGameState = new PreGameState(refLink);
        endState = new EndState(refLink);
        ///Seteaza starea implicita cu care va fi lansat programul in executie
        State.SetState(menuState);

        database =  new ProxySQLiteDatabase("gameDatabase.db", refLink);
        database.loadDatabase();
        database.loadSettings();
    }

    /*! \fn public void run()
        \brief Functia ce va rula in thread-ul creat.

        Aceasta functie va actualiza starea jocului si va redesena tabla de joc (va actualiza fereastra grafica)
     */
    public void run() {
        /// Initializeaza obiectul game
        InitGame();
        long oldTime = System.nanoTime();   /*!< Retine timpul in nanosecunde aferent frame-ului anterior.*/
        long curentTime;                    /*!< Retine timpul curent de executie.*/

        /// Apelul functiilor Update() & Draw() trebuie realizat la fiecare 16.7 ms
        /// sau mai bine spus de 60 ori pe secunda.

        final int framesPerSecond = 60; /*!< Constanta intreaga initializata cu numarul de frame-uri pe secunda.*/
        final double timeFrame = 1000000000 / framesPerSecond; /*!< Durata unui frame in nanosecunde.*/

        /// Atat timp timp cat threadul este pornit Update() & Draw()
        while (runState) {
            /// Se obtine timpul curent
            curentTime = System.nanoTime();
            /// Daca diferenta de timp dintre curentTime si oldTime mai mare decat 16.6 ms
            if ((curentTime - oldTime) > timeFrame) {

                /// Actualizeaza pozitiile elementelor
                try{
                    Update();
                }catch (Exception e){
                    ErrorScreenPrinter.addErrorMessage("Error updating the game! - " + e.getMessage());
                }

                /// Deseneaza elementele grafica in fereastra.
                try {
                    Draw();
                }catch (Exception e){
                    ErrorScreenPrinter.addErrorMessage("Error displaying the graphics! - " + e.getMessage());
                }

                oldTime = curentTime;

            }
        }

    }

    /*! \fn public synchronized void start()
        \brief Creaza si starteaza firul separat de executie (thread).

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StartGame() {
        if (!runState) {
            /// Se actualizeaza flagul de stare a threadului
            runState = true;
            /// Se construieste threadul avand ca parametru obiectul Game. De retinut faptul ca Game class
            /// implementeaza interfata Runnable. Threadul creat va executa functia run() suprascrisa in clasa Game.
            gameThread = new Thread(this);
            /// Threadul creat este lansat in executie (va executa metoda run())
            gameThread.start();
        }
    }

    /*! \fn public synchronized void stop()
        \brief Opreste executie thread-ului.

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StopGame() {
        if (runState) {
            /// Actualizare stare thread
            runState = false;




            /// Metoda join() arunca exceptii motiv pentru care trebuie incadrata intr-un block try - catch.
            try {
                /// Metoda join() pune un thread in asteptare panca cand un altul isi termina executie.
                /// Totusi, in situatia de fata efectul apelului este de oprire a threadului.
                Music.stopLastSong();
                System.exit(0);

                gameThread.join();

            }
            catch(InterruptedException ex)
            {
                    /// In situatia in care apare o exceptie pe ecran vor fi afisate informatii utile pentru depanare.
                ex.printStackTrace();
            }
        }

    }

    /*! \fn private void Update()
        \brief Actualizeaza starea elementelor din joc.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Update() {
        //wnd.GetWndFrame().requestFocus();
        wnd.GetCanvas().requestFocus();
        ///Determina starea tastelor
        keyManager.Update();
        ///Trebuie obtinuta starea curenta pentru care urmeaza a se actualiza starea, atentie trebuie sa fie diferita de null.
        if (State.GetState() != null) {
            ///Actualizez starea curenta a jocului daca exista.

            State.GetState().Update();
        }
    }

    /*! \fn private void Draw()
        \brief Deseneaza elementele grafice in fereastra coresponzator starilor actualizate ale elementelor.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Draw() {
        /// Returnez bufferStrategy pentru canvasul existent
        bs = wnd.GetCanvas().getBufferStrategy();
        /// Verific daca buffer strategy a fost construit sau nu
        if (bs == null) {
            /// Se executa doar la primul apel al metodei Draw()
            try {
                /// Se construieste tripul buffer
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            } catch (Exception e) {
                /// Afisez informatii despre problema aparuta pentru depanare.
                e.printStackTrace();
            }
        }
        /// Se obtine contextul grafic curent in care se poate desena.
        g = bs.getDrawGraphics();
        /// Se sterge ce era
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());

        /// operatie de desenare
        ///Trebuie obtinuta starea curenta pentru care urmeaza a se actualiza starea, atentie trebuie sa fie diferita de null.
        if (State.GetState() != null) {
            ///Actualizez starea curenta a jocului daca exista

            State.GetState().Draw(g);
            camera.draw(g);

            errorPrinter.Draw(g);

        }
        /// end operatie de desenare

        /// Se afiseaza pe ecran
        bs.show();

        /// Elibereaza resursele de memorie aferente contextului grafic curent (zonele de memorie ocupate de
        /// elementele grafice ce au fost desenate pe canvas).
        g.dispose();
    }

    /*! \fn public int GetWidth()
        \brief Returneaza latimea ferestrei
     */
    public int GetWidth() {
        return wnd.GetWndWidth();
    }

    /*! \fn public int GetHeight()
        \brief Returneaza inaltimea ferestrei
     */
    public int GetHeight() {
        return wnd.GetWndHeight();
    }

    /*! \fn public KeyManager GetKeyManager()
        \brief Returneaza obiectul care gestioneaza tastatura.
     */
    public KeyManager GetKeyManager() {
        return keyManager;
    }

    public GameWindow GetGameWindow() {
        return this.wnd;
    }

    public void SetPlayState() {
        State.SetState(playState);

    }

    public void SetMenuState() {
        State.SetState(menuState);

    }

    public void SetSettingsState() {
        State.SetState(settingsState);
    }

    public void SetPreGameState() {
        State.SetState(preGameState);
    }

    public PathFinderBFS getPathFinder() {
        return this.pathFinder;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public State getPlayState() {
        return playState;
    }

    public GameDatabase getDatabase(){
        return database;
    }

    public void resetRunState(){
        runState = false;
    }

    public void setEndState(){
        State.SetState(endState);

    }
}
