package PaooGame.Database;

import PaooGame.Game;
import PaooGame.RefLinks;

public abstract class GameDatabase {
    protected boolean canYouSaveGame = false;  /*!< Retine daca se poate sau nu salva jocul la un moment dat in baza de date*/
    protected boolean canYouLoadGame = false; /*!< Retine daca se poate incarca o salvare*/
    protected boolean canYouLoadSettings = false; /*!< Retine daca se pot incarca setarile */


    protected String fileName; /*!<Retine numele bazei de date */

    protected String url ; /*!< Retine path-ul catre fisier */
    protected String databaseFolder ; /*!< Retine folder-ul in care se afla baza de date */

    protected RefLinks refs; /*!< Referinta cu ajutorul careia se pot accesa mai usor elementele jocului */


    /*! \fn GameDatabase(RefLinks refs)
        \brief Constructor de initializare al clasei abstracte Database.

        Acest constructor primeste ca parametru o referinta catre un obiect de tip RefLinks

        \param refs referinta catre un obiect RefLinks

     */

    GameDatabase(RefLinks refs){
        this.refs = refs;
    }


    public abstract void saveGame(Game game);
    public abstract void loadGame();

    public abstract void saveSettings( );
    public abstract void loadSettings();

    public boolean isSaveAvailable(){
        return canYouSaveGame;
    }
    public boolean isLoadAvailable(){
        return canYouLoadGame;
    }

    public abstract void loadDatabase();

}
