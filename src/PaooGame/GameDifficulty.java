package PaooGame;

public class GameDifficulty {
    protected static int NO_OF_ENEMIES_HARD = 10;
    protected static int NO_OF_ENEMIES_EASY = 2;
    protected static int NO_OF_ENEMIES_MEDIUM = 5;

    public static int current_no_of_enemies = NO_OF_ENEMIES_EASY;

    public static void setDifficultyHard(){
        current_no_of_enemies = NO_OF_ENEMIES_HARD;
    }

    public static void setDifficultyEasy(){
        current_no_of_enemies = NO_OF_ENEMIES_EASY;
    }

    public static void setDifficultyMedium(){
        current_no_of_enemies = NO_OF_ENEMIES_MEDIUM;
    }

    public static void setCurrentDifficulty(int value){
        current_no_of_enemies = value;
    }

}
